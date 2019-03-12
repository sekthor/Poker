package application.model;

import java.util.ArrayList;

import application.PokerGame;



public class PokerGameModel {
	private final ArrayList<Player> players = new ArrayList<>();
	private DeckOfCards deck;
	
	public PokerGameModel() {
		for (int i = 0; i < PokerGame.NUM_PLAYERS; i++) {
			players.add(new Player("Player " + i));
		}
		
		deck = new DeckOfCards();
	}
	
	public Player getPlayer(int i) {
		return players.get(i);
	}
	
	public DeckOfCards getDeck() {
		return deck;
	}
	
	public void addPlayer() {
		/***
		 * This method adds a new Player to the ArrayList of players
		 * */
		players.add(new Player("Player " + (PokerGame.NUM_PLAYERS-1)));
	}
	
	public void rmvPlayer() {
		players.remove(PokerGame.NUM_PLAYERS);
	}
}
