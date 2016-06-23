package interfaces;

import javax.swing.JPanel;

public interface IClientView {
	public void playerJoined(String playerName);
	public void playerLeft(String playerName);
	
	public void playerComplete(String playerName, long time, int clickCount);
	public void playerQuit(String playerName);
	public void pauseRequested();	
	public void togglePause();
	
	public void changeTime(long milliSeconds);
	public void setClicks(int clickCount);

	public void changeTargetDesination(String desination);
	public void addSiteToPath(String site);
	
	public void setStartPage(String page);
	public void addDesitination(String page);
	public void prepNewRound();
	
	public void addReadyPanel(JPanel readyPanel);
	
	public IWebBrowser getBrowser();
}
