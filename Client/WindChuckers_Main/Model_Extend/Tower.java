package WindChuckers_Main.Model_Extend;

import javafx.scene.control.Button;

public abstract class Tower extends Button {
	private int playerNumber;
	private String color;
	private int row; 
	private int column; 
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
	
	public int getZeile(){
		return this.column;
	}
	
	public void setZeile(int zeile){
		this.column=zeile;
	}
	
	public int getSpalte(){
		return this.row;
	}
	
	public void setSpalte(int spalte){
		this.row=spalte;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
}