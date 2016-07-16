package main;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import game.GameClient;
import game.GameServer;
import networking.server.ClientConnection;
import networking.server.IClientConnectedListener;
import networking.server.Server;
import window.browser.WikipediaURLStreamHandlerFactory;
import window.testing.SamShowFrameFactory;

/* Known Bugs:
 *		- Page's with # (ie. Ref) display # in History
 *
 * TODO:
*/

public class WikiGame_Start {
	public static void main(String[] args) throws UnknownHostException, IOException {
		Scanner delay = new Scanner(System.in);
		
		URL.setURLStreamHandlerFactory(new WikipediaURLStreamHandlerFactory());
		
		try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } 
		catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		GameServer server = new GameServer(Inet4Address.getLocalHost(), Server.DEFAULT_PORT, 100);
//		try { Thread.sleep(1000); } catch(InterruptedException e) { e.printStackTrace(); }
//		System.out.println("Server Started\nWaiting..."); delay.nextLine();
		
		Object sync = new Object();
		server.getServer().addConnectionListener(new IClientConnectedListener() {
			public void clientDisconnected(ClientConnection connection) throws IOException {}
			public void clientConnected(ClientConnection connection) throws IOException {
				synchronized(sync) { sync.notify(); }
			}
		});
		
		synchronized(sync) { try { sync.wait(); } catch(InterruptedException e) {} }
		
		GameClient[] client = new GameClient[1];
		for(int i = 0; i < client.length; i ++) {
			try { Thread.sleep(100); } catch(InterruptedException e) { e.printStackTrace(); }
			client[i] = new GameClient("Client #" + i, Inet4Address.getLocalHost(), Server.DEFAULT_PORT, new SamShowFrameFactory());
		}
		System.out.println();
		
		server.prepNextRound("Google");
		server.addNewDestinination("Corporation");
		
		server.readyNextRound();
		
		System.out.println("\nRound Ready\nWaiting...");
		delay.nextLine();
		
		server.prepNextRound("Twitch.tv");
		server.addNewDestinination("Multimedia");
		
		server.readyNextRound();
		
		System.out.println("\nRound Ready\nWaiting...");
		delay.nextLine();
		
		for(int i = 0; i < client.length; i ++)
			client[i].disconnect();
		
		server.stop();
		
		delay.close();
	}
}
