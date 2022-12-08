import java.io.Serializable;
import java.util.HashMap;
import java.util.function.Consumer;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// main class in Client program, handles the GUI for Client
public class GUIClient extends Application {
	BaccaratInfo info;  // used to communicate with the server
	TextField IP, Port, bid, currentBet, totalWinnings;  // textfields used to display info
	Button start, Player, Banker, Draw, startGame, newGame, exit;  // buttons to control the game
	Client clientConnection;  // an instance of Client
	ListView<String> gameinfo;  // shows what happened in each game
	ListView<ImageView> pcard1, pcard2, pcard3, bcard1, bcard2, bcard3;  // shows card images
	HashMap<String, Scene> sceneMap; // stores all the scenes
	// labels
	Label label;
	Label $label;
	Label pcards;
	Label bcards;
	Label currentwinlbl;
	Label totalwinlbl;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		// initializes all the widgets and adds style
		primaryStage.setTitle("Client");
		info = new BaccaratInfo();
		start = new Button("START");
		start.setStyle("-fx-background-color: green");
		IP = new TextField();
		bid = new TextField();
		bid.setPromptText("Enter Your Bid Amount");
		bid.setPrefHeight(30);
		Port = new TextField();
		Port.setStyle("-fx-background-color: black; -fx-text-fill: white;");
		Port.setPromptText("Enter Port Number");
		IP.setPromptText("Enter IP Address");
		IP.setStyle("-fx-background-color: black; -fx-text-fill: white;");
		newGame = new Button("New Game");
		newGame.setStyle("-fx-background-color: green");
		startGame = new Button("Start Game");
		startGame.setPrefSize(280, 40);
		startGame.setStyle("-fx-background-color: green; -fx-font-size: 20px");
		exit = new Button("Exit");
		exit.setStyle("-fx-background-color: green");
		
		
		Player = new Button("PLAYER");
		Player.setStyle("-fx-background-color: brown; -fx-font-size: 24px");
		Player.setPrefSize(250, 40);
		
		Banker = new Button("BANKER");
		Banker.setStyle("-fx-background-color: brown; -fx-font-size: 24px");
		Banker.setPrefSize(250, 40);
		
		Draw = new Button("DRAW");
		Draw.setStyle("-fx-background-color: brown; -fx-font-size: 24px");
		Draw.setPrefSize(250, 40);
		
		currentBet = new TextField();
		currentBet.setPrefHeight(40);
		totalWinnings = new TextField();
		totalWinnings.setPrefHeight(40);
		gameinfo = new ListView<>();
		gameinfo.setPrefHeight(230);
		gameinfo.setStyle("-fx-control-inner-background: red; -fx-font-size: 20px");
		pcard1 = new ListView<>();
		pcard2 = new ListView<>();
		pcard3 = new ListView<>();
		bcard1 = new ListView<>();
		bcard2 = new ListView<>();
		bcard3 = new ListView<>();
		pcard1.setPrefHeight(335);
		pcard2.setPrefHeight(335);
		pcard3.setPrefHeight(335);
		bcard1.setPrefHeight(335);
		bcard2.setPrefHeight(335);
		bcard3.setPrefHeight(335);
		pcard1.setStyle("-fx-border-width: 6; -fx-border-color: black;");
		pcard2.setStyle("-fx-border-width: 6; -fx-border-color: black;");
		pcard3.setStyle("-fx-border-width: 6; -fx-border-color: black;");
		bcard1.setStyle("-fx-border-width: 6; -fx-border-color: black;");
		bcard2.setStyle("-fx-border-width: 6; -fx-border-color: black;");
		bcard3.setStyle("-fx-border-width: 6; -fx-border-color: black;");
		
		label = new Label("WELCOME TO BACCARAT GAME");
		label.setPrefSize(400, 60);
		label.setStyle("-fx-background-color: yellow; -fx-font-size: 19px; -fx-font-weight: bold;");
		$label = new Label("  BID: $  ");
		$label.setPrefHeight(30);
		$label.setStyle("-fx-control-inner-background: green; -fx-font-size: 24px");
		$label.setStyle("-fx-background-color: white");
		currentwinlbl = new Label(" CURRENT WIN: $ ");
		currentwinlbl.setPrefHeight(40);
		currentwinlbl.setStyle("-fx-background-color: white");
		totalwinlbl = new Label(" TOTAL WINNINGS: $ ");
		totalwinlbl.setPrefHeight(40);
		totalwinlbl.setStyle("-fx-background-color: white");
		pcards = new Label("    PLAYER CARDS");
		pcards.setPrefSize(200, 40);
		pcards.setStyle("-fx-background-color: yellow; -fx-font-size: 18px");
		bcards = new Label("    BANKER CARDS");
		bcards.setPrefSize(200, 40);
		bcards.setStyle("-fx-background-color: yellow; -fx-font-size: 18px");
		
		startGame.setDisable(true);
		currentBet.setEditable(false);
		totalWinnings.setEditable(false);
		
		exit.setOnAction(e -> Platform.exit());  // quits the program
		
