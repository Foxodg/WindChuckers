package AI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MiniMaxAlphaBeta {

	private PlayerType playerType;
	private int maxDepth = 4;
	private Random rand;
	private Move savedMove;
	private double weightVertical;
	private double weightPossible;
	private double weightBlock;
	private double weightSumoWin;
	private double weightSumoBlock;
	private double win;
	

	public MiniMaxAlphaBeta(PlayerType playerType, int maxDepth, double weightVertical, double weightPossible, double weightBlock, double weightSumoWin, double weightSumoBlock, double win) {
		this.playerType = playerType;
		this.maxDepth = maxDepth;
		rand = new Random();
		this.weightVertical = weightVertical;
		this.weightPossible = weightPossible;
		this.weightBlock = weightBlock;
		this.weightSumoWin = weightSumoWin;
		this.weightSumoBlock = weightSumoBlock;
		this.win = win;
	}

	/**
	 * Main-Method for make a Decision Calculate with each Move as a own Thread
	 * 
	 * @author L.Weber
	 * @param board
	 * @param playerType
	 * @return
	 **/

	public Move decision(final Board board, PlayerType playerType) {
		Random rand = new Random();

		// get the possibleMoves for this playerType
		ArrayList<Move> moves = board.getNextPossibleMoves(playerType);

		// if the moves-Array is empty, then the next Tower can't make a move
		// the rules says, that the other player become the next chance to make
		// a move with his tower in this color
		if (moves == null) {
			System.err.println("In decision moves are empty");
			moves = board.getNextPossibleMoves(changePlayerType(playerType));
		}

		ArrayList<Move> badMoves = null;
		for (int i = 0; i < moves.size(); i++) {
			badMoves = board.removeMoves(changePlayerType(playerType), moves.get(i));
		}
		try {
			moves.removeAll(badMoves);
		} catch (Exception e) {
		}

		if (moves.size() == 0)
			return null;

		ArrayList<Double> costs = new ArrayList<Double>();
		ArrayList<Thread> threads = null;
		MiniMaxAlphaBetaThead mini = null;
		ArrayList<MiniMaxAlphaBetaThead> minis = new ArrayList<MiniMaxAlphaBetaThead>();

		for (int i = 0; i < moves.size(); i++) {
			mini = new MiniMaxAlphaBetaThead(playerType, maxDepth, weightVertical, weightPossible, weightBlock, weightSumoWin, weightSumoBlock, win);
			minis.add(mini);
			threads = new ArrayList<Thread>();
			threads.add(mini);
			mini.setBoard(board);
			mini.start();
		}

		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		for (int i = 0; i < minis.size(); i++) {
			costs.add(minis.get(i).getTmp());
		}

		// get the maximum move
		int maxi = -1;
		Double max = Double.NEGATIVE_INFINITY;
		for (int i = 0; i < moves.size(); i++) {
			Double cost;
			cost = costs.get(i);
			if (cost >= max) {
				maxi = i;
			}
		}
		return new Move(moves.get(maxi), true);
	}

	public static PlayerType changePlayerType(PlayerType playerType) {
		if (playerType == PlayerType.ONE) {
			playerType = PlayerType.TWO;
		} else {
			playerType = PlayerType.ONE;
		}
		return playerType;
	}
}
