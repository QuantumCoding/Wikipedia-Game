package game;

public class ServerProperties {
	private float pausePercentage = 1.00f;
	private float unpausePercentage = 1.00f;
	
	public float getPausePercentage() { return pausePercentage; }
	public void setPausePercentage(float percentage) { this.pausePercentage = percentage; }

	public float getUnpausePercentage() { return unpausePercentage; }
	public void setUnpausePercentage(float percentage) { this.unpausePercentage = percentage; }
	
//	---------------------------------------------------------------------------------------------------------------------------  \\
	
	private float readyPercentage = 1.00f;
	private long noResponseAutoReadyTime = 1 * 1000;//-1;
	private int countDownAmount = 1;

	public float getReadyPercentage() { return readyPercentage; }
	public void setReadyPercentage(float percentage) { this.readyPercentage = percentage; }
	
	public long getNoResponseAutoReadyTime() { return noResponseAutoReadyTime; }
	public void setNoResponseAutoReadyTime(long milliseconds) { this.noResponseAutoReadyTime = milliseconds; }
	
	public int getCountDownAmount() { return countDownAmount; }
	public void setCountDownAmount(int amount) { this.countDownAmount = amount; }
	
//	---------------------------------------------------------------------------------------------------------------------------  \\
	
	private boolean allowJumpBack = true;
	private boolean letAllPlayersFinish = true;
	
	public boolean allowJumpBack() { return allowJumpBack; }
	public void setAllowJumpBack(boolean allow) { this.allowJumpBack = allow; }

	public boolean letAllPlayersFinish() { return letAllPlayersFinish; }
	public void setLetAllPlayersFinish(boolean allow) { this.letAllPlayersFinish = allow; }
	
//	---------------------------------------------------------------------------------------------------------------------------  \\
	
	private boolean determineWinnerByTime = true;
	
	public boolean determineWinnerByTime() { return determineWinnerByTime; }
	public void setDetermineWinnerByTime(boolean allow) { this.determineWinnerByTime = allow; }


//	---------------------------------------------------------------------------------------------------------------------------  \\
	
	private float loadedPercentage = 0.5f;
	
	public float getLoadedPercentage() { return loadedPercentage; }
	public void setLoadedPercentage(float perentage) { this.loadedPercentage = perentage; }
}
