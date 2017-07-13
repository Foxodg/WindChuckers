package TestClasses;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import Message.Message;
import Message.Message.MessageType;

public class MessageTest {
	Message chatMessage;
	Message dbMessageFull;
	Message dbMessageInsert;
	Message MessageAIDouble;
	Message MessageAI;
	Message MessageCoordinateSingle;
	Message MessageCoordinateMulti;
	Message MessageUpdate;
	
	@Before
	public void makeChatMessage(){
		chatMessage = new Message(MessageType.ChatMessage,"Chat");
		dbMessageFull = new Message(MessageType.DBMessage,0);
		dbMessageInsert = new Message(MessageType.DBMessage,1,1,"Testy","PreName","LastName","password");
		MessageAIDouble = new Message(MessageType.AIDouble,100.0,100.0,10.0,10.0,10.0,100.0,100.0,100.0,10.0,10.0,10.0,100.0);
		MessageAI = new Message(MessageType.AISingle,100.0,100.0,10.0,10.0,10.0,100.0);
		MessageCoordinateSingle = new Message(MessageType.Coordinate,0,0,0,1,1);
		MessageCoordinateMulti = new Message(MessageType.Coordinate,0,0,0,1,1);
		MessageUpdate = new Message(MessageType.Update,0,1,15,1);
	}
	
	@Test
	public void testChatMessage(){
		assertEquals("Same MessageType",MessageType.ChatMessage, this.chatMessage.getMessageType());
		assertEquals("Same Text","Chat", this.chatMessage.getChatMessage());
	}
	
	@Test
	public void testDBMessage(){
		assertEquals("Full DB Request - MessageType", MessageType.DBMessage, this.dbMessageFull.getMessageType());
		assertEquals("Full DB Request - DB-Type", 0, this.dbMessageFull.getDB());
		assertEquals("Insert DB Request - MessageType", MessageType.DBMessage, this.dbMessageInsert.getMessageType());
		assertEquals("Insert DB Request - DB-Type",1, this.dbMessageInsert.getDB());
		assertEquals("Insert DB Request - ID", 1, this.dbMessageInsert.getId());
		assertEquals("Insert DB Request - UserName", "Testy", this.dbMessageInsert.getUserName());
		assertEquals("Insert DB Request - PreName", "PreName", this.dbMessageInsert.getPreName());
		assertEquals("Insert DB Request - LastName", "LastName", this.dbMessageInsert.getLastName());
		assertEquals("Insert DB Request - Passoword", "password", this.dbMessageInsert.getPassword());
	}
	
	@Test
	public void testAIMessage() {
		assertEquals("DoubleAI - MessageType", MessageType.AIDouble, this.MessageAIDouble.getMessageType());
		assertEquals("DoubleAI - weightProgressL", 100,0, this.MessageAIDouble.getProgressONE());
		assertEquals("DoubleAI - weightMovesL", 100,0, this.MessageAIDouble.getWinONE());
		assertEquals("DoubleAI - weightBlockL", 10,0, this.MessageAIDouble.getBlockONE());
		assertEquals("DoubleAI - weightSumoBlockL", 10,0, this.MessageAIDouble.getSumoBlockONE());
		assertEquals("DoubleAI - weightSumoWinL", 10,0, this.MessageAIDouble.getSumoWinONE());
		assertEquals("DoubleAI - weightWinL", 100,0, this.MessageAIDouble.getWinONE());
		assertEquals("DoubleAI - weightProgressR", 100,0, this.MessageAIDouble.getProgressTWO());
		assertEquals("DoubleAI - weightMovesR", 100,0, this.MessageAIDouble.getWinTWO());
		assertEquals("DoubleAI - weightBlockR", 10,0, this.MessageAIDouble.getBlockTWO());
		assertEquals("DoubleAI - weightSumoBlockR", 10,0, this.MessageAIDouble.getSumoBlockTWO());
		assertEquals("DoubleAI - weightSumoWinR", 10,0, this.MessageAIDouble.getSumoWinTWO());
		assertEquals("DoubleAI - weightWinR", 100,0, this.MessageAIDouble.getWinTWO());
		
		assertEquals("AI - weightProgressR", 100,0, this.MessageAI.getProgressTWO());
		assertEquals("AI - weightMovesR", 100,0, this.MessageAI.getWinTWO());
		assertEquals("AI - weightBlockR", 10,0, this.MessageAI.getBlockTWO());
		assertEquals("AI - weightSumoBlockR", 10,0, this.MessageAI.getSumoBlockTWO());
		assertEquals("AI - weightSumoWinR", 10,0, this.MessageAI.getSumoWinTWO());
		assertEquals("AI - weightWinR", 100,0, this.MessageAI.getWinTWO());
	}
	
