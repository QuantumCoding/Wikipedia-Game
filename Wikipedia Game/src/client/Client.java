package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import server.Server;

public class Client implements Runnable {
	private Socket serverConnection;
	private InetAddress address;
	private int port;
	
	private DataInputStream in;
	private DataOutputStream out;
	
	private String username;
	
	private boolean running;
	private Thread thread;
	
	private ArrayList<IClientMessageProcesser> messageProcess;

	public Client(InetAddress address, String username) {
		this(address, Server.DEFAULT_PORT, username);
	}
	
	public Client(InetAddress address, int port, String username) {
		this.address = address;
		this.port = port;
		this.username = username;
		
		messageProcess = new ArrayList<>();
	}
	
	public void addMessageProcesser(IClientMessageProcesser processer) {
		this.messageProcess.add(processer);
	}
	
	public void connect() throws IOException {
		serverConnection = new Socket(address, port);
		in = new DataInputStream(serverConnection.getInputStream());
		out = new DataOutputStream(serverConnection.getOutputStream());
		
		String read = in.readUTF();
//		System.out.println("Client - " + username + ": " + read);
		if(!read.equalsIgnoreCase("connected"))
			return;
		out.writeUTF(username);
		
		start();
	}

	public void start() {
		running = true;
		thread = new Thread(this, "Wikipedia Game - Client \"" + username + "\" Thread");
		thread.start();
	}

	public void run() {
		while(running) {
			try {
				while(in.available() == 0 && running) 
					try { Thread.sleep(10); }  catch(InterruptedException e) {}
				if(!running) continue;
				
				String read = in.readUTF();
				process(read);				
				
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		try { disconnect(); } 
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void disconnect() throws IOException {
		running = false;
		thread.interrupt();
		try { thread.join(); } 
		catch(InterruptedException e) {}
		
		in.close();
		out.close();
		
		serverConnection.close();
	}
	
	private void process(String read) throws IOException {
		for(IClientMessageProcesser processer : messageProcess)
			processer.process(this, read);
	}
	
	public void sendMessage(String message) throws IOException {
		out.writeUTF(message);
	}

	public boolean isRunning() { return running; }
	public String getUsername() { return username; }
}
