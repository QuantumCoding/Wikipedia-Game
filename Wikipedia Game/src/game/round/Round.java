package game.round;

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
		if(server.getProprties().getNoResponseAutoReadyTime() > 0 && readyCount > 0 && forceGoThread == null) {
			forceGoThread = new Thread(() -> {
				try { 
					server.sendMessage(Communication.AUTO_READY_START + server.getProprties().getNoResponseAutoReadyTime());
					Thread.sleep(server.getProprties().getNoResponseAutoReadyTime());
					if(readyCount <= 0) return;
					readyCount = server.getPlayerCount();
					checkReady();
				} catch(InterruptedException e) {}
				
			}, "Auto-Start Round Thread");
			
			forceGoThread.start();
		
		} else if(server.getProprties().getNoResponseAutoReadyTime() > 0 && readyCount <= 0 && forceGoThread != null) {
			forceGoThread.interrupt();
			forceGoThread = null;
			
			server.sendMessage(Communication.STOP_READY_START);
		}
		
		if(ready) server.sendMessage(Communication.PLAYER_READY   + connection.getUsername());
		else      server.sendMessage(Communication.PLAYER_UNREADY + connection.getUsername());
		
		checkReady();
	}
	
	public void checkReady() {
		float readyPerc = (float) readyCount / server.getPlayerCount();
		server.sendMessage(Communication.PLAYERS_READY_NEEDED + 
				(int) Math.ceil(server.getPlayerCount() *  server.getProprties().getReadyPercenatge()));
		server.sendMessage(Communication.PLAYERS_READY_RECALC + (int)(readyPerc * 100));
		
		if(server.getProprties().getReadyPercenatge() <= readyPerc)
			start();
	}
	
	public void readyGame() {
		sendRound(); 
		server.sendMessage(Communication.READY_ROUND);
		checkReady();
	}
	
	public void start() {
		if(forceGoThread != null) {
			readyCount = 0;
			forceGoThread.interrupt();
			forceGoThread = null;
			
			server.sendMessage(Communication.STOP_READY_START);
		}
		
		isStarted = true;
		server.nextRoundStated();
		playerCount = server.getPlayerCount();
		
		for(int i = server.getProprties().getCountDown(); i > 0; i --) {
			server.sendMessage(Communication.COUNT_DOWN_ROUND + i);
			try { Thread.sleep(2000); } catch(InterruptedException e) {}
		}
		
		try { Thread.sleep(500); } catch(InterruptedException e) {}
		server.sendMessage(Communication.START_ROUND);
	}
	
	public boolean isStarted() { return isStarted; }
	public int getPlayerCount() { return playerCount; }
}
