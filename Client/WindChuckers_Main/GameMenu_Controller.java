package WindChuckers_Main;

import WindChuckers_Main.WindChuckers;
import WindChuckers_Main.Model_Extend.Board;
import WindChuckers_Main.Model_Extend.Field;
import WindChuckers_Main.Model_Extend.Movement;
import WindChuckers_Main.Model_Extend.Player;
import WindChuckers_Main.Model_Extend.Position;
import WindChuckers_Main.Model_Extend.Tower;
import WindChuckers_Main.Model_Extend.normalTower;
import WindChuckers_Main.Model_Extend.sumoTower;
import WindChuckers_Main.AI.AI;
import abstractClasses.Controller;
import commonClasses.ServiceLocator;
import commonClasses.Translator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
	private normalTower normalTower;
	private sumoTower sumoTower;
	private Position position;
	private AI ai;
	private Player player; 

	public GameMenu_Controller(GameMenu_Model model, GameMenu_View view, Board board, Movement movement, Position position, normalTower normalTower, sumoTower sumoTower, AI ai, Player player) {
		super(model, view);
		this.board = board;
		this.movement = movement;
		this.position = position;
		this.normalTower = normalTower;
		this.sumoTower = sumoTower;
		this.ai = ai;
		this.player = player;
		serviceLocator = ServiceLocator.getServiceLocator();
		TowerHandler towerHandler = new TowerHandler(); // Anonym Class to handle the tower events
		
		
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
		
		
		/**
		 * Set Towers on action
		 * @author robin
		 */

		for(int y = 0; y < GameMenu_Model.DIMENSION; y++){
			for(int x = 0; x < GameMenu_Model.DIMENSION; x++){
				if(view.getTowersP1()[x][y]!=null){
					view.getTowersP1()[x][y].setOnAction(towerHandler);
				}}}
		
		for(int y = 0; y < GameMenu_Model.DIMENSION; y++){
			for(int x = 0; x < GameMenu_Model.DIMENSION; x++){
				if(view.getTowersP2()[x][y]!=null){
					view.getTowersP2()[x][y].setOnAction(towerHandler);
					view.getTowersP2()[x][y].setDisable(true); // entscheidet wer beginnt, soll später vom User bestimmt werden
				}}}
		
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


//	/**
//	 * Setters for Model-Extend
//	 * @author L.Weber
//	 */
//	public void setBoard(Board board){
//		this.board = board;
//	}
//	public void setMovement(Movement movement){
//		this.movement = movement;
//	}
//	public void setTower(Tower tower){
//		this.tower = tower;
//	}
//	public void setPosition(Position position){
//		this.position = position;
//	}
//	public void setAI(AI ai){
//		this.ai = ai;
//	}
	
	protected class TowerHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event){
			Tower tower = (Tower) event.getSource();
			model.getPlayer1().setOnTurn(true);
			model.getPlayer2().setOnTurn(false);
			
			if(model.getPlayer1().isOnTurn()){	
				for(int y = 0; y < GameMenu_Model.DIMENSION; y++){
					for(int x = 0; x < GameMenu_Model.DIMENSION; x++){
						if(view.getTowersP2()[x][y]!=null){
							view.getTowersP2()[x][y].setDisable(true);
					}}}
				
				// Activate possible fields where the tower can be moved to
				tower.showPossibleMoves(view.getFields(), view.getGameBoard(), view.getTowersP1(), view.getTowersP2());
				
				

			}
			
	if(model.getPlayer2().isOnTurn()){	
				for(int y = 0; y < GameMenu_Model.DIMENSION; y++){
					for(int x = 0; x < GameMenu_Model.DIMENSION; x++){
						if(view.getTowersP1()[x][y]!=null){
							view.getTowersP1()[x][y].setDisable(true);
					}}}
				
				// Activate possible fields where the tower can be moved to
				tower.showPossibleMoves(view.getFields(), view.getGameBoard(), view.getTowersP1(), view.getTowersP2());


			}

		}}}

