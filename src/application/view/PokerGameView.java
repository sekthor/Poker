package application.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import javafx.stage.Stage;
import application.PokerGame;
import application.model.PokerGameModel;

public class PokerGameView {
	private GridPane players;
	private Stage stage;
	private ControlArea controls;
	private MenuBar menu;
	
	private PokerGameModel model;
	private MenuItem addPlayer;
	private MenuItem rmvPlayer;
	private MenuItem changeNames;
	private MenuItem resetStats;
	private MenuItem autoShuffle;
	private MenuItem frameWinner;
	private StatisticsView stats;
	
	public PokerGameView(Stage stage, PokerGameModel model) {
		this.model = model;
		this.stage = stage;
		// Create all of the player panes we need, and put them into an HBox
		players = new GridPane();
		//players = new HBox();
		
		int count = 0;
		for (int i = 0; i < (PokerGame.NUM_PLAYERS/3+1); i++) {
			for (int j = 0; j<3 && count<PokerGame.NUM_PLAYERS; j++) {
				PlayerPane pp = new PlayerPane();
				pp.setPlayer(model.getPlayer(count));
				pp.setMaxHeight(Double.MAX_VALUE);
				players.add(pp, j, i);
				count++;
			}
		}
		
		
		// Create the control area
		controls = new ControlArea();
		controls.linkDeck(model.getDeck()); // link DeckLabel to DeckOfCards in the logic		
		
		
		// menu
		menu = new MenuBar();
		
		Menu playerMenu = new Menu("Players");
		addPlayer = new MenuItem("Add Player");
		rmvPlayer = new MenuItem("Remove Player");
		changeNames = new MenuItem("Change Names");
		playerMenu.getItems().addAll(addPlayer, rmvPlayer, changeNames);
		
		Menu statistics = new Menu("Statistics");
		resetStats = new MenuItem("Reset");
		statistics.getItems().add(resetStats);
		
		Menu settings = new Menu("Settings");
		autoShuffle = new MenuItem("Enable Auto-Shuffle");
		frameWinner = new MenuItem("Enable Winner-Frame");
		settings.getItems().addAll(autoShuffle, frameWinner);
		
		
		
		menu.getMenus().addAll(playerMenu, statistics, settings);
		
		
		stats = new StatisticsView();
		stats.setStats(model.getStats());
		stats.getStyleClass().add("stats");
		
		// Put players and controls into a BorderPane
		BorderPane root = new BorderPane();
		root.setCenter(players);
		root.setTop(menu);
		root.setBottom(controls);
		root.setLeft(stats);
		root.setId("root");
		
		// Disallow resizing - which is difficult to get right with images
		//stage.setResizable(true);

        // Create the scene using our layout; then display it
        Scene scene = new Scene(root);
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
	public MenuItem getResetStatsButton() {
		return this.resetStats;
	}
	public MenuItem getRemoveButton() {
		return this.rmvPlayer;
	}
	public MenuItem getChangeNamesButton() {
		return this.changeNames;
	}
	public MenuItem getAutoShuffleButton() {
		return this.autoShuffle;
	}
	public void setAutoShuffleText(String string) {
		this.autoShuffle.setText(string);
	}
	public MenuItem getWinnerFrameButton() {
		return this.frameWinner;
	}
	public void setWinnerFrameText(String string) {
		this.frameWinner.setText(string);
	}
	public StatisticsView getStatsView() {
		return this.stats;
	}
	public Stage getStage() {
		return this.stage;
	}
	public void setMaximize() {
		stage.setMaximized(true);
	}
	
	public void addPlayer() {
		PlayerPane pp = new PlayerPane();
		pp.setPlayer(model.getPlayer(PokerGame.NUM_PLAYERS-1)); // link to player object in the logic
		int col = (PokerGame.NUM_PLAYERS-1)%3;
		int row = (PokerGame.NUM_PLAYERS-1)/3;
		players.add(pp, col, row);
	}
	
	public void rmvPlayer(int i) {
		players.getChildren().remove(this.getPlayerPane(i));
	}
}
