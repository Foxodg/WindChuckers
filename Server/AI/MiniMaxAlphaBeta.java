package AI;
import java.util.ArrayList;
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
	private Thread t;
	

	public MiniMaxAlphaBeta(PlayerType playerType, int maxDepth) {
		this.playerType = playerType;
		this.maxDepth = maxDepth;
		rand = new Random();
	}

	/**
	 * For Building the Decision-Tree with the mini Max
	 * MaxValue
	 * @author L.Weber
	 * @param board
	 * @param state
	 * @param alpha
	 * @param beta
	 * @param depth at this Moment 5
	 * @return
	 */
	private static double maxValue(Board b, ArrayList<Move> state, double alpha, double beta, int depth) {
		int maxDepth = 5;
		if(depth > maxDepth)
			return buildHeuristic(PlayerType.ONE, b);
		
		ArrayList<Move> moves = b.getNextPossibleMoves(PlayerType.ONE, state);
		if(moves.size() == 0) // TODO add draw
			return Double.NEGATIVE_INFINITY;
		
		for(int i = 0; i < moves.size(); i++) {
			state.add(moves.get(i));
			double tmp = minValue(b, state, alpha, beta, depth + 1);
			state.remove(state.lastIndexOf(moves.get(i)));
			if(tmp > alpha) {
				alpha = tmp;
			}
			
			if(beta <= alpha)
				break;

		}
		
		return alpha;
	}
	
	/**
	 * For Building the Decision-Tree with the mini Max
	 * minValue
	 * @author L.Weber
	 * @param b
	 * @param state
	 * @param alpha
	 * @param beta
	 * @param depth
	 * @return
	 */
	private static double minValue(Board b, ArrayList<Move> state, double alpha, double beta, int depth) {
		int maxDepth = 5;
		if(depth > maxDepth)
			return buildHeuristic(PlayerType.TWO, b);
		
		ArrayList<Move> moves = b.getNextPossibleMoves(PlayerType.TWO, state);
		if(moves.size() == 0) // TODO add draw
			return Double.POSITIVE_INFINITY;
		
		for(int i = 0; i < moves.size(); i++) {
			state.add(moves.get(i));
			double tmp = maxValue(b, state, alpha, beta, depth + 1);
			state.remove(state.lastIndexOf(moves.get(i)));
			if(tmp < beta) {
				beta = tmp;
			}
			
			if(beta <= alpha)
				break;
				
		}
		
		return beta;
	}
	
	/**
	 * Main-Method for make a Decision
	 * Calculate with each Move as a own Thread
	 * @author L.Weber
	 * @param board
	 * @param playerType
	 * @return
	 */
	public static Move decision(final Board board, PlayerType playerType) {
		Random rand = new Random();
		
		//get the possibleMoves for this playerType
		ArrayList<Move> moves = board.getNextPossibleMoves(playerType);
		
		//if the moves-Array is empty, then the next Tower can't make a move
		//the rules says, that the other player become the next chance to make a move with his tower in this color
		if(moves == null){
			System.err.println("In decision moves are empty");
			moves = board.getNextPossibleMoves(changePlayerType(playerType));
		}
		
		
		ArrayList<Move> badMoves = null;
		for(int i = 0; i < moves.size(); i++){
			badMoves = board.removeMoves(playerType, moves.get(i));
		}
		try{
			moves.removeAll(badMoves);
		} catch (Exception e){
		}

		if(moves.size() == 0)
			return null;
 		
		Vector<Future<Double>> costs = new Vector<Future<Double>>(moves.size());
		costs.setSize(moves.size());
		
 		ExecutorService exec = Executors.newFixedThreadPool(moves.size());
 		try {
 		    for (int i = 0; i < moves.size(); i++) {
 		    	final Move move = moves.get(i);
 		        Future<Double> result = exec.submit(new Callable<Double>() {

 		            @Override
 		            public Double call() {
 		            	ArrayList<Move> state = new ArrayList<Move>();
 		            	state.add(move);
 		            	
 		            	double tmp = minValue(board, state, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1);
 		            	return (double) tmp;
 		            }
 		        });
 		        costs.set(i, result);
 		    }
 		} finally {
 		    exec.shutdown();
 		}

 		// max
 		int maxi = -1;
		Double max = Double.NEGATIVE_INFINITY;
 		for(int i = 0; i < moves.size(); i++) {
 			Double cost;
			try {
				cost = costs.get(i).get();
			} catch (Exception e) {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e1) {
				}
				continue;
			}
 			if(cost >= max) {
 				if(Math.abs(cost-max) < 0.1) // add a little random element
 					if(rand.nextBoolean())
 						continue;

 				max = cost;
 				maxi = i;
 			}
 		}
 		if(maxi == -1){
 			maxi = rand.nextInt(moves.size());
 		}
 		Move endMove = new Move(moves.get(maxi),true);
 		return endMove;
	}
	
	/**
	 * Main-Method for build the Heuristic to rating the move
	 * Now we calculate 4 points: Vertical moves / Winning Moves / Possible Moves / Block Moves
	 * @author L.Weber
	 * @param playerType
	 * @param board
	 * @return double to rate the move
	 */
	public static double buildHeuristic(PlayerType playerType, Board board){
		double heuristic = 0;
		ArrayList<Integer> listofVertialMoves = new ArrayList<Integer>(); 
		ArrayList<Integer> listofWinningMoves = new ArrayList<Integer>();
		ArrayList<Integer> listOfPossibleMoves = new ArrayList<Integer>();
		ArrayList<Integer> listOfBlockMoves = new ArrayList<Integer>();
		ArrayList<Integer> listOfSumoPushMoves = new ArrayList<Integer>();
		
		ArrayList<Move> getMoves = board.getNextPossibleMoves(playerType);
		
		for (int i = 0; i < getMoves.size(); i++) {
			int vertical = board.numberOfMovesVertical(getMoves.get(i), playerType);
			listofVertialMoves.add(vertical);
			int winning = board.isItAWinningMove(getMoves.get(i));
			listofWinningMoves.add(winning);
			int possibleMoves = board.numberOfPossibleMoves(playerType, getMoves.get(i));
			listOfPossibleMoves.add(possibleMoves);
			int possibleBlock = board.isABlockMove(playerType, getMoves.get(i));
			listOfBlockMoves.add(possibleBlock);
			int possibleSumoPush = board.sumoPush(getMoves.get(i), playerType);
			listOfSumoPushMoves.add(possibleSumoPush);

		}
		heuristic = heuristic(listofVertialMoves, listofWinningMoves, listOfPossibleMoves, listOfSumoPushMoves);
		
		listofVertialMoves.clear();
		listofWinningMoves.clear();
		listOfPossibleMoves.clear();
		listOfSumoPushMoves.clear();
		
		return heuristic;
	}
	
	/**
	 * Calculating-Method for the Heuristic
	 * @author L.Weber
	 * @param listofVertialMoves
	 * @param listofWinningMoves
	 * @param listOfPossibleMoves
	 * @return double for rate the Move
	 */
	private static double heuristic(ArrayList<Integer> listofVertialMoves, ArrayList<Integer> listofWinningMoves,
			ArrayList<Integer> listOfPossibleMoves, ArrayList<Integer> listOfSumoPushMoves) {
		
		double sumVertical = 0;
		double sumWinning = 0;
		double sumOfFurther = 0;
		double sumOfPushs = 0;
		
		for (int i = 0; i < listofVertialMoves.size(); i++) {
			sumVertical += listofVertialMoves.get(i);
		}
		
		for (int i = 0; i < listofWinningMoves.size(); i++) {
			sumWinning += listofWinningMoves.get(i);
		}
		for (int i = 0; i < listOfPossibleMoves.size(); i++) {
			sumOfFurther += listOfPossibleMoves.get(i);
		}
		for (int i = 0; i < listOfSumoPushMoves.size(); i++) {
			sumOfPushs += listOfSumoPushMoves.get(i);
		}
		
		double temp = sumVertical + sumWinning + sumOfFurther + sumOfPushs;
		double totalHeuristic = 0.0;
		
		return totalHeuristic = temp / 100;
	}
	
	public static PlayerType changePlayerType(PlayerType playerType) {
		if (playerType == PlayerType.ONE) {
			playerType = PlayerType.TWO;
		} else {
			playerType = PlayerType.ONE;
		}
		return playerType;
	}

	/**
	public static double buildHeuristic(PlayerType playerType, Board board){
		
		//return Value for the Rating
		double heuristic;
		
		//Lists for Player.ONE
		ArrayList<Integer> listofVertialMoves = new ArrayList<Integer>(); 
		ArrayList<Integer> listofWinningMoves = new ArrayList<Integer>();
		ArrayList<Integer> listOfPossibleMoves = new ArrayList<Integer>();
		
		//Lists for Player.TWO
		ArrayList<Integer> listofOpponentMoves = new ArrayList<Integer>();
		ArrayList<Integer> listofWinningOpponentMoves = new ArrayList<Integer>();
		ArrayList<Integer> listOfPossibleOppontentMoves = new ArrayList<Integer>();
		

		ArrayList<Move> getMoves = board.getNextPossibleMoves(playerType);
		ArrayList<Move> getOpponent = board.getNextPossibleMoves(changePlayerType(playerType), getMoves);

		for (int i = 0; i < getMoves.size(); i++) {
			int vertical = board.numberOfMovesVertical(getMoves.get(i), playerType);
			listofVertialMoves.add(vertical);
			int winning = board.isItAWinningMove(getMoves.get(i));
			listofWinningMoves.add(winning);

			int oppositPossibleMoves = board.numberOfPossibleMoves(changePlayerType(playerType), getMoves.get(i));
			listOfPossibleOppontentMoves.add(oppositPossibleMoves);

		}

		for (int i = 0; i < getOpponent.size(); i++) {
			int opponent = board.numberOfMovesVertical(getOpponent.get(i), changePlayerType(playerType));
			listofOpponentMoves.add(opponent);
			int oppositWinning = board.isItAWinningMove(getOpponent.get(i));
			listofWinningOpponentMoves.add(oppositWinning);

			int possibleMoves = board.numberOfPossibleMoves(playerType, getOpponent.get(i));
			listOfPossibleMoves.add(possibleMoves);

		}
		heuristic = heuristic(listofVertialMoves, listofOpponentMoves, listofWinningMoves, listofWinningOpponentMoves,
					listOfPossibleMoves, listOfPossibleOppontentMoves);

		listofVertialMoves.clear();
		listofOpponentMoves.clear();
		listofWinningMoves.clear();
		listofWinningOpponentMoves.clear();
		listOfPossibleMoves.clear();
		listOfPossibleOppontentMoves.clear();
		
		return heuristic;

	}
	
	

	public static double heuristic(ArrayList<Integer> listofVertialMoves, ArrayList<Integer> listofOpponentMoves,
			ArrayList<Integer> listofWinningMoves, ArrayList<Integer> listofWinningOpponentMoves,
			ArrayList<Integer> listOfPossibleMoves, ArrayList<Integer> listOfPossibleOppontentMoves) {

		// sum only the next Step with all his possible next steps
		// f1
		int sumF1 = 0;
		int sumTWOF1 = 0;
		int sumONEF1 = 0;

		for (int i = 0; i < listofVertialMoves.size(); i++) {
			sumTWOF1 += listofVertialMoves.get(i);
		}
		for (int i = 0; i < listofOpponentMoves.size(); i++) {
			sumONEF1 += listofOpponentMoves.get(i);
		}
		sumF1 = sumTWOF1 - sumONEF1;

		// sum the winning-Chance-Moves with all his possible next steps
		// f2
		int sumF2 = 0;
		int sumTWOF2 = 0;
		int sumONEF2 = 0;
		for (int i = 0; i < listofWinningMoves.size(); i++) {
			sumTWOF2 += listofWinningMoves.get(i);
		}
		for (int i = 0; i < listofWinningOpponentMoves.size(); i++) {
			sumONEF2 += listofWinningOpponentMoves.get(i);
		}
		sumF2 = sumTWOF2 - sumONEF2;

		// sum the possible next Moves
		// f3
		int sumF3 = 0;
		int sumTWOF3 = 0;
		int sumONEF3 = 0;

		for (int i = 0; i < listOfPossibleMoves.size(); i++) {
			sumTWOF3 += listOfPossibleMoves.get(i);
		}
		for (int i = 0; i < listOfPossibleOppontentMoves.size(); i++) {
			sumONEF3 += listOfPossibleOppontentMoves.get(i);
		}
		sumF3 = sumTWOF3 - sumONEF3;

		double temp = sumF1 + sumF2 + sumF3;
		double totalHeuristic = 0.0;

		return totalHeuristic = temp / 100;
	}

	public static PlayerType changePlayerType(PlayerType playerType) {
		if(playerType == PlayerType.ONE){
			return PlayerType.TWO;
		} else {
			return PlayerType.ONE;
		}
	}

**/
}
