package AI;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Kamisado {

	public static void main(String[] args) {
		Board board = new Board();
		int iter = 100;
		float player1Score = 0;
		int draw = 0;

		System.out.println(board);

		Tile[][] temp = new Tile[8][8];

		AlphaBetaPlayer player1 = new AlphaBetaPlayer(PlayerType.ONE, 5);
		AlphaBetaPlayer player2 = new AlphaBetaPlayer(PlayerType.TWO, 5);

		play(player1, player2, board);
	}

	public static void play(Player player1, Player player2, Board board) {

		while(true){
			board.firstMove(PlayerType.ONE);
			while(board.isWinSituation() == false){
				board.makeMove(player1.getNextMove(board, PlayerType.TWO));
				if (board.isWinSituation() == false){
					board.makeMove(player2.getNextMove(board, PlayerType.ONE));
				} 
			}
			board.newRound(PlayerType.ONE,NewRound.Left);
			System.out.println(board);
		}
	}
}
