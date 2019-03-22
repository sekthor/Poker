package application.view;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Alert extends Stage{
	
	private HBox box;
	private Label label;

	
	public Alert(String msg) {
		label = new Label();
		label.setText(msg);
		
		
		box = new HBox();
		box.getChildren().add(this.label);
		
		Scene root = new Scene(box);
		root.getStylesheets().add(
                getClass().getResource("alert.css").toExternalForm());
		this.setScene(root);
		
		this.getIcons().add(new Image("file:./src/images/alert.png"));
		
		
	}
	
	
	
		
	

}
