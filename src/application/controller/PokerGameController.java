package application.controller;

import java.util.ArrayList;
import java.util.Collections;

import application.PokerGame;
import application.model.Card;
import application.model.DeckOfCards;
import application.model.HandType;
import application.model.Player;
import application.model.PokerGameModel;
import application.model.Winner;
import application.view.PlayerPane;
import application.view.PokerGameView;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class PokerGameController {
	private PokerGameModel model;
	private PokerGameView view;
	private PokerGame pokerGame;
	
	public PokerGameController(PokerGameModel model, PokerGameView view, PokerGame pokerGame) {
		this.model = model;
		this.view = view;
		this.pokerGame = pokerGame;
		
		view.getShuffleButton().setOnAction( e -> shuffle() );
		view.getDealButton().setOnAction( e -> deal() );
		view.getAddButton().setOnAction(this::addPlayer);
		
		
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
        		PlayerPane pp = view.getPlayerPane(i);
        		pp.updatePlayerDisplay();
        	}
    	} else {
            Alert alert = new Alert(AlertType.ERROR, "Not enough cards - shuffle first");
            alert.showAndWait();
    	}
    	
    	/*
    	 * Here we determine the Winner
    	 */
    	ArrayList<Player> players = new ArrayList<Player>();
    	for (int i = 0; i < PokerGame.NUM_PLAYERS; i++) {
    		players.add(model.getPlayer(i)) ;   		    		
    	}
    	int winner = Winner.evaluateWinner(players);
    	model.getPlayer(winner).addWin();
    	view.getPlayerPane(winner).updatePlayerDisplay();
    }
    
     
    
    
     
    private void addPlayer(Event e) {
    	/***
    	 * this method is called when add playerButton is pressed
    	 * It executes the respective methods in View, Model, and PokerGame
    	 */
    	pokerGame.addPlayer();
    	model.addPlayer();
    	view.addPlayer();
    }
}
