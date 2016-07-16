package game;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import game.round.ReadyPanel;
import game.structure.ILogger;
import game.structure.LogType;
import interfaces.IClientInterface;
import interfaces.IClientView;
import interfaces.IClientViewFactory;
import interfaces.ISiteChangeListener;
import interfaces.IWebBrowser;
import networking.client.Client;
import networking.client.IClientMessageProcesser;
import networking.server.Server;
import window.browser.WikipediaURLStreamHandlerFactory;
import window.testing.SamShowFrameFactory;

public class GameClient implements IClientMessageProcesser, IClientInterface, ISiteChangeListener {
	private Client client;
	private IClientView view;
	private ReadyPanel ready;
	private ILogger log;

	private String startDestination;
	private ArrayList<String> destinations;
	private String nextDestination;
	private int currentDestinationIndex;
	
	private String username;
	
	private Thread timerThread;
	private long roundTimer;
	private int  clickCount;
	private boolean roundDone;
	
	public GameClient(String username, InetAddress address, int port, IClientViewFactory viewFactory) throws IOException {
		client = new Client(address, port, username);
		this.username = username;
		
		destinations = new ArrayList<>();
		log = new ILogger() {
			public void log(String message, LogType type) {
				System.out.println(type + ": " + message);
			}
			
			public void clear() {}
		};

		view = viewFactory.createView(this);
		ready = new ReadyPanel(this);
		view.addReadyPanel(ready);
		ready.setVisible(false);
		
		try {
			client.addMessageProcesser(this);
			client.connect();
		} catch(IOException e) {
			view.close();
			throw e;
		}
		
		
		view.getBrowser().addSiteChangeListener(this);
	}
	
	public void process(Client connection, String message) throws IOException {
		System.out.println(username + " Message: " + message);
		if(connection != client) throw new IllegalArgumentException("Invalid Client");
		
		if(message.equals(Communication.REQUEST_PAUSE)) { view.pauseRequested(); return; }
		if(message.equals(Communication.TOGGLE_PAUSE)) { view.togglePause(); return; }
		
		if(message.startsWith(Communication.PAUSE_ACCEPTANCE_HAVE)) { view.setPercatageAgree(Integer.parseInt(message.substring(Communication.PAUSE_ACCEPTANCE_HAVE.length()))); return; }
		if(message.startsWith(Communication.PAUSE_ACCEPTANCE_NEED)) { view.setPlayersNeedToPause(Integer.parseInt(message.substring(Communication.PAUSE_ACCEPTANCE_NEED.length()))); return; }
		
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
			view.playerPlaying(message.substring(Communication.PLAYER_PLAYING.length()));
			ready.addPlayer(message.substring(Communication.PLAYER_PLAYING.length()));
			log.log(message.substring(Communication.PLAYER_PLAYING.length()) + " is now Playing", ClientLogType.PlayerState);
			return;
		}
		
		if(message.startsWith(Communication.PLAYER_SPECTATING)) {
			view.playerSpectating(message.substring(Communication.PLAYER_SPECTATING.length()));
			ready.playerSpectate(message.substring(Communication.PLAYER_SPECTATING.length()));
			log.log(message.substring(Communication.PLAYER_SPECTATING.length()) + " is now Spectating", ClientLogType.PlayerState);
			return;
		}
		
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
			
