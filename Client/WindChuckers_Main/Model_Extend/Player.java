package WindChuckers_Main.Model_Extend;

import abstractClasses.Model;

public class Player extends Model {

	
	private int playerNumber; 
	private boolean onTurn = false;

	
	public Player(int playerNumber){
		this.setPlayerNumber(playerNumber);
	}


	public boolean isOnTurn() {
		return onTurn;
	}


	public void setOnTurn(boolean onTurn) {
		this.onTurn = onTurn;
	}

	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}
		

}
