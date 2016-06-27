package networking.server;

import java.io.IOException;

public interface IServerMessageProcesser {
	public void process(ClientConnection connection, String message) throws IOException;
}
