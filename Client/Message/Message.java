package Message;


import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import Message.Message.MessageType;


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
		Coordinate, WinMessage, ChatMessage, DBMessage, DBMessageFull, Error, Update, AISingle, AIDouble, Hash, Time, NewRound
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

	@Element(required = false)
	private double weightProgressOne;

	@Element(required = false)
	private double weightMovesOne;

	@Element(required = false)
	private double weightBlockOne;

	@Element(required = false)
	private double weightSumoBlockOne;

	@Element(required = false)
	private double weightSumoWinOne;

	@Element(required = false)
	private double weightWinOne;
	
	@Element(required = false)
	private double weightProgressTwo;

	@Element(required = false)
	private double weightMovesTwo;

	@Element(required = false)
	private double weightBlockTwo;

	@Element(required = false)
	private double weightSumoBlockTwo;

	@Element(required = false)
	private double weightSumoWinTwo;

	@Element(required = false)
	private double weightWinTwo;
	
	@ElementList(required=false, inline=true)
	private ArrayList<String> nameList;
	
	@Element(required = false)
	private String preName;
	
	@Element(required = false)
	private String lastName;
	
	@Element(required = false)
	private String userName;
	
	@Element(required = false)
	private int Id;
	
	@Element(required = false)
	private String password;
	
	@Element(required = false)
	private String something;
	
	@Element(required = false)
	private long time;
	
	@Element(required = false)
	private int player;
	
	@Element(required = false)
	private int friendId;
	

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
	public Message (MessageType messageType, int xCoordinate1, int yCoordinate1, int xCoordinate2, int yCoordinate2, int player) {
		createStandardMessage(messageType);
		this.xCoordinate1 = xCoordinate1;
		this.yCoordinate1 = yCoordinate1;
		this.xCoordinate2 = xCoordinate2;
		this.yCoordinate2 = yCoordinate2;
		this.player = player;
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
	
	/**
	 * For Update
	 * @param messageType
	 * @param update
	 * @param xCoordinate1
	 * @param yCoordinate1
	 * @param xCoordinate2
	 * @param yCoordinate2
	 * @param gems
	 */
	public Message(MessageType messageType, int xCoordinate2, int yCoordinate2, int gems, int player ){
		createStandardMessage(messageType);
		this.xCoordinate2 = xCoordinate2;
		this.yCoordinate2 = yCoordinate2;
		this.gems = gems;
		this.player = player;
	}
	
	/**
	 * For AI Single
	 * @param messageType
	 * @param singlePlayer
	 * @param weightProgress
	 * @param weightMoves
	 * @param weightBlock
	 * @param weightSumoBlock
	 * @param weightSumoWin
	 * @param weightWin
	 */
	public Message(MessageType messageType, double weightProgress, double weightMoves, double weightBlock, double weightSumoBlock, double weightSumoWin, double weightWin){
		createStandardMessage(messageType);
		this.weightProgressTwo = weightProgress;
		this.weightMovesTwo = weightMoves;
		this.weightBlockTwo = weightBlock;
		this.weightSumoBlockTwo = weightSumoBlock;
		this.weightSumoWinTwo = weightSumoWin;
		this.weightWinTwo = weightWin;
	}
	
	/**
	 * For AI Double
	 * @param messageType
	 * @param singlePlayer
	 * @param weightProgress
	 * @param weightMoves
	 * @param weightBlock
	 * @param weightSumoBlock
	 * @param weightSumoWin
	 * @param weightWin
	 */
	public Message(MessageType messageType, double weightProgressL, double weightMovesL, double weightBlockL, double weightSumoBlockL, double weightSumoWinL, double weightWinL, 
			double weightProgressR, double weightMovesR, double weightBlockR, double weightSumoBlockR, double weightSumoWinR, double weightWinR){
		createStandardMessage(messageType);
		this.weightProgressOne = weightProgressL;
		this.weightMovesOne = weightMovesL;
		this.weightBlockOne = weightBlockL;
		this.weightSumoBlockOne = weightSumoBlockL;
		this.weightSumoWinOne = weightSumoWinL;
		this.weightWinOne = weightWinL;
		
		this.weightProgressTwo = weightProgressR;
		this.weightMovesTwo = weightMovesR;
		this.weightBlockTwo = weightBlockR;
		this.weightSumoBlockTwo = weightSumoBlockR;
		this.weightSumoWinTwo = weightSumoWinR;
		this.weightWinTwo = weightWinR;
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
	 * For DB full Request
	 * @param messageType
	 * @param db
	 * @param ArrayList<String>
	 */
	public Message(MessageType messageType, int db, ArrayList<String> nameList){
		createStandardMessage(messageType);
		this.DB = db;
		this.nameList = nameList;
	}
	
	/**
	 * For DB insert
	 * @param messageType
	 * @param db
	 * @param preName
	 * @param lastName
	 */
	public Message(MessageType messageType, int db, int Id, String userName, String preName, String lastName, String password){
		createStandardMessage(messageType);
		this.DB = db;
		this.Id = Id;
		this.preName = preName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
	}
	
	/**
	 * For DB Updates
	 * @author L.Weber
	 * @param messageType
	 * @param db
	 * @param preName
	 * @param lastName
	 */
	public Message(MessageType messageType, int db, String preName, String lastName){
		createStandardMessage(messageType);
		this.DB = db;
		this.preName = preName;
		this.lastName = lastName;
	}
	
	/**
	 * For Time-Cap-Message
	 * @param messageType
	 * @param time
	 */
	public Message(MessageType messageType, long time){
		createStandardMessage(messageType);
		this.time = time;
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
	 * For DB with one 
	 * @param dbmessage
	 * @param db2
	 * @param something
	 */
	public Message(MessageType messageType, int db, int id) {
		createStandardMessage(messageType);
		this.DB = db;
		this.Id = id;
		
	}
	
	/**
	 * For make new Friends
	 * @param messageType
	 * @param db
	 * @param id
	 * @param friendId
	 */
	public Message(MessageType messageType, int db, int id, int friendId) {
		createStandardMessage(messageType);
		this.DB = db;
		this.Id = id;
		this.friendId = friendId;
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
	
	public double getProgressONE(){
		return this.weightProgressOne;
	}
	
	public double getProgressTWO(){
		return this.weightProgressTwo;
	}
	
	public double getMovesONE(){
		return this.weightMovesOne;
	}
	
	public double getMovesTWO(){
		return this.weightMovesTwo;
	}
	
	public double getBlockONE(){
		return this.weightBlockOne;
	}
	
	public double getBlockTWO(){
		return this.weightBlockTwo;
	}
	
	public double getSumoBlockONE(){
		return this.weightSumoBlockOne;
	}
	
	public double getSumoBlockTWO(){
		return this.weightSumoBlockTwo;
	}
	
	public double getSumoWinONE(){
		return this.weightSumoWinOne;
	}
	
	public double getSumoWinTWO(){
		return this.weightSumoWinTwo;
	}
	
	public double getWinONE(){
		return this.weightWinOne;
	}
	
	public double getWinTWO(){
		return this.weightWinTwo;
	}
	
	public ArrayList<String> getNameList(){
		return this.nameList;
	}
	
	public String getPreName(){
		return this.preName;
	}
	
	public String getLastName(){
		return this.lastName;
	}
	
	public String getUserName(){
		return this.userName;
	}
	
	public int getId(){
		return this.Id;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public String getSomething(){
		return this.something;
	}
	
	public long getTime(){
		return this.time;
	}
	
	public int getPlayer(){
		return this.player;
	}
	
	public int getFriendId() {
		return this.friendId;
	}

}
