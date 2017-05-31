package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;


import AI.Board;
import AI.Kamisado;
import AI.Move;
import DataBase.DataBase;
import Message.Message;
import Message.Message.MessageType;
import Message.Message.Value;

public class ServerThreadForClient extends Thread {
	private final Logger logger = Logger.getLogger("");
	private Socket clientSocket;
	private Kamisado kamisado;
	private Board board = Board.getBoard();
	
	private boolean wantAI = false;
	private boolean wantDoubleAI = false;
	
	DataBase h2 = DataBase.getDB();

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
			if(this.wantAI){
				Move move = kamisado.setPlayConfiguration(true, 5);
				Message messageAI = new Message(MessageType.Coordinate, message.getSinglePlayer(), move.getX1(),move.getY1(),move.getX2(),move.getY2(), Value.Player2);
				sendMessageBackToClient(messageAI);
			}

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
			if(message.getDB() == 0){
				// 0 stands for give the hole ArrayList Back
				try {
					sendMessageBackToClient(new Message(MessageType.DBMessageFull,0,h2.selectPlayer()));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else if(message.getDB() == 1){
				//1 stands for Insert
				insertPlayer(message.getId(), message.getUserName(), message.getPreName(), message.getLastName(), message.getPassword());
				try {
					sendMessageBackToClient(new Message(MessageType.DBMessageFull,0,h2.selectPlayer()));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else if(message.getDB() == 2){
				//2 stands for Update
				updatePlayer(message.getId(), message.getUserName(), message.getPreName(), message.getLastName(), message.getPassword());
				try {
					sendMessageBackToClient(new Message(MessageType.DBMessageFull,0,h2.selectPlayer()));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else if(message.getDB() == 3){
				//3 stands for Delete
				
			}
		}
		else if(message.getMessageType() == MessageType.WinMessage){
			logger.info("Server: " + "Win-Message: " );
			//Win-Message
		}
		else if(message.getMessageType() == MessageType.AISingle){
			logger.info("Server: "+"Paramter-Injection SinglePlayer:\n"+message.getProgressTWO()+"\n"+message.getMovesTWO()+"\n"+message.getBlockTWO()+"\n"+message.getSumoBlockTWO()+"\n"+message.getSumoWinTWO()+"\n"+message.getWinTWO());
			kamisado.setProgressTwo(message.getProgressTWO());
			kamisado.setMovesTwo(message.getMovesTWO());
			kamisado.setBlockTwo(message.getBlockTWO());
			kamisado.setSumoBlockTwo(message.getSumoBlockTWO());
			kamisado.setSumoWinTwo(message.getSumoWinTWO());
			kamisado.setWinTwo(message.getWinTWO());
			this.wantAI = true;
		}
		else if(message.getMessageType() == MessageType.AIDouble){
			logger.info("Server: "+"Paramter-Injection DoublePlayer:\n"
					+"\nONE: "+message.getProgressONE()+"\n"+message.getMovesONE()+"\n"+message.getBlockONE()+"\n"+message.getSumoBlockONE()+"\n"+message.getSumoWinONE()+"\n"+message.getWinONE()
					+"\nTWO: "+message.getProgressTWO()+"\n"+message.getMovesTWO()+"\n"+message.getBlockTWO()+"\n"+message.getSumoBlockTWO()+"\n"+message.getSumoWinTWO()+"\n"+message.getWinTWO());

			
			kamisado.setProgressOne(message.getProgressONE());
			kamisado.setMovesOne(message.getMovesONE());
			kamisado.setBlockOne(message.getBlockONE());
			kamisado.setSumoBlockOne(message.getSumoBlockONE());
			kamisado.setSumoWinOne(message.getSumoWinONE());
			kamisado.setWinOne(message.getWinONE());
			
			kamisado.setProgressTwo(message.getProgressTWO());
			kamisado.setMovesTwo(message.getMovesTWO());
			kamisado.setBlockTwo(message.getBlockTWO());
			kamisado.setSumoBlockTwo(message.getSumoBlockTWO());
			kamisado.setSumoWinTwo(message.getSumoWinTWO());
			kamisado.setWinTwo(message.getWinTWO());
			this.wantAI = true;
			this.wantDoubleAI = true;
			
			if(this.wantAI && this.wantDoubleAI){
				Move move = kamisado.setPlayConfiguration(false, 5);
			}
		}
		else {
			logger.info("Server" + "Error-Message: ");
			//Else must be an Error-Message
		}
	}
	
	private void insertPlayer(int id, String userName, String preName, String lastName, String password) {
		// TODO Auto-generated method stub
		try {
			h2.insertPlayer(id, userName, preName, lastName, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void updatePlayer(int id, String userName, String preName, String lastName, String password){
		try {
			h2.updatePreparedStatementPreName(preName, id);
			h2.updatePreparedStatementSurName(lastName, id);
			h2.updatePreparedStatementUserName(userName, id);
			h2.updatePreparedStatementPassword(password, id);
			
		} catch (SQLException e) {
			e.printStackTrace();
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
