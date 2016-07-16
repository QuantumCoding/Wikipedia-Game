package game;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;

import game.round.Finishing;
import game.round.Round;
import game.round.RoundModel;
import networking.server.ClientConnection;
import networking.server.IClientConnectedListener;
import networking.server.IServerMessageProcesser;
import networking.server.Server;
import window.startup.ServerSetup;

public class GameServer implements IServerMessageProcesser, IClientConnectedListener {
	private Server server;
	private ServerSetup setupView;
	private ServerProperties properties;
	
	private Round currentRound;
	private Round nextRound;
	
	private int pauseAcceptedCount;

	private HashSet<ClientConnection> done;
	private HashSet<ClientConnection> playing;
	private HashSet<ClientConnection> spectiating;
	
	private ArrayList<Finishing> finingOrder;
	private boolean roundOver;
	
	public GameServer(InetAddress address, int port, int count) throws IOException {
		server = new Server(address, port, count);
		properties = new ServerProperties();
		
		done = new HashSet<>();
		playing = new HashSet<>();
		spectiating = new HashSet<>();
		finingOrder = new ArrayList<>();
		
//		currentRound = new Round(this, "NuLl");
		
		server.addConnectionListener(this);
		server.addMessageProcesser(this);
		
		server.connect();
		
		setupView = new ServerSetup(this);
		setupView.setVisible(true);
		
		roundOver = true;
	}
	
