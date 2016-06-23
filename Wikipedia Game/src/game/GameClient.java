package game;

import java.io.IOException;
import java.util.ArrayList;

import client.Client;
import client.IClientMessageProcesser;
import game.structure.ILogger;
import interfaces.IClientInterface;
import interfaces.IClientView;

public class GameClient implements IClientMessageProcesser, IClientInterface {
	private Client client;
	private IClientView view;
	private ReadyPanel ready;
	private ILogger log;

	private String startDestination;
	private ArrayList<String> destinations;
	private String nextDestination;
	private int currentDestinationIndex;
	
	public void process(Client connection, String message) throws IOException {
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
			ready.removePlayer(message.substring(Communication.PLAYER_SPECTATING.length()));
			log.log(message.substring(Communication.PLAYER_SPECTATING.length()) + " is now Spectating", ClientLogType.PlayerState);
			return;
		}

		if(message.equals(Communication.NEW_ROUND)) { view.prepNewRound(); return; }
		
		if(message.startsWith(Communication.PLAYER_READY)) { ready.playerReady(message.substring(Communication.PLAYER_READY.length()), true); return; }
		if(message.startsWith(Communication.PLAYER_UNREADY)) { ready.playerReady(message.substring(Communication.PLAYER_UNREADY.length()), false); return; }

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
			
			view.playerComplete(username, time, clickCount);
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
			nextDestination = startDestination;
			currentDestinationIndex = -1;
			return;
		}
	}

	public void requestPause() { sendMessage(Communication.REQUEST_PAUSE);  }
	public void acceptPause()  { sendMessage(Communication.PAUSE_ACCEPT);   }
	public void rejectPause()  { sendMessage(Communication.PAUSE_REJECTED); }

	public void quit() { try { client.sendMessage(Communication.PLAYER_QUIT); } catch(IOException e) { e.printStackTrace(); } }
	public void disconnect() { try { client.disconnect(); } catch(IOException e) { e.printStackTrace(); } }
	
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
		
		view.addSiteToPath(newSite);
	}
	
	private void sendMessage(String message) {
		try { client.sendMessage(message);
		} catch(IOException e) { e.printStackTrace(); }   
	}
}
