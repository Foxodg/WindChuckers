package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Logger;

import AI.Board;
import AI.Kamisado;
import AI.Move;
import Message.Message;
import Message.Message.MessageType;
import Message.Message.Value;

public class ServerThreadForClient extends Thread {
	private final Logger logger = Logger.getLogger("");
	private Socket clientSocket;
	private Kamisado kamisado;
	private Board board = Board.getBoard();

	public ServerThreadForClient(Socket clientSocket) {
		this.clientSocket = clientSocket;
		this.kamisado = Kamisado.getKamisado();
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
			logger.info("Server: " + "x-Coordinates1: " + message.getXCoordinate1() + " y-Coordinates1: " + message.getYCoordinate1() +
					" x-Coordinates2: " + message.getXCoordinate2() + " y-Coordinates2: " + message.getYCoordinate2() + " Value: " + message.getValue());
			//send the Message Back to all Clients
			board.makeMove(new Move(message.getXCoordinate1(), message.getYCoordinate1(), message.getXCoordinate2(), message.getYCoordinate2(),true));
			sendMessageBackToClient(message);
			//Safes the coordinates for the hidden-Board on the Server
			Move move = kamisado.setPlayConfiguration(message.getSinglePlayer(), 5, 100, 100, 15, 25, 12, Double.POSITIVE_INFINITY);
			Message messageAI = new Message(MessageType.Coordinate, message.getSinglePlayer(), move.getX1(),move.getY1(),move.getX2(),move.getY2(), Value.Player2);
			sendMessageBackToClient(messageAI);
		}
		else if(message.getMessageType() == MessageType.Update){
			logger.info("Server: " + "Update: " + message.getUpdate() + " x-Coordinates1: " + message.getXCoordinate1() + " y-Coordinates1: " + message.getYCoordinate1() +
					" x-Coordinates2: " + message.getXCoordinate2() + " y-Coordinates2: " + message.getYCoordinate2() + " Gems: " + message.getGems());
			//send the Message Back to all Clients
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
	
	public static void sendMessageBackToClient(Message message){
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
