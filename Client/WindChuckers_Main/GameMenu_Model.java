package WindChuckers_Main;

import java.net.Socket;

import Client.ClientThreadForServer;
import Message.Message;
import Message.Message.MessageType;
import Message.Message.Value;
import abstractClasses.Model;
import commonClasses.ServiceLocator;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public class GameMenu_Model extends Model {
	
	protected static final int TOTALFIELDS = 64;
	protected static final int TOTALROWS = 8;
	protected static final int TOTALCOLUMNS = 8;
	
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
	 * For the Coordinate-Message
	 * @author L.Weber
	 * @param xCoordinate
	 * @param yCoordinate
	 * @param Value
	 */
	public void messageConstructorForCoordinate(int xCoordinate, int yCoordinate, Value value) {
		Message message = new Message(MessageType.Coordinate, xCoordinate, yCoordinate, value);
		sendMessage(message);
	}

	/**
	 * For the Win-Message
	 * 
	 * @param win
	 */
	public void messageConstructorForWin(boolean win) {
		Message message = new Message(MessageType.WinMessage, win);
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
	
	
}
