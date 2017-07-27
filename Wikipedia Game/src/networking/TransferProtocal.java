package networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayDeque;

public class TransferProtocal {
	private ArrayDeque<String> messageLog;
	protected DataInputStream in;
	protected DataOutputStream out;
	
	public TransferProtocal() {
		messageLog = new ArrayDeque<>();
	}
	
	protected String receiveMessage() throws IOException {
		String message = in.readUTF();
		
		int index = message.indexOf("_"); 
		if(index == -1) index = message.length();
		
		int hash; try { hash = Integer.parseInt(message.substring(0, index)); }
		catch(NumberFormatException e) {
			System.err.println("Resending Message...");
			sendMessage(messageLog.pop());
			return null;
		}
		
		message = message.substring(index + 1);
		
		if(message.hashCode() != hash) {
			System.err.println("Bad Message: " + message);
			out.writeUTF("_"); 
			return null;
		}
		
		return message;
	}
	
	public void sendMessage(String message) throws IOException {
		messageLog.push(message);
		out.writeUTF(message.hashCode() + "_" + message);
	}
}
