package application.view;

import java.util.ArrayList;

import application.model.HandType;
import application.model.Statistics;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class StatisticsView extends VBox{

	private Label title;
	private Statistics stats;
	private GridPane statsPane;
	private ArrayList<Label> percentages;
	
	public StatisticsView() {
		this.setId("stats");
		
		title = new Label();
		title.setText("Statistics");
		percentages = new ArrayList<Label>();
		
		
		
		statsPane = new GridPane();
		
		for (HandType hand : HandType.values()) {
			Label label = new Label(hand.toString());
			Label label2 = new Label();
			percentages.add(label2);
			label.setId(hand.toString());
			label2.setId(hand.toString()+"Value");
			label2.setText("0 %");
			label.getStyleClass().add("stats");
			label2.getStyleClass().add("stats");
			
			statsPane.add(label,0,hand.ordinal());
			statsPane.add(label2,1, hand.ordinal());			
		}
    	
		this.getChildren().addAll(title,statsPane);
		
	}
	
	public void setStats(Statistics stats) {
		this.stats = stats;
	}
	
	public void updateStats() {
		ArrayList<Integer> statistics = stats.getStats();
		for (int i = 0; i < percentages.size(); i++) {
			percentages.get(i).setText(statistics.get(i).toString()+" %");
		}
	}
}
