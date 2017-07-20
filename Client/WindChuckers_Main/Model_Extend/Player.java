package WindChuckers_Main.Model_Extend;

import abstractClasses.Model;

public class Player extends Model {

	
	private int playerNumber; 
	private boolean onTurn = false;
	private boolean lastWinner = false;
	private boolean causedPat = false;
	private int wins = 0;

	
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


	public boolean isLastWinner() {
		return lastWinner;
	}


	public void setLastWinner(boolean lastWinner) {
		this.lastWinner = lastWinner;
	}


	public int getWins() {
		return this.wins;
	}


	public void setWins() {
		this.wins++;
	}


	public boolean causedPat() {
		return causedPat;
	}


	public void setCausedPat(boolean causedPat) {
		this.causedPat = causedPat;
	}
		

}
