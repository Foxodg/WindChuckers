package AI;

public class AlphaBetaPlayer extends Player {
	private MiniMaxAlphaBeta minimax;

	public AlphaBetaPlayer(PlayerType playerType, int maxDepth) {
		super(playerType);
		minimax = new MiniMaxAlphaBeta(playerType, maxDepth);
	}
	
	/**
	 * Make a new Decision
	 * @author L.Weber
	 */
	@Override
	public Move getNextMove(Board board, PlayerType playerType) {
		Move move = minimax.decision(board, playerType);
		return move;
	}


}
