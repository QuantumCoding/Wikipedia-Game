package Util;

public class Formatter {
	public static String formatTime(long milli) {
		int minutes = (int) (milli / (1000 * 60));
		int seconds = (int) (milli / 1000 - minutes * 60);
		int milliRem = (int) (milli - seconds * 1000 - minutes * 1000 * 60);
		
		if(minutes > 100) return "**:**.000";
		
		return  String.format("%02d", Integer.parseInt(minutes + "")) + ":" +
				String.format("%02d", Integer.parseInt(seconds + "")) + "." +
				String.format("%03d", Integer.parseInt(milliRem + "")); 
	}
}
