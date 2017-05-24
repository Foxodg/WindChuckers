package Client;

import java.net.Socket;
import java.util.logging.Logger;

import Message.Message;
import Message.Message.MessageType;
import Message.Message.Value;
import commonClasses.ServiceLocator;
import javafx.beans.property.SimpleStringProperty;

public class ClientThreadForServer extends Thread {
	private static ClientThreadForServer clientServer;
	
	private ServiceLocator serviceLocator = ServiceLocator.getServiceLocator();
	private final Logger logger = serviceLocator.getLogger();
	private Socket serverSocket;
	private ClientController controller;
	
	// SimpleStringProperty for overwatching the chat
	private SimpleStringProperty chatMessage = new SimpleStringProperty();
	
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
	
	public ClientThreadForServer(){
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
			logger.info("Client: " + "x-Coordinates1: " + message.getXCoordinate1() + " y-Coordinates1: " + message.getYCoordinate1() +
					" x-Coordinates2: " + message.getXCoordinate2() + " y-Coordinates2: " + message.getYCoordinate2() + " Value: " + message.getValue());
			Value[][] board = Board.getBoard();
			board[message.getXCoordinate2()][message.getYCoordinate2()] = message.getValue(); 
		}
		else if (message.getMessageType() == MessageType.Update){
			logger.info("Client: " + "Update: " + message.getUpdate() + " x-Coordinates1: " + message.getXCoordinate1() + " y-Coordinates1: " + message.getYCoordinate1() +
					" x-Coordinates2: " + message.getXCoordinate2() + " y-Coordinates2: " + message.getYCoordinate2() + " Gems: " + message.getGems());
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