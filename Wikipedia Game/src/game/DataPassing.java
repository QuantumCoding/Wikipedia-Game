package game;

import java.util.ArrayList;

public class DataPassing {
	public static final String DATA_SPLIT = "%$^#";
	
	public static final String[] splitData(String raw) {
		ArrayList<String> parts = new ArrayList<>();
		
		int index = raw.indexOf(DATA_SPLIT), lastIndex = 0;
		while(index != -1) {
			parts.add(raw.substring(lastIndex, index));
			lastIndex = index += DATA_SPLIT.length();
			index = raw.indexOf(DATA_SPLIT, index);
		}
		
		parts.add(raw.substring(lastIndex));
		return parts.toArray(new String[parts.size()]);
	}
}
