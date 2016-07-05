package main;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import game.GameClient;
import game.GameServer;
import networking.server.Server;

/* Known Bugs:
 *
 * TODO:
 * 
*/

public class WikiGame_Start {
	public static void main(String[] args) throws UnknownHostException, IOException {
		Scanner delay = new Scanner(System.in);
		
		try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } 
		catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		GameServer server = new GameServer(Inet4Address.getLocalHost(), Server.DEFAULT_PORT, 100);
		try { Thread.sleep(1000); } catch(InterruptedException e) { e.printStackTrace(); }
//		System.out.println("Server Started\nWaiting..."); delay.nextLine();
		GameClient[] client = new GameClient[3
		                                     ];
		for(int i = 0; i < client.length; i ++)
			client[i] = new GameClient("Client #" + i, Inet4Address.getLocalHost());
		System.out.println();
		
		server.addNewDestinination("Google");
		server.addNewDestinination("Twitch.tv");
		
		server.readyNextRound();
		
		System.out.println("\nRound Ready\nWaiting...");
		delay.nextLine();

		server.startRound();
		
		System.out.println("\nRound Started\nWaiting...");
		delay.nextLine();
		
		for(int i = 0; i < client.length; i ++)
			client[i].disconnect();
		
		server.stop();
		
		delay.close();
		
//		SamShowFrame frame = new SamShowFrame(client);
//		frame.setVisible(true);
		
		
	}
}
