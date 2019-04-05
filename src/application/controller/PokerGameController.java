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
import application.view.NameChanger;
import application.view.PlayerPane;
import application.view.PokerGameView;
import application.view.Tutorial;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class PokerGameController {
	
	private PokerGameModel model;
	private PokerGameView view;
	private PokerGame pokerGame;
	private boolean shuffleMode = false; // Autoshuffling ON/OFF
	private boolean frame = false;  // Winner-frame ON/OFF
	private boolean fullScreen = false; // Fullscreen ON/OFF
	private NameChanger changer;
	
	public PokerGameController(PokerGameModel model, PokerGameView view) {
		this.model = model;
		this.view = view;
		
		view.getShuffleButton().setOnAction( e -> shuffle() );
		view.getDealButton().setOnAction( e -> deal() );
		view.getAddButton().setOnAction(e -> addPlayer());
		view.getRemoveButton().setOnAction(e -> rmvPlayer());
		view.getResetStatsButton().setOnAction(e -> resetStats());
		view.getResetWinsButton().setOnAction(e -> resetWins());
		view.getAutoShuffleButton().setOnAction(e -> changeAutoShuffleMode());
		view.getWinnerFrameButton().setOnAction(e -> changeWinnerFrameMode());
		view.getChangeNamesButton().setOnAction(e -> changeNameWindow());
		view.getFullScreenButton().setOnAction(e -> changeFullScreenMode());
		view.getTutorial().setOnAction(e -> tutorial());
		view.getScene().setOnKeyReleased(e -> parseKey(e));
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
        	int winner = Winner.evaluateWinner(model.getPlayers());
        	model.getPlayer(winner).addWin();
        	view.getPlayerPane(winner).updatePlayerDisplay();
        	view.getPlayerPane(winner).winnerAnimation();
        	if (frame) {
        		view.getPlayerPane(winner).setStyle("-fx-padding: 11px; -fx-border-color: #5f1919; -fx-border-width: 10px;");
        	}
        	view.getStatsView().updateStats();
        	
    	} else {
    		// If autoshuffle-mode is enabled it will automatically shuffle and then deal
    		if (shuffleMode) {
    			shuffle();
    			deal();
    		} else {
    			Alert alert = new Alert("Not enough cards - shuffle first\n\nTip: Enable Auto-Shuffle");
    			alert.initOwner(view.getStage());
    			alert.showAndWait();
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
    		PokerGame.NUM_PLAYERS += 1;
    		model.addPlayer();
    		view.addPlayer();
    	} else {
    		Alert alert = new Alert("Six is the maximum ammount of players!");
    		alert.initOwner(view.getStage());
    		alert.showAndWait();
    	}
    }
    
    private void rmvPlayer() {
    	if (PokerGame.NUM_PLAYERS > 2) {
    		PokerGame.NUM_PLAYERS -= 1;
    		model.rmvPlayer();
    		view.rmvPlayer(pokerGame.NUM_PLAYERS);
    	} else {
    		Alert alert = new Alert("Two is the minimum ammount of players!");
    		alert.initOwner(view.getStage());
    		alert.showAndWait();
    	}
    }
    
    private void resetStats() {
    	model.getStats().resetStats();
    	view.getStatsView().resetLabels();
    }
    
    private void resetWins() {
    	for (Player p : model.getPlayers()) {
    		p.wins = 0;
    	}
    	for (int i=0; i<PokerGame.NUM_PLAYERS;i++) {
    		view.getPlayerPane(i).updatePlayerDisplay();
    	}
    	
    }
    
    private void changeAutoShuffleMode() {
    	this.shuffleMode = !this.shuffleMode;
    	if(shuffleMode) {
    		view.setAutoShuffleText("Disable Auto-Shuffle");
    	} else {
    		view.setAutoShuffleText("Enable Auto-Shuffle");
    	}
    }
    
    /*
     * Will change WinnerFrame-mode and adjust Button Text
     * */
    private void changeWinnerFrameMode() {
    	this.frame = !this.frame;
    	if(frame) {
    		view.setWinnerFrameText("Disable Winner-Frame");
    	} else {
    		view.setWinnerFrameText("Enable Winner-Frame");
    	}
    }
    
    /*
     * Will make new window to change names
     * */
    private void changeNameWindow() {
    	changer = new NameChanger(model.getPlayers());
    	changer.show();
    	changer.getSubmitButton().setOnAction(e -> changeNames(changer.getFields(), changer));
    }
    
    /*
     * Will get Names from Namechanger and change NAme of every Player
     * */
    private void changeNames(ArrayList<TextField> players, NameChanger changer) {
    	for(TextField f : players) {
    		model.getPlayers().get(players.indexOf(f)).setName(f.getText());
    	}
    	for (int i = 0; i<model.getPlayers().size(); i++) {
    		view.getPlayerPane(i).updatePlayerDisplay();
    	}
    	changer.hide();
    }
    
    /*
     * Will change FullScreen-mode.
     * */
    private void changeFullScreenMode() {
    	fullScreen = !fullScreen;
    	view.setFullScreen(fullScreen);
    	if (fullScreen) {
    		view.setFullScreenText("Full Screen OFF");
    	} else {
    		view.setFullScreenText("Full Screen ON");
    	}
    }
    
    public void tutorial() {
    	Tutorial t = new Tutorial();
    	t.show();
    }
    
    
    /*
     * Fullscreen mode can be terminated with Esc-Key
     * This method prevents this from interfering with my custom 
     * changeFullScreenMode() method 
     * */
    public void parseKey(KeyEvent e) {
		if (e.getCode() == KeyCode.ESCAPE && fullScreen) {
			changeFullScreenMode();
		} else if (e.getCode() == KeyCode.D) {
			deal();
		} else if (e.getCode() == KeyCode.S) {
			shuffle();
		} else if (e.getCode() == KeyCode.A) {
			addPlayer();
		} else if (e.getCode() == KeyCode.R) {
			rmvPlayer();
		} else if (e.getCode() == KeyCode.F) {
			changeWinnerFrameMode();
		} else if (e.getCode() == KeyCode.E) {
			changeAutoShuffleMode();
		} else if (e.getCode() == KeyCode.W) {
			changeNameWindow();
		} 
	}
}
