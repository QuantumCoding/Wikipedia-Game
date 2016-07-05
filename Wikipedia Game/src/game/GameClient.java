package game;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import javax.swing.JPanel;

import game.round.ReadyPanel;
import game.structure.ILogger;
import game.structure.LogType;
import interfaces.IClientInterface;
import interfaces.IClientView;
import interfaces.IWebBrowser;
import networking.client.Client;
import networking.client.IClientMessageProcesser;

public class GameClient implements IClientMessageProcesser, IClientInterface {
	private Client client;
	private IClientView view;
	private ReadyPanel ready;
	private ILogger log;

	private String startDestination;
	private ArrayList<String> destinations;
	private String nextDestination;
	private int currentDestinationIndex;
	
	private String username;
	
	public GameClient(String username, InetAddress address) throws IOException {
		client = new Client(address, username);
		this.username = username;
		
		destinations = new ArrayList<>();
		log = new ILogger() {
			public void log(String message, LogType type) {
				System.out.println(type + ": " + message);
			}
			
			public void clear() {}
		};
		
		view = new IClientView() {
			public void togglePause() { System.out.println("Toggle Pause"); }
			public void startNewRound()  { System.out.println("Start New Round"); }
			public void setStartPage(String page) { System.out.println("StartPage = " + page); }
			public void setClicks(int clickCount)  { System.out.println("Clicks = " + clickCount); }
			public void prepNewRound() { System.out.println("prepNewRound"); }
			public void playerWin(String playerName) { System.out.println("playerWin = " + playerName); }
			public void playerStatsChanged(String playerName, long time, int clickCount) { System.out.println("playerStateChange = " + playerName + ", " + time + ", " + clickCount); }
			public void playerSpectating(String playerName)  { System.out.println("playerSpectating = " + playerName); }
			public void playerQuit(String playerName) { System.out.println("playerQuit = " + playerName); }
			public void playerProgress(String playerName, int percentage) { System.out.println("playerProgress = " + playerName + ", " + percentage); }
			public void playerPlaying(String playerName) { System.out.println("playerPlaying = " + playerName); }
			public void playerLeft(String playerName) { System.out.println("playerLeft = " + playerName); }
			public void playerJoined(String playerName) { System.out.println("playerJoined = " + playerName); }
			public void playerDone(String playerName) { System.out.println("playerDone = " + playerName); }
			public void pauseRequested() { System.out.println("pauseRequested"); }
			public IWebBrowser getBrowser() { System.out.println("getBrowser"); return null; }
			public void changeTime(long milliSeconds) { System.out.println("changeTime = " + milliSeconds); }
			public void changeTargetDesination(String desination) { System.out.println("changeTargetDesination = " + desination); }
			public void addToHistory(String page) { System.out.println("addToHistory = " + page); }
			@SuppressWarnings("static-access")
			public void addReadyPanel(JPanel readyPanel) { System.out.println("addReadyPanel = " + readyPanel); ((ReadyPanel) readyPanel).main(readyPanel, false); }
			public void addDesitination(String page) { System.out.println("addDesitination = " + page); }
		};
		
		ready = new ReadyPanel(this);
		view.addReadyPanel(ready);
		
		client.addMessageProcesser(this);
		client.connect();
	}
	
