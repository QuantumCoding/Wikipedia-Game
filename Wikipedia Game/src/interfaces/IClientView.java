package interfaces;

import javax.swing.JPanel;

public interface IClientView {
	public void playerJoined(String playerName);
	public void playerLeft(String playerName);
	
	public void playerStatsChanged(String playerName, long time, int clickCount);
	
	public void playerDone(String playerName);
	public void playerPlaying(String playerName);
	public void playerSpectating(String playerName);
	public void playerQuit(String playerName);
	
	public void playerWin(String playerName);
	public void playerProgress(String playerName, int percentage);
	
	public void pauseRequested();	
	public void togglePause();
	
	public void changeTime(long milliSeconds);
	public void setClicks(int clickCount);

	public void changeTargetDesination(String desination);
	
	public void setStartPage(String page);
	public void addDesitination(String page);
	public void addToHistory(String page);
	public void prepNewRound();
	public void startNewRound();
	
	public void addReadyPanel(JPanel readyPanel);
	
	public IWebBrowser getBrowser();
}
