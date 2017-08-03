package TestClasses;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import AI.Board;
import AI.Move;
import AI.PlayerType;
import AI.Tower;
import AI.Type;
import AI.Color;

public class BoardTest {
	Board board = Board.getBoard();
	
	@BeforeClass
	public static void setUp() {

	}

	@Test
	public void testMakeMove() {
		//sets up the test
		Move lastmove = new Move(0,0,0,1);
		board.setLastMove(lastmove);
		Board emptyBoard = new Board();
		Board notProvBoard = new Board();
		Board provBoard = new Board();
		Board sumoBoard = new Board();
		
		Move testMove = new Move(0,7,0,6,false);
		Move testMove1 = new Move(0,7,0,6,true);
		
		//make method
		Move provMove = new Move(0,7,0,6,true);
		provBoard.makeMove(provMove);
		
		Move defMove = new Move(0,7,0,6,false);
		notProvBoard.makeMove(defMove);
		
		sumoBoard.upgradeTower(sumoBoard.getTile(0, 7).getTower());
		Move moving = new Move(0,0,0,1,false);
		Move sumoMove = new Move(0,7,0,1,true);
		sumoBoard.makeMove(sumoMove);
		
		
		//assert
		assertEquals("Test the move-response", testMove, new Move(0,7,0,6,false));
		assertEquals("Test the move-response", testMove1, new Move(0,7,0,6,true));
		assertEquals("Test the boards when the move is prov", false, provBoard.getTile(0, 7).isOccupied());
		assertEquals("Test the boards when the move is not prov", true, notProvBoard.getTile(0,7).isOccupied());
		assertEquals("Test the sumoPush", true, sumoBoard.getTile(0, 1).isOccupied());
		assertEquals("Test the pushedTower", true, sumoBoard.getTile(0, 0).isOccupied());
	}
	@Test
	public void testUpdateNoGem(){
		//set up
		Board updateBoard = new Board();
		
		//make method
		//Nothing to do here
		
		//assert
		assertEquals("Test the updateTower / Gems = 1", 0, updateBoard.getTile(0, 0).getTower().getGems());
		assertNotEquals("Test other numbers", 1, updateBoard.getTile(0, 0).getTower().getGems());
		assertNotEquals("Test other numbers", 2, updateBoard.getTile(0, 0).getTower().getGems());
		assertNotEquals("Test other numbers", 3, updateBoard.getTile(0, 0).getTower().getGems());
		assertNotEquals("Test other numbers", 4, updateBoard.getTile(0, 0).getTower().getGems());
		assertNotEquals("Test other numbers", -1, updateBoard.getTile(0, 0).getTower().getGems());
	}
	
	
	@Test
	public void testUpdateOneGem(){
		//set up
		Board updateBoard1 = new Board();
		
		//make method
		updateBoard1.upgradeTower(updateBoard1.getTile(0, 0).getTower());
		
		//assert
		assertEquals("Test the updateTower / Gems = 1", 1, updateBoard1.getTile(0, 0).getTower().getGems());
		assertNotEquals("Test other numbers", 2, updateBoard1.getTile(0, 0).getTower().getGems());
		assertNotEquals("Test other numbers", 3, updateBoard1.getTile(0, 0).getTower().getGems());
		assertNotEquals("Test other numbers", 4, updateBoard1.getTile(0, 0).getTower().getGems());
		assertNotEquals("Test other numbers", 0, updateBoard1.getTile(0, 0).getTower().getGems());
		assertNotEquals("Test other numbers", -1, updateBoard1.getTile(0, 0).getTower().getGems());
	}
	
	@Test
	public void testUpdateTwoGem(){
		//set up
		Board updateBoard2 = new Board();
		
		//make method
		updateBoard2.upgradeTower(updateBoard2.getTile(0, 0).getTower());
		updateBoard2.upgradeTower(updateBoard2.getTile(0, 0).getTower());
		
		//assert
		assertEquals("Test the updateTower / Gems = 2", 2, updateBoard2.getTile(0, 0).getTower().getGems());
		assertNotEquals("Test other numbers", 1, updateBoard2.getTile(0, 0).getTower().getGems());
		assertNotEquals("Test other numbers", 3, updateBoard2.getTile(0, 0).getTower().getGems());
		assertNotEquals("Test other numbers", 4, updateBoard2.getTile(0, 0).getTower().getGems());
		assertNotEquals("Test other numbers", 0, updateBoard2.getTile(0, 0).getTower().getGems());
		assertNotEquals("Test other numbers", -1, updateBoard2.getTile(0, 0).getTower().getGems());
	}
	
