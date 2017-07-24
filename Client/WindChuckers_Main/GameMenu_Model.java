package WindChuckers_Main;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

import Client.ClientThreadForServer;
import Login.LoginModel;
import Message.Message;
import Message.Message.MessageType;
import Message.Message.Value;
import WindChuckers_Main.Model_Extend.Player;
import abstractClasses.Model;
import commonClasses.ServiceLocator;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public class GameMenu_Model extends Model {

	public static final int DIMENSION = 8;
	public static final int MAX_FIELDS = DIMENSION * DIMENSION;
	public static boolean gameStart = true;

	public final static String ORANGE = "#FF8C00";
	public final static String BLUE = "#4169E1";
	public final static String VIOLET = "#663399";
	public final static String PINK = "#FF69B4";
	public final static String YELLOW = "#FFD700";
	public final static String RED = "#B22222";
	public final static String GREEN = "#008000";
	public final static String BROWN = "#8B4513";
	
	public static int playerCausedPat = 0;
	
	public static IntegerProperty Winner = new SimpleIntegerProperty(0);
	
	private static int gameMode = 2;

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
	private static Hashtable<Integer, String> users = new Hashtable<Integer, String>();
	private static Hashtable<Integer, String> friends = new Hashtable<Integer, String>();
	private static Hashtable<Integer, Boolean> friendsRequest = new Hashtable<Integer, Boolean>();

	// SimpleBooleanProperty for overwatching the incoming moves
	private SimpleBooleanProperty moveProperty = new SimpleBooleanProperty();

	// SimpleBooleanProperty for overwatching the incoming dbRequests
	private SimpleBooleanProperty dbRequest = new SimpleBooleanProperty();
	
	// SimmpleStringProperty for chat
	public SimpleStringProperty thisUserNameProperty = new SimpleStringProperty();	
	
	// SimpleIntegerProperty for HashCode
	public SimpleLongProperty HashCode = new SimpleLongProperty();

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
	 * Make new Friends
	 * 
	 * @param db
	 * @param id
	 * @param friendId
	 * @author L.Weber
	 */
	public void messageConstructorForFriendsInsert(int db, int id, int friendId) {
		Message message = new Message(MessageType.DBMessage, db, id, friendId);
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
	
	/**
	 * Message Constructor for send the name
	 * @param message
	 */
	public void messageConstructorForName(long hash, String username) {
		Message message = new Message(MessageType.Name, hash, username);
		sendMessage(message);		
	}
	
	/**
	 * Message Constructor for send the binom
	 * @param username
	 */
	public void messageConstructorForBuildBinom(String username, String friendname) {
		Message message = new Message(MessageType.Binom, username, friendname);
		sendMessage(message);
	}	
	
	/**
	 * Message Constructor for send Message capsuled
	 * 
	 * @param hash
	 * @param chat
	 *            To get the chat message is getUserName();
	 */
	public void messageConstructorForBuildCapsule(long hash, String chat) {
		Message message = new Message(MessageType.Capsule, hash, chat);
		sendMessage(message);
	}
	
	public void messageConstructorForWaiter(long hash) {
		Message message = new Message(MessageType.Waiter, hash);
		sendMessage(message);
	}


	/**
	 * Create the userList
	 * @param userList
	 * @author L.Weber
	 */
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
			if (!userList.get(1).equals("deleted")) {
				users.put(Integer.parseInt(userList.get(0)), userList.get(1));
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
				if (userMap.get(id) == null) {
					userMap.put(id, new ArrayList<String>());
					userMap.get(id).addAll(uList);
				}
			}
			for (int i = 0; i < 6; i++) {
				userList.remove(0);
			}
		}
	}

	/**
	 * 
	 * @return
	 * @author L.Weber / T.Bosshard
	 */
	public ArrayList<String> getFriends() {
		ArrayList<String> friendsLocal = new ArrayList<String>();
		this.messageConstructorForDB(4);

		Enumeration e = friends.keys();
		while (e.hasMoreElements()) {
			int key = (int) e.nextElement();
			friendsLocal.add(friends.get(key));
		}

		return friendsLocal;
	}

	/**
	 * 
	 * @return
	 * @author L.Weber / T.Bosshard
	 */
	public ArrayList<String> getFriendsRequests() {
		ArrayList<String> friendsRequests = new ArrayList<String>();
		this.messageConstructorForDB(4);
		
		Enumeration e = friendsRequest.keys();
		int key;
		boolean request;
		while (e.hasMoreElements()) {
			key = (int) e.nextElement();
			request = (boolean) friendsRequest.get(key);
			if(!request) {
				friendsRequests.add(users.get(key));
			}
		}
		return friendsRequests;
	}

	/**
	 * 
	 * @param searchString
	 * @author L.Weber / T.Bosshard
	 */
	public ArrayList<String> getFriendsSearchResults(String searchString) {
		ArrayList<String> allPossibleFriends = new ArrayList<String>();
		ArrayList<String> matchingSearchResults = new ArrayList<String>();

		Enumeration e = users.keys();
		while (e.hasMoreElements()) {
			int key = (int) e.nextElement();
			allPossibleFriends.add(users.get(key));
		}
		if (searchString.equalsIgnoreCase("") || searchString.isEmpty()) {
			for (int i = 0; i < allPossibleFriends.size(); i++) {
				matchingSearchResults.add(allPossibleFriends.get(i));
			}
		} else {
			for (int i = 0; i < allPossibleFriends.size(); i++) {
				if (searchString.equalsIgnoreCase(allPossibleFriends.get(i))) {
					matchingSearchResults.add(allPossibleFriends.get(i));
				}
			}
		}

		return matchingSearchResults;
	}

	/**
	 * 
	 * @param friend
	 * @author L.Weber / T.Bosshard
	 */
	public void acceptFriendsRequest(String friend, String self) {
		int id = 0;
		int friendId = 0;
		String friendSplit[] = friend.split(" ");
		String selfSplit[] = self.split(" ");
		for (int i = 1; i <= users.size(); i++) {
			if (friendSplit[0].equalsIgnoreCase(users.get(i))) {
				friendId = i;
			}
		}
		for (int j = 1; j <= users.size(); j++) {
			if (selfSplit[0].equalsIgnoreCase(users.get(j))) {
				id = j;
			}
		}

		this.messageConstructorForFriendsInsert(5, id, friendId);

		System.out.println("Friend added: " + self + " : " + friend);
	}

	/**
	 * 
	 * @param friend
	 * @author L.Weber / T.Bosshard
	 */
	public void refuseFriendsRequest(String friend) {
		int friendId = 0;

		for (int i = 1; i <= users.size(); i++) {
			String[] name = friend.split(" ");
			if (name[0].equalsIgnoreCase(users.get(i))) {
				friendId = i;
			}
		}

		this.messageConstructorForDBUpdate(6, friendId);
		System.out.println("FriendsRequest refused: " + friend);
	}

	/**
	 * 
	 * @param friend
	 * @author T.Bosshard
	 */
	public void removeFriend(String friend) {
		System.out.println("Friend removed: " + friend);
	}

	/**
	 * 
	 * @param friend
	 * @author T.Bosshard
	 */
	public void addFriend(String friend, String self) {
		int id = 0;
		int friendId = 0;
		for (int i = 1; i <= users.size(); i++) {
			if (friend.equalsIgnoreCase(users.get(i))) {
				friendId = i;
			}
		}
		for (int j = 1; j <= users.size(); j++) {
			if (self.equalsIgnoreCase(users.get(j))) {
				id = j;
			}
		}
		this.messageConstructorForFriendsInsert(5, id, friendId);

		System.out.println("Friend added: " + self + " : " + friend);
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
	
	public void setHashCode(long newValue) {
		this.HashCode.setValue(newValue);
	}

	public SimpleLongProperty getHashCode() {
		return this.HashCode;
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
	
	public static int getGameMode() {
		return gameMode;
	}
	
	public void setGameMode(int gameMode) {
		GameMenu_Model.gameMode=gameMode;
	}

	
	/**
	 * Split to effective Friends and Request
	 * @param friendsList
	 * @author L.Weber
	 */
	public void setFriends(ArrayList<String> friendsList) {
		int requestedId = 0;
		int requestId = 0;
		String username = null;
		boolean request = false;

		if (!(friendsList == null)) {
			while (!friendsList.isEmpty()) {
				
				//the user who is requested
				requestedId = Integer.parseInt(friendsList.get(2));
				
				//the user who made the request
				requestId = Integer.parseInt(friendsList.get(1));
				
				if (friendsList.get(3).equals("TRUE")) {
					request = true;
				} else {
					request = false;
				}

				//Safe this users Name
				String thisUserName = LoginModel.getUserName();
				int thisUserId = 0;
				
				Enumeration e = users.keys();
				while (e.hasMoreElements()) {
					int key = (int) e.nextElement();
					
					//Need the Id for this User
					if(thisUserName.equalsIgnoreCase(users.get(key))) {
						thisUserId = key;
					}
					
					//if the id has the same number like the key then add this name to username
					if (key == requestedId) {
						username = users.get(key);
					}
				}

				// add the found to the list
				if (request) {
					//only add this to the friends when this is not the same user
					if(requestedId != thisUserId) {
						friends.put(requestedId, username);
					}
				} else {
					serviceLocator.getLogger().info("Not both player will this");
				}
				if(requestedId == thisUserId) {
					// the request has to be for this user
					if(requestId != thisUserId) {
						//all Requests that this user started i will not see
						
						Enumeration e1 = friends.keys();
						while (e1.hasMoreElements()) {
							int key = (int) e1.nextElement();
							if(key != requestId) {
								// this person not allowed when it in friends
								friendsRequest.put(requestId, request);
							}
						}
					}
				}
				// remove this because the while loop
				friendsList.remove(0);
				friendsList.remove(0);
				friendsList.remove(0);
				friendsList.remove(0);
			}
		}
	}
}
