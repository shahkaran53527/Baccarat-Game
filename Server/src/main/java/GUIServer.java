import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// main application for Serve Class, handles GUI
public class GUIServer extends Application {

	TextField Port;  // for getting the port number
	Button start;  // for starting the server
	Button end;  // for shutting down the server
	Server serverConnection;  // server object
	HashMap<String, Scene> sceneMap;  // holds the two scenes
	ListView<String> totalClients;  // for displaying how many clients are currently on the server
	ListView<String> gameStatus;  // for displaying all other game information
	Label label;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Server");
		// sets up all the graphics
		start = new Button("START SERVER");
		start.setPrefSize(120, 40);
		start.setStyle("-fx-background-color: green; -fx-font-weight: bold;");
		end = new Button("SHUT DOWN SERVER");
		end.setStyle("-fx-background-color: red; -fx-font-weight: bold; -fx-border-color: black;");
		Port = new TextField();
		Port.setMaxWidth(150);
		totalClients = new ListView<String>();
		totalClients.setPrefHeight(100);
		totalClients.setStyle("-fx-control-inner-background: blue");
		gameStatus = new ListView<String>();
		gameStatus.setPrefHeight(700);
		gameStatus.setStyle("-fx-control-inner-background: yellow");
		Port.setPromptText("Enter Port Number");
		Port.setStyle("-fx-background-color: black; -fx-text-fill: white;");
		label = new Label("WELCOME TO BACCARAT GAME SERVER");
		label.setPrefSize(400, 60);
		label.setStyle("-fx-background-color: yellow; -fx-font-size: 19px; -fx-font-weight: bold;");
		start.setOnAction(e -> {
			int port;
			try { 
				port = Integer.parseInt(Port.getText());  // attempts to get port from textfield
			}
			catch (Exception e1) {
				port = -1;
			};
			primaryStage.setScene(sceneMap.get("gameinfo"));  // changes scene
			serverConnection = new Server(data->Platform.runLater(()-> {totalClients.getItems().clear();  // provide methods to update totalClients and gameStatus
					totalClients.getItems().add((String) data);}),
					data->Platform.runLater(()-> {gameStatus.getItems().add((String) data);}), port);
			totalClients.getItems().clear(); gameStatus.getItems().clear();});  // clears the scene
		
		end.setOnAction(e -> {serverConnection.closeDownServer();  // calls method to close down the server and changes scene
			primaryStage.setScene(sceneMap.get("start"));});
		
		sceneMap = new HashMap<String, Scene>();
		sceneMap.put("start", createServerGUI());  // beginning scene
		sceneMap.put("gameinfo", createGameInfoGUI());  // game status scene
		for (Scene s : sceneMap.values()) {
			s.getRoot().setStyle("-fx-font-family: 'serif'");
		}
		primaryStage.setScene(sceneMap.get("start"));
		primaryStage.show();
	}

	// creates the beginning scene
	public Scene createServerGUI() {
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(150, 50, 150, 50));
		pane.setBackground(new Background(new BackgroundImage(new Image("server.jpeg",500,500,false,true,true), null, null, null, null)));
		VBox vbox = new VBox(label, Port, start);
		vbox.setSpacing(15);
		vbox.setAlignment(Pos.BASELINE_CENTER);
		pane.setCenter(vbox);
		return new Scene(pane, 500, 500);
	}

	// creates the game status scene
	public Scene createGameInfoGUI() {
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(20));
		pane.setBackground(new Background(new BackgroundImage(new Image("server2.jpeg",1000,750,false,true,true), null, null, null, null)));
		VBox vbox = new VBox(end, gameStatus, totalClients);
		vbox.setSpacing(10);
		pane.setCenter(vbox);
		return new Scene(pane, 1000, 750);
	}

}
