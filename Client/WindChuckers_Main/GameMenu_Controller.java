package WindChuckers_Main;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import com.sun.media.jfxmedia.logging.Logger;

import Client.ClientThreadForServer;
import Friends.FriendsController;
import Login.LoginController;
import Login.LoginModel;
import Message.Message;
import Message.Message.MessageType;
import WindChuckers_Main.WindChuckers;
import WindChuckers_Main.Model_Extend.Board;
import WindChuckers_Main.Model_Extend.Field;
import WindChuckers_Main.Model_Extend.Movement;
import WindChuckers_Main.Model_Extend.Player;
import WindChuckers_Main.Model_Extend.Position;
import WindChuckers_Main.Model_Extend.Tower;
import WindChuckers_Main.newRoundPopup.newRoundView;
import WindChuckers_Main.Model_Extend.SumoTower;
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
	private SumoTower SumoTower;
	private Position position;
	private Player player;
	private ClientThreadForServer clientServer;
	private HashMap<Integer, ArrayList<String>> userMap;
	private long stopTimer;

	public GameMenu_Controller(GameMenu_Model model, GameMenu_View view, newRoundView newRoundView, Board board, Movement movement,
			Position position, Player player) {
		super(model, view);
		this.board = board;
		this.movement = movement;
		this.position = position;
		this.player = player;
		this.clientServer = ClientThreadForServer.getClientServer();
		serviceLocator = ServiceLocator.getServiceLocator();
		model.messageConstructorForDB(91); //For the random start
		model.sendMessage(new Message(MessageType.DBMessage, 0)); // Get
																	// User-Data
		TowerHandler towerHandler = new TowerHandler(); // Anonym Class to
														// handle the tower
														// events
		model.getPlayer1().setOnTurn(true); // Je nach dem wer anfängt, müssen
											// wir noch implementieren
		// get the friendsList
		model.setFriends(clientServer.getFriendsList());
		//send the hash-code and the name to the server for key / value
		long hash = GameMenu_View.getHashCode();
		model.messageConstructorForName(hash, LoginModel.getUserName());
		
		//generate a new Player with this hash
		model.buildNewPlayer(hash);
		
		//set the right player to the right place
		model.setPlayer(ClientThreadForServer.getHashCodeThis(), ClientThreadForServer.getHashCodeFriend());
		
		/**
		 * For End the Application
		 * 
		 * @author L.Weber
		 */
		view.menuFileExit.setOnAction(e -> {
			serviceLocator.getLogger().info("Close the Application");
			view.stop();
			windChuckers = WindChuckers.getWindChuckers();
			windChuckers.startMainMenuView();
		});
		
		/**
		 * Starts a new Game
		 * @author L.Weber
		 */
		view.menuFileRestart.setOnAction(e -> {
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
		 * Start the Friends-View
		 * 
		 * @author L.Weber
		 */
		view.menuFileFriends.setOnAction(e -> {
			serviceLocator.getLogger().info("Start Friends");
			windChuckers = WindChuckers.getWindChuckers();
			windChuckers.startFriends();
			view.getStage().hide();
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
			view.getStage().close();
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
		 * For give the details for upgrade a tower
		 * @author L.Weber
		 */
		clientServer.getUpgrade().addListener((observable, oldValue, newValue) -> {
						
			Tower[][] towers;
			if(model.getPlayerType() == 1){
				towers = view.getTowersP1();
			} else {
				towers = view.getTowersP2();
			}
			Tower tower = towers[model.getEndColumn()][model.getEndRow()];
			Field[][] fields = view.getFields();
			GridPane gameBoard= view.getGameBoard();
			Player player1 = model.getPlayer1();
			Player player2 = model.getPlayer2();
			Tower[][] tower1 = view.getTowersP1();
			Tower[][] tower2 = view.getTowersP2();

			Tower towerUpgrade = towers[clientServer.getXCoordinateUpgrade()][clientServer.getYCoordinateUpgrade()];
			if(tower.getGems() == clientServer.getGems()) {
				//update only if the update not already done
				towerUpgrade.upgradeTower(view.getFields(), towerUpgrade, clientServer.getXCoordinateUpgrade(), clientServer.getYCoordinateUpgrade(), clientServer.getGems(), view.getTowersP1(), view.getTowersP2());
			}
		});
		
		/**
		 * For know - now is time for a new round
		 * @author L.Weber
		 */
		clientServer.getNewRound().addListener((observable, oldValue, newValue) -> {
			this.buildNewRound(clientServer.getNewRoundLeftRight());
		});
		
		/**
		 * Set the randomStart Integer for start the game
		 */
		clientServer.getRandomStart().addListener((observable, oldValue, newValue) -> {
			if(FriendsController.getRandomStart() == 0) {
				FriendsController.setRandomStart(clientServer.getRandomStartInt());
				serviceLocator.getLogger().info("Random-Object: " + clientServer.getRandomStartInt());
			}
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
		
		// Friendslist overwatch
		clientServer.getDBFriends().addListener((observable, oldValue, newValue) -> {
			model.setFriends(clientServer.getFriendsList());
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
		
		//this client has to know what is his hashcode for the server
		clientServer.getHashCode().addListener((observable, oldValue, newValue) -> {
			model.setHashCode(clientServer.getHashCodeInt());
		});

		//start the timer
		view.btnStartTimer.setOnAction(e -> {
			//send the time-cap also to the other player the start is then when the message come back from the server
			model.messageConstructorForTime(Long.parseLong(view.tfTimerTime.getText()));
		});
		
		//is there an timer?
		clientServer.getTime().addListener((observable, oldValue, newValue) -> {
			view.tfTimerTime.setText(Long.toString(clientServer.getTimeLong()));
			stopTimer = Long.parseLong(view.tfTimerTime.getText());
			timer.start();
		});
		
		// if round is given - send it to the other players
		view.tfRoundMax.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue){
			} else {
				model.messageConstructorForDBUpdate(88, Integer.parseInt(view.tfRoundMax.getText()));
			}
		});
		//only numbers allowed
		view.tfRoundMax.textProperty().addListener((observable, oldValue, newValue)-> {
			if(!newValue.matches("\\d")) {
				view.tfRoundMax.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});
		
		// set the round from the other and make it not changable
		clientServer.getRound().addListener((observable, oldValue, newValue) -> {
			view.tfRoundMax.setText(Integer.toString(clientServer.getRoundInt()));
			view.tfRoundMax.setEditable(false);
		});
		
		//ready To Play
		clientServer.getReadyToPlay().addListener((observable, oldValue, newValue) -> {
			windChuckers.startApp();
		});
		
		//start a Game with Friendsd
		clientServer.getStartGame().addListener((observable, oldValue, newValue) -> {
			windChuckers = WindChuckers.getWindChuckers();
			Platform.runLater(() -> {
				if(!windChuckers.getGameMenuView().getStage().isShowing()) {
					windChuckers.startApp();
				}
			});
			serviceLocator.getLogger().info("Boolean for start the game is here, start now");	
		});
		
		//Watch the model for a Single-Game - Disable if its true
		model.getDisableSingeleGame().addListener((observable, oldValue, newValue) -> {
			view.userBox.setDisable(true);
		});
		
		//Watch the model for a Friends-Game - Enable if its true
		model.getEnableFriendsGame().addListener((observable, oldValue, newValue) -> {
			if(view.userBox.isDisable()) {
				view.userBox.setDisable(false);
			}
		});
		
		// check the rounds when change it - is the round equals to the maxround then stop the game
		view.tfRound.textProperty().addListener((observable, oldValue, newValue) -> {
			int max = Integer.parseInt(view.tfRoundMax.getText());
			int acutal = Integer.parseInt(view.tfRound.getText());
			if(acutal >= acutal){
				disableAll();
			}
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
		
		/**
		 * When the moves are here, show it in the GUI
		 * @author L.Weber
		 */
		model.getMoveProperty().addListener((observable, oldValue, newValue) -> {
			serviceLocator.getLogger().info("The Move Message reaches the GameMenu_Controller");
			
			Tower[][] towers;
			if(model.getPlayerType() == 1){
				towers = view.getTowersP1();
			} else {
				towers = view.getTowersP2();
			}
			Tower tower = towers[model.getStartColumn()][model.getStartRow()];
			Field[][] fields = view.getFields();
			GridPane gameBoard= view.getGameBoard();
			Player player1 = model.getPlayer1();
			Player player2 = model.getPlayer2();
			Tower[][] tower1 = view.getTowersP1();
			Tower[][] tower2 = view.getTowersP2();
			
			serviceLocator.getLogger().info("Move coordinates: " + model.getStartColumn() + " " + model.getStartRow() + " " + model.getEndColumn() + " " + model.getEndRow() + " " + model.getPlayerType());
			if(!fields[model.getStartColumn()][model.getStartRow()].isEmpty()) {
				tower.move(view.getFields(), view.getGameBoard(), model.getPlayer1(), model.getPlayer2(), view.getTowersP1(), view.getTowersP2(), model.getStartColumn(), model.getStartRow(), model.getEndColumn(), model.getEndRow(), model.getPlayerType());
			}
		});

		view.menuTutorial.setOnAction(e -> {
			serviceLocator.getLogger().info("Start Tutorial");
			windChuckers = WindChuckers.getWindChuckers();
			windChuckers.startTutorial();
		});

		
//		Anmerkung LKu Hier müssen wir noch die Fälle nach einer neuen Runde implementieren.
//		Immer der Verlierer beginnt

		if (FriendsController.getRandomStart() == 2){
				model.getPlayer1().setOnTurn(true);
				model.getPlayer2().setOnTurn(false);
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
						view.getTowersP2()[x][y].setDisable(true);
					}
				}
			}
			
			}
		else if (FriendsController.getRandomStart() == 1){
			model.getPlayer1().setOnTurn(false);
			model.getPlayer2().setOnTurn(true);
		for (int y = 0; y < GameMenu_Model.DIMENSION; y++) {
			for (int x = 0; x < GameMenu_Model.DIMENSION; x++) {
				if (view.getTowersP2()[x][y] != null) {
					view.getTowersP2()[x][y].setOnAction(towerHandler);
				}
			}
		}

		for (int y = 0; y < GameMenu_Model.DIMENSION; y++) {
			for (int x = 0; x < GameMenu_Model.DIMENSION; x++) {
				if (view.getTowersP1()[x][y] != null) {
					view.getTowersP1()[x][y].setOnAction(towerHandler);
					view.getTowersP1()[x][y].setDisable(true); // Je nach dem
																// wer anfängt
																// -> müssen wir
																// noch
																// implementieren 
					}
				}
			}
		}else if (FriendsController.getRandomStart() == 0){
			model.getPlayer1().setOnTurn(false);
			model.getPlayer2().setOnTurn(true);
		for (int y = 0; y < GameMenu_Model.DIMENSION; y++) {
			for (int x = 0; x < GameMenu_Model.DIMENSION; x++) {
				if (view.getTowersP2()[x][y] != null) {
					view.getTowersP2()[x][y].setOnAction(towerHandler);
				}
			}
		}

		for (int y = 0; y < GameMenu_Model.DIMENSION; y++) {
			for (int x = 0; x < GameMenu_Model.DIMENSION; x++) {
				if (view.getTowersP1()[x][y] != null) {
					view.getTowersP1()[x][y].setOnAction(towerHandler);
					view.getTowersP1()[x][y].setDisable(true); // Je nach dem
																// wer anfängt
																// -> müssen wir
																// noch
																// implementieren
					}
				}
			}
		
		/**
		 *  Boolean Winner will be watched with a property. As soon as someone wins the win method will be started
		 *  @author robin
		 */
		model.Winner.addListener(
				(observable, oldValue, newValue) -> {
					if(newValue.intValue()==1){
						model.getPlayer1().setWins();
						this.win(model.getPlayer1().getWins());
					}
					
					if(newValue.intValue()==2){
						model.getPlayer2().setWins();
						this.win(model.getPlayer2().getWins());
					}
				});
		
		
		
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
		view.tfHashCode.setDisable(false);
		view.tfPointsUser1.setDisable(false);
		view.tfPointsUser2.setDisable(false);
		view.tfRound.setDisable(false);
		view.tfRoundMax.setDisable(false);
		view.tfTimer.setDisable(false);
		view.tfTimerTime.setDisable(false);
		view.getStage().close();
		Scene scene = view.create_GUI();
		view.setScene(scene);
		view.setModel(model);
		this.setView(view);
		this.setModel(model);
		view.start();
	}
	
	/**
	 * Disable all
	 * @author L.Weber
	 */
	private void disableAll(){
		serviceLocator.getLogger().info("Disable all");
		
		view.tfHashCode.setDisable(true);
		view.tfPointsUser1.setDisable(true);
		view.tfPointsUser2.setDisable(true);
		view.tfRound.setDisable(true);
		view.tfRoundMax.setDisable(true);
		view.tfTimer.setDisable(true);
		view.tfTimerTime.setDisable(true);
		view.GameBoard.setDisable(true);
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
					disableAll();
				}
			}
		}
	};

	/**
	 * This Handler controls the towers and fields 
	 * @author robin
	 *
	 */
	protected class TowerHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			Tower tower = (Tower) event.getSource();

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
	
	/**
	 * Build the game new
	 * @param newRoundLeftRight
	 * @author l.kunz
	 */
	private void buildNewRound(boolean newRoundLeftRight) {				
		Tower[][] towersP1Temp = new Tower[model.DIMENSION][model.DIMENSION];
		Tower[][] towersP2Temp = new Tower[model.DIMENSION][model.DIMENSION];				
			
			if (newRoundLeftRight){
				// Player 1 Towers will be added in a Temp Array
				int i = 0;
					for(int y = 0; y < 8; y++){
						for(int x = 0; x < 8; x++){
							if(view.fields[x][y].isEmpty() == false && view.towersP1[x][y] != null){
								towersP1Temp[i][7] = view.towersP1[x][y];
								i++;
							}
						}
					}
				
					// Player 2 Towers will be added in a Temp Array
					int k = 0;
					for(int y = 0; y < 8; y++){
						for(int x = 0; x < 8; x++){
							if(view.fields[x][y].isEmpty() == false && view.towersP2[x][y] != null){
								towersP2Temp[7-k][0] = view.towersP2[x][y];
								k++;
							}
							view.fields[x][y].setEmpty(true);
						}
					}
				
				} else if (!newRoundLeftRight){
						
					// Player 1 Towers will be added in a Temp Array
					int i = 0;
						for(int y = 0; y < 8; y++){
							for(int x = 0; x < 8; x++){
								if(view.fields[x][y].isEmpty() == false && view.towersP1[x][y] != null){
								towersP1Temp[7-i][7] = view.towersP1[x][y];
								i++;
								}
							}
						}
						
					// Player 2 Towers will be added in a Temp Array
					int k = 0;
						for(int y = 0; y < 8; y++){
							for(int x = 0; x < 8; x++){
								if(view.fields[x][y].isEmpty() == false && view.towersP2[x][y] != null){
									towersP2Temp[k][0] = view.towersP2[x][y];
									k++;
								}
								view.fields[x][y].setEmpty(true);
							}
						}
				}
				

			// Towers will be reset on a start position (right or left)
			for(int x = 0; x < GameMenu_Model.DIMENSION;x++){
				
				towersP1Temp[x][7].setxPosition(x);
				towersP1Temp[x][7].setyPosition(7);
				GridPane.setColumnIndex(towersP1Temp[x][7], x);
				GridPane.setRowIndex(towersP1Temp[x][7], 0);
					
				towersP2Temp[x][0].setxPosition(x);
				towersP2Temp[x][0].setyPosition(0);	
				GridPane.setColumnIndex(towersP2Temp[x][0], x);
				GridPane.setRowIndex(towersP2Temp[x][0], 7);
					
				view.towersP1 = towersP1Temp;
				view.towersP2 = towersP2Temp;
				view.fields[x][view.towersP1.length-1].setEmpty(false);
				view.fields[x][view.towersP2.length-view.towersP2.length].setEmpty(false);
	
			}
	}
	
	/**
	 * The whole win procedure as soon as someone reaches the back line
	 * @param win
	 * @author robin
	 */
	private void win(int win){
		//Win Procedure
		windChuckers = WindChuckers.getWindChuckers();
		Alert winMessage = new Alert(AlertType.INFORMATION);
		winMessage.setTitle("Gratulation!");

		if(model.getPlayer1().win()){
			winMessage.setHeaderText("Player 1 wins!");
			winMessage.showAndWait();
			view.resetGameBoard();
			view.getStage().close();
			windChuckers.startMainMenu();
		}
		
		else if(model.getPlayer2().win()){
			winMessage.setHeaderText("Player 2 wins!");
			winMessage.showAndWait();
			view.resetGameBoard();
			view.getStage().close();
			windChuckers.startMainMenu();
		}	
		
		if(!model.getPlayer1().win()&&!model.getPlayer2().win()){
			windChuckers.startNewRound();
			
			// a new Round with left order will be created
			newRoundView.leftPlay.setOnAction(e -> {
				this.buildNewRound(true);
				//also send this to the server
				model.messageConstructorForNewRound(true);
				model.Winner.set(0);
				((Stage)(((Button)e.getSource()).getScene().getWindow())).close();
			});
			
			// a new Round with right order will be created
			newRoundView.rightPlay.setOnAction(e -> {
				this.buildNewRound(false);
				//also send this to the server
				model.messageConstructorForNewRound(false);
				model.Winner.set(0);
				((Stage)(((Button)e.getSource()).getScene().getWindow())).close();
			});
}

		//update the DB
		int winsBefore = LoginModel.getWins();
		win = win + winsBefore;
		model.messageConstructorForWin(win, LoginModel.getUserName());
	}
	
	//}
	
	public void setView(GameMenu_View view){
		this.view = view;
	}
	
	public void setModel(GameMenu_Model model){
		this.model = model;
	}
}