	public void process(Client connection, String message) throws IOException {
		System.out.println("Message: " + message);
		if(connection != client) throw new IllegalArgumentException("Invalid Client");
		
		if(message.equals(Communication.REQUEST_PAUSE)) { view.pauseRequested(); return; }
		if(message.equals(Communication.TOOGLE_PAUSE)) { view.togglePause(); return; }
		
		if(message.startsWith(Communication.SET_START)) {
			view.setStartPage(startDestination = message.substring(Communication.SET_START.length()));
			return;
		}
		
		if(message.startsWith(Communication.ADD_DESTINATION)) {
			String destination = message.substring(Communication.ADD_DESTINATION.length());
			destinations.add(destination);
			view.addDesitination(destination);
			return;
		}

		if(message.startsWith(Communication.PLAYER_PLAYING)) {
			ready.addPlayer(message.substring(Communication.PLAYER_PLAYING.length()));
			log.log(message.substring(Communication.PLAYER_PLAYING.length()) + " is now Playing", ClientLogType.PlayerState);
			return;
		}
		
		if(message.startsWith(Communication.PLAYER_SPECTATING)) {
			ready.playerSpectate(message.substring(Communication.PLAYER_SPECTATING.length()));
			log.log(message.substring(Communication.PLAYER_SPECTATING.length()) + " is now Spectating", ClientLogType.PlayerState);
			return;
		}

		if(message.equals(Communication.NEW_ROUND)) { view.prepNewRound(); return; }
		
		if(message.startsWith(Communication.PLAYER_READY)) { ready.playerReady(message.substring(Communication.PLAYER_READY.length()), true); return; }
		if(message.startsWith(Communication.PLAYER_UNREADY)) { ready.playerReady(message.substring(Communication.PLAYER_UNREADY.length()), false); return; }
		
		if(message.startsWith(Communication.PLAYERS_READY_RECALC)) { ready.setPercatageReady(Integer.parseInt(message.substring(Communication.PLAYERS_READY_RECALC.length()))); return; }
		if(message.startsWith(Communication.PLAYERS_READY_NEEDED)) { ready.setPlayersNeedToStart(Integer.parseInt(message.substring(Communication.PLAYERS_READY_NEEDED.length()))); return; }
		
		if(message.startsWith(Communication.AUTO_READY_START)) { ready.startTimer(Long.parseLong(message.substring(Communication.AUTO_READY_START.length()))); return; }
		if(message.startsWith(Communication.STOP_READY_START)) { ready.stopTimer(); return; }
		
		if(message.startsWith(Communication.PLAYER_CONNECTED)) { 
			String username = message.substring(Communication.PLAYER_CONNECTED.length());
			log.log(username, ClientLogType.PlayerConnetion); 
			view.playerJoined(username);
			return;
		}
		
		if(message.startsWith(Communication.PLAYER_DISCONNECTED)) { 
			String username = message.substring(Communication.PLAYER_DISCONNECTED.length());
			log.log(username, ClientLogType.PlayerConnetion); 
			view.playerLeft(username);
			return;
		}
		
		if(message.startsWith(Communication.PLAYER_DONE)) { 
			String[] parts = DataPassing.splitData(message.substring(Communication.PLAYER_DONE.length()));
			int clickCount = Integer.parseInt(parts[2]);
			long time = Long.parseLong(parts[1]);
			String username = parts[0];
			
			view.playerStatsChanged(username, time, clickCount);
			return;
		}
		
		if(message.startsWith(Communication.PLAYER_QUIT)) { 
			view.playerQuit(message.substring(Communication.PLAYER_QUIT.length()));
			return;
		}
		
		if(message.equals(Communication.READY_ROUND)) { ready.setVisible(true); return; }
		
		if(message.startsWith(Communication.COUNT_DOWN_ROUND)) {
			ready.countDown(Integer.parseInt(message.substring(Communication.COUNT_DOWN_ROUND.length())));
			return;
		}
		
		if(message.equals(Communication.START_ROUND)) {
			ready.countDown(0);
			nextDestination = startDestination;
			view.changeTargetDesination(nextDestination);
			currentDestinationIndex = -1;
			return;
		}
		
		System.err.println("Raw Data from Server: " + message);
	}

	public void requestPause() { sendMessage(Communication.REQUEST_PAUSE);  }
	public void acceptPause()  { sendMessage(Communication.PAUSE_ACCEPT);   }
	public void rejectPause()  { sendMessage(Communication.PAUSE_REJECTED); }

	public void quit() { try { client.sendMessage(Communication.PLAYER_QUIT); } catch(IOException e) { e.printStackTrace(); } }
	public void disconnect() { try { client.disconnect(); } catch(IOException e) { e.printStackTrace(); } }
	
	public String getUsername() { return username; }
	
	public void siteChanged(String newSite, String oldSite) {
		if(newSite.equals(nextDestination)) {
			if(++ currentDestinationIndex >= destinations.size()) {
				try { client.sendMessage(Communication.PLAYER_DONE);
				} catch(IOException e) { e.printStackTrace(); }
			} else {
				nextDestination = destinations.get(currentDestinationIndex);
				view.changeTargetDesination(nextDestination);
			}
		}
		
		view.addToHistory(newSite);
	}
	
	public void sendMessage(String message) {
		try { client.sendMessage(message);
		} catch(IOException e) { e.printStackTrace(); }   
	}
}
