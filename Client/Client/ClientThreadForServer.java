package Client;

import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

import Message.Message;
import Message.Message.MessageType;
import Message.Message.Value;
import commonClasses.ServiceLocator;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
	private int userID;
	private ArrayList<String> userList;
	
	// SimpleStringProperty for overwatching the chat
	private SimpleStringProperty chatMessage = new SimpleStringProperty();
	
	// SimpleBooleanProperty for overwatching the incoming moves
	private SimpleBooleanProperty moveProperty = new SimpleBooleanProperty();
	
	// SimpleBooleanProperty for overwatching the incoming moves
	private SimpleBooleanProperty dbRequest = new SimpleBooleanProperty();
	
	// SimpleIntegerProperty for HashCode
	public SimpleIntegerProperty HashCode = new SimpleIntegerProperty();
	
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
			setValue(true);
			
		}
		else if (message.getMessageType() == MessageType.Update){
			logger.info("Client: " + "Update: " + message.getUpdate() + " x-Coordinates1: " + message.getXCoordinate1() + " y-Coordinates1: " + message.getYCoordinate1() +
					" x-Coordinates2: " + message.getXCoordinate2() + " y-Coordinates2: " + message.getYCoordinate2() + " Gems: " + message.getGems());
		}
		else if (message.getMessageType() == MessageType.DBMessage || message.getMessageType() == MessageType.DBMessageFull){
			logger.info("Client: DB-Message is arrived " + message.getDB());

			if(message.getDB() == 0){
				// 0 stands for full List
				setDBRequest(false);
				userList = message.getNameList();
				setDBRequest(true);
			}
		}
		else if(message.getMessageType() == Message.MessageType.Hash){
			logger.info("Hash-Code comes back: " + message.getDB());
			this.setHashCode(message.getDB());
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
	
	public SimpleBooleanProperty getDBRequest(){
		return this.dbRequest;
	}
	
	public SimpleBooleanProperty getValue(){
		return this.moveProperty;
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
	
	public int getUserID(){
		return this.userID;
	}
	
	public ArrayList<String> getUserList(){
		return this.userList;
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