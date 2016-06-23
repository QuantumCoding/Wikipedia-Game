package game;

import java.util.ArrayList;

public class DataPassing {
	public static final String DATA_SPLIT = "%$^#";
	
	public static final String[] splitData(String raw) {
		ArrayList<String> parts = new ArrayList<>();
		int index = raw.indexOf(DATA_SPLIT);
		while(index != -1) {
			parts.add(raw.substring(0, index));
			raw = raw.substring(index + DATA_SPLIT.length());
		}
		
		parts.add(raw);
		return parts.toArray(new String[parts.size()]);
	}
}
