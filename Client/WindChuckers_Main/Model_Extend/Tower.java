package WindChuckers_Main.Model_Extend;

import javafx.scene.control.Button;

public abstract class Tower<C> extends Button {
	private int playerNumber;
	private String color;
	private int spalte; 
	private int zeile; 
	private int number;
	
	protected Tower (String color, int playerNumber){
		super();
		this.color = color;
		this.playerNumber=playerNumber;
	}
	
	protected int getPlayer(){
		return this.playerNumber;
	}
	
	protected String getColor(){
		return this.color;
	}
	
	protected int getZeile(){
		return this.zeile;
	}
	
	protected void setZeile(int zeile){
		this.zeile=zeile;
	}
	
	protected int getSpalte(){
		return this.spalte;
	}
	
	protected void setSpalte(int spalte){
		this.spalte=spalte;
	}

	protected int getNumber() {
		return number;
	}

	protected void setNumber(int number) {
		this.number = number;
	}
}