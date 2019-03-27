package application.view;

import java.text.DecimalFormat;
import java.util.ArrayList;

import application.model.HandType;
import application.model.Statistics;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class StatisticsView extends VBox{

	private Label title;
	private Label hands;
	private Label rankings;
	private Statistics stats;
	private GridPane statsPane;
	private ArrayList<Label> percentages;
	
	public StatisticsView() {
		this.setId("statsview");
		
		title = new Label("Statistics");
		
		hands = new Label("Total Hands: 0");
		percentages = new ArrayList<Label>();
		statsPane = new GridPane();
		statsPane.getStyleClass().add("stats");
		rankings = new Label("Rankings:\n1:\n2:\n");
		
		for (HandType hand : HandType.values()) {
			Label label = new Label(hand.toString());
			Label label2 = new Label();
			percentages.add(label2);
			label.setId(hand.toString());
			label2.setId(hand.toString()+"Value");
			label2.setText("0.0 %");
			label.getStyleClass().add("percentlabel");
			label2.getStyleClass().add("percent");
			
			statsPane.add(label,0,hand.ordinal());
			statsPane.add(label2,1, hand.ordinal());			
		}
    	
		this.setSpacing(10);
		this.getChildren().addAll(title,hands,statsPane,rankings);
		
	}
	
	public void setStats(Statistics stats) {
		this.stats = stats;
	}
	
	public void updateStats() {
		ArrayList<Double> statistics = stats.getStats();
		DecimalFormat df = new DecimalFormat("##0.0");
		for (int i = 0; i < percentages.size(); i++) {
			percentages.get(i).setText(df.format(statistics.get(i))+" %");
		}
		hands.setText("Total Hands: "+stats.getTotal());
		rankings.setText(stats.getRanking());
	}
	
	public void resetLabels() {
		for (int i = 0; i < percentages.size(); i++) {
			percentages.get(i).setText("0.0 %");
		}
		hands.setText("Total Hands: 0");
	}
}
