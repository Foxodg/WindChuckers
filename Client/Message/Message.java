package Message;


import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;


/**
 * A simple example showing how to encapsulate messages in a class. This class sends and receives
 * some simple data via sockets. The data is formatted in XML.
 * 
 * Each message is uniquely identified with an ID and a timestamp. This can be useful, for example,
 * if you want to keep a log of messages.
 */
@Root
public class Message {
	public enum MessageType {
		Coordinate, WinMessage, ChatMessage, DBMessage, Error, Update
	};
	
	public enum Value {
		Player1, Player2, Empty, Danger
	};

	// Data included in a message
	@Attribute
	private long id;

	@Attribute(required = false)
	private long timestamp;

	@Attribute
	private MessageType type;

	@Element(required = false)
	private Integer xCoordinate1;
	
	@Element(required = false)
	private Integer yCoordinate1;
	
	@Element(required = false)
	private Integer xCoordinate2;
	
	@Element(required = false)
	private Integer yCoordinate2;
	
	@Element(required = false)
	private Boolean win;
	
	@Element(required = false)
	private Boolean update;
	
	@Element(required = false)
	private int gems;
	
	@Element(required = false)
	private String chat;

	@Element(required = false)
	private Integer DB;
	
	@Element(required = false)
	private Value value;
	
	@Element(required = false)
	private boolean singlePlayer;

	// Generator for a unique message ID
	private static long messageID = 0;

	/**
	 * Increment the global messageID
	 * 
	 * @return the next valid ID
	 */
	private static long nextMessageID() {
		return messageID++;
	}
	

	/**
	 * For Coordinate
	 * @param messageType
	 * @param xCoordinate
	 * @param yCoordinate
	 * @param value
	 */
	public Message (MessageType messageType, boolean singlePlayer, int xCoordinate1, int yCoordinate1, int xCoordinate2, int yCoordinate2, Value value) {
		createStandardMessage(messageType);
		this.singlePlayer = singlePlayer;
		this.xCoordinate1 = xCoordinate1;
		this.yCoordinate1 = yCoordinate1;
		this.xCoordinate2 = xCoordinate2;
		this.yCoordinate2 = yCoordinate2;
		this.value = value;
	}
	
	/**
	 * For win
	 * @param messageType
	 * @param win
	 */
	public Message(MessageType messageType, boolean win){
		createStandardMessage(messageType);
		this.win = win;
	}
	
	
	public Message(MessageType messageType, boolean update, int xCoordinate1, int yCoordinate1, int xCoordinate2, int yCoordinate2, int gems ){
		createStandardMessage(messageType);
		this.update = update;
		this.xCoordinate1 = xCoordinate1;
		this.yCoordinate1 = yCoordinate1;
		this.xCoordinate2 = xCoordinate2;
		this.yCoordinate2 = yCoordinate2;
		this.gems = gems;
	}
	
	/**
	 * For Chat
	 * @param messageType
	 * @param chat
	 */
	public Message(MessageType messageType, String chat ){
		createStandardMessage(messageType);
		this.chat = chat;
	}
	
	/**
	 * For DB
	 * @param messageType
	 * @param db
	 */
	public Message(MessageType messageType, int db){
		createStandardMessage(messageType);
		this.DB = db;
	}
	
	/**
	 * For Error
	 * @param messageType
	 */
	public Message(MessageType messageType){
		createStandardMessage(messageType);
	}
	
	/**
	 * Empty
	 */
	public Message(){
	}
	
	
	/**
	 * For the standard in a Message
	 * @param type MessageType
	 */
	public void createStandardMessage(MessageType type){
		this.type = type;
		this.id = nextMessageID();
		this.timestamp = System.currentTimeMillis();
	}
	
	/**
	 * Send the current message.
	 * 
	 * @param socket
	 *          the socket to write to
	 * @throws Exception
	 */
	public void send(Socket socket) throws Exception {
		Serializer netOut = new Persister();
		OutputStream out = socket.getOutputStream();
		netOut.write(this, out);
		out.write('\n');
		out.flush();
	}
	
	/**
	 * Receive a message: create a message object and fill it using data transmitted over the given
	 * socket.
	 * 
	 * @param socket
	 *          the socket to read from
	 * @return the new message object, or null in case of an error
	 * @throws Exception
	 */
	public static Message receive(Socket socket) throws Exception {
		Serializer netIn = new Persister();
		InputStream in = socket.getInputStream();
		Message msg = netIn.read(Message.class, in);
		return msg;
	}
	
	
	/**
	 * Simple string representation
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		StringBuffer msg = new StringBuffer();
		msg.append(this.type.toString());
		return msg.toString();		
	}
	
	public MessageType getMessageType(){
		return this.type;
	}
	public String getChatMessage(){
		return this.chat;
	}
	
	public int getXCoordinate1(){
		return this.xCoordinate1;
	}
	
	public int getYCoordinate1(){
		return this.yCoordinate1;
	}
	
	public int getXCoordinate2(){
		return this.xCoordinate2;
	}
	
	public int getYCoordinate2(){
		return this.yCoordinate2;
	}
	
	public int getGems(){
		return this.gems;
	}
	
	public boolean getWin(){
		return this.win;
	}
	
	public boolean getUpdate(){
		return this.update;
	}
	
	public int getDB(){
		return this.DB;
	}
	
	public Value getValue(){
		return this.value;
	}
	
	public boolean getSinglePlayer(){
		return this.singlePlayer;
	}

}