			view.playerDone(username);
			view.playerStatsChanged(username, time, clickCount);
			return;
		}
		
		if(message.startsWith(Communication.PLAYER_RANK)) {
			String[] parts = DataPassing.splitData(message.substring(Communication.PLAYER_RANK.length()));
			int rank = Integer.parseInt(parts[1]);
			String username = parts[0];
			
			view.updatePlayerRank(username, rank);
			return;
		}
		
		if(message.startsWith(Communication.PLAYER_WON)) {
			roundDone = true;
			view.playerWin(message.substring(Communication.PLAYER_WON.length()));
			
			try {
				if(timerThread == null) return;
				sendMessage(Communication.PLAYER_DONE
						+ DataPassing.DATA_SPLIT + roundTimer
						+ DataPassing.DATA_SPLIT + 999);
				
				timerThread.interrupt();
				timerThread.join();
				timerThread = null;
			} catch(InterruptedException e) {}
			
			return;
		}
		
		if(message.startsWith(Communication.PLAYER_QUIT)) { 
			String[] parts = DataPassing.splitData(message.substring(Communication.PLAYER_QUIT.length()));
			int clickCount = Integer.parseInt(parts[2]);
			long time = Long.parseLong(parts[1]);
			String username = parts[0];
			
			view.playerQuit(username);
			view.playerStatsChanged(username, time, clickCount);
			return;
		}
		
		if(message.equals(Communication.ROUND_READY)) { ready.setVisible(true); return; }
		
		if(message.startsWith(Communication.COUNT_DOWN_ROUND)) {
			ready.countDown(Integer.parseInt(message.substring(Communication.COUNT_DOWN_ROUND.length())));
			return;
		}
		
		if(message.equals(Communication.NEW_ROUND)) { destinations.clear(); view.prepNewRound(); return; }
		
		if(message.equals(Communication.START_ROUND)) {
			ready.countDown(0);
			nextDestination = startDestination;
			view.setStartPage(nextDestination);
			view.startNewRound();
			currentDestinationIndex = -1;
			
			roundDone = false;
			clickCount = 0;
			timerThread = new Thread(() -> {
				long lapStart = System.currentTimeMillis();
				while(!roundDone) {
					try { Thread.sleep(5); } catch(InterruptedException e) {}
					roundTimer += System.currentTimeMillis() - lapStart;
					lapStart = System.currentTimeMillis();
					
					view.changeTime(roundTimer);
				}
				
			}, username + " - Round Timer - Tread");
			timerThread.start();
			
			return;
		}
		
		if(message.startsWith(Communication.ALLOW_JUMP_BACK)) { view.allowJumpBack(Boolean.parseBoolean(message.substring(Communication.ALLOW_JUMP_BACK.length()))); return; }
		
		System.err.println("Raw Data from Server: " + message);
	}

	public void requestPause()   { sendMessage(Communication.REQUEST_PAUSE);  }
	public void acceptPause()    { sendMessage(Communication.PAUSE_ACCEPT);   }
	public void rejectPause()    { sendMessage(Communication.PAUSE_REJECTED); }
	public void requestUnpause() { sendMessage(Communication.UNPAUSE); 		  }

	public void quit() { sendMessage(Communication.PLAYER_QUIT); view.nowDone(); }
	public void disconnect() { try { client.disconnect(); } catch(IOException e) { e.printStackTrace(); } }
	
	public String getUsername() { return username; }
	
	public void siteChanged(String newSite, String oldSite) {
		clickCount ++;
		view.setClicks(clickCount);
		
		if(newSite.equals(nextDestination)) {
			if(++ currentDestinationIndex >= destinations.size()) {
				try { 
					roundDone = true;
					
					view.nowDone();
					sendMessage(Communication.PLAYER_DONE
						+ DataPassing.DATA_SPLIT + roundTimer
						+ DataPassing.DATA_SPLIT + clickCount
					);
					
					timerThread.interrupt();
					timerThread.join();
					timerThread = null;
				} catch(InterruptedException e) { e.printStackTrace(); }
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

	public void siteChanges(IWebBrowser browser, String oldSite, String newSite) {
		siteChanged(processURLString(newSite), processURLString(oldSite));
	}
	
	private String processURLString(String url) {
		if(url == null) return "";
		
		try { url = URLDecoder.decode(url, "UTF-8"); } catch(UnsupportedEncodingException e) {}
		String string = url.substring(url.lastIndexOf("/") + 1);
		if(string.isEmpty()) return "";
		
		string = Character.toUpperCase(string.charAt(0)) + string.substring(1);
		return string.toString().replace("_", " ");
	}
	
	public IClientView getView() { return view; } 
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		URL.setURLStreamHandlerFactory(new WikipediaURLStreamHandlerFactory());
		
		try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } 
		catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		new GameClient("Client #", Inet4Address.getLocalHost(), Server.DEFAULT_PORT, new SamShowFrameFactory());
	}
}
