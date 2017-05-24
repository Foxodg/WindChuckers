package AI;

import javafx.beans.property.SimpleBooleanProperty;

public class Kamisado {
	private static Kamisado kamisado;
	private Move move;
	
	// SimpleBooleanProperty for overwatching the a new return value
	private SimpleBooleanProperty moveHere = new SimpleBooleanProperty();
	
	public static Kamisado getKamisado() {
		if(kamisado == null){
			kamisado = new Kamisado();
		}
		return kamisado;
	}

//	public static void main(String[] args) {
//		Board board = Board.getBoard();
////		board.makeMove(new Move(0, 0, 0, 1, true));
//		setPlayConfiguration(false, 5, 100, 100, 15, 25, 15, Double.POSITIVE_INFINITY);
//	}

	public Move setPlayConfiguration(boolean singlePlayer, int depth, double weightVertical,
			double weightPossible, double weightBlock, double weightSumoWin, double weightSumoBlock, double win) {
		Board board = Board.getBoard();

		if (singlePlayer) {
			AlphaBetaPlayer player2 = new AlphaBetaPlayer(PlayerType.TWO, depth, weightVertical, weightPossible,
					weightBlock, weightSumoWin, weightSumoBlock, win);
			return play(player2, board);
		} else {
			AlphaBetaPlayer player1 = new AlphaBetaPlayer(PlayerType.ONE, depth, weightVertical, weightPossible,
					weightBlock, weightSumoWin, weightSumoBlock, win);
			AlphaBetaPlayer player2 = new AlphaBetaPlayer(PlayerType.TWO, depth, weightVertical, weightPossible,
					weightBlock, weightSumoWin, weightSumoBlock, win);
			play(player1, player2, board);
			return null;
		}

	}

	private Move play(AlphaBetaPlayer player2, Board board) {
		long start;
		long end;
		Move move = null;

		if (board.isWinSituation() == false) {
			start = System.currentTimeMillis();
			move = board.makeMove(player2.getNextMove(board, PlayerType.TWO));
			end = System.currentTimeMillis();
			System.out.println("time: " + (end - start));
			
		}
		return move;
	}

	public void play(AlphaBetaPlayer player1, AlphaBetaPlayer player2, Board board) {
		long start;
		long end;

		while (true) {
			move = board.firstMove(PlayerType.ONE);
			while (board.isWinSituation() == false) {
				start = System.currentTimeMillis();
				setValue(false);
				move = board.makeMove(player1.getNextMove(board, PlayerType.TWO));
				setValue(true);
				end = System.currentTimeMillis();
				System.out.println("time: " + (end - start));
				if (board.isWinSituation() == false) {
					start = System.currentTimeMillis();
					setValue(false);
					move = board.makeMove(player2.getNextMove(board, PlayerType.ONE));
					setValue(true);
					end = System.currentTimeMillis();
					System.out.println("time: " + (end - start));
				}
			}
			board.newRound(PlayerType.ONE, NewRound.Left);
			System.out.println(board);
		}
	}
	
	public SimpleBooleanProperty getValueProperty() {
		return this.moveHere;
	}
	
	// setter for the SimpleBooleanProperty
	public void setValue(Boolean newValue) {
		try{
			moveHere.setValue(newValue);;
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	public Move getMove(){
		return this.move;
	}
}