		// starts the connection
		start.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				int port;
				try {
					port = Integer.parseInt(Port.getText());
				}
				catch (Exception e) {
					port = -1;
				}
				clientConnection = new Client(new Consumer<Serializable>() {
					// changes the Scene
					public void accept(Serializable data) {
						if (data == "Success") {
							Platform.runLater(()->{primaryStage.setScene(sceneMap.get("game")); primaryStage.setMaximized(true);});
						} else {
							Platform.runLater(()->{IP.setText("Invalid IP Address or Port");
												Port.setText("Invalid IP Address or Port");});
						}
					}
				}, new Consumer<Serializable>() {
					// updates the GUI with finished game info
					public void accept(Serializable data) {
						PauseTransition pause = new PauseTransition(Duration.seconds(4));
						BaccaratInfo info = (BaccaratInfo) data;
						if (info.playerHand.size() != 0) {
							// adds the cards
							Platform.runLater(()->pcard1.getItems().add(loadImage(String.valueOf(info.playerHand.get(0).suite),		
									info.playerHand.get(0).value)));

							Platform.runLater(()->pcard2.getItems().add(loadImage(String.valueOf(info.playerHand.get(1).suite),		
									info.playerHand.get(1).value)));
							
							Platform.runLater(()->bcard1.getItems().add(loadImage(String.valueOf(info.bankerHand.get(0).suite),		
									info.bankerHand.get(0).value)));

							Platform.runLater(()->{bcard2.getItems().add(loadImage(String.valueOf(info.bankerHand.get(1).suite),		
									info.bankerHand.get(1).value));});
							
							Platform.runLater(()->{pause.play();
								pause.setOnFinished(new EventHandler<ActionEvent>() {
									public void handle(ActionEvent event) {
										// updates the rest of graphics
										updateGraphics(info, pause);
									}
										
								});});
				}}}, IP.getText(), port);
				clientConnection.start();  // starts a new Client thread
				currentBet.setText(String.valueOf(info.currentBet)); 
				totalWinnings.setText(String.valueOf(info.totalWinnings));
			}
		});
		
		// selects Player to bet on
		Player.setOnAction(e -> {Player.setDisable(true); Banker.setDisable(true); Draw.setDisable(true);
			startGame.setDisable(false); bid.setEditable(false);
			double value;
			try {
				value = Double.parseDouble(bid.getText());
			}
			catch (Exception e2){
				value = 0.0;
			}
			info.setBaccaratInfoClient("Player", value);
		});
		
		// selects Banker to bet on
		Banker.setOnAction(e -> {Player.setDisable(true); Banker.setDisable(true); Draw.setDisable(true);
			startGame.setDisable(false); bid.setEditable(false);
			double value;
			try {
				value = Double.parseDouble(bid.getText());
			}
			catch (Exception e2){
				value = 0.0;
			}
			info.setBaccaratInfoClient("Banker", value);
		});
		
		// selects Draw to bet on
		Draw.setOnAction(e -> {Player.setDisable(true); Banker.setDisable(true); Draw.setDisable(true);
			startGame.setDisable(false); bid.setEditable(false);
			double value;
			try {
				value = Double.parseDouble(bid.getText());
			}
			catch (Exception e2){
				value = 0.0;
			}
			info.setBaccaratInfoClient("Banker", value);
		});
		
		// starts the game  by sending the info object
		startGame.setOnAction(e -> {startGame.setDisable(true);
			try {
				clientConnection.send(info);
				newGame.setDisable(true);
				
			}
			catch (Exception e1){
				gameinfo.getItems().clear();
				gameinfo.getItems().add("Server Closed Down Unexpectedly");
			}
		});
		
		// creates a new game
		newGame.setOnAction(e -> {bid.clear(); bid.setEditable(true); Player.setDisable(false);
			Banker.setDisable(false); Draw.setDisable(false); pcard1.getItems().clear(); pcard2.getItems().clear();
			pcard3.getItems().clear(); bcard1.getItems().clear(); bcard2.getItems().clear(); bcard3.getItems().clear();
			info = new BaccaratInfo();
			info.totalWinnings = Double.parseDouble(totalWinnings.getText());
			currentBet.setText(String.valueOf(info.currentBet)); 
			totalWinnings.setText(String.valueOf(info.totalWinnings));
			gameinfo.getItems().add("The Client betted on " + info.Bet
					+ " for $" + info.currentBet + " and " + info.whoWon
					+ " won. Player Total was " + info.playerTotal + " and Banker Total was "
					+ info.bankerTotal);
			gameinfo.getItems().clear();
		});
		
		// creates the scene map
		sceneMap = new HashMap<String, Scene>();
		sceneMap.put("start", createStartGUI());
		sceneMap.put("game", createGameGUI());
		for (Scene s : sceneMap.values()) {
			s.getRoot().setStyle("-fx-font-family: 'serif'");
		}
		primaryStage.setScene(sceneMap.get("start"));
		primaryStage.show();
	}

	
	// helper method called to increase readability for start button eventhandler 
	private void updateGraphics(BaccaratInfo info, PauseTransition pause) {
		if (!(info.playerHand.size() == 3 || info.bankerHand.size() == 3)) {
			gameinfo.getItems().add("Natural Win");  // natural win
			pause.play();
			pause.setOnFinished(e->{totalWinnings.setText(String.valueOf(info.totalWinnings));
			gameinfo.getItems().add("The Client betted on " + info.Bet
					+ " for $" + info.currentBet + " and " + info.whoWon
					+ " won. Player Total was " + info.playerTotal + " and Banker Total was "
					+ info.bankerTotal + "\nClick on NEW GAME to bet again.");});
			newGame.setDisable(false);
		} else {
			gameinfo.getItems().add("No Natural Win");  // no natural win
			pause.play();
			pause.setOnFinished(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					if (info.playerHand.size() == 3) {
						gameinfo.getItems().add("Player Got Additional Card");
						pcard3.getItems().add(loadImage(String.valueOf(info.playerHand.get(2).suite),		
								info.playerHand.get(2).value));
					} else {
						gameinfo.getItems().add("Player Didn't Get Additional Card");
					}
					pause.play();
					pause.setOnFinished(new EventHandler<ActionEvent>() {
						public void handle(ActionEvent event) { // adds the additional card images(s)
							if (info.bankerHand.size() == 3) {
								gameinfo.getItems().add("Banker Got Additional Card");
								bcard3.getItems().add(loadImage(String.valueOf(info.bankerHand.get(2).suite),		
										info.bankerHand.get(2).value));
							}
							else {
								gameinfo.getItems().add("Banker Didn't Get Additional Card");
							}
							pause.play();
							pause.setOnFinished(e->{currentBet.setText(String.valueOf(info.currentBet)); 
							totalWinnings.setText(String.valueOf(info.totalWinnings));
							gameinfo.getItems().add("The Client betted on " + info.Bet
									+ " for $" + info.currentBet + " and " + info.whoWon
									+ " won. Player Total was " + info.playerTotal + " and Banker Total was "
									+ info.bankerTotal + "\nClick NEW GAME to bet again.");});
							newGame.setDisable(false);
					}});
				}
			});											
			
		}
		
	}
	
	// returns an ImageView for a given card
	public ImageView loadImage(String suite, int value) {
		String str = "";
		if (value == 11) {
			str = "jack_of_" + suite + "s2";
		} else if (value == 12) {
			str = "queen_of_" + suite + "s2";
		} else if (value == 13) {
			str = "king_of_" + suite + "s2";
		} else if (value == 1) {
			str = "ace_of_" + suite + "s";
		} else {
			str = value + "_of_" + suite + "s";
		}
		return new ImageView(new Image(str + ".png", 265, 265, true, false));
	}
	
	// returns the start Scene
	public Scene createStartGUI() {
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(0, 100, 0, 100));
		VBox vbox = new VBox(new VBox(label, IP, Port, start));
		vbox.setSpacing(50);
		vbox.setAlignment(Pos.CENTER);
		pane.setCenter(vbox);
		pane.setBackground(new Background(new BackgroundImage(new Image("client.jpeg",750,600,false,true,false), null, null, null, null)));
		return new Scene(pane, 750, 600);
	}

	// returns the Game Scene
	public Scene createGameGUI() {
		BorderPane pane = new BorderPane();
		HBox box1 = new HBox($label, bid);
		box1.setPadding(new Insets(15, 0, 0, 600));
		HBox box2 = new HBox(Player, Banker, Draw);
		box2.setSpacing(80);
		box2.setPadding(new Insets(0, 0, 0, 250));
		HBox box3 = new HBox(startGame);
		box3.setPadding(new Insets(0, 0, 0, 570));
		VBox vbox = new VBox(box1, box2, box3);
		vbox.setSpacing(20);
		HBox labelbox = new HBox(pcards);
		labelbox.setPadding(new Insets(0, 500, 0, 300));
		HBox labelbox2 = new HBox(labelbox, bcards);
		labelbox2.setPadding(new Insets(0, 0, 10, 0));
		pane.setTop(new VBox(new HBox(newGame, exit), vbox));
		HBox cards1 = new HBox(pcard1, pcard2, pcard3);
		HBox cards2 = new HBox(bcard1, bcard2, bcard3);
		cards1.setPadding(new Insets(0, 100, 10, 20));
		cards2.setPadding(new Insets(0, 0, 10, 20));
		cards1.setSpacing(5);
		cards2.setSpacing(5);
		pane.setCenter(new VBox(labelbox2, new HBox(cards1, cards2), gameinfo));
		
		HBox b1 = new HBox(currentwinlbl, currentBet);
		b1.setPadding(new Insets(0, 870, 0, 0));
		
		pane.setBottom(new HBox(b1, totalwinlbl, totalWinnings));
		
		pane.setBackground(new Background(new BackgroundImage(new Image("client1.png",1450,850,false,false,false), null, null, null, null)));
		return new Scene(pane, 1800, 850);
	}

}