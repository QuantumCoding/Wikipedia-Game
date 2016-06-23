package game;

public class ServerProperties {
	private float pausePercentage;
	
	public float getPausePercenatge() { return pausePercentage; }
	public void setPausePercenatge(float percentage) { this.pausePercentage = percentage; }
	
//	---------------------------------------------------------------------------------------------------------------------------  \\
	
	private float readyPercentage;
	private long noResponseAutoReady;
	private int countDownAmount;

	public float getReadyPercenatge() { return readyPercentage; }
	public void setReadyPercenatge(float percentage) { this.readyPercentage = percentage; }
	
	public long getNoResponseAutoReadyTime() { return noResponseAutoReady; }
	public void setNoResponseAutoReadyTime(long milliseconds) { this.noResponseAutoReady = milliseconds; }
	
	public int getCountDown() { return countDownAmount; }
	public void setCountDown(int amount) { this.countDownAmount = amount; }
}
