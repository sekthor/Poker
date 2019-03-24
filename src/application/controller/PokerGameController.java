package application.controller;

import java.util.ArrayList;

import application.PokerGame;
import application.model.Card;
import application.model.DeckOfCards;
import application.model.Player;
import application.model.PokerGameModel;
import application.model.Statistics;
import application.model.Winner;
import application.view.Alert;
import application.view.PlayerPane;
import application.view.PokerGameView;


public class PokerGameController {
	private PokerGameModel model;
	private PokerGameView view;
	private PokerGame pokerGame;
	private boolean shuffleMode = false;
	
	public PokerGameController(PokerGameModel model, PokerGameView view, PokerGame pokerGame) {
		this.model = model;
		this.view = view;
		this.pokerGame = pokerGame;
		
		view.getShuffleButton().setOnAction( e -> shuffle() );
		view.getDealButton().setOnAction( e -> deal() );
		view.getAddButton().setOnAction(e -> addPlayer());
		view.getRemoveButton().setOnAction(e -> rmvPlayer());
		view.getResetStatsButton().setOnAction(e -> resetStats());
		view.getAutoShuffleButton().setOnAction(e -> changeAutoShuffleMode());
		
		
	}
	


    /**
     * Remove all cards from players hands, and shuffle the deck
     */
    private void shuffle() {
    	for (int i = 0; i < PokerGame.NUM_PLAYERS; i++) {
    		Player p = model.getPlayer(i);
    		p.discardHand();
    		PlayerPane pp = view.getPlayerPane(i);
    		pp.updatePlayerDisplay();
    	}

    	model.getDeck().shuffle();
    }
    
    
    
    /**
     * Deal each player five cards, then evaluate the two hands
     */
    private void deal() {
    	int cardsRequired = PokerGame.NUM_PLAYERS * Player.HAND_SIZE;
    	DeckOfCards deck = model.getDeck();
    	if (cardsRequired <= deck.getCardsRemaining()) {
        	for (int i = 0; i < PokerGame.NUM_PLAYERS; i++) {
        		Player p = model.getPlayer(i);
        		p.discardHand();
        		for (int j = 0; j < Player.HAND_SIZE; j++) {
        			Card card = deck.dealCard();
        			p.addCard(card);
        		}
        		p.evaluateHand();
        		model.getStats();
				Statistics.addHand(p.getHand());
        		
        		PlayerPane pp = view.getPlayerPane(i);
        		pp.updatePlayerDisplay();
        	}
        	
        	/*
        	 * Here we determine the winner
        	 */
        	ArrayList<Player> players = new ArrayList<Player>();
        	for (int i = 0; i < PokerGame.NUM_PLAYERS; i++) {
        		players.add(model.getPlayer(i)) ;   		    		
        	}
        	int winner = Winner.evaluateWinner(players);
        	model.getPlayer(winner).addWin();
        	view.getPlayerPane(winner).updatePlayerDisplay();
        	view.getStatsView().updateStats();
        	
    	} else {
    		if (shuffleMode) {
    			shuffle();
    			deal();
    		} else {
    			Alert alert = new Alert("Not enough cards - shuffle first\n\nTip: Enable Auto-Shuffle");
    			alert.show();
    		}  
    	}	
    }
    
     
    
    
     
    private void addPlayer() {
    	/***
    	 * this method is called when add playerButton is pressed
    	 * It executes the respective methods in View, Model, and PokerGame
    	 */
    	
    	if(PokerGame.NUM_PLAYERS<6) {
    		if(PokerGame.NUM_PLAYERS>=2) {
    			view.setMaximize();
    		}
    		pokerGame.addPlayer();
    		model.addPlayer();
    		view.addPlayer();
    	} else {
    		Alert alert = new Alert("Six is the maximum ammount of players!");
    		alert.showAndWait();
    	}
    }
    
    private void rmvPlayer() {
    	if (PokerGame.NUM_PLAYERS > 2) {
    		pokerGame.rmvPlayer();
    		model.rmvPlayer();
    		view.rmvPlayer(pokerGame.NUM_PLAYERS);
    	} else {
    		Alert alert = new Alert("Two is the minimum ammount of players!");
    		alert.showAndWait();
    	}
    }
    
    private void resetStats() {
    	model.getStats().resetStats();
    	view.getStatsView().resetLabels();
    }
    
    private void changeAutoShuffleMode() {
    	this.shuffleMode = !this.shuffleMode;
    	if(shuffleMode) {
    		view.setAutoShuffleText("Disable Auto-Shuffle");
    	} else {
    		view.setAutoShuffleText("Enable Auto-Shuffle");
    	}
    }
}
