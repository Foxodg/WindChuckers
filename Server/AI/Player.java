package AI;
/**
 * Super-Class for Players
 * @author L.Weber
 *
 */

public class Player {
	
	PlayerType playerType;
	
	public Player(PlayerType playerType) {
		playerType = playerType;
	}
	
	public Move getNextMove(Board b) {
		return null;
	}
	
	public PlayerType getPlayer() {
		return playerType;
	}
	
	public void setPlayerType(PlayerType playerType){
		this.playerType = playerType;
	}

	public Move getNextMove(Board board, PlayerType playerType) {
		return null;
	}

}