	@Test
	public void testUpdateTreeGem(){
		//set up
		Board updateBoard3 = new Board();
		
		//make method
		updateBoard3.upgradeTower(updateBoard3.getTile(0, 0).getTower());
		updateBoard3.upgradeTower(updateBoard3.getTile(0, 0).getTower());
		updateBoard3.upgradeTower(updateBoard3.getTile(0, 0).getTower());
		
		//assert
		assertEquals("Test the updateTower / Gems = 3", 3, updateBoard3.getTile(0, 0).getTower().getGems());
		assertNotEquals("Test other numbers", 1, updateBoard3.getTile(0, 0).getTower().getGems());
		assertNotEquals("Test other numbers", 2, updateBoard3.getTile(0, 0).getTower().getGems());
		assertNotEquals("Test other numbers", 4, updateBoard3.getTile(0, 0).getTower().getGems());
		assertNotEquals("Test other numbers", 0, updateBoard3.getTile(0, 0).getTower().getGems());
		assertNotEquals("Test other numbers", -1, updateBoard3.getTile(0, 0).getTower().getGems());
	}
	
	@Test
	public void testUpdateFourGem(){
		//set up
		Board updateBoard4 = new Board();
		
		//make method
		updateBoard4.upgradeTower(updateBoard4.getTile(0, 0).getTower());
		updateBoard4.upgradeTower(updateBoard4.getTile(0, 0).getTower());
		updateBoard4.upgradeTower(updateBoard4.getTile(0, 0).getTower());
		updateBoard4.upgradeTower(updateBoard4.getTile(0, 0).getTower());
		
		//assert
		assertEquals("Test the updateTower / Gems = 4", 4, updateBoard4.getTile(0, 0).getTower().getGems());
		assertNotEquals("Test other numbers", 1, updateBoard4.getTile(0, 0).getTower().getGems());
		assertNotEquals("Test other numbers", 2, updateBoard4.getTile(0, 0).getTower().getGems());
		assertNotEquals("Test other numbers", 3, updateBoard4.getTile(0, 0).getTower().getGems());
		assertNotEquals("Test other numbers", 0, updateBoard4.getTile(0, 0).getTower().getGems());
		assertNotEquals("Test other numbers", -1, updateBoard4.getTile(0, 0).getTower().getGems());
	}
	
	@Test
	public void testUpdateFiveGem(){
		//set up
		Board updateBoard5 = new Board();
		
		//make method
		updateBoard5.upgradeTower(updateBoard5.getTile(0, 0).getTower());
		updateBoard5.upgradeTower(updateBoard5.getTile(0, 0).getTower());
		updateBoard5.upgradeTower(updateBoard5.getTile(0, 0).getTower());
		updateBoard5.upgradeTower(updateBoard5.getTile(0, 0).getTower());
		updateBoard5.upgradeTower(updateBoard5.getTile(0, 0).getTower());
		
		//assert
		assertEquals("Test the updateTower / Gems = 5", 5, updateBoard5.getTile(0, 0).getTower().getGems());
		assertNotEquals("Test other numbers", 1, updateBoard5.getTile(0, 0).getTower().getGems());
		assertNotEquals("Test other numbers", 2, updateBoard5.getTile(0, 0).getTower().getGems());
		assertNotEquals("Test other numbers", 3, updateBoard5.getTile(0, 0).getTower().getGems());
		assertNotEquals("Test other numbers", 0, updateBoard5.getTile(0, 0).getTower().getGems());
		assertNotEquals("Test other numbers", -1, updateBoard5.getTile(0, 0).getTower().getGems());
	}
	
	@Test
	public void testGetAllMovesONE(){
		//set up
		ArrayList<Move> movesONE = new ArrayList<Move>();
		
		// make method
		movesONE = board.getAllMoves(PlayerType.ONE);
		
		//assert
		assertNotNull(movesONE);
	}
	
	@Test
	public void testGetAllMovesTWO(){
		//set up
		ArrayList<Move> movesTWO = new ArrayList<Move>();
		
		// make method
		movesTWO = board.getAllMoves(PlayerType.TWO);
		
		//assert
		assertNotNull(movesTWO);
	}
	
	@Test
	public void testFirstMove() {
		//set up
		Move move;
		
		//make method
		move = board.firstMove(PlayerType.ONE);
		
		//assert
		assertNotNull(move);
	}
	
	@Test
	public void testFirstMoveTWO(){
		//set up
		Move move;
		
		//make method
		move = board.firstMove(PlayerType.TWO);
		
		//assert
		assertNotNull(move);
	}
	
	@Test
	public void testIsWinningSituation() {
		//set up
		Board board = new Board();
		board.makeMove(new Move(0,0,4,4,true));
		board.makeMove(new Move(0,7,0,0,true));
		
		//make method
		boolean winner = board.isWinSituation();
		
		//assert
		assertTrue(winner);
		assertNotEquals("Test other Tower ist not updated", 1, board.getTile(4, 4).getTower().getGems());
	}
	
