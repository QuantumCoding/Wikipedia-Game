package game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import game.structure.Finishing;
import game.structure.Round;
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
	}
	
	public void clientConnected(ClientConnection connection) throws IOException {
		sendMessage(Communication.PLAYER_CONNECTED + connection.getUsername());
		
		if(currentRound.isStarted()) {
			sendMessage(Communication.PLAYER_SPECTATING + connection.getUsername());
			spectiating.add(connection);
		} else {
			sendMessage(Communication.PLAYER_PLAYING + connection.getUsername());
			playing.add(connection);
		}
	}

	public void clientDisconnected(ClientConnection connection) throws IOException {
		sendMessage(Communication.PLAYER_DISCONNECTED + connection.getUsername());
		spectiating.remove(connection);
		playing.remove(connection);
	}
	
	public void sendMessage(String message) {
		for(ClientConnection conn : server.getConnections()) {
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
	
	public void roundStart() { this.currentRound = nextRound; nextRound = null; finingOrder.clear(); }
	
	public void prepNextRound(String startPage) { prepNextRound(new Round(this, startPage)); }
	public void prepNextRound(Round nextRound) { this.nextRound = nextRound; }
	
	public void addNewDestinination(String destination) {
		nextRound.addDestination(destination);
	}
	
	public ServerProperties getProprties() { return properties; }
	public int getConnectionCount() { return server.getConnectedCount(); }
	public int getPlayerCount() { return playing.size(); }
}