	@Test
	public void testCoordinateMessage(){
		assertEquals("Coordinate - MessageType", MessageType.Coordinate, this.MessageCoordinateSingle.getMessageType());
		assertEquals("Coordinate - SinglePlayer", true, this.MessageCoordinateSingle.getSinglePlayer());
		assertEquals("Coordinate - xCoordinate1", 0 , this.MessageCoordinateSingle.getXCoordinate1());
		assertEquals("Coordinate - yCoordinate1", 0 , this.MessageCoordinateSingle.getYCoordinate1());
		assertEquals("Coordinate - xCoordinate2", 0 , this.MessageCoordinateSingle.getXCoordinate2());
		assertEquals("Coordinate - yCoordinate2", 1 , this.MessageCoordinateSingle.getYCoordinate2());
		
		assertEquals("CoordinateMulti - MessageType", MessageType.Coordinate, this.MessageCoordinateMulti.getMessageType());
		assertEquals("CoordinateMulti - SinglePlayer", false, this.MessageCoordinateMulti.getSinglePlayer());
		assertEquals("CoordinateMulti - xCoordinate1", 0 , this.MessageCoordinateMulti.getXCoordinate1());
		assertEquals("CoordinateMulti - yCoordinate1", 0 , this.MessageCoordinateMulti.getYCoordinate1());
		assertEquals("CoordinateMulti - xCoordinate2", 0 , this.MessageCoordinateMulti.getXCoordinate2());
		assertEquals("CoordinateMulti - yCoordinate2", 1 , this.MessageCoordinateMulti.getYCoordinate2());
	}
	
	@Test
	public void testUpdateMessage(){
		assertEquals("Update - MessageType", MessageType.Update, this.MessageUpdate.getMessageType());
		assertEquals("Update - Update", true, this.MessageUpdate.getUpdate());
		assertEquals("Update - xCoordinate1", 0, this.MessageUpdate.getXCoordinate1());
		assertEquals("Update - yCoordinate1", 0, this.MessageUpdate.getYCoordinate1());
		assertEquals("Update - xCoordinate2", 0, this.MessageUpdate.getXCoordinate2());
		assertEquals("Update - yCoordinate2", 1, this.MessageUpdate.getYCoordinate2());
	}
	
	
	
//	@Parameters
//	public static Collection<Object[]> inputDates() {
//		return Arrays.asList(new Object[][] {
//			//Coordinate Type
//			{1, 0, 0, 0, 1 },
//			{2, 1, 1, 2, 2},
//			
//			//Win Type
//			{2},
//			{5},
//			
//			//Update Type
//			{true, 1, 0,0,0,1},
//			{false,1, 0,0,0,1},
//			{true,2, 0,0,0,1},
//			{false,2,0,0,0,1}
//			
//			//AI Single
//		});
//	}
//	
//	@RunWith(Parameterized.class)
//	public class MessageParamTest {
//		MessageType messageType;
//		int xCoordinate;
//		int yCoordinate;
//		int xCoordinate2;
//		int yCoordinate2;
//		boolean update;
//		int singlePlayer;
//	
//	@Before
//	public void construcData(){
//		this.xCoordinate = Loc
//	}
//	}
//	

}
