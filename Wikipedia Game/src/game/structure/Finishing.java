package game.structure;

import game.DataPassing;
import server.ClientConnection;

public class Finishing {
	private ClientConnection connection;
	private int clickCount;
	private long time;
	
	public static Finishing load(String message, ClientConnection connection) {
		Finishing finishing = new Finishing();
		
		String[] parts = DataPassing.splitData(message);
		finishing.clickCount = Integer.parseInt(parts[1]);
		finishing.time = Long.parseLong(parts[0]);
		finishing.connection = connection;

		return finishing;
	}

	public ClientConnection getConnection() { return connection; }
	public int getClickCount() { return clickCount; }
	public long getTime() { return time; }
	
	public String toString() {
		return connection.getUsername() + DataPassing.DATA_SPLIT
				+ time  + DataPassing.DATA_SPLIT + clickCount;
	}
}