	public void process(ClientConnection connection, String message) throws IOException {
		if(message.equals(Communication.REQUEST_PAUSE)) {
			if(spectiating.contains(connection)) return;
			
			pauseAcceptedCount = 1;
			sendMessageToPlayers(Communication.REQUEST_PAUSE, connection);
			checkPause();
				
			return;
		}

		if(message.equals(Communication.UNPAUSE)) { pauseAcceptedCount ++; checkUnpause(); return; }
		if(message.equals(Communication.PAUSE_ACCEPT)) { pauseAcceptedCount ++; checkPause(); return; }
		if(message.equals(Communication.PAUSE_REJECTED)) { return; }
		
		if(message.equals(Communication.PLAYER_READY)) {
			sendMessage(Communication.PLAYER_READY + connection.getUsername());
			if(nextRound != null)
				nextRound.playerReadyChange(connection, true);
			return;
		}
		
		if(message.equals(Communication.PLAYER_UNREADY)) {
			sendMessage(Communication.PLAYER_UNREADY + connection.getUsername());
			
			if(spectiating.contains(connection)) {
				spectiating.remove(connection);
				playing.add(connection);
				if(nextRound != null)
					nextRound.checkReady();
			} else if(nextRound != null)
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
		
		if(message.startsWith(Communication.PLAYER_DONE)) {
			Finishing finishing = Finishing.load(this, message, connection);
			sendMessage(Communication.PLAYER_DONE + finishing);
			playing.remove(connection);
			done.add(connection);
			
			updatePlayerRanks(finishing);
			return;
		}
		
		if(message.equals(Communication.PLAYER_QUIT)) {
			Finishing finishing = Finishing.load(this, "" + DataPassing.DATA_SPLIT + 
					Long.MAX_VALUE + DataPassing.DATA_SPLIT + 99999, connection);
			sendMessage(Communication.PLAYER_QUIT + finishing);
			playing.remove(connection);
			done.add(connection);
			
			updatePlayerRanks(finishing);
			return;
		}
		
		System.err.println("Raw Data from \"" + connection.getUsername() + "\": " + message);
	}
	
	private void updatePlayerRanks(Finishing finishing) {
		finingOrder.add(finishing);
		finingOrder.sort(null);
		
		if(done.size() >= currentRound.getPlayerCount() - (properties.lettingAllPlayersFinish() ? 0 : 1) && !roundOver) {
			if(done.isEmpty()) return; roundOver = true;
			sendMessage(Communication.PLAYER_WON + finingOrder.get(0).getConnection().getUsername());
			
			RoundModel model = RoundModel.getSelectedModel();
			if(model == null) ; // Notify Clients that server is creating round
			else { prepNextRound(model.creatRound(this)); readyNextRound(); }
		}
		
		int rank = 0;
		for(Finishing fin : finingOrder) {
			sendMessage(Communication.PLAYER_RANK + fin.getConnection().getUsername() + 
					DataPassing.DATA_SPLIT + rank ++);
		}
	}
	
	private void checkUnpause() {
		float readyPerc = (float) pauseAcceptedCount / playing.size();
		sendMessageToPlayers(Communication.PAUSE_ACCEPTANCE_NEED + 
				(int) Math.ceil(playing.size() *  properties.getUnpausePercenatge()));
		sendMessageToPlayers(Communication.PAUSE_ACCEPTANCE_HAVE + (int)(readyPerc * 100));
		
		if(properties.getUnpausePercenatge() <= readyPerc) {
			sendMessageToPlayers(Communication.TOGGLE_PAUSE);
			pauseAcceptedCount = 0;
		}
	}
	
	private void checkPause() {
		float readyPerc = (float) pauseAcceptedCount / playing.size();
		sendMessageToPlayers(Communication.PAUSE_ACCEPTANCE_NEED + 
				(int) Math.ceil(playing.size() *  properties.getPausePercenatge()));
		sendMessageToPlayers(Communication.PAUSE_ACCEPTANCE_HAVE + (int)(readyPerc * 100));
		
		if(properties.getPausePercenatge() <= readyPerc) {
			sendMessageToPlayers(Communication.TOGGLE_PAUSE);
			pauseAcceptedCount = 0;
			
			checkUnpause();
		}
	}
	
	public void clientConnected(ClientConnection connection) throws IOException {
		System.out.println("Player Joined = " + connection.getUsername());
		connection.sendMessage(Communication.ALLOW_JUMP_BACK + properties.allowsJumpBack());
		sendMessage(Communication.PLAYER_CONNECTED + connection.getUsername());
		
		for(ClientConnection conn : server.getConnections()) {
			if(conn == null || !conn.isRunning() || conn == connection) continue;
			connection.sendMessage(Communication.PLAYER_CONNECTED + conn.getUsername());
			connection.sendMessage(
					(spectiating.contains(conn) ? Communication.PLAYER_SPECTATING : Communication.PLAYER_PLAYING)
					+ conn.getUsername());
		}
		
		if(currentRound == null || !currentRound.isStarted()) {
			sendMessage(Communication.PLAYER_PLAYING + connection.getUsername());
			playing.add(connection);
		} else {
			sendMessage(Communication.PLAYER_SPECTATING + connection.getUsername());
			spectiating.add(connection);
		}
	}

	public void clientDisconnected(ClientConnection connection) throws IOException {
		System.out.println("Player_Left = " + connection.getUsername());
		sendMessage(Communication.PLAYER_DISCONNECTED + connection.getUsername());
		spectiating.remove(connection);
		playing.remove(connection);
	}
	
	public void sendMessageToPlayers(String message) {
		for(ClientConnection conn : playing) {
			if(conn == null || !conn.isRunning()) continue;
			try { conn.sendMessage(message);
			} catch(IOException e) { e.printStackTrace(); }
		}
	}
	
	public void sendMessage(String message) {
		for(ClientConnection conn : server.getConnections()) {
			if(conn == null || !conn.isRunning()) continue;
			try { conn.sendMessage(message);
			} catch(IOException e) { e.printStackTrace(); }
		}
	}
	
	public void sendMessageToPlayers(String message, ClientConnection... exclude) {
		skip:
		for(ClientConnection conn : playing) {
			for(ClientConnection connection : exclude)
				if(connection == conn) continue skip;
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
			if(conn == null || !conn.isRunning()) continue;
			try { conn.sendMessage(message);
			} catch(IOException e) { e.printStackTrace(); }
		}
	} 
	
	public void readyNextRound() { (currentRound = nextRound).readyGame(); }
	public void startRound() { currentRound.start(); }
	
	public void nextRoundStated() { 
		this.currentRound = nextRound; 
		nextRound = null; 
		roundOver = false;
		finingOrder.clear(); 
	}
	
	public void prepNextRound(String startPage) { prepNextRound(new Round(this, startPage)); }
	public void prepNextRound(Round nextRound) { 
		this.nextRound = nextRound; 
		playing.addAll(done);
		playing.addAll(spectiating);
		spectiating.clear();
		done.clear();
		
		finingOrder.clear(); 
		
		for(ClientConnection connection : server.getConnections()) {
			if(connection == null || !connection.isRunning()) continue;
			sendMessage(Communication.PLAYER_PLAYING + connection.getUsername());
		}
	}
	
	public void addNewDestinination(String destination) {
		if(nextRound == null) 
			prepNextRound(destination);
		else
			nextRound.addDestination(destination);

		if(currentRound == null)
			currentRound = nextRound;
	}
	
	public void stop() throws IOException { server.close(); }
	
	public ServerProperties getProprties() { return properties; }
	public int getConnectionCount() { return server.getConnectedCount(); }
	public int getPlayerCount() { return playing.size(); }
	
	public Server getServer() { return server; } 
	public boolean isRoundOver() { return roundOver; }
}
