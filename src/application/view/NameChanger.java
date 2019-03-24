package application.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import application.model.Player;

public class NameChanger extends Stage{
	
	private Button submit;
	private ArrayList<TextField> playerFields;
	private VBox changer;
	
	public NameChanger(ArrayList<Player> players) {
		playerFields = new ArrayList<TextField>();
		changer = new VBox();
		for (Player p : players) {
			TextField name = new TextField();
			name.setText(p.getPlayerName());
			playerFields.add(name);
			changer.getChildren().add(name);
		}
		submit = new Button("submit");
		changer.getChildren().add(submit);
		Scene scene = new Scene(changer);
		this.setScene(scene);
	}
	
	public Button getSubmitButton() {
		return this.submit;
	}
	
	public ArrayList<TextField> getFields() {
		return this.playerFields;
	}
}
