package networking.server;

import java.io.IOException;

public interface IClientConnectedListener {
	public void clientConnected(ClientConnection connection) throws IOException;
	public void clientDisconnected(ClientConnection connection) throws IOException;
}
