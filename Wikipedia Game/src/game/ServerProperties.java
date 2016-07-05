package game;

public class ServerProperties {
	private float pausePercentage = 1.00f;
	
	public float getPausePercenatge() { return pausePercentage; }
	public void setPausePercenatge(float percentage) { this.pausePercentage = percentage; }
	
//	---------------------------------------------------------------------------------------------------------------------------  \\
	
	private float readyPercentage = 1.00f;
	private long noResponseAutoReady = 30 * 1000;//-1;
	private int countDownAmount = 3;

	public float getReadyPercenatge() { return readyPercentage; }
	public void setReadyPercenatge(float percentage) { this.readyPercentage = percentage; }
	
	public long getNoResponseAutoReadyTime() { return noResponseAutoReady; }
	public void setNoResponseAutoReadyTime(long milliseconds) { this.noResponseAutoReady = milliseconds; }
	
	public int getCountDown() { return countDownAmount; }
	public void setCountDown(int amount) { this.countDownAmount = amount; }
}
