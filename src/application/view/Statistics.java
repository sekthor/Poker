package application.view;

import application.model.HandType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class Statistics extends VBox{
	
	
	private Label title;
	Image deck = new Image(this.getClass().getClassLoader().getResourceAsStream("images/clover.png"));
	
	public Statistics() {
		this.setId("stats");
		
		title = new Label();
		title.setText("Statistics");
		
		
		GridPane stats = new GridPane();
		
		for (HandType hand : HandType.values()) {
			Label label = new Label(hand.toString());
			Label label2 = new Label();
			label2.setId(hand.toString()+"Value");
			label2.setText("0");
			label.getStyleClass().add("sats");
			label2.getStyleClass().add("stats");
			
			stats.add(label,0,hand.ordinal());
			stats.add(label2,1, hand.ordinal());			
		}
		
		ImageView imv = new ImageView(deck);
    	imv.setId("deckimg");
    	imv.setFitWidth(75);
    	imv.setFitHeight(100);
    	
    	stats.add(imv, 0, 10);
    	
		this.getChildren().addAll(title,stats);
		
	}

}
