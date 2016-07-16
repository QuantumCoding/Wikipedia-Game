package game.round;

import java.util.ArrayList;

import game.GameServer;

public class RoundModel {
	private static RoundModel nextRound;
	public static RoundModel getSelectedModel() { return nextRound; }
	
	private ArrayList<String> locations;
	private String winner;
	
	public RoundModel() {
		locations = new ArrayList<>();
	}
	
	private void swap(int index, int indexOther) {
		String hold = locations.get(index);
		locations.set(index, locations.get(indexOther));
		locations.set(indexOther, hold);
	}
	
	public void moveUp(int index) { swap(index, index - 1); }
	public void moveDown(int index) { swap(index, index + 1); }
	
	public void addDestination(String destination) { locations.add(destination); }
	public void removeDestination(int index) { locations.remove(index); }
	
	public String getStartingLocation() { return locations.get(0); }
	public ArrayList<String> getLocations() { return locations; }
	
	public boolean isValid() { return locations.size() >= 2; }
	
	public Round creatRound(GameServer server) {
		if(!isValid())
			throw new IllegalStateException("Round Model does not contain enought Destinations");
		
		return new Round(server, this);
	}
	
	public String getWinner() { return winner == null ? "<No Winner>" : winner; }
	public void setWinner(String username) { this.winner = username; }
	
	public void makeNextRound() { RoundModel.nextRound = this; }
	public boolean isNextRound() { return this == nextRound; }
	
	public void delete() { if(isNextRound()) nextRound = null; }
	
	public String toString() { return locations.size() < 1 ? "<Click to Config Round>" : getStartingLocation(); }
}
