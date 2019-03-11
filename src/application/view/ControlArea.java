package application.view;

import application.model.DeckOfCards;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;


public class ControlArea extends HBox{
    private DeckLabel lblDeck = new DeckLabel();
    private Region spacer = new Region(); // Empty spacer
    Button btnShuffle = new Button("Shuffle");
    Button btnDeal = new Button("Deal");
    Button btnAdd = new Button("Add Player");
    Button btnRemove = new Button("Remove Player");

    public ControlArea() {
    	super(); // Always call super-constructor first !!
    	
    	this.getChildren().addAll(lblDeck, spacer, btnAdd, btnRemove, btnShuffle, btnDeal);

        HBox.setHgrow(spacer, Priority.ALWAYS); // Use region to absorb resizing
        this.setId("controlArea"); // Unique ID in the CSS
    }
    
    public void linkDeck(DeckOfCards deck) {
    	lblDeck.setDeck(deck);
    }
}
