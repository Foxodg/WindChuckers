package AI;

public class AlphaBetaPlayer {
	private MiniMaxAlphaBeta minimax;
	private PlayerType playerType;

	public AlphaBetaPlayer(PlayerType playerType, int maxDepth, double weightVertical, double weightPossible, double weightBlock, double weightSumoWin, double weightSumoBlock, double win) {
		this.playerType = playerType;
		minimax = new MiniMaxAlphaBeta(playerType, maxDepth, weightVertical, weightPossible, weightBlock, weightSumoWin, weightSumoBlock, win);
	}
	
	/**
	 * Make a new Decision
	 * @author L.Weber
	 */
	public Move getNextMove(Board board, PlayerType playerType) {
		Move move = minimax.decision(board, playerType);
		return move;
	}


}
