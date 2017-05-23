package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Logger;

import Message.Message;
import Message.Message.MessageType;
import Message.Message.Value;

public class ServerThreadForClient extends Thread {
	private final Logger logger = Logger.getLogger("");
	private Socket clientSocket;

	public ServerThreadForClient(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	/**
	 * Process messages 
	 */
	@Override
	public void run() {
		while (true) {
			Message.MessageType lastMessageType = null;
			logger.info("Request from client " + clientSocket.getInetAddress().toString() + " for server "
					+ clientSocket.getLocalAddress().toString());
			try {
				Message message = Message.receive(clientSocket);
				getMessageType(message);
				
			} catch (Exception e) {
				logger.severe(e.toString());
			}
		}
	}

	public void getMessageType(Message message) {
		if (message.getMessageType() == MessageType.ChatMessage) {
			logger.info("Server: " + "Chat-Message: " + message.getChatMessage()); 
			//For the Chat-Messaging
			sendMessageBackToClient(message);
		}
		else if(message.getMessageType() == MessageType.Coordinate){
			logger.info("Server: " + "x-Coordinates: " + message.getXCoordinate() + " y-Coordinates: " + message.getYCoordinate() + " Value: " + message.getValue());
			//Safes the coordinates for the hidden-Board on the Server
			Value[][] board = Board.getBoard();
			board[message.getXCoordinate()][message.getYCoordinate()] = message.getValue();
			sendMessageBackToClient(message);
		}
		else if(message.getMessageType() == MessageType.DBMessage){
			logger.info("Server: " + "DB-Message: " );
			//DB-Message
		}
		else if(message.getMessageType() == MessageType.WinMessage){
			logger.info("Server: " + "Win-Message: " );
			//Win-Message
		}
		else {
			logger.info("Server" + "Error-Message: ");
			//Else must be an Error-Message
		}
	}
	
	public void sendMessageBackToClient(Message message){
		try {
			ServerModel model = ServerModel.getServerModel();
			ArrayList<Socket> sockets = model.getSockets();
			for(Socket socket : sockets){
				message.send(socket);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
