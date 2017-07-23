package WindChuckers_Main;

import java.util.Locale;
import java.util.logging.Logger;

import Client.ClientThreadForServer;
import WindChuckers_Main.Model_Extend.Board;
import WindChuckers_Main.Model_Extend.Field;
import WindChuckers_Main.Model_Extend.Tower;
import WindChuckers_Main.Model_Extend.Movement;
import WindChuckers_Main.Model_Extend.Player;
import WindChuckers_Main.Model_Extend.Position;
import WindChuckers_Main.Model_Extend.SumoTower;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public class GameMenu_View extends View<GameMenu_Model> {
	protected Stage stage;
	
	private Board board;
	private Movement movement;
	private Position position;
	private Player player;
	
	private Menu menuFile;
	private Menu menuFileLanguage;
	private Menu menuHelp;
	private Menu menuClient;
	private Menu menuAI;
	
	//chat
	protected TitledPane chatPanel;
	protected VBox chatBox;
	protected Button btnSend;
    protected TextArea txtMessages;
    protected TextField txtChat;
    
    //user
    protected VBox userBox;
    protected TextField tfHashCode;
    protected Label lblUser1;
    protected Label lblUser2;
    private Label lblPointsUser1;
    private Label lblPointsUser2;
    protected TextField tfPointsUser1;
    protected TextField tfPointsUser2;
    private Label lblTimer;
    protected TextField tfTimer;
    protected TextField tfTimerTime;
    private Label lblRound;
    private Label lblRoundCounter;
    protected TextField tfRoundMax;
    protected TextField tfRound;
    protected Button btnStartTimer;
    
	
	public MenuItem menuHelpAbout;
	public MenuItem menuFileRestart;
	public MenuItem menuFileExit;
	public MenuItem menuClientGUI;
	public MenuItem menuFileLobby;
	public MenuItem menuFileLogin;
	public MenuItem menuFileFriends;
	public MenuItem menuMainMenu;
	public MenuItem menuFileAI;
	public MenuItem menuTutorial;
	
	// GameBoard
	protected GridPane GameBoard;
	protected Field[][] fields;
	protected Tower[][] towersP1;
	protected Tower[][] towersP2;

	public GameMenu_View(Stage stage, GameMenu_Model model, Board board, Movement movement, Position position, Player player) {
		super(stage, model);
		this.stage = stage;
		this.board = board;
		this.movement = movement;
		this.position = position;
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
		menuTutorial = new MenuItem();
		menuFile.getItems().addAll(menuFileRestart,menuFileLanguage,menuFileLobby, menuFileLogin, menuFileFriends, menuMainMenu,menuFileExit);

		for (Locale locale : sl.getLocales()) {
			MenuItem language = new MenuItem(locale.getLanguage());
			menuFileLanguage.getItems().add(language);
			language.setOnAction(event -> {
				sl.setTranslator(new Translator(locale.getLanguage()));
				updateTexts();
				sl.getConfiguration().setLocalOption("Language", locale.getLanguage());
			});
		}

		menuHelp = new Menu();
		menuClient = new Menu();
		menuAI = new Menu();
		menuBar.getMenus().addAll(menuFile, menuHelp, menuClient, menuAI);

		menuHelpAbout = new MenuItem();
		menuHelp.getItems().addAll(menuHelpAbout, menuTutorial);
		
		chatBox = new VBox();
		btnSend = new Button();
		txtMessages = new TextArea();
		txtMessages.setEditable(false);
		txtChat = new TextField();
		chatBox.getChildren().addAll(txtMessages,txtChat, btnSend);
		
		chatPanel = new TitledPane();
		chatPanel.setExpanded(true);
		chatPanel.setText("Chat");
		chatPanel.setContent(chatBox);
		
		userBox = new VBox();
		tfHashCode = new TextField(Integer.toString(ClientThreadForServer.hashCodeStatic));
		tfHashCode.setEditable(false);
		lblTimer = new Label();
		lblRound = new Label();
		lblRoundCounter = new Label();
	    lblUser1 = new Label();
	    lblUser2 = new Label();
	    lblPointsUser1 = new Label();
	    lblPointsUser2 = new Label();
	    tfPointsUser1 = new TextField();
	    tfPointsUser1.setEditable(false);
	    tfPointsUser2 = new TextField();
	    tfPointsUser2.setEditable(false);
	    tfTimer = new TextField();
	    tfTimer.setEditable(false);
	    tfTimerTime = new TextField();
	    btnStartTimer = new Button();
	    tfRound = new TextField();
	    tfRound.setEditable(false);
	    tfRoundMax = new TextField();
	    Region spacer = new Region();
	    Region spacer1 = new Region();
	    Region spacer2 = new Region();
	    Region spacer3 = new Region();
	    userBox.setVgrow(spacer, Priority.ALWAYS);
		userBox.setVgrow(spacer1, Priority.ALWAYS);
		userBox.setVgrow(spacer2, Priority.ALWAYS);
		userBox.setVgrow(spacer3, Priority.ALWAYS);
		Label lblSpacer1 = new Label();
		Label lblSpacer2 = new Label();
		Label lblSpacer3 = new Label();
		Label lblSpacer4 = new Label();
		Label lblSpacer5 = new Label();
		Label lblSpacer6 = new Label();
		Label lblSpacer7 = new Label();
	    
	    userBox.getChildren().addAll(tfHashCode,spacer,lblUser1,lblSpacer1,lblPointsUser1,tfPointsUser1,spacer1,lblUser2,lblSpacer2,lblPointsUser2,tfPointsUser2,spacer2,lblTimer,tfTimerTime,lblSpacer5,btnStartTimer,lblSpacer3,tfTimer,spacer3,lblRound,lblSpacer4,tfRoundMax,lblSpacer6,lblRoundCounter,lblSpacer7,tfRound);
	    
		menuClientGUI = new MenuItem();
		menuClient.getItems().add(menuClientGUI);
		
		menuFileAI = new MenuItem();
		menuAI.getItems().add(menuFileAI);
		
		
		/**
		 * The gameBoard will be created
		 * @author robin
		 */
		fields = this.getFieldArray();
		towersP1 = this.getTowersP1Array();
		towersP2 = this.getTowersP2Array();
		GameBoard = this.getCompleteGameBoard(fields, towersP1, towersP2);
		
		
		
		GridPane root = new GridPane();
		root.add(menuBar, 0, 0);
		root.add(GameBoard, 0, 1);
		root.add(chatPanel, 0, 2);
		root.add(userBox, 1, 1);
			
		updateTexts();

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("WindChuckers_Main.css").toExternalForm());
		return scene;
	}
	
	/**
	 * Each element of the GameBoard will be set to the right place on the GridPane
	 * @author robin
	 */
	private GridPane getCompleteGameBoard(Field[][] fields, Tower[][] towersP1,Tower[][] towersP2) {
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		
		// Fields will be added
		for(int y = 0; y < GameMenu_Model.DIMENSION; y++){
			for(int x = 0; x < GameMenu_Model.DIMENSION; x++){
			fields[x][y].setxPosition(x);
			fields[x][y].setyPosition(y);
			fields[x][y].setDisable(true);
			pane.add(fields[x][y],x,(fields.length-1)-y);
			}
		}
	
		// Towers will be added
		for(int x = 0; x < GameMenu_Model.DIMENSION;x++){
			towersP1[x][towersP1.length-1].setxPosition(x);
			towersP1[x][towersP1.length-1].setyPosition(towersP1.length-1);
			
			towersP2[x][towersP2.length-towersP2.length].setxPosition(x);
			towersP2[x][towersP2.length-towersP2.length].setyPosition(towersP2.length-towersP2.length);	
			
			pane.add(towersP1[x][towersP1.length-1],x,towersP1.length-towersP1.length);
			pane.add(towersP2[x][towersP2.length-towersP2.length],x,towersP2.length-1);
			
			fields[x][towersP1.length-1].setEmpty(false);
			fields[x][towersP2.length-towersP2.length].setEmpty(false);
		}
		return pane;
	}
	
	/**
	 * An Array with all towers of Player 1 will be created
	 * @author robin
	 */
	private Tower[][] getTowersP1Array() {
		Tower[][]towersP1 = new Tower[GameMenu_Model.DIMENSION][GameMenu_Model.DIMENSION];
		
		towersP1[0][7] = Tower.getOrangeTower();
		towersP1[1][7] = Tower.getBlueTower();
		towersP1[2][7] = Tower.getVioletTower();
		towersP1[3][7] = Tower.getPinkTower();
		towersP1[4][7] = Tower.getYellowTower();
		towersP1[5][7] = Tower.getRedTower();
		towersP1[6][7] = Tower.getGreenTower();
		towersP1[7][7] = Tower.getBrownTower();
		
		for(int i = 0; i< GameMenu_Model.DIMENSION; i++){
			towersP1[i][towersP1.length-1].setPlayerNumber(1);
			towersP1[i][towersP1.length-1].setId("towersP1");
			towersP1[i][towersP1.length-1].setDisable(false);
			GridPane.setHalignment(towersP1[i][towersP1.length-1], HPos.CENTER);
		}
		return towersP1;
	}
	
	/**
	 * An Array with all towers of Player 2 will be created
	 * @author robin
	 */
	private Tower[][] getTowersP2Array() {
		Tower[][]towersP2 = new Tower[GameMenu_Model.DIMENSION][GameMenu_Model.DIMENSION];

		towersP2[7][0] = Tower.getOrangeTower();
		towersP2[6][0] = Tower.getBlueTower();
		towersP2[5][0] = Tower.getVioletTower();
		towersP2[4][0] = Tower.getPinkTower();
		towersP2[3][0] = Tower.getYellowTower();
		towersP2[2][0] = Tower.getRedTower();
		towersP2[1][0] = Tower.getGreenTower();
		towersP2[0][0] = Tower.getBrownTower();
		
		for(int i = 0; i < GameMenu_Model.DIMENSION;i++){
			towersP2[i][towersP2.length-towersP2.length].setPlayerNumber(2);
			towersP2[i][towersP2.length-towersP2.length].setId("towersP2");
			towersP2[i][towersP2.length-towersP2.length].setDisable(false);
			GridPane.setHalignment(towersP2[i][towersP2.length-towersP2.length], HPos.CENTER);
		}
		return towersP2;
	}
	
	/**
	 * This method offers an Array with all fields. The right color is set on the right place. 
	 * @author robin
	 */
	private Field[][] getFieldArray() {
		Field[][] fields = new Field[GameMenu_Model.DIMENSION][GameMenu_Model.DIMENSION];
		
		// orange fields will be added
		fields[0][7] = Field.getOrangeField();
		fields[1][6] = Field.getOrangeField();
		fields[2][5] = Field.getOrangeField();
		fields[3][4] = Field.getOrangeField();
		fields[4][3] = Field.getOrangeField();
		fields[5][2] = Field.getOrangeField();
		fields[6][1] = Field.getOrangeField();
		fields[7][0] = Field.getOrangeField();
		
		// blue fields will be added
		fields[6][0] = Field.getBlueField();
		fields[3][1] = Field.getBlueField();
		fields[0][2] = Field.getBlueField();
		fields[5][3] = Field.getBlueField();
		fields[2][4] = Field.getBlueField();
		fields[7][5] = Field.getBlueField();
		fields[4][6] = Field.getBlueField();
		fields[1][7] = Field.getBlueField();
		
		// violet fields will be added
		fields[5][0] = Field.getVioletField();
		fields[0][1] = Field.getVioletField();
		fields[3][2] = Field.getVioletField();
		fields[6][3] = Field.getVioletField();
		fields[1][4] = Field.getVioletField();
		fields[4][5] = Field.getVioletField();
		fields[7][6] = Field.getVioletField();
		fields[2][7] = Field.getVioletField();
		
		// pink fields will be added
		fields[4][0] = Field.getPinkField();
		fields[5][1] = Field.getPinkField();
		fields[6][2] = Field.getPinkField();
		fields[7][3] = Field.getPinkField();
		fields[0][4] = Field.getPinkField();
		fields[1][5] = Field.getPinkField();
		fields[2][6] = Field.getPinkField();
		fields[3][7] = Field.getPinkField();
		
		// yellow fields will be added
		fields[3][0] = Field.getYellowField();
		fields[2][1] = Field.getYellowField();
		fields[1][2] = Field.getYellowField();
		fields[0][3] = Field.getYellowField();
		fields[7][4] = Field.getYellowField();
		fields[6][5] = Field.getYellowField();
		fields[5][6] = Field.getYellowField();
		fields[4][7] = Field.getYellowField();
		
		// red fields will be added
		fields[2][0] = Field.getRedField();
		fields[7][1] = Field.getRedField();
		fields[4][2] = Field.getRedField();
		fields[1][3] = Field.getRedField();
		fields[6][4] = Field.getRedField();
		fields[3][5] = Field.getRedField();
		fields[0][6] = Field.getRedField();
		fields[5][7] = Field.getRedField();
		
		// green fields will be added
		fields[1][0] = Field.getGreenField();
		fields[4][1] = Field.getGreenField();
		fields[7][2] = Field.getGreenField();
		fields[2][3] = Field.getGreenField();
		fields[5][4] = Field.getGreenField();
		fields[0][5] = Field.getGreenField();
		fields[3][6] = Field.getGreenField();
		fields[6][7] = Field.getGreenField();
		
		// brown fields will be added
		fields[0][0] = Field.getBrownField();
		fields[1][1] = Field.getBrownField();
		fields[2][2] = Field.getBrownField();
		fields[3][3] = Field.getBrownField();
		fields[4][4] = Field.getBrownField();
		fields[5][5] = Field.getBrownField();
		fields[6][6] = Field.getBrownField();
		fields[7][7] = Field.getBrownField();

		return fields;
	}
	
	/**
	 	* Will reset the complete game Board after a match is finished
	 * @author robin
	 */
	protected void resetGameBoard(){
		this.towersP1=this.getTowersP1Array();
		this.towersP2=this.getTowersP2Array();
		this.fields=this.getFieldArray();
		this.GameBoard.getChildren().clear();

		for(int y = 0; y < GameMenu_Model.DIMENSION; y++){
			for(int x = 0; x < GameMenu_Model.DIMENSION; x++){
			fields[x][y].setxPosition(x);
			fields[x][y].setyPosition(y);
			fields[x][y].setDisable(true);
			GameBoard.add(fields[x][y],x,(fields.length-1)-y);
			}
		}
	
		// Towers will be added
		for(int x = 0; x < GameMenu_Model.DIMENSION;x++){
			towersP1[x][7].setxPosition(x);
			towersP1[x][7].setyPosition(7);
			
			towersP2[x][0].setxPosition(x);
			towersP2[x][0].setyPosition(0);	
			
			GameBoard.add(towersP1[x][7],x,towersP1.length-towersP1.length);
			GameBoard.add(towersP2[x][0],x,towersP2.length-1);
			
			fields[x][7].setEmpty(false);
			fields[x][0].setEmpty(false);
		}	
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
		menuFileAI.setText(t.getString("program.menu.file.AI"));
		menuAI.setText(t.getString("program.menu.AI"));
		menuTutorial.setText(t.getString("program.menu.help.tutorial"));
		
		// Buttons
		btnSend.setText(t.getString("program.btn.btnSend"));
		btnStartTimer.setText(t.getString("program.btn.btnStartTimer"));

		// Labels
		lblUser1.setText(t.getString("program.lbl.lblUser1"));
		lblUser2.setText(t.getString("program.lbl.lblUser2"));
		lblPointsUser1.setText(t.getString("program.lbl.lblPointsUser1"));
		lblPointsUser2.setText(t.getString("program.lbl.lblPointsUser2"));
		lblTimer.setText(t.getString("program.lbl.lblTimer"));
		lblRound.setText(t.getString("program.lbl.lblRound"));
		lblRoundCounter.setText(t.getString("program.lbl.lblRoundCounter"));

		// Other controls

	}

	// getter and setter
	public Field[][] getFields() {
		return fields;
	}

	public Tower[][] getTowersP1() {
		return towersP1;
	}

	public Tower[][] getTowersP2() {
		return towersP2;
	}

	public GridPane getGameBoard() {
		return GameBoard;
	}
	
	public void setScene (Scene scene) {
		this.scene = scene;
	}
	
	public void setModel (GameMenu_Model model){
		this.model = model;
	}
}