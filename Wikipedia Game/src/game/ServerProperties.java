package game;

public class ServerProperties {
	private float pausePercentage = 1.00f;
	private float unpausePercentage = 1.00f;
	
	public float getPausePercenatge() { return pausePercentage; }
	public void setPausePercenatge(float percentage) { this.pausePercentage = percentage; }

	public float getUnpausePercenatge() { return unpausePercentage; }
	public void setUnpausePercenatge(float percentage) { this.unpausePercentage = percentage; }
	
//	---------------------------------------------------------------------------------------------------------------------------  \\
	
	private float readyPercentage = 1.00f;
	private long noResponseAutoReady = 1 * 1000;//-1;
	private int countDownAmount = 1;

	public float getReadyPercenatge() { return readyPercentage; }
	public void setReadyPercenatge(float percentage) { this.readyPercentage = percentage; }
	
	public long getNoResponseAutoReadyTime() { return noResponseAutoReady; }
	public void setNoResponseAutoReadyTime(long milliseconds) { this.noResponseAutoReady = milliseconds; }
	
	public int getCountDown() { return countDownAmount; }
	public void setCountDown(int amount) { this.countDownAmount = amount; }
	
//	---------------------------------------------------------------------------------------------------------------------------  \\
	
	private boolean allowJumpBack = true;
	private boolean letAllPlayersFinish = true;
	
	public boolean allowsJumpBack() { return allowJumpBack; }
	public void setAllowJumpBack(boolean allow) { this.allowJumpBack = allow; }

	public boolean lettingAllPlayersFinish() { return letAllPlayersFinish; }
	public void setLetAllPlayersFinish(boolean allow) { this.letAllPlayersFinish = allow; }
	
//	---------------------------------------------------------------------------------------------------------------------------  \\
	
	private boolean determineWinnerByTime = true;
	
	public boolean determineWinnerByTime() { return determineWinnerByTime; }
	public void shouldDetermineWinnerByTime(boolean allow) { this.determineWinnerByTime = allow; }
}
