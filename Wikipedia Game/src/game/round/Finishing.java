package game.round;

import game.DataPassing;
import game.GameServer;
import networking.server.ClientConnection;

public class Finishing implements Comparable<Finishing> {
	private GameServer server;
	
	private ClientConnection connection;
	private int clickCount;
	private long time;
	
	public static Finishing load(GameServer server, String message, ClientConnection connection) {
		Finishing finishing = new Finishing();
		
		String[] parts = DataPassing.splitData(message);
		finishing.clickCount = Integer.parseInt(parts[2]);
		finishing.time = Long.parseLong(parts[1]);
		finishing.connection = connection;
		
		finishing.server = server;

		return finishing;
	}

	public ClientConnection getConnection() { return connection; }
	public int getClickCount() { return clickCount; }
	public long getTime() { return time; }
	
	public String toString() {
		return connection.getUsername() + DataPassing.DATA_SPLIT
				+ time + DataPassing.DATA_SPLIT + clickCount;
	}

	public int compareTo(Finishing other) {
		long valueOne = server.getProprties().determineWinnerByTime() ? time : clickCount;
		long valueTwo = server.getProprties().determineWinnerByTime() ? other.time : other.clickCount;
		return Long.compare(valueOne, valueTwo);
	}
}
