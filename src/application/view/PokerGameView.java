package application.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import application.PokerGame;
import application.model.PokerGameModel;

public class PokerGameView {
	private HBox players;
	private ControlArea controls;
	private MenuBar menu;
	
	private PokerGameModel model;
	private MenuItem addPlayer;
	private MenuItem rmvPlayer;
	private Statistics stats;
	
	public PokerGameView(Stage stage, PokerGameModel model) {
		this.model = model;
		
		// Create all of the player panes we need, and put them into an HBox
		players = new HBox();
		for (int i = 0; i < PokerGame.NUM_PLAYERS; i++) {
			PlayerPane pp = new PlayerPane();
			pp.setPlayer(model.getPlayer(i)); // link to player object in the logic
			players.getChildren().add(pp);
		}
		
		// Create the control area
		controls = new ControlArea();
		controls.linkDeck(model.getDeck()); // link DeckLabel to DeckOfCards in the logic		
		
		
		// menu
		menu = new MenuBar();
		Menu playerMenu = new Menu("Players");
		addPlayer = new MenuItem("Add Player");
		rmvPlayer = new MenuItem("Remove Player");
		playerMenu.getItems().addAll(addPlayer, rmvPlayer);
		menu.getMenus().add(playerMenu);
		//menu.getStyleClass().add("menu1");
		//playerMenu.getStyleClass().add("menu1");
		//rmvPlayer.getStyleClass().add("menu1");
		
		stats = new Statistics();
		
		// Put players and controls into a BorderPane
		BorderPane root = new BorderPane();
		root.setCenter(players);
		root.setTop(menu);
		root.setBottom(controls);
		root.setLeft(stats);
		root.setId("root");
		
		// Disallow resizing - which is difficult to get right with images
		stage.setResizable(true);

        // Create the scene using our layout; then display it
        Scene scene = new Scene(root,1000,600);
        scene.getStylesheets().add(
                getClass().getResource("poker.css").toExternalForm());
        stage.setTitle("Poker Miniproject");
        stage.setScene(scene);
        stage.show();		
	}
	
	public PlayerPane getPlayerPane(int i) {
		return (PlayerPane) players.getChildren().get(i);
	}
	
	public Button getShuffleButton() {
		return controls.btnShuffle;
	}
	
	public Button getDealButton() {
		return controls.btnDeal;
	}
	public MenuItem getAddButton() {
		return this.addPlayer;
	}
	public MenuItem getRemoveButton() {
		return this.rmvPlayer;
	}
	
	public void addPlayer() {
		PlayerPane pp = new PlayerPane();
		pp.setPlayer(model.getPlayer(PokerGame.NUM_PLAYERS-1)); // link to player object in the logic
		players.getChildren().add(pp);
	}
	
	public void rmvPlayer(int i) {
		players.getChildren().remove(this.getPlayerPane(i));
	}
}
