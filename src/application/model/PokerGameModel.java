package application.model;

import java.util.ArrayList;

import application.PokerGame;



public class PokerGameModel {
	private final ArrayList<Player> players = new ArrayList<>();
	private DeckOfCards deck;
	private Statistics stats;
	
	public PokerGameModel() {
		for (int i = 0; i < PokerGame.NUM_PLAYERS; i++) {
			players.add(new Player("Player " + (i+1)));
		}
		
		deck = new DeckOfCards();
		stats = new Statistics(this);
	}
	
	public Player getPlayer(int i) {
		return players.get(i);
	}
	
	public ArrayList<Player> getPlayers(){
		return this.players;
	}
	
	public DeckOfCards getDeck() {
		return deck;
	}
	
	public Statistics getStats() {
		return stats;
	}
	
	public void addPlayer() {
		/***
		 * This method adds a new Player to the ArrayList of players
		 * */
		players.add(new Player("Player " + (PokerGame.NUM_PLAYERS)));
	}
	
	public void rmvPlayer() {
		players.remove(PokerGame.NUM_PLAYERS);
	}
}
