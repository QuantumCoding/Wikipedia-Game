package game.structure;

public interface ILogger {
	public void clear();
	public void log(String message, LogType type);
}