	@Test
	public void testIsNotWinningSituation() {
		//set up
		Board board = new Board();
		board.makeMove(new Move(0,0,4,4,true));
		board.makeMove(new Move(0,7,0,1,true));
		
		//make method
		boolean winner = board.isWinSituation();
		
		//assert
		assertFalse(winner);
		assertNotEquals("Test the tower is not updated", 1, board.getTile(0, 1).getTower().getGems());
		assertNotEquals("Test the tower is not updated", 1, board.getTile(4, 4).getTower().getGems());
	}
	
	@Test
	public void testGetNextPossibleMovesWithOneMoves(){
		//set up
		ArrayList<Move> moves = new ArrayList<Move>();
		Tower tower = new Tower(Color.Blue, Type.normalTower ,PlayerType.ONE,0);
		
		Board board = new Board();
		board.makeMove(new Move(0,0,2,2,true));
		
		//make method
		moves = board.getNextPossibleMovesWithOneMove(PlayerType.TWO, new Move(0,0,0,1));
		
		//assert
		assertNotNull(moves);
		for(Move move : moves){
			assertTrue(tower.valid(move.getX2(), move.getY2()));
		}
		assertFalse(moves.contains(new Move(2,7,2,1)));
	}
	
	@Test
	public void testGetNextPossibleMovesWithOneMoveIgnore(){
		//set up
		ArrayList<Move> moves = new ArrayList<Move>();
		Tower tower = new Tower(Color.Blue, Type.normalTower ,PlayerType.ONE,0);
		
		Board board = new Board();
		board.makeMove(new Move(0,0,2,2,true));
		
		//make method
		moves = board.getNextPossibleMovesWithOneMoveIgnore(PlayerType.TWO, new Move(0,0,0,1));
		
		// assert
		assertNotNull(moves);
		for(Move move : moves){
			assertTrue(tower.valid(move.getX2(), move.getY2()));
		}
		assertTrue(moves.contains(new Move(2,7,2,1)));
	}
	
	@Test
	public void testGetNumberOfMovesVertical(){
		//set up
		Move move = new Move(0,0,0,1,true);
		double weight = 10.5;
		double result = 1.5;
		
		//make method
		result = board.numberOfMovesVertical(move, PlayerType.ONE, weight);
		
		//assert
		assertNotEquals("Write an heuristic", 1.5, result);
	}
	
	@Test
	public void testNumberOfPossibleMoves(){
		//set up
		Move move = new Move(0,0,4,4,true);
		double weight = 10.5;
		double result = 1.5;
		
		//make method
		result = board.numberOfPossibleMoves(PlayerType.ONE, move, weight);
		
		//assert
		assertNotEquals("Write an heuristic", 1.5, result);
	}
	
	@Test
	public void testIsItAWinningMove(){
		//set up
		Board board = new Board();
		double weight = 100.5;
		double result = 1.5;
		board.makeMove(new Move(0,0,5,5));
		Move move = new Move(0,7,0,0);
		
		// make method
		result = board.isItAWinningMove(move, weight);
		
		// assert
		assertNotEquals("Write an heuristic", 1.5, result);
		assertTrue(100.5 == result);
		assertFalse(0 == result);
		assertFalse(-1 == result);
	}
	
	@Test
	public void testIsItAWinningMoveBool(){
		//set up
		Board board = new Board();
		boolean win;
		boolean notwin;
		board.makeMove(new Move(0,0,5,5));
		Move move = new Move(0,7,0,0);
		Move moveNotWin = new Move(0,7,0,1);
		
		// make method
		win = board.isItAWinningMoveBool(move);
		notwin = board.isItAWinningMoveBool(moveNotWin);
		
		// assert
		assertTrue(win);
		assertFalse(notwin);
	}
	
	@Test
	public void testIsABlockMove(){
		//set up
		Board board = new Board();
		
		Tower tower = new Tower(Color.Blue, Type.normalTower ,PlayerType.ONE,0);
		ArrayList<Move> moves = new ArrayList<Move>();
		moves = tower.getMoves(board, 2, 0);
			
		board.makeMove(new Move(0,0,7,6,true));
		board.makeMove(new Move(1,0,7,5,true));
		board.makeMove(new Move(2,0,7,4,true));
		board.makeMove(new Move(3,0,7,3,true));
		board.makeMove(new Move(4,0,7,2,true));
		board.makeMove(new Move(5,0,7,1,true));

		double result = 1.5;
		
		Move moveOne = new Move(7,1,1,1,true);
		
		//make method
		result = board.isABlockMove(PlayerType.ONE, moveOne, 100.0);
		
		//assert
		assertNotEquals(1.5,result);
		
	}
	
}
