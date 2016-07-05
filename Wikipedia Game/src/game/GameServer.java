package game;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;

import game.round.Finishing;
import game.round.Round;
import networking.server.ClientConnection;
import networking.server.IClientConnectedListener;
import networking.server.IServerMessageProcesser;
import networking.server.Server;

public class GameServer implements IServerMessageProcesser, IClientConnectedListener {
	private Server server;
	private ServerProperties properties;
	
	private Round currentRound;
	private Round nextRound;
	
	private int pauseAcceptedCount;
	
	private HashSet<ClientConnection> playing;
	private HashSet<ClientConnection> spectiating;
	
	private ArrayList<Finishing> finingOrder;
	
	public GameServer(InetAddress address, int port, int count) throws IOException {
		server = new Server(address, port, count);
		properties = new ServerProperties();
		
		playing = new HashSet<>();
		spectiating = new HashSet<>();
		finingOrder = new ArrayList<>();
		
		currentRound = new Round(this, "NuLl");
		
		server.addConnectionListener(this);
		server.addMessageProcesser(this);
		
		server.connect();
	}
	
	public void process(ClientConnection connection, String message) throws IOException {
		if(message.equals(Communication.REQUEST_PAUSE)) {
			pauseAcceptedCount = 0;
			sendMessage(Communication.REQUEST_PAUSE, connection);
			
			return;
		}
		
		if(message.equals(Communication.PAUSE_ACCEPT)) {
			pauseAcceptedCount ++;
			if(properties.getPausePercenatge() <= pauseAcceptedCount / currentRound.getPlayerCount()) {
				sendMessage(Communication.TOOGLE_PAUSE);
				pauseAcceptedCount = 0;
			}
			
			return;
		}
		
		if(message.equals(Communication.PLAYER_READY)) {
			sendMessage(Communication.PLAYER_READY + connection.getUsername());
			nextRound.playerReadyChange(connection, true);
			return;
		}
		
		if(message.equals(Communication.PLAYER_UNREADY)) {
			sendMessage(Communication.PLAYER_UNREADY + connection.getUsername());
			
			if(spectiating.contains(connection)) {
				spectiating.remove(connection);
				playing.add(connection);
				nextRound.checkReady();
			} else
				nextRound.playerReadyChange(connection, false);
		
			
			return;
		}
		
		if(message.equals(Communication.PLAYER_PLAYING)) {
			sendMessage(Communication.PLAYER_PLAYING + connection.getUsername());
			spectiating.remove(connection);
			playing.add(connection);
			return;
		}
		
		if(message.equals(Communication.PLAYER_SPECTATING)) {
			sendMessage(Communication.PLAYER_SPECTATING + connection.getUsername());
			playing.remove(connection);
			spectiating.add(connection);
			nextRound.checkReady();
			return;
		}
		
		if(message.equals(Communication.PLAYER_DONE)) {
			Finishing finishing = Finishing.load(message, connection);
			sendMessage(Communication.PLAYER_DONE + finishing);
			finingOrder.add(finishing);
			return;
		}
		
		if(message.equals(Communication.PLAYER_QUIT)) {
			Finishing finishing = Finishing.load(Long.MAX_VALUE + DataPassing.DATA_SPLIT + Integer.MAX_VALUE, connection);
			sendMessage(Communication.PLAYER_QUIT);
			finingOrder.add(finishing);
			return;
		}
		
		System.err.println("Raw Data from \"" + connection.getUsername() + "\": " + message);
	}
	
	public void clientConnected(ClientConnection connection) throws IOException {
		System.out.println("Player Joined = " + connection.getUsername());
		sendMessage(Communication.PLAYER_CONNECTED + connection.getUsername());
		
		for(ClientConnection conn : server.getConnections()) {
			if(conn == null || !conn.isRunning() || conn == connection) continue;
			connection.sendMessage(
					(spectiating.contains(conn) ? Communication.PLAYER_SPECTATING : Communication.PLAYER_PLAYING)
					+ conn.getUsername());
		}
		
		if(currentRound.isStarted()) {
			sendMessage(Communication.PLAYER_SPECTATING + connection.getUsername());
			spectiating.add(connection);
		} else {
			sendMessage(Communication.PLAYER_PLAYING + connection.getUsername());
			playing.add(connection);
		}
	}

	public void clientDisconnected(ClientConnection connection) throws IOException {
		System.out.println("Player_Left = " + connection.getUsername());
		sendMessage(Communication.PLAYER_DISCONNECTED + connection.getUsername());
		spectiating.remove(connection);
		playing.remove(connection);
	}
	
	public void sendMessage(String message) {
		for(ClientConnection conn : server.getConnections()) {
			if(conn == null || !conn.isRunning()) continue;
			try { conn.sendMessage(message);
			} catch(IOException e) { e.printStackTrace(); }
		}
	}
	
	public void sendMessage(String message, ClientConnection... exclude) {
		skip:
		for(ClientConnection conn : server.getConnections()) {
			for(ClientConnection connection : exclude)
				if(connection == conn) continue skip;
			try { conn.sendMessage(message);
			} catch(IOException e) { e.printStackTrace(); }
		}
	} 
	
	public void readyNextRound() { currentRound.readyGame(); }
	public void startRound() { currentRound.start(); }
	
	public void nextRoundStated() { 
		this.currentRound = nextRound; 
		nextRound = null; 
		finingOrder.clear(); 
	}
	
	public void prepNextRound(String startPage) { prepNextRound(new Round(this, startPage)); }
	public void prepNextRound(Round nextRound) { this.nextRound = nextRound; }
	
	public void addNewDestinination(String destination) {
		if(nextRound == null) 
			nextRound = new Round(this, destination); //TODO: Proper nextRound Creation
		else
			nextRound.addDestination(destination);
	}
	
	public void stop() throws IOException { server.close(); }
	
	public ServerProperties getProprties() { return properties; }
	public int getConnectionCount() { return server.getConnectedCount(); }
	public int getPlayerCount() { return playing.size(); }
}
