package game.round;

import game.Communication;
import game.GameServer;
import networking.server.ClientConnection;

public class Round {
	private GameServer server;

	private RoundModel model;
	private boolean isStarted;
	private int playerCount;
	
	private int readyCount;
	private Thread forceGoThread;
	
	public Round(GameServer server, RoundModel model) {
		this.server = server;
		this.model = model;
	}
	
	public Round(GameServer server, String startingPoint) {
		this.server = server;
		this.model = new RoundModel();
		this.model.addDestination(startingPoint);
	}
	
	public void addDestination(String destination) { model.addDestination(destination); }
	
	public void playerReadyChange(ClientConnection connection, boolean ready) {
		readyCount += ready ? 1 : -1; if(readyCount < 0) readyCount = 0;
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
				(int) Math.ceil(server.getPlayerCount() *  server.getProprties().getReadyPercentage()));
		server.sendMessage(Communication.PLAYERS_READY_RECALC + (int)(readyPerc * 100));
		
		if(server.getProprties().getReadyPercentage() <= readyPerc)
			start();
	}
	
	public void readyGame() {
		if(!model.isValid())
			throw new IllegalStateException("Round Model is not Valid! Make Sure Round has enough Desinations");
		
		sendRound(); 
		server.sendMessage(Communication.ROUND_READY);
		checkReady();
	}
	
	private void sendRound() {
		server.sendMessage(Communication.NEW_ROUND);
		server.sendMessage(Communication.SET_START + model.getStartingLocation());
		
		for(String destination : model.getLocations())
			server.sendMessage(Communication.ADD_DESTINATION + destination);
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
		
		for(int i = server.getProprties().getCountDownAmount(); i > 0; i --) {
			server.sendMessage(Communication.COUNT_DOWN_ROUND + i);
			try { Thread.sleep(2000); } catch(InterruptedException e) {}
		}
		
		try { Thread.sleep(500); } catch(InterruptedException e) {}
		server.sendMessage(Communication.START_ROUND);
	}
	
	public RoundModel getModel() { return model; }
	public boolean isStarted() { return isStarted; }
	public int getPlayerCount() { return playerCount; }
}
