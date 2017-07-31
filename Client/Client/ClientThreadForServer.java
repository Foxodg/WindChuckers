package Client;

import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

import Friends.FriendsController;
import Message.Message;
import Message.Message.MessageType;
import Message.Message.Value;
import WindChuckers_Main.GameMenu_View;
import WindChuckers_Main.WindChuckers;
import commonClasses.ServiceLocator;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class ClientThreadForServer extends Thread {
	private static ClientThreadForServer clientServer;
	
	private ServiceLocator serviceLocator = ServiceLocator.getServiceLocator();
	private final Logger logger = serviceLocator.getLogger();
	private Socket serverSocket;
	private ClientController controller;
	private int startColumn;
	private int startRow;
	private int endColumn;
	private int endRow;
	private int gems;
	private int xCoordinateUpgrade;
	private int yCoordinateUpgrade;
	private int playerType;
	private int userID;
	private int player;
	private boolean newRoundLeftRight; //left is true, right is false
	private ArrayList<String> userList;
	public static long hashCodeStatic = 0;
	public static long hashCodeStaticThis;
	public static long hashCodeStaticFriend;
	private static ArrayList<String> friendsList = new ArrayList<String>();
	
	// SimpleStringProperty for overwatching the chat
	private SimpleStringProperty chatMessage = new SimpleStringProperty();
	
	// SimpleBooleanProperty for overwatching the incoming moves
	private SimpleBooleanProperty moveProperty = new SimpleBooleanProperty();
	
	// SimpleBooleanProperty for overwatching the incoming moves
	private SimpleBooleanProperty dbRequest = new SimpleBooleanProperty();
	
	// SimpleIntegerProperty for HashCode
	public SimpleLongProperty HashCode = new SimpleLongProperty();
	
	//SimpleLongProperty for TimeCap
	public SimpleLongProperty timeCap = new SimpleLongProperty();
	
	//SimpleIntegerProperty for Round
	public SimpleIntegerProperty roundCap = new SimpleIntegerProperty();
	
	//SimpleBooleanProperty for overwatching the incoming Updates
	public SimpleBooleanProperty upgrade = new SimpleBooleanProperty();
	
	//SimpleBooleanProperty for new Round
	public SimpleBooleanProperty newRound = new SimpleBooleanProperty();
	
	//SimpleIntegerProperty for Random-Start the Game
	public SimpleIntegerProperty randomStart = new SimpleIntegerProperty();
	
	//SimpleBooleanProperty for Friendsincoming
	public SimpleBooleanProperty friends = new SimpleBooleanProperty();
	
	//For start the Main Game
	public SimpleBooleanProperty readyToPlay = new SimpleBooleanProperty();
	
	//SimplyBooleanProperty for start a Game with the friend
	public SimpleBooleanProperty startGame = new SimpleBooleanProperty();
	
	public void setSocket(Socket serverSocket) {
		this.serverSocket = serverSocket;
		}
	
	/**
	 * Factory method for returning the singleton board
	 * 
	 * @param mainClass
	 *            The main class of this program
	 * @return The singleton resource locator
	 */
	public static ClientThreadForServer getClientServer() {
		if (clientServer == null)
			clientServer = new ClientThreadForServer();
		return clientServer;
	}
	
	private ClientThreadForServer(){
	}
	
	public void setController(ClientController controller) {
		this.controller = controller;
	}

	/**
	 * Process messages until the client says "Goodbye"
	 */
	@Override
	public void run() {
		while (true) {
			Message.MessageType lastMessageType = null;
			logger.info("Request from server " + serverSocket.getInetAddress().toString() + " for server "
					+ serverSocket.getLocalAddress().toString());
			try {
				Message message = Message.receive(serverSocket);
				logger.info("Message auf Client erhalten");
				getMessageType(message);
				
			} catch (Exception e) {
				logger.severe(e.toString());
			}
		}
	}
	
	public void getMessageType(Message message) {
		if (message.getMessageType() == MessageType.ChatMessage) {
			logger.info("Client: " + "Chat-Message back from Server: " + message.getChatMessage());
			String chatMessage = message.getChatMessage();
			this.chatMessage.setValue(chatMessage);
			logger.info("Client: " + "Message auf SimpleProperty geschrieben");
		}
		else if (message.getMessageType() == MessageType.Coordinate){
			//Safes the coordinate from the server in the double Array
			setValue(false);
			logger.info("Client: " + "x-Coordinates1: " + message.getXCoordinate1() + " y-Coordinates1: " + message.getYCoordinate1() +
					" x-Coordinates2: " + message.getXCoordinate2() + " y-Coordinates2: " + message.getYCoordinate2() + " Value: " + message.getValue());
			startColumn = message.getXCoordinate1();
			startRow = message.getYCoordinate1();
			endColumn = message.getXCoordinate2();
			endRow = message.getYCoordinate2();
			playerType = message.getPlayer();
			setValue(true);
			
		}
		else if (message.getMessageType() == MessageType.Update){
			this.setUpgrade(false);
			logger.info("Client: " + "Update: " + " x-Coordinates2: " + message.getXCoordinate2() + " y-Coordinates2: " + message.getYCoordinate2() + " Gems: " + message.getGems() + " Player: " + message.getPlayer());
			this.setGems(message.getGems());
			this.setXCoordinateUpgrade(message.getXCoordinate2());
			this.setYCoordinateUpgrade(message.getYCoordinate2());
			this.setPlayer(message.getPlayer());
			this.setUpgrade(true);
			
		}
		else if (message.getMessageType() == MessageType.DBMessage || message.getMessageType() == MessageType.DBMessageFull){
			logger.info("Client: DB-Message is arrived " + message.getDB());

			if(message.getDB() == 0){
				// 0 stands for full List
				setDBRequest(false);
				userList = message.getNameList();
				setDBRequest(true);
			}
			else if(message.getDB() == 4) {
				//4 Stands for Friends
				setDBFriends(false);
				friendsList = message.getNameList();
				setDBFriends(true);
			}			
			// Round-_Cap
			else if(message.getDB() == 88){
				logger.info("Round-Cap has arrived");
				this.setRound(message.getId());
			}
			else if(message.getDB() == 91) {
				logger.info("RandomStartObject is here: " + message.getId());
				FriendsController.setRandomStart(message.getId());
//				this.setRandomStart(message.getId());
			}
		}
		else if(message.getMessageType() == Message.MessageType.Hash){
			logger.info("Hash-Code comes back: " + message.getTime());
			long hash = message.getTime();
			this.setHashCode(hash);
			if(hashCodeStatic == 0) {
				hashCodeStatic = hash;
			}
			
		}
		else if(message.getMessageType() == Message.MessageType.Time){
			logger.info("Time-Cap has arrived");
			this.setTime(message.getTime());
		}
		else if(message.getMessageType() == Message.MessageType.NewRound){
			logger.info("New Round");
			this.newRoundLeftRight = message.getWin();
			this.setNewRound(true);
		}
		else if(message.getMessageType() == Message.MessageType.Waiter) {
			this.setStartGame(true);
			logger.info("Start now the game");
			this.setStartGame(false);
		}
		else if(message.getMessageType() == Message.MessageType.Binom) {
			long hash = message.getHash();
			long hashFriend = message.getHashFriend();
			hashCodeStaticThis = hash;
			hashCodeStaticFriend = hashFriend;
		}
	}
	
	// Get the MessageProperty from the chat
	public SimpleStringProperty getChatMessageProperty() {
		return this.chatMessage;
	}

	// Get the MessageProperty from the chat
	public void setChatMessageProperty(String newValue) {
		try {
			this.chatMessage.setValue(newValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// transform the simpleStringProperty to a normal string to give it back to
	// the controller and then to the view
	public String getChatMessageInString() {
		return chatMessage.get();
	}
	
	//set a new move
	public void setValue(Boolean newValue) {
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
	
	public void setDBFriends(Boolean newValue) {
		try {
			this.friends.setValue(newValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public SimpleBooleanProperty getDBRequest(){
		return this.dbRequest;
	}
	
	public SimpleBooleanProperty getDBFriends() {
		return this.friends;
	}
	
	public SimpleBooleanProperty getValue(){
		return this.moveProperty;
	}
	
	public void setHashCode(long newValue){
		this.HashCode.setValue(newValue);
	}
	
	public SimpleLongProperty getHashCode(){
		return this.HashCode;
	}
	
	public long getHashCodeInt(){
		return this.HashCode.get();
	}
	
	public SimpleLongProperty getTime(){
		return this.timeCap;
	}
	
	public long getTimeLong(){
		return this.timeCap.get();
	}
	
	public void setTime(long time) {
		this.timeCap.set(time);
	}
	
	public SimpleIntegerProperty getRound(){
		return this.roundCap;
	}
	
	public void setUpgrade(boolean upgrade){
		this.upgrade.set(upgrade);
	}
	
	public SimpleBooleanProperty getUpgrade(){
		return this.upgrade;
	}
	
	public void setNewRound(boolean newRound){
		this.newRound.set(newRound);
	}
	
	public SimpleBooleanProperty getNewRound(){
		return this.newRound;
	}
	
	public int getRoundInt(){
		return this.roundCap.get();
	}
	
	public void setRound(int roundCap){
		this.roundCap.set(roundCap);
	}
	
	public SimpleBooleanProperty getReadyToPlay() {
		return this.readyToPlay;
	}
	
	public void setReadyToPlay(boolean ready) {
		this.readyToPlay.set(ready);
	}
	
	public SimpleIntegerProperty getRandomStart() {
		return this.randomStart;
	}
	
	public int getRandomStartInt() {
		return this.randomStart.get();
	}
	
	public void setRandomStart(int randomStart) {
		this.randomStart.set(randomStart);
	}
	
	public void setStartGame(boolean start) {
		this.startGame.set(start);
	}
	
	public SimpleBooleanProperty getStartGame() {
		return this.startGame;
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
	
	public int getPlayerType(){
		return this.playerType;
	}
	
	public int getUserID(){
		return this.userID;
	}
	
	public ArrayList<String> getUserList(){
		return this.userList;
	}
	
	public void setXCoordinateUpgrade(int upgrade){
		this.xCoordinateUpgrade = upgrade;
	}
	
	public void setYCoordinateUpgrade(int upgrade){
		this.yCoordinateUpgrade = upgrade;
	}
	
	public void setGems(int gems){
		this.gems = gems;
	}
	
	public int getXCoordinateUpgrade(){
		return this.xCoordinateUpgrade;
	}
	
	public int getYCoordinateUpgrade() {
		return this.yCoordinateUpgrade;
	}
	
	public int getGems(){
		return this.gems;
	}
	
	public boolean getNewRoundLeftRight(){
		return this.newRoundLeftRight;
	}
	
	public void setNewRoundLeftRight(boolean newRound){
		this.newRoundLeftRight = newRound;
	}
	
	public static ArrayList<String> getFriendsList(){
		return friendsList;
	}
	
	public static long getHashCodeThis() {
		return hashCodeStaticThis;
	}
	
	public static long getHashCodeFriend() {
		return hashCodeStaticFriend;
	}
	
	public int getPlayer() {
		return this.player;
	}
	
	public void setPlayer(int player) {
		this.player = player;
	}

}

class Board {
	private static Value[][] board; // singleton
	private static int boardSize = 8;

	public Board() {
	}

	/**
	 * Factory method for returning the singleton board
	 * 
	 * @param mainClass
	 *            The main class of this program
	 * @return The singleton resource locator
	 */
	public static Value[][] getBoard() {
		if (board == null)
			board = new Value[boardSize][boardSize];
		return board;
	}
	
	

}