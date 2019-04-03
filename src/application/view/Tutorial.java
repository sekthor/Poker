package application.view;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Tutorial extends Stage{
	
	private Label tutorial = new Label();
	
	public Tutorial() {
		
		String instructions = " To play the Game, the folloing Keys may be pressed: \n\n"
				+ " D:\tdeal cards\n"
				+ " S:\tshuffle\n"
				+ " A:\tadd player\n"
				+ " R:\tremove player\n"
				+ " E:\tenable auto-shuffle-mode\n"
				+ " F:\tframe winner\n"
				+ " W:\trename players";
		
		tutorial.setId("tut");
		tutorial.setText(instructions);
		
		Scene scene = new Scene(tutorial);
		scene.getStylesheets().add(getClass().getResource("namechanger.css").toExternalForm());
		this.getIcons().add(new Image("file:./src/images/chip.png"));
		this.setTitle("Tutorial");
		this.setScene(scene);
		
	}

}
