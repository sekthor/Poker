package application.view;

import application.model.Card;
import application.model.HandType;
import application.model.Player;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


public class PlayerPane extends VBox {
    private Label lblName = new Label();
    private HBox hboxCards = new HBox();
    private Label lblEvaluation = new Label("--");
    private Label winner = new Label("--");
    private ScaleTransition winnerAnimation = new ScaleTransition(Duration.millis(400), winner);
    
    
    // Link to player object
    private Player player;
    
    public PlayerPane() {
        super(); // Always call super-constructor first !!
        this.getStyleClass().add("player"); // CSS style class
        
        hboxCards.setSpacing(5);
        // Add child nodes
        this.getChildren().addAll(lblName, hboxCards, lblEvaluation, winner);
        

        // Add CardLabels for the cards
        for (int i = 0; i < 5; i++) {
            Label lblCard = new CardLabel();
            hboxCards.getChildren().add(lblCard);
        }

        // Animation for winner 
        winnerAnimation.setByX(1.1);
        winnerAnimation.setByY(1.1);
        winnerAnimation.setCycleCount(2);
        winnerAnimation.setAutoReverse(true); 
    }
    
    public void setPlayer(Player player) {
    	this.player = player;
    	updatePlayerDisplay(); // Immediately display the player information
    }
    
    public void winnerAnimation() {
    	winnerAnimation.play();
    }
    
    public void updatePlayerDisplay() {
    	lblName.setText(player.getPlayerName());
    	for (int i = 0; i < Player.HAND_SIZE; i++) {
    		Card card = null;
    		if (player.getCards().size() > i) card = player.getCards().get(i);
    		CardLabel cl = (CardLabel) hboxCards.getChildren().get(i);
    		cl.setCard(card);
    		HandType evaluation = player.evaluateHand();
    		if (evaluation != null)
    			lblEvaluation.setText(evaluation.toString());
    		else
    			lblEvaluation.setText("--");
    	}
    	winner.setText("Wins: " + Integer.toString(player.wins));
    	this.setStyle("-fx-border-color: white; -fx-padding: 20px;");
    }   
}
