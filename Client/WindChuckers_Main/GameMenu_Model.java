package WindChuckers_Main;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import Client.ClientThreadForServer;
import Login.LoginModel;
import Message.Message;
import Message.Message.MessageType;
import Message.Message.Value;
import WindChuckers_Main.Model_Extend.Player;
import abstractClasses.Model;
import commonClasses.ServiceLocator;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public class GameMenu_Model extends Model {

	protected static final int DIMENSION = 8;
	protected static final int MAX_FIELDS = DIMENSION * DIMENSION;

	protected static final String ORANGE = "#FF8C00";
	protected static final String BLUE = "#4169E1";
	protected static final String VIOLET = "#663399";
	protected static final String PINK = "#FF69B4";
	protected static final String YELLOW = "#FFD700";
	protected static final String RED = "#B22222";
	protected static final String GREEN = "#008000";
	protected static final String BROWN = "#8B4513";
	
	public static SimpleBooleanProperty Winner = new SimpleBooleanProperty(false);

	private Player player1 = new Player(1);
	private Player player2 = new Player(2);

	protected ServiceLocator serviceLocator;
	private static GameMenu_Model gameModel;

	/**
	 * Factory method for returning the singleton board
	 * 
	 * @param mainClass
	 *            The main class of this program
	 * @return GameMenu_Model
	 * @author L.Weber
	 */
	public static GameMenu_Model getGameModel() {
		if (gameModel == null)
			gameModel = new GameMenu_Model();
		return gameModel;
	}

	public GameMenu_Model() {

		serviceLocator = ServiceLocator.getServiceLocator();
		serviceLocator.getLogger().info("Application model initialized");
	}

	/**
	 * All following Methods are for the Client-Server-Communications
	 * 
	 * @author L.Weber
	 */

	Socket socket;
	private int startColumn;
	private int startRow;
	private int endColumn;
	private int endRow;
	private int playerType;
	private int userID;
	private int randomStart;
	private ArrayList<String> userList;
	private HashMap<Integer, ArrayList<String>> userMap;

	// SimpleBooleanProperty for overwatching the incoming moves
	private SimpleBooleanProperty moveProperty = new SimpleBooleanProperty();

	// SimpleBooleanProperty for overwatching the incoming dbRequests
	private SimpleBooleanProperty dbRequest = new SimpleBooleanProperty();
	
	// SimmpleStringProperty for chat
	public SimpleStringProperty thisUserNameProperty = new SimpleStringProperty();	
	
	// SimpleIntegerProperty for HashCode
	public SimpleIntegerProperty HashCode = new SimpleIntegerProperty();

	public boolean connect(String ipAddress, Integer port) {
		boolean success = false;
		try {
			socket = new Socket(ipAddress, port);
			success = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		ClientThreadForServer server = ClientThreadForServer.getClientServer();
		server.setSocket(socket);
		server.start();
		return success;
	}

	/**
	 * For send the Message via SimpleXML
	 * 
	 * @author L.Weber
	 * @param message
	 */
	public void sendMessage(Message message) {
		try {
			message.send(socket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void messageConstructorForTime(long time) {
		Message message = new Message(MessageType.Time, time);
		sendMessage(message);
	}

	/**
	 * For the Chat-Messages
	 * 
	 * @author L.Weber
	 * @param input
	 */
	public void messageContructorForChat(String input) {
		Message message = new Message(MessageType.ChatMessage, input);
		sendMessage(message);
	}

	/**
	 * For the gamePlay
	 * 
	 * @author L.Weber
	 * @param xCoordinate1
	 * @param yCoordinate1
	 * @param xCoordinate2
	 * @param yCoordinate2
	 * @param value
	 */
	public void messageConstructorForCoordinate(int xCoordinate1, int yCoordinate1,
			int xCoordinate2, int yCoordinate2, int player) {
		Message message = new Message(MessageType.Coordinate, xCoordinate1, yCoordinate1, xCoordinate2,
				yCoordinate2, player);
		sendMessage(message);
	}

	/**
	 * For the Win-Message
	 * 
	 * @author L.Weber
	 * @param win
	 */
	public void messageConstructorForWin(int win) {
		Message message = new Message(MessageType.WinMessage, win);
		sendMessage(message);
	}

	/**
	 * For the Update-Message
	 * 
	 * @author L.weber
	 * @param update
	 * @param xCoordinate1
	 * @param yCoordinate1
	 * @param xCoordinate2
	 * @param yCoordinate2
	 * @param gems
	 */
	public void messageConstructorForUpdate(boolean update, int xCoordinate2, int yCoordinate2, int gems, int player) {
		Message message = new Message(MessageType.Update, xCoordinate2,	yCoordinate2, gems, player);
		sendMessage(message);

	}

	/**
	 * For the DB-Messages
	 * 
	 * @author L.Weber
	 * @param db
	 */
	public void messageConstructorForDB(int db) {
		Message message = new Message(MessageType.DBMessage, db);
		sendMessage(message);
	}
	
	/**
	 * For DB Insert / Update
	 * @author L.Weber
	 * @param db
	 * @param preName
	 * @param lastName
	 */
	public void messageConstructorForDBInsertUpdate(int db, int Id, String userName, String preName, String lastName, String password){
		Message message = new Message(MessageType.DBMessage, db,Id, userName, preName, lastName, password);
		sendMessage(message);
	}
	
	/**
	 * For DB Update 
	 * @author L.Weber
	 * @param db
	 * @param something
	 */
	public void messageConstructorForDBUpdate(int db, int id){
		Message message = new Message(MessageType.DBMessage, db, id);
		sendMessage(message);
	}

	/**
	 * For the Error-Message
	 * 
	 * @author L.Weber
	 */
	public void messageConstructorForError() {
		Message message = new Message(MessageType.Error);
		sendMessage(message);
	}

	/**
	 * For the AI SinglePlayer
	 * 
	 * @author L.Weber
	 * @param singlePlayer
	 * @param weightProgress
	 * @param weightMoves
	 * @param weightBlock
	 * @param weightSumoBlock
	 * @param weightSumoWin
	 * @param weightWin
	 */
	public void messageContstructorForAISingle(double weightProgress, double weightMoves, double weightBlock,
			double weightSumoBlock, double weightSumoWin, double weightWin) {
		Message message = new Message(MessageType.AISingle, weightProgress, weightMoves, weightBlock, weightSumoBlock,
				weightSumoWin, weightWin);
		sendMessage(message);
	}

	/**
	 * For the AI DoublePlayer
	 * 
	 * @author L.Weber
	 * @param weightProgressL
	 * @param weightMovesL
	 * @param weightBlockL
	 * @param weightSumoBlockL
	 * @param weightSumoWinL
	 * @param weightWinL
	 * @param weightProgressR
	 * @param weightMovesR
	 * @param weightBlockR
	 * @param weightSumoBlockR
	 * @param weightSumoWinR
	 * @param weightWinR
	 */
	public void messageContstructorForAIDouble(double weightProgressL, double weightMovesL, double weightBlockL,
			double weightSumoBlockL, double weightSumoWinL, double weightWinL, double weightProgressR,
			double weightMovesR, double weightBlockR, double weightSumoBlockR, double weightSumoWinR,
			double weightWinR) {

		Message message = new Message(MessageType.AIDouble, weightProgressL, weightMovesL, weightBlockL,
				weightSumoBlockL, weightSumoWinL, weightWinL, weightProgressR, weightMovesR, weightBlockR,
				weightSumoBlockR, weightSumoWinR, weightWinR);

		sendMessage(message);
	}
	

	public void makeUserList(ArrayList<String> userList) {
		int id;
		String prename;
		String username;
		String surname;
		String password;
		String win;

		userMap = new HashMap<Integer, ArrayList<String>>();
		ArrayList<String> uList = new ArrayList<String>();
		while (!userList.isEmpty()) {
			if(!userList.get(1).equals("deleted")){
				uList.clear();			
				id = Integer.parseInt(userList.get(0));
				username = userList.get(1);
				uList.add(username);
				prename = userList.get(2);
				uList.add(prename);
				surname = userList.get(3);
				uList.add(surname);
				password = userList.get(4);
				uList.add(password);
				win = userList.get(5);
				uList.add(win);
				if(userMap.get(id) == null){
					userMap.put(id, new ArrayList<String>());
					userMap.get(id).addAll(uList);
				}
			}
			for(int i = 0; i < 6; i ++){
				userList.remove(0);
			}
		}
	}
	

	// set a new move
	public void setMoveProperty(Boolean newValue) {
		try {
			this.moveProperty.setValue(newValue);
			;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// set a new db request
	public void setDBRequest(Boolean newValue) {
		try {
			this.dbRequest.setValue(newValue);
			;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SimpleBooleanProperty getDBRequest() {
		return this.dbRequest;
	}

	public SimpleBooleanProperty getMoveProperty() {
		return this.moveProperty;
	}
	
	
	public void setUserName(String newValue){
		try{
			this.thisUserNameProperty.setValue(newValue);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public SimpleStringProperty getuserName(){
		return this.thisUserNameProperty;
	}
	
	public String getUserNameString(){
		return thisUserNameProperty.get();
	}
	
	public void setHashCode(int newValue){
		this.HashCode.setValue(newValue);
	}
	
	public SimpleIntegerProperty getHashCode(){
		return this.HashCode;
	}
	
	public int getHashCodeInt(){
		return this.HashCode.get();
	}

	// Getter and setter
	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void makeMove(int startColumn, int startRow, int endColumn, int endRow, int playerType) {
		this.setMoveProperty(false);
		this.startColumn = startColumn;
		this.startRow = startRow;
		this.endColumn = endColumn;
		this.endRow = endRow;
		this.playerType = playerType;
		this.setMoveProperty(true);

	}

	public int getStartColumn() {
		return this.startColumn;
	}

	public int getStartRow() {
		return this.startRow;
	}

	public int getEndColumn() {
		return this.endColumn;
	}

	public int getEndRow() {
		return this.endRow;
	}
	
	public int getPlayerType(){
		return this.playerType;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getUserID() {
		return this.userID;
	}

	public void setUserList(ArrayList<String> userList) {
		this.userList = userList;

	}

	public ArrayList<String> getUserList() {
		return this.userList;
	}
	
	public HashMap<Integer, ArrayList<String>> getUserMap(){
		return this.userMap;
	}
	
	public void setRandomStart(int randomStart) {
		this.randomStart = randomStart;
	}
	
	public int getRandomStart() {
		return this.randomStart;
	}
	
}
