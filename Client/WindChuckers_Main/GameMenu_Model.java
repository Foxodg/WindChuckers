package WindChuckers_Main;

import java.net.Socket;
import java.util.ArrayList;

import Client.ClientThreadForServer;
import Message.Message;
import Message.Message.MessageType;
import Message.Message.Value;
import WindChuckers_Main.Model_Extend.Player;
import abstractClasses.Model;
import commonClasses.ServiceLocator;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public class GameMenu_Model extends Model {
	
	protected static final int DIMENSION = 8;
	protected static final int MAX_FIELDS = DIMENSION*DIMENSION;

	protected static final String ORANGE = "#FF8C00";
	protected static final String BLUE = "#4169E1";
	protected static final String VIOLET = "#663399";
	protected static final String PINK = "#FF69B4";
	protected static final String YELLOW = "#FFD700";
	protected static final String RED = "#B22222";
	protected static final String GREEN = "#008000";
	protected static final String BROWN = "#8B4513";
	
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
	 * @author L.Weber
	 */

	Socket socket;
	private int startColumn;
	private int startRow;
	private int endColumn;
	private int endRow;
	private int userID;
	private ArrayList<String> userList;

	// SimpleBooleanProperty for overwatching the incoming moves
	private SimpleBooleanProperty moveProperty = new SimpleBooleanProperty();
	
	// SimpleBooleanProperty for overwatching the incoming dbRequests
	private SimpleBooleanProperty dbRequest = new SimpleBooleanProperty();
	
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

	/**
	 * For the Chat-Messages
	 * @author L.Weber
	 * @param input
	 */
	public void messageContructorForChat(String input) {
		Message message = new Message(MessageType.ChatMessage, input);
		sendMessage(message);
	}

	/**
	 * For the gamePlay
	 * @author L.Weber
	 * @param xCoordinate1
	 * @param yCoordinate1
	 * @param xCoordinate2
	 * @param yCoordinate2
	 * @param value
	 */
	public void messageConstructorForCoordinate(boolean singlePlayer, int xCoordinate1, int yCoordinate1, int xCoordinate2, int yCoordinate2, Value value) {
		Message message = new Message(MessageType.Coordinate,singlePlayer, xCoordinate1, yCoordinate1, xCoordinate2, yCoordinate2, value);
		sendMessage(message);
	}

	/**
	 * For the Win-Message
	 * @author L.Weber
	 * @param win
	 */
	public void messageConstructorForWin(boolean win) {
		Message message = new Message(MessageType.WinMessage, win);
		sendMessage(message);
	}
	
	/**
	 * For the Update-Message
	 * @author L.weber
	 * @param update
	 * @param xCoordinate1
	 * @param yCoordinate1
	 * @param xCoordinate2
	 * @param yCoordinate2
	 * @param gems
	 */
	public void messageConstructorForUpdate(boolean update, int xCoordinate1, int yCoordinate1, int xCoordinate2, int yCoordinate2, int gems){
		Message message = new Message(MessageType.Update, update, xCoordinate1, yCoordinate1, xCoordinate2, yCoordinate2, gems);
		sendMessage(message);
		
	}

	/**
	 * For the DB-Messages
	 * @author L.Weber
	 * @param db
	 */
	public void messageConstructorForDB(int db) {
		Message message = new Message(MessageType.DBMessage, db);
		sendMessage(message);
	}
	
	/**
	 * For the Error-Message
	 * @author L.Weber
	 */
	public void messageConstructorForError() {
		Message message = new Message(MessageType.Error);
		sendMessage(message);
	}
	
	/**
	 * For the AI SinglePlayer
	 * @author L.Weber
	 * @param singlePlayer
	 * @param weightProgress
	 * @param weightMoves
	 * @param weightBlock
	 * @param weightSumoBlock
	 * @param weightSumoWin
	 * @param weightWin
	 */
	public void messageContstructorForAISingle(double weightProgress, double weightMoves, 
			double weightBlock, double weightSumoBlock, double weightSumoWin, double weightWin) {
		Message message = new Message(MessageType.AISingle, weightProgress, weightMoves, weightBlock, weightSumoBlock, weightSumoWin, weightWin);
		sendMessage(message);
	}
	
	/**
	 * For the AI DoublePlayer
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
	public void messageContstructorForAIDouble(double weightProgressL, double weightMovesL, 
			double weightBlockL, double weightSumoBlockL, double weightSumoWinL, double weightWinL,
			double weightProgressR, double weightMovesR, double weightBlockR, double weightSumoBlockR, double weightSumoWinR, double weightWinR) {
		
		Message message = new Message(MessageType.AIDouble, weightProgressL, weightMovesL, weightBlockL, weightSumoBlockL, weightSumoWinL, weightWinL,
				weightProgressR, weightMovesR, weightBlockR, weightSumoBlockR, weightSumoWinR, weightWinR);
		
		sendMessage(message);
	}
	
	//set a new move
	public void setMoveProperty(Boolean newValue) {
		try{
			this.moveProperty.setValue(newValue);;
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	//set a new db request
	public void setDBRequest(Boolean newValue) {
		try{
			this.dbRequest.setValue(newValue);;
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public SimpleBooleanProperty getDBRequest(){
		return this.dbRequest;
	}
	
	public SimpleBooleanProperty getMoveProperty(){
		return this.moveProperty;
	}

	
	// Getter and setter
	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void makeMove(int startColumn, int startRow, int endColumn, int endRow) {
		this.setMoveProperty(false);
		this.startColumn = startColumn;
		this.startRow = startRow;
		this.endColumn = endColumn;
		this.endRow = endRow;
		this.setMoveProperty(true);
		
	}
	
	public int getStartColumn(){
		return this.startColumn;
	}

	public int getStartRow(){
		return this.startRow;
	}
	
	public int getEndColumn(){
		return this.endColumn;
	}
	
	public int getEndRow(){
		return this.endRow;
	}

	public void setUserID(int userID) {
		this.userID = userID;	
	}
	
	public int getUserID(){
		return this.userID;
	}

	public void setUserList(ArrayList<String> userList) {
		this.userList = userList;
		
	}

	public ArrayList<String> getUserList() {
		return this.userList;
	}
}
