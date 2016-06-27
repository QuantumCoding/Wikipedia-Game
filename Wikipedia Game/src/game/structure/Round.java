package game.structure;

import java.util.ArrayList;

import game.Communication;
import game.GameServer;
import networking.server.ClientConnection;

public class Round {
	private GameServer server;
	
	private String startingPoint;
	private ArrayList<String> destinations;
	private boolean isStarted;
	private int playerCount;
	
	private int readyCount;
	private Thread forceGoThread;
	
	public Round(GameServer server, String startingPoint) {
		this.server = server;
		this.startingPoint = startingPoint;
		this.destinations = new ArrayList<>();
	}
	
	public void addDestination(String destination) {
		destinations.add(destination);
	}
	
	public void sendRound() {
		server.sendMessage(Communication.NEW_ROUND);
		server.sendMessage(Communication.SET_START + startingPoint);
		
		for(String destination : destinations)
			server.sendMessage(Communication.ADD_DESTINATION + destination);
	}
	
	public void playerReadyChange(ClientConnection connection, boolean ready) {
		readyCount += ready ? 1 : -1;
		if(server.getProprties().getNoResponseAutoReadyTime() > 0 && readyCount > 0) {
			forceGoThread = new Thread(() -> {
				try { 
					Thread.sleep(server.getProprties().getNoResponseAutoReadyTime());
					if(readyCount <= 0) return;
					readyCount = server.getConnectionCount();
					checkReady();
				} catch(InterruptedException e) {}
				
			}, "Auto-Start Round Thread");
			forceGoThread.start();
		
		} else if(server.getProprties().getNoResponseAutoReadyTime() > 0 && forceGoThread != null) {
			forceGoThread.interrupt();
			forceGoThread = null;
		}
		
		if(ready) server.sendMessage(Communication.PLAYER_READY   + connection.getUsername());
		else      server.sendMessage(Communication.PLAYER_UNREADY + connection.getUsername());
		
		checkReady();
	}
	
	private void checkReady() {
		if(server.getProprties().getReadyPercenatge() <= readyCount / server.getConnectionCount())
			start();
	}
	
	public void readyGame() {
		sendRound();
		server.sendMessage(Communication.READY_ROUND);
	}
	
	public void start() {
		isStarted = true;
		server.roundStart();
		playerCount = server.getPlayerCount();
		
		for(int i = server.getProprties().getCountDown(); i > 0; i --) {
			server.sendMessage(Communication.COUNT_DOWN_ROUND + i);
			try { Thread.sleep(1000); } catch(InterruptedException e) {}
		}
		
		server.sendMessage(Communication.START_ROUND);
	}
	
	public boolean isStarted() { return isStarted; }
	public int getPlayerCount() { return playerCount; }
}
