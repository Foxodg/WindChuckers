package WindChuckers_Main;

import java.util.Locale;
import java.util.logging.Logger;
import WindChuckers_Main.Model_Extend.Board;
import WindChuckers_Main.Model_Extend.Field;
import WindChuckers_Main.Model_Extend.Tower;
import WindChuckers_Main.Model_Extend.normalTower;
import WindChuckers_Main.AI.AI;
import WindChuckers_Main.Model_Extend.Board;
import WindChuckers_Main.Model_Extend.Movement;
import WindChuckers_Main.Model_Extend.Player;
import WindChuckers_Main.Model_Extend.Position;
import WindChuckers_Main.Model_Extend.normalTower;
import WindChuckers_Main.Model_Extend.sumoTower;
import abstractClasses.View;
import commonClasses.ServiceLocator;
import commonClasses.Translator;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public class GameMenu_View extends View<GameMenu_Model> {
	private Board board;
	private Movement movement;
	private Position position;
	private normalTower normalTower;
	private sumoTower sumoTower;
	private AI ai;
	private Player player;
	
	private Menu menuFile;
	private Menu menuFileLanguage;
	private Menu menuHelp;
	private Menu menuClient;
	public MenuItem menuHelpAbout;
	public MenuItem menuFileRestart;
	public MenuItem menuFileExit;
	public MenuItem menuClientGUI;
	public MenuItem menuFileLobby;
	public MenuItem menuFileLogin;
	public MenuItem menuFileFriends;
	public MenuItem menuMainMenu;

	public GameMenu_View(Stage stage, GameMenu_Model model, Board board, Movement movement, Position position, normalTower normalTower, sumoTower sumoTower, AI ai, Player player) {
		super(stage, model);
		this.board = board;
		this.movement = movement;
		this.position = position;
		this.normalTower = normalTower;
		this.sumoTower = sumoTower;
		this.ai = ai;
		this.player = player;
		
		stage.setTitle("WindChuckers Kamisado");

		ServiceLocator.getServiceLocator().getLogger().info("Application view initialized");
	}

	@Override
	protected Scene create_GUI() {
		ServiceLocator sl = ServiceLocator.getServiceLocator();
		Logger logger = sl.getLogger();
		Translator t = sl.getTranslator();

		
		MenuBar menuBar = new MenuBar();
		menuFile = new Menu();
		menuFileLanguage = new Menu();
		menuFileRestart = new MenuItem();
		menuFileExit = new MenuItem();
		menuFileLobby = new MenuItem();
		menuFileLogin = new MenuItem();
		menuFileFriends = new MenuItem();
		menuMainMenu = new MenuItem();
		menuFile.getItems().addAll(menuFileRestart,menuFileLanguage,menuFileLobby, menuFileLogin, menuFileFriends, menuMainMenu,menuFileExit);

		for (Locale locale : sl.getLocales()) {
			MenuItem language = new MenuItem(locale.getLanguage());
			menuFileLanguage.getItems().add(language);
			language.setOnAction(event -> {
				sl.setTranslator(new Translator(locale.getLanguage()));
				updateTexts();
			});
		}

		menuHelp = new Menu();
		menuClient = new Menu();
		menuBar.getMenus().addAll(menuFile, menuHelp, menuClient);

		menuHelpAbout = new MenuItem();
		menuHelp.getItems().add(menuHelpAbout);
		

		menuClientGUI = new MenuItem();
		menuClient.getItems().add(menuClientGUI);

		GridPane root = new GridPane();
		root.add(menuBar, 0, 0);
			
		updateTexts();

		Scene scene = new Scene(root);
		return scene;
		

	}

	protected void updateTexts() {
		Translator t = ServiceLocator.getServiceLocator().getTranslator();

		// Menu strings
		menuFile.setText(t.getString("program.menu.file"));
		menuFileRestart.setText(t.getString("program.menu.file.restart"));
		menuFileLanguage.setText(t.getString("program.menu.file.language"));
		menuFileExit.setText(t.getString("program.menu.file.exit"));
		menuHelp.setText(t.getString("program.menu.help"));
		menuHelpAbout.setText(t.getString("program.menu.help.helpabout"));
		menuClient.setText(t.getString("program.menu.client"));
		menuClientGUI.setText(t.getString("program.menu.clientGUI"));
		menuFileLobby.setText(t.getString("program.menu.file.lobby"));
		menuFileLogin.setText(t.getString("program.menu.file.login"));
		menuFileFriends.setText(t.getString("program.menu.file.friends"));
		menuMainMenu.setText(t.getString("program.menu.file.mainmenu"));
		
		// Buttons

		// Labels

		// Other controls

	}
	
	protected GridPane getCompleteGameBoard(){
		GridPane gameBoard = new GridPane();
		Field[][] fields = this.getGameFields();
		
		for (int column = 1; column<=GameMenu_Model.totalFields; column++){
			for(int row = 1; row<=GameMenu_Model.totalFields; row++){
			gameBoard.add(fields[column-1][row-1], column, row);
			}}
		
		return gameBoard;
	}
	
	protected Field[][] getGameFields(){
		Field[][] fields = new Field[GameMenu_Model.totalColumns][GameMenu_Model.totalRows];
		for (int column = 1; column<=GameMenu_Model.totalFields; column++){
		for(int row = 1; row<=GameMenu_Model.totalFields; row++){
		fields[column][row] = new Field();
		}}
		return fields;
	}

	
	protected normalTower[][] getTowers(){
		return null;
	}
}