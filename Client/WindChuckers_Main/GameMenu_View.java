package WindChuckers_Main;

import java.util.Locale;
import java.util.logging.Logger;

import WindChuckers_Main.Model_Extend.Board;
import WindChuckers_Main.Model_Extend.Field;
import WindChuckers_Main.Model_Extend.Tower;
import WindChuckers_Main.Model_Extend.normalTower;
import WindChuckers_Main.AI.AI;
import WindChuckers_Main.Model_Extend.Movement;
import WindChuckers_Main.Model_Extend.Player;
import WindChuckers_Main.Model_Extend.Position;
import WindChuckers_Main.Model_Extend.sumoTower;
import abstractClasses.View;
import commonClasses.ServiceLocator;
import commonClasses.Translator;
import javafx.geometry.HPos;
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
	
	// GameBoard
	private GridPane GameBoard;
	private Field[][] fields;
	private normalTower[][] towersP1;
	private normalTower[][] towersP2;

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
		
		// The GameBoard will be initialized
		fields = this.getFieldArray();
		towersP1 = this.getTowersP1Array();
		towersP2 = this.getTowersP2Array();
		GameBoard = this.getCompleteGameBoard(fields, towersP1, towersP2);
		
		GridPane root = new GridPane();
		root.add(menuBar, 0, 0);
		root.add(GameBoard, 0, 1);
			
		updateTexts();

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("WindChuckers_Main.css").toExternalForm());
		return scene;
	}
	
	/**
	 * Each element of the GameBoard will be set to the right place on the GridPane
	 * @author robin
	 */
	private GridPane getCompleteGameBoard(Field[][] fields, normalTower[][] towersP1,normalTower[][] towersP2) {
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		
		// Fields will be added
		int counter = 0;
		for(int y = 1; y<=GameMenu_Model.DIMENSION; y++){
			for(int x = 1; x<=GameMenu_Model.DIMENSION; x++){
				pane.add(fields[x][y], x,y);
				fields[x][y].setNumber(counter);
				fields[x][y].setText(""+fields[x][y].getNumber());
				counter++;
			}
		}
	
		// Towers will be added
		for(int x = 1; x<=GameMenu_Model.DIMENSION;x++){
			towersP1[x][1].setNumber(x-1);
			towersP1[x][1].setText(""+towersP1[x][1].getNumber());
			pane.add(towersP1[x][1], x, 1);
			
			towersP2[x][8].setNumber(56+x-1);
			towersP2[x][8].setText(""+towersP2[x][8].getNumber());
			pane.add(towersP2[x][8], x, 8);
		}
		return pane;
	}
	
	/**
	 * An Array with all towers of Player 1 will be created
	 * @author robin
	 */
	private normalTower[][] getTowersP1Array() {
		normalTower[][]towersP1 = new normalTower[GameMenu_Model.DIMENSION+1][GameMenu_Model.DIMENSION+1];
		
		towersP1[1][1] = normalTower.getOrangeTower(1, 1);
		towersP1[2][1] = normalTower.getBlueTower(2, 1);
		towersP1[3][1] = normalTower.getVioletTower(3, 1);
		towersP1[4][1] = normalTower.getPinkTower(4, 1);
		towersP1[5][1] = normalTower.getYellowTower(5, 1);
		towersP1[6][1] = normalTower.getRedTower(6, 1);
		towersP1[7][1] = normalTower.getGreenTower(7, 1);
		towersP1[8][1] = normalTower.getBrownTower(8, 1);
		
		for(int i = 1; i<=GameMenu_Model.DIMENSION; i++){
			towersP1[i][1].setPlayerNumber(1);
			towersP1[i][1].setId("towersP1");
			towersP1[i][1].setDisable(false);
			GridPane.setHalignment(towersP1[i][1], HPos.CENTER);
		}
		return towersP1;
	}
	
	/**
	 * An Array with all towers of Player 2 will be created
	 * @author robin
	 */
	private normalTower[][] getTowersP2Array() {
		normalTower[][]towersP2 = new normalTower[GameMenu_Model.DIMENSION+1][GameMenu_Model.DIMENSION+1];

		towersP2[8][8] = normalTower.getOrangeTower(8, 8);
		towersP2[7][8] = normalTower.getBlueTower(7, 8);
		towersP2[6][8] = normalTower.getVioletTower(6, 8);
		towersP2[5][8] = normalTower.getPinkTower(5, 8);
		towersP2[4][8] = normalTower.getYellowTower(4, 8);
		towersP2[3][8] = normalTower.getRedTower(3, 8);
		towersP2[2][8] = normalTower.getGreenTower(2, 8);
		towersP2[1][8] = normalTower.getBrownTower(1, 8);
		
		for(int i = 1; i<=GameMenu_Model.DIMENSION;i++){
			towersP2[i][8].setPlayerNumber(2);
			towersP2[i][8].setId("towersP2");
			towersP2[i][8].setDisable(false);
			GridPane.setHalignment(towersP2[i][8], HPos.CENTER);
		}
		return towersP2;
	}
	
	/**
	 * This method offers an Array with all fields. The right color is set on the right place. 
	 * @author robin
	 */
	private Field[][] getFieldArray() {
		Field[][] fields = new Field[GameMenu_Model.DIMENSION+1][GameMenu_Model.DIMENSION+1];
		
		// orange fields will be added
		fields[1][1] = Field.getOrangeField(1, 1);
		fields[2][2] = Field.getOrangeField(1, 1);
		fields[3][3] = Field.getOrangeField(1, 1);
		fields[4][4] = Field.getOrangeField(1, 1);
		fields[5][5] = Field.getOrangeField(1, 1);
		fields[6][6] = Field.getOrangeField(1, 1);
		fields[7][7] = Field.getOrangeField(1, 1);
		fields[8][8] = Field.getOrangeField(1, 1);
		
		// blue fields will be added
		fields[2][1] = Field.getBlueField(1, 1);
		fields[5][2] = Field.getBlueField(1, 1);
		fields[8][3] = Field.getBlueField(1, 1);
		fields[3][4] = Field.getBlueField(1, 1);
		fields[6][5] = Field.getBlueField(1, 1);
		fields[1][6] = Field.getBlueField(1, 1);
		fields[4][7] = Field.getBlueField(1, 1);
		fields[7][8] = Field.getBlueField(1, 1);
		
		// violet fields will be added
		fields[3][1] = Field.getVioletField(1, 1);
		fields[8][2] = Field.getVioletField(1, 1);
		fields[5][3] = Field.getVioletField(1, 1);
		fields[2][4] = Field.getVioletField(1, 1);
		fields[7][5] = Field.getVioletField(1, 1);
		fields[4][6] = Field.getVioletField(1, 1);
		fields[1][7] = Field.getVioletField(1, 1);
		fields[6][8] = Field.getVioletField(1, 1);
		
		// pink fields will be added
		fields[4][1] = Field.getPinkField(1, 1);
		fields[3][2] = Field.getPinkField(1, 1);
		fields[2][3] = Field.getPinkField(1, 1);
		fields[1][4] = Field.getPinkField(1, 1);
		fields[8][5] = Field.getPinkField(1, 1);
		fields[7][6] = Field.getPinkField(1, 1);
		fields[6][7] = Field.getPinkField(1, 1);
		fields[5][8] = Field.getPinkField(1, 1);
		
		// yellow fields will be added
		fields[5][1] = Field.getYellowField(1, 1);
		fields[6][2] = Field.getYellowField(1, 1);
		fields[7][3] = Field.getYellowField(1, 1);
		fields[8][4] = Field.getYellowField(1, 1);
		fields[1][5] = Field.getYellowField(1, 1);
		fields[2][6] = Field.getYellowField(1, 1);
		fields[3][7] = Field.getYellowField(1, 1);
		fields[4][8] = Field.getYellowField(1, 1);
		
		// red fields will be added
		fields[6][1] = Field.getRedField(1, 1);
		fields[1][2] = Field.getRedField(1, 1);
		fields[4][3] = Field.getRedField(1, 1);
		fields[7][4] = Field.getRedField(1, 1);
		fields[2][5] = Field.getRedField(1, 1);
		fields[5][6] = Field.getRedField(1, 1);
		fields[8][7] = Field.getRedField(1, 1);
		fields[3][8] = Field.getRedField(1, 1);
		
		// green fields will be added
		fields[7][1] = Field.getGreenField(1, 1);
		fields[4][2] = Field.getGreenField(1, 1);
		fields[1][3] = Field.getGreenField(1, 1);
		fields[6][4] = Field.getGreenField(1, 1);
		fields[3][5] = Field.getGreenField(1, 1);
		fields[8][6] = Field.getGreenField(1, 1);
		fields[5][7] = Field.getGreenField(1, 1);
		fields[2][8] = Field.getGreenField(1, 1);
		
		// brown fields will be added
		fields[8][1] = Field.getBrownField(1, 1);
		fields[7][2] = Field.getBrownField(1, 1);
		fields[6][3] = Field.getBrownField(1, 1);
		fields[5][4] = Field.getBrownField(1, 1);
		fields[4][5] = Field.getBrownField(1, 1);
		fields[3][6] = Field.getBrownField(1, 1);
		fields[2][7] = Field.getBrownField(1, 1);
		fields[1][8] = Field.getBrownField(1, 1);

		return fields;
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

	// getter and setter
	protected normalTower[][] getTowers(){
		return null;
	}

	public Field[][] getFields() {
		return fields;
	}

	public void setFields(Field[][] fields) {
		this.fields = fields;
	}

	public Tower[][] getTowersP1() {
		return towersP1;
	}

	public void setTowersP1(normalTower[][] towersP1) {
		this.towersP1 = towersP1;
	}

	public Tower[][] getTowersP2() {
		return towersP2;
	}

	public void setTowersP2(normalTower[][] towersP2) {
		this.towersP2 = towersP2;
	}

	public GridPane getGameBoard() {
		return GameBoard;
	}

	public void setGameBoard(GridPane gameBoard) {
		GameBoard = gameBoard;
	}
}