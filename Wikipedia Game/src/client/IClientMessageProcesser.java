package client;

import java.io.IOException;

public interface IClientMessageProcesser {
	public void process(Client connection, String message) throws IOException;
}
