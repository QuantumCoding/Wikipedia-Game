package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import client.Client;

public class Server implements Runnable {
	public static final int DEFAULT_PORT = 1024;
	public static final int DEFAULT_SIZE = 8;
	
	private ServerSocket socket;
	private InetAddress address;
	private int port, size;
	
	private ClientConnection[] connections;
	private int connectedCount;
	
	private boolean running;
	private Thread thread;
	
	private ArrayList<IServerMessageProcesser> messageProcess;
	private ArrayList<IClientConnectedListener> connectionListeners;
	
	public Server(InetAddress address) throws IOException { this(address, DEFAULT_PORT, DEFAULT_SIZE); }
	public Server(InetAddress address, int port) throws IOException { this(address, port, DEFAULT_SIZE); }
	
	public Server(InetAddress address, int port, int size) {
		this.address = address;
		this.port = port;
		this.size = size;
		
		messageProcess = new ArrayList<>();
		connectionListeners = new ArrayList<>();
	}
	
	public void addMessageProcesser(IServerMessageProcesser processer) {
		this.messageProcess.add(processer);
	}
	
	public void addConnectionListener(IClientConnectedListener listener) {
		this.connectionListeners.add(listener);
	}
	
	public void connect() throws IOException {
		this.socket = new ServerSocket(port, size, address);
		connections = new ClientConnection[size];
		socket.setSoTimeout(10);
		
		start();
	}
	
	public void start() {
		running = true;
		thread = new Thread(this, "Wikipedia Game - Server Thread");
		thread.start();
	}

	public void run() {
		while(running) {
			try {
				Socket connection = socket.accept();
				int avalableIndex = -1;
				while(connections[++ avalableIndex] != null || avalableIndex == size - 1);
				
				if(connections[avalableIndex] != null) {
					DataOutputStream out = new DataOutputStream(connection.getOutputStream());
					out.writeUTF("Sevrer Full");
					out.close();
					
					connection.close();
					continue;
				}
				
				connections[avalableIndex] = new ClientConnection(this, connection);
				connections[avalableIndex].connect();
				connectedCount ++;
				
				for(IClientConnectedListener listener : connectionListeners) 
					listener.clientConnected(connections[avalableIndex]);
				
			} catch(SocketTimeoutException se) {
				
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		for(ClientConnection connection : connections) {
			try { if(connection != null) connection.disconnect();
			} catch(IOException e) { e.printStackTrace();}
		}
	}
	
	protected void process(ClientConnection connection, String read) throws IOException {
		for(IServerMessageProcesser processer : messageProcess)
			processer.process(connection, read);
	}
	
	protected void disconnected(ClientConnection connection) {
		for(IClientConnectedListener listener : connectionListeners) {
			try { listener.clientDisconnected(connection); } 
			catch(IOException e) { e.printStackTrace(); }
		}
		
		for(int i = 0; i < size; i ++) {
			if(connections[i] == connection) {
				connections[i] = null;
				connectedCount --;
				return;
			}
		}
	}
	
	public void close() throws IOException {
		running = false;
		thread.interrupt();
		try { thread.join(); }
		catch(InterruptedException e) {}
		
		socket.close();
	}

	public boolean isRunning() { return running; }
	public ClientConnection[] getConnections() { return connections; }
	public int getConnectedCount() { return connectedCount; }
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		Server server = new Server(Inet4Address.getLocalHost());
		Client client = new Client(Inet4Address.getLocalHost(), "Pablo");
		
		server.addMessageProcesser((connection, message) -> { 
			System.out.println(connection.getUsername() + ": " + message);
			connection.sendMessage("Hi!");
		});
		
		client.addMessageProcesser((connection, message) -> { 
			System.out.println("Client-" + connection.getUsername() + ": " + message);
		});
		
		server.connect();
		client.connect();
		
		client.sendMessage("Hello, Server");
		
		try { Thread.sleep(2000); } 
		catch(InterruptedException e) {}
		
		client.disconnect();
		server.close();
	}
}
