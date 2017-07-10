package WindChuckers_Main;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import com.sun.media.jfxmedia.logging.Logger;

import Client.ClientThreadForServer;
import Login.LoginController;
import Message.Message;
import Message.Message.MessageType;
import WindChuckers_Main.WindChuckers;
import WindChuckers_Main.Model_Extend.Board;
import WindChuckers_Main.Model_Extend.Field;
import WindChuckers_Main.Model_Extend.Movement;
import WindChuckers_Main.Model_Extend.Player;
import WindChuckers_Main.Model_Extend.Position;
import WindChuckers_Main.Model_Extend.Tower;
import WindChuckers_Main.Model_Extend.normalTower;
import WindChuckers_Main.Model_Extend.sumoTower;
import abstractClasses.Controller;
import commonClasses.ServiceLocator;
import commonClasses.Translator;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public class GameMenu_Controller extends Controller<GameMenu_Model, GameMenu_View> {
	private ServiceLocator serviceLocator;
	private Translator t;
	private WindChuckers windChuckers;
	private Board board;
	private Movement movement;
	private Tower tower;
	private normalTower normalTower;
	private sumoTower sumoTower;
	private Position position;
	private Player player;
	private ClientThreadForServer clientServer;
	private HashMap<Integer, ArrayList<String>> userMap;
	private long stopTimer;

	public GameMenu_Controller(GameMenu_Model model, GameMenu_View view, Board board, Movement movement,
			Position position, normalTower normalTower, sumoTower sumoTower, Player player) {
		super(model, view);
		this.board = board;
		this.movement = movement;
		this.position = position;
		this.normalTower = normalTower;
		this.sumoTower = sumoTower;
		this.player = player;
		this.clientServer = ClientThreadForServer.getClientServer();
		serviceLocator = ServiceLocator.getServiceLocator();
		model.sendMessage(new Message(MessageType.DBMessage, 0)); // Get
																	// User-Data
		TowerHandler towerHandler = new TowerHandler(); // Anonym Class to
														// handle the tower
														// events
		model.getPlayer1().setOnTurn(true); // Je nach dem wer anfängt, müssen
											// wir noch implementieren

		/**
		 * For End the Application
		 * 
		 * @author L.Weber
		 */
		view.menuFileExit.setOnAction(e -> {
			serviceLocator.getLogger().info("Close the Application");
			cleanUp();
		});

		/**
		 * For Help
		 * 
		 * @author L.Weber
		 */
		view.menuHelpAbout.setOnAction((event) -> {
			serviceLocator.getLogger().info("Help started");
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(t.getString("HelpAboutTitle"));
			alert.setHeaderText(t.getString("HelpAboutHeader"));
			alert.setContentText(t.getString("HelpAboutMessage"));
			alert.showAndWait();
		});

		serviceLocator = ServiceLocator.getServiceLocator();
		t = serviceLocator.getTranslator();
		serviceLocator.getLogger().info("Application controller initialized");

		/**
		 * Start the Lobby-View
		 * 
		 * @author L.Weber
		 */
		view.menuFileLobby.setOnAction(e -> {
			windChuckers = WindChuckers.getWindChuckers();
			windChuckers.startLobby();
		});

		/**
		 * Start the Login-View
		 * 
		 * @author L.Weber
		 */
		view.menuFileLogin.setOnAction(e -> {
			serviceLocator.getLogger().info("Start Login");
			windChuckers = WindChuckers.getWindChuckers();
			windChuckers.startLogin();
		});

		/**
		 * Start the Friends-View
		 * 
		 * @author L.Weber
		 */
		view.menuFileFriends.setOnAction(e -> {
			serviceLocator.getLogger().info("Start Friends");
			windChuckers = WindChuckers.getWindChuckers();
			windChuckers.startFriends();
		});

		/**
		 * Start the MainMenu
		 * 
		 * @author L.Weber
		 */
		view.menuMainMenu.setOnAction(e -> {
			serviceLocator.getLogger().info("Start MainMenu");
			windChuckers = WindChuckers.getWindChuckers();
			windChuckers.startMainMenu();
		});

		/**
		 * Start the Client-View
		 * 
		 * @author L.Weber
		 */
		view.menuClientGUI.setOnAction(e -> {
			serviceLocator.getLogger().info("Start Client");
			windChuckers = WindChuckers.getWindChuckers();
			windChuckers.getStartetClient();
		});

		/**
		 * All for the chat
		 * 
		 * @author L.Weber
		 */
		view.chatPanel.heightProperty().addListener((observable, oldValue, newValue) -> {
			view.stage.sizeToScene();
		});

		// Watch the client for ChatMessage
		clientServer.getChatMessageProperty().addListener((obervable, oldValue, newValue) -> {
			this.serviceLocator.getLogger().info("Message from Chat is arrived");
			updateGUI(newValue);
		});

		// Send-Button send Message to Server
		view.btnSend.setOnAction(e -> {
			String input = model.getUserNameString();
			input += ": " + view.txtChat.getText();
			model.messageContructorForChat(input);
			view.txtChat.clear();
		});

		// Enter for send the chat message
		view.txtChat.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER)) {
				String input = model.getUserNameString();
				input += ": " + view.txtChat.getText();
				model.messageContructorForChat(input);
				view.txtChat.clear();
			}
		});

		/**
		 * For the User-Menu
		 * 
		 * @author L.Weber
		 */
		model.getDBRequest().addListener((oservable, oldValue, newValue) -> {
			userMap = model.getUserMap();
			fillPersonList();
		});

		clientServer.getHashCode().addListener((observable, oldValue, newValue) -> {
			model.setHashCode(clientServer.getHashCodeInt());
		});

		view.btnStartTimer.setOnAction(e -> {
			stopTimer = Long.parseLong(view.tfTimerTime.getText());
			timer.start();
		});

		/**
		 * Start the AI Configurator
		 * 
		 * @author L.Weber
		 */
		view.menuFileAI.setOnAction(e -> {
			serviceLocator.getLogger().info("Start AIConfigurator");
			windChuckers = WindChuckers.getWindChuckers();
			windChuckers.startAI();
		});

		model.getMoveProperty().addListener((observable, oldValue, newValue) -> {
			serviceLocator.getLogger().info("The Move Message reaches the GameMenu_Controller");
			// TODO here is now the move - with model.getStartColumn/Row
			// model.getEndColumn/Row it gets the start or end x-Coordinates or
			// y-Coordinates

		});

		view.menuTutorial.setOnAction(e -> {
			serviceLocator.getLogger().info("Start Tutorial");
			windChuckers = WindChuckers.getWindChuckers();
			windChuckers.startTutorial();
		});

		/**
		 * Set Towers on action
		 * 
		 * @author robin
		 */

		for (int y = 0; y < GameMenu_Model.DIMENSION; y++) {
			for (int x = 0; x < GameMenu_Model.DIMENSION; x++) {
				if (view.getTowersP1()[x][y] != null) {
					view.getTowersP1()[x][y].setOnAction(towerHandler);
				}
			}
		}

		for (int y = 0; y < GameMenu_Model.DIMENSION; y++) {
			for (int x = 0; x < GameMenu_Model.DIMENSION; x++) {
				if (view.getTowersP2()[x][y] != null) {
					view.getTowersP2()[x][y].setOnAction(towerHandler);
					view.getTowersP2()[x][y].setDisable(true); // Je nach dem
																// wer anfängt
																// -> müssen wir
																// noch
																// implementieren
				}
			}
		}
	}

	/**
	 * Clean all up
	 * 
	 * @author L.Weber
	 */
	private void cleanUp() {
		serviceLocator.getLogger().info("Clean up");
		view.stop();
		// Implement more Methods for Cleanup
	}

	/**
	 * Update the Chat
	 * 
	 * @param newValue
	 * @author L.Weber
	 */
	private void updateGUI(String newValue) {
		Platform.runLater(() -> {
			view.txtMessages.appendText("\n" + clientServer.getChatMessageInString());
		});
	}

	public void fillPersonList() {
		// Platform.runLater(() -> {
		for (Entry<Integer, ArrayList<String>> ee : userMap.entrySet()) {
			ArrayList<String> searchList = ee.getValue();
			this.serviceLocator.getLogger().info("Fill Person List");
			// is for searching a entry
			try {
				if (model.getUserNameString().equals(searchList.get(0))) {
					view.tfPointsUser1.setText(searchList.get(4));
					view.lblUser1.setText(model.getUserNameString());
					// Add also the other Player when game is started the we
					// shout know how's the other
				}
			} catch (Exception ex) {
				this.serviceLocator.getLogger().warning("Not found");
			}
		}
		// });
	}

	/**
	 * Timer for the Game - GameClock
	 * 
	 * @author L.Weber
	 */
	AnimationTimer timer = new AnimationTimer() {
		private long timestamp;
		private long time = 0;
		private long fraction = 0;

		@Override
		public void start() {
			timestamp = System.currentTimeMillis() - fraction;
			super.start();
		}

		@Override
		public void stop() {
			super.stop();
			fraction = System.currentTimeMillis() - timestamp;
		}

		@Override
		public void handle(long now) {
			long newTime = System.currentTimeMillis();
			if (timestamp + 1000 <= newTime) {
				long deltaT = (newTime - timestamp) / 1000;
				time += deltaT;
				timestamp += 1000 * deltaT;
				if(time <= 59){
					view.tfTimer.setText(Long.toString(time) + " Seconds");
				}
				if(time >= 60){
					Duration dSec = Duration.ofSeconds(time);
					long dMin = dSec.toMinutes();
					view.tfTimer.setText(dMin + " Minutes " + Long.toString(time - (dMin * 60)) + " Seconds");
				}

				if (time >= TimeUnit.MINUTES.toSeconds(stopTimer)){
					stop();
				}
			}
		}
	};

	// /**
	// * Setters for Model-Extend
	// * @author L.Weber
	// */
	// public void setBoard(Board board){
	// this.board = board;
	// }
	// public void setMovement(Movement movement){
	// this.movement = movement;
	// }
	// public void setTower(Tower tower){
	// this.tower = tower;
	// }
	// public void setPosition(Position position){
	// this.position = position;
	// }
	// public void setAI(AI ai){
	// this.ai = ai;
	// }

	protected class TowerHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			Tower tower = (Tower) event.getSource();

			if (model.getPlayer1().isOnTurn()) {

				// Activate possible fields where the tower can be moved to
				tower.showMoves(view.getFields(), view.getGameBoard(), view.getTowersP1(), view.getTowersP2());

				// Handler for the fields. The Player chooses a field and the
				// tower will move
				for (int y = 0; y < GameMenu_Model.DIMENSION; y++) {
					for (int x = 0; x < GameMenu_Model.DIMENSION; x++) {
						view.getFields()[x][y].setOnAction((FieldHandler) -> {
							Field field = (Field) FieldHandler.getSource();
							tower.move(view.getFields(), view.getGameBoard(), view.getTowersP1(), view.getTowersP2(),
									field, model.getPlayer1(), model.getPlayer2());
						});
					}
				}
			}

			if (model.getPlayer2().isOnTurn()) {

				// Activate possible fields where the tower can be moved to
				tower.showMoves(view.getFields(), view.getGameBoard(), view.getTowersP1(), view.getTowersP2());

				// Handler for the fields. The Player chooses a field and the
				// tower will move
				for (int y = 0; y < GameMenu_Model.DIMENSION; y++) {
					for (int x = 0; x < GameMenu_Model.DIMENSION; x++) {
						view.getFields()[x][y].setOnAction((FieldHandler) -> {
							Field field = (Field) FieldHandler.getSource();
							tower.move(view.getFields(), view.getGameBoard(), view.getTowersP1(), view.getTowersP2(),
									field, model.getPlayer1(), model.getPlayer2());
						});
					}
				}
			}

		}
	}
}
