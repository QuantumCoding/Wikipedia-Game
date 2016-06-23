package interfaces;

public interface IClientInterface {
	public void requestPause();
	public void acceptPause();
	public void rejectPause();
	
	public void quit();
	public void disconnect();
	
	public void siteChanged(String newSite, String oldSite);
}
