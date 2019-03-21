package application.view;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Alert extends Stage{
	
	private HBox box;
	private Label label;

	
	public Alert(String msg) {
		label = new Label();
		label.setText(msg);
		
		box = new HBox();
		box.getChildren().add(this.label);
		
		Scene root = new Scene(box);
		this.setScene(root);
		//this.show();
	}
	
	
	
		
	

}
