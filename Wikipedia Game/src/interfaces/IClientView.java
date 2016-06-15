package interfaces;

public interface IClientView {
	public void playerJoined(String newPage);
	public void playerLeft(String newPage);
	
	public void playerComplete(String newPage);	
	public void pauseRequested(String newPage);
	
	public IWebBrowser getBrowser();
}
