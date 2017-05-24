package AI;

import java.util.ArrayList;

public class MiniMaxAlphaBetaThead extends Thread{
	
	private PlayerType playerType;
	private int depth;
	private Board board;
	private double tmp;
	private double weightVertical;
	private double weightPossible;
	private double weightBlock;
	private double weightSumoWin;
	private double weightSumoBlock;
	private double win;
	
	public MiniMaxAlphaBetaThead(PlayerType playerType, int depth, double weightVertical, double weightPossible, double weightBlock, double weightSumoWin, double weightSumoBlock, double win){
		this.playerType = playerType;
		this.depth = depth;
		this.weightVertical = weightVertical;
		this.weightPossible = weightPossible;
		this.weightBlock = weightBlock;
		this.weightSumoWin = weightSumoWin;
		this.weightSumoBlock = weightSumoBlock;
		this.win = win;
	}
	
	public void setBoard(Board board){
		this.board = board;
	}
	
	public double getTmp(){
		return this.tmp;
	}
	
	@Override
	public void run() {
		ArrayList<Move> state = new ArrayList<Move>();
		tmp = minValue(board, state, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1);
	}
	
	
	/**
	 * For Building the Decision-Tree with the mini Max MaxValue
	 * 
	 * @author L.Weber
	 * @param board
	 * @param state
	 * @param alpha
	 * @param beta
	 * @param depth
	 *            at this Moment 5
	 * @return
	 */
	private double maxValue(Board b, ArrayList<Move> state, double alpha, double beta, int depth) {
		int maxDepth = 5;
		if (depth > maxDepth)
			return buildHeuristic(this.playerType, b, state);

		ArrayList<Move> moves = b.getNextPossibleMoves(this.playerType, state);
		if (moves.size() == 0) // TODO add draw
			return Double.NEGATIVE_INFINITY;

		for (int i = 0; i < moves.size(); i++) {
			state.add(moves.get(i));
			double tmp = minValue(b, state, alpha, beta, depth + 1);
			state.remove(state.lastIndexOf(moves.get(i)));
			if (tmp > alpha) {
				alpha = tmp;
			}

			if (beta <= alpha)
				break;

		}

		return alpha;
	}

	/**
	 * For Building the Decision-Tree with the mini Max minValue
	 * 
	 * @author L.Weber
	 * @param b
	 * @param state
	 * @param alpha
	 * @param beta
	 * @param depth
	 * @return
	 */
	private double minValue(Board b, ArrayList<Move> state, double alpha, double beta, int depth) {
		int maxDepth = 5;
		if (depth > maxDepth)
			return buildHeuristic(changePlayerType(this.playerType), b, state);

		ArrayList<Move> moves = b.getNextPossibleMoves(changePlayerType(this.playerType), state);
		if (moves.size() == 0) // TODO add draw
			return Double.POSITIVE_INFINITY;

		for (int i = 0; i < moves.size(); i++) {
			state.add(moves.get(i));
			double tmp = maxValue(b, state, alpha, beta, depth + 1);
			state.remove(state.lastIndexOf(moves.get(i)));
			if (tmp < beta) {
				beta = tmp;
			}

			if (beta <= alpha)
				break;

		}

		return beta;
	}
	
	/**
	 * Main-Method for build the Heuristic to rating the move Now we calculate 4
	 * points: Vertical moves / Winning Moves / Possible Moves / Block Moves
	 * 
	 * @author L.Weber
	 * @param playerType
	 * @param board
	 * @return double to rate the move
	 */
	public double buildHeuristic(PlayerType playerType, Board board, ArrayList<Move> moves) {
		double heuristic = 0;

		// ArrayList<Move> getMoves = board.getNextPossibleMoves(playerType);
		Tile[][] tiles = board.getTempTile();

		for (int i = 0; i < moves.size(); i++) {
			heuristic = getTheValuesForHeuristic(playerType, board, moves, heuristic, weightVertical, weightPossible, weightBlock, weightSumoWin, weightSumoBlock, win);
		}
		return heuristic;
	}

	public double getTheValuesForHeuristic(PlayerType playerType, Board board, ArrayList<Move> moves,
			double heuristic, double weightVertical, double weightPossible, double weightBlock, double weightSumoWin, double weightSumoBlock, double win) {
		ArrayList<Double> listofVertialMoves = new ArrayList<Double>();
		ArrayList<Double> listOfPossibleMoves = new ArrayList<Double>();
		ArrayList<Double> listOfBlockMoves = new ArrayList<Double>();
		ArrayList<Double> listOfSumoPushMoves = new ArrayList<Double>();
		ArrayList<Double> listOfWinningMoves = new ArrayList<Double>();

		for (int i = 0; i < moves.size(); i++) {
			double vertical = board.numberOfMovesVertical(moves.get(i), playerType, weightVertical);
			listofVertialMoves.add(vertical);
			double possibleMoves = board.numberOfPossibleMoves(playerType, moves.get(i), weightPossible);
			listOfPossibleMoves.add(possibleMoves);
			double possibleBlock = board.isABlockMove(playerType, moves.get(i), weightBlock);
			listOfBlockMoves.add(possibleBlock);
			double possibleSumoPush = board.sumoPush(moves.get(i), playerType, weightSumoWin, weightSumoBlock);
			listOfSumoPushMoves.add(possibleSumoPush);
			double poosibleWinMoves = board.isItAWinningMove(moves.get(i), win);

		}
		heuristic = heuristic(listofVertialMoves, listOfPossibleMoves, listOfSumoPushMoves, listOfWinningMoves);

		listofVertialMoves.clear();
		listOfPossibleMoves.clear();
		listOfSumoPushMoves.clear();

		return heuristic;
	}

	/**
	 * Calculating-Method for the Heuristic
	 * 
	 * @author L.Weber
	 * @param listofVertialMoves
	 * @param listofWinningMoves
	 * @param listOfPossibleMoves
	 * @return double for rate the Move
	 */
	private double heuristic(ArrayList<Double> listofVertialMoves, ArrayList<Double> listOfPossibleMoves,
			ArrayList<Double> listOfSumoPushMoves, ArrayList<Double> listOfWinMoves) {

		double sumVertical = 0;
		double sumOfFurther = 0;
		double sumOfPushs = 0;
		double sumOfWins = 0;

		for (int i = 0; i < listofVertialMoves.size(); i++) {
			sumVertical += listofVertialMoves.get(i);
		}

		for (int i = 0; i < listOfPossibleMoves.size(); i++) {
			sumOfFurther += listOfPossibleMoves.get(i);
		}
		for (int i = 0; i < listOfSumoPushMoves.size(); i++) {
			sumOfPushs += listOfSumoPushMoves.get(i);
		}
		for (int i = 0; i < listOfWinMoves.size(); i++){
			sumOfWins += listOfWinMoves.get(i);
		}

		double temp = sumVertical + sumOfFurther + sumOfPushs + sumOfWins;
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
	 * Setter
	 */
	public void setWeightVertical(double weight){
		this.weightVertical = weight;
	}
	public void setWeightPossible(double weight){
		this.weightPossible = weight;
	}
	public void setWeightBlock(double weight) {
		this.weightBlock = weight;
	}
	public void setWeightSumoWin(double weight) {
		this.weightSumoWin = weight;
	}
	public void setWeightSumoBlock(double weight) {
		this.weightSumoBlock = weight;
	}
	public void setWin(double weight){
		this.win = weight;
	}

}
