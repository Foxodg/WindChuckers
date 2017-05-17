package WindChuckers_Main.Model_Extend;

import WindChuckers_Main.GameMenu_Model;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class Tower extends Button {
	private int playerNumber;
	private String color;
	private int yPosition;
	private int xPosition;
	private int number;
	
	protected Tower (String color){
		super();
		this.color = color;
	}

	public int getPlayerNumber(){
		return this.playerNumber;
	}
	public void setPlayerNumber(int playerNumber){
		this.playerNumber=playerNumber;
	}
	
	public String getColor(){
		return this.color;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	public int getyPosition() {
		return yPosition;
	}

	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}

	public int getxPosition() {
		return xPosition;
	}

	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	public void showPossibleMoves(Field[][] fields, GridPane gridPane) {
		if(this.getPlayerNumber()==1){

					// Enable possible fields for a front move
				
					// Enable possible fields for a diagonal move bottom right
				
					// Enable possible fields for a diagonal move bottom left
				}
	
		if(this.getPlayerNumber()==2){

					// Enable possible fields for a front move
					
					// Enable possible fields for a diagonal move bottom right
				
					// Enable possible fields for a diagonal move bottom left
		}
		
	}
}