package WindChuckers_Main;

import WindChuckers_Main.WindChuckers;
import WindChuckers_Main.Model_Extend.Board;
import WindChuckers_Main.Model_Extend.Movement;
import WindChuckers_Main.Model_Extend.Position;
import WindChuckers_Main.Model_Extend.Tower;
import WindChuckers_Main.AI.AI;
import abstractClasses.Controller;
import commonClasses.ServiceLocator;
import commonClasses.Translator;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

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
	private Position position;
	private AI ai;
	

	public GameMenu_Controller(GameMenu_Model model, GameMenu_View view) {
		super(model, view);
		serviceLocator = ServiceLocator.getServiceLocator();
		
		
		/**
		 * For End the Application
		 * @author L.Weber
		 */
		view.menuFileExit.setOnAction(e -> {
			serviceLocator.getLogger().info("Close the Application");
			cleanUp();
		});

		
		/**
		 * For Help
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
		 * @author L.Weber
		 */
		view.menuFileLobby.setOnAction(e -> {
			windChuckers = WindChuckers.getWindChuckers();
			windChuckers.startLobby();
		});
		
		/**
		 * Start the Login-View
		 * @author L.Weber
		 */
		view.menuFileLogin.setOnAction(e -> {
			serviceLocator.getLogger().info("Start Login");
			windChuckers = WindChuckers.getWindChuckers();
			windChuckers.startLogin();
		});
		
		/**
		 * Start the Friends-View
		 * @author L.Weber
		 */
		view.menuFileFriends.setOnAction(e -> {
			serviceLocator.getLogger().info("Start Friends");
			windChuckers = WindChuckers.getWindChuckers();
			windChuckers.startFriends();
		});
		
		/**
		 * Start the MainMenu
		 * @author L.Weber
		 */
		view.menuMainMenu.setOnAction(e -> {
			serviceLocator.getLogger().info("Start MainMenu");
			windChuckers = WindChuckers.getWindChuckers();
			windChuckers.startMainMenu();
		});
		
		/**
		 * Start the Client-View
		 * @author L.Weber
		 */
		view.menuClientGUI.setOnAction(e -> {
			serviceLocator.getLogger().info("Start Client");
			windChuckers = WindChuckers.getWindChuckers();
			windChuckers.startClient();
		});

	}
	
	/**
	 * Clean all up
	 * @author L.Weber
	 */
	private void cleanUp() {
		serviceLocator.getLogger().info("Clean up");
		view.stop();
		// Implement more Methods for Cleanup
	}


	/**
	 * Setters for Model-Extend
	 * @author L.Weber
	 */
	public void setBoard(Board board){
		this.board = board;
	}
	public void setMovement(Movement movement){
		this.movement = movement;
	}
	public void setTower(Tower tower){
		this.tower = tower;
	}
	public void setPosition(Position position){
		this.position = position;
	}
	public void setAI(AI ai){
		this.ai = ai;
	}

}
