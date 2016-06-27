package networking.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientConnection implements Runnable {

	private Server server;
	private Socket connection;
	
	private DataOutputStream out;
	private DataInputStream in;
	
	private String username;
	
	private boolean running;
	private Thread thread;
	
	public ClientConnection(Server server, Socket connection) throws IOException {
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
				while(in.available() == 0 && running) 
					try { Thread.sleep(10); }  catch(InterruptedException e) {}
				if(!running) continue;
				
				String read = in.readUTF();
				read = process(read);
				if(!read.isEmpty())
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
		out.writeUTF(message);
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
