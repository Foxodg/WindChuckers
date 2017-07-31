package AI;

import WindChuckers_Main.GameMenu_Model;

public class AlphaBetaPlayer {
	private MiniMaxAlphaBeta minimax;
	private PlayerType playerType;
	private static AlphaBetaPlayer alphaBeta1;
	private static AlphaBetaPlayer alphaBeta2;
	
	public static AlphaBetaPlayer getAlphaBeta1(PlayerType playerType, int maxDepth, double weightVertical, double weightPossible, double weightBlock, double weightSumoWin, double weightSumoBlock, double win) {
		if (alphaBeta1 == null)
			alphaBeta1 = new AlphaBetaPlayer(playerType, maxDepth, weightVertical, weightPossible, weightBlock, weightSumoWin, weightSumoBlock, win);
		return alphaBeta1;
	}
	
	public static AlphaBetaPlayer getAlphaBeta2(PlayerType playerType, int maxDepth, double weightVertical, double weightPossible, double weightBlock, double weightSumoWin, double weightSumoBlock, double win) {
		if (alphaBeta2 == null)
			alphaBeta2 = new AlphaBetaPlayer(playerType, maxDepth, weightVertical, weightPossible, weightBlock, weightSumoWin, weightSumoBlock, win);
		return alphaBeta2;
	}

	private AlphaBetaPlayer(PlayerType playerType, int maxDepth, double weightVertical, double weightPossible, double weightBlock, double weightSumoWin, double weightSumoBlock, double win) {
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
