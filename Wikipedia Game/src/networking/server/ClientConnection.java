package networking.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import networking.TransferProtocal;

public class ClientConnection extends TransferProtocal implements Runnable {

	private Server server;
	private Socket connection;
	
	private String username;
	
	private boolean running;
	private Thread thread;
	
	public ClientConnection(Server server, Socket connection) throws IOException {
		super();
		
		this.server = server;
		this.connection = connection;
		
		out = new DataOutputStream(connection.getOutputStream());
		in = new DataInputStream(connection.getInputStream());
	}

	public void connect() throws IOException {
		out.writeUTF("connected");
		username = in.readUTF();
		
		start();
	}

	public void start() {
		running = true;
		thread = new Thread(this, "Wikipedia Game - \"" + username + "\" Connection Thread");
		thread.start();
	}
	
	public void run() {
		while(running) {
			try {
				while(running && in.available() == 0) 
					try { Thread.sleep(10); }  catch(InterruptedException e) {}
				if(!running) continue;
				
				String read = receiveMessage();
				read = process(read);
				if(read != null && !read.isEmpty())
					server.process(this, read);
				
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		try { disconnect(); } 
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private String process(String read) {
		return read;
	}

	public void sendMessage(String message) throws IOException {
		if(!running || connection.isClosed()) { 
			System.err.println("Connection Closed"); 
			return; 
		}
		
		super.sendMessage(message);
	}

	public void disconnect() throws IOException {
		running = false;
		thread.interrupt();
		try { thread.join(); } 
		catch(InterruptedException e) {}
		
		in.close();
		out.close();
		connection.close();
		
		server.disconnected(this);
	}

	public String getUsername() { return username; }
	public boolean isRunning() { return running; }
}
