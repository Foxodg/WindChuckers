package WindChuckers_Main.Model_Extend;

import WindChuckers_Main.GameMenu_Model;
import abstractClasses.Model;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class Tower extends Button {
	private int playerNumber;
	private String color;
	private int yPosition;
	private int xPosition;
	
	private boolean gameStart = true;
	private String colorField;
	
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

	/**
	 * This method will show all possible moves and enable all possible fields
	 * @param fields
	 * @param gridPane
	 * @param towersP1
	 * @param towersP2
	 */
	public void showMoves(Field[][] fields, GridPane gridPane, Tower[][] towersP1, Tower[][] towersP2) {
		
		if(this.getPlayerNumber()==1){
			
			//disable towers P1
			this.disableTowers(towersP1);
				
				// Down move
				for(int i = 1; i<=7 ; i++){
					if(this.getyPosition()-i < 0){
						break;
					} else if(fields[this.getxPosition()][this.getyPosition()-i].isEmpty()){
						fields[this.getxPosition()][this.getyPosition()-i].setDisable(false);
						} if(!fields[this.getxPosition()][this.getyPosition()-i].isEmpty()){
							break;
							}
					}
				
				// Diagonal move bottom right
				outerloop:
				for(int i = 1; i<=7; i++){
					if((this.getxPosition()+i>7) || (this.getyPosition()-i < 0)){
						break outerloop;
					} else if (fields[this.getxPosition()+i][this.getyPosition()-i].isEmpty()){
							fields[this.getxPosition()+i][this.getyPosition()-i].setDisable(false);
							} if(!fields[this.getxPosition()+i][this.getyPosition()-i].isEmpty()){
								break outerloop;
							}
						}
					
				// Diagonal move bottom left
				outerloop:
					for(int i = 1; i<=7; i++){
						if((this.getxPosition()-i<0) || (this.getyPosition()-i<0)){
							break outerloop;
						} else if (fields[this.getxPosition()-i][this.getyPosition()-i].isEmpty()){
								fields[this.getxPosition()-i][this.getyPosition()-i].setDisable(false);
								} if(!fields[this.getxPosition()-i][this.getyPosition()-i].isEmpty()){
									break outerloop;
								}
							}
				}

		if(this.getPlayerNumber()==2){
			
			// Disable Towers of Player2
			this.disableTowers(towersP2);

			// Up move
			for(int i = 1; i<=7 ; i++){
				if(this.getyPosition()+i > 7){
					break;
				} else if(fields[this.getxPosition()][this.getyPosition()+i].isEmpty()){
					fields[this.getxPosition()][this.getyPosition()+i].setDisable(false);
					} if(!fields[this.getxPosition()][this.getyPosition()+i].isEmpty()){
						break;
					}
				}
			
			// Diagonal move top right
			outerloop:
			for(int i = 1; i<=7; i++){
				if((this.getxPosition()+i>7) || (this.getyPosition()+i > 7)){
					break outerloop;
				} else if (fields[this.getxPosition()+i][this.getyPosition()+i].isEmpty()){
						fields[this.getxPosition()+i][this.getyPosition()+i].setDisable(false);
						} if(!fields[this.getxPosition()+i][this.getyPosition()+i].isEmpty()){
							break outerloop;
						}
					}
				
			// Diagonal move top left
			outerloop:
				for(int i = 1; i<=7; i++){
					if((this.getxPosition()-i<0) || (this.getyPosition()+i>7)){
						break outerloop;
					} else if (fields[this.getxPosition()-i][this.getyPosition()+i].isEmpty()){
							fields[this.getxPosition()-i][this.getyPosition()+i].setDisable(false);
							} if(!fields[this.getxPosition()-i][this.getyPosition()+i].isEmpty()){
								break outerloop;
							}
						}
				}
		}

	/**
	 * This method will change the position of the tower on the GridPane
	 * @param fields
	 * @param gameBoard
	 * @param towersP1
	 * @param towersP2
	 * @param field
	 * @param player1
	 * @param player2
	 */
	public void move(Field[][] fields, GridPane gameBoard, Tower[][] towersP1, Tower[][] towersP2, Field field, Player player1, Player player2) {
		int column = GridPane.getColumnIndex(field);
		int row = GridPane.getRowIndex(field);
		
		// The old field is empty and the new field is busy
		fields[this.getxPosition()][this.getyPosition()].setEmpty(true);
		field.setEmpty(false);

		// The coordinates of the tower will be changed
		this.setxPosition(field.getxPosition());
		this.setyPosition(field.getyPosition());
		this.setText(field.getxPosition()+"."+field.getyPosition());
		colorField = field.getColor();
		// The Tower will be moved on the GridPane
		GridPane.setColumnIndex(this, column);
		GridPane.setRowIndex(this, row);
		
		
		// The turn is finished, disable all fields
//		Achtung Änderung LKU
		this.disableFields(fields);
		this.gameStart = false;
		
		// Towers of other player will be enabled
		this.changeTurn(player1, player2, towersP1, towersP2);
}

	/**
	 * This method will activate the opponents towers and change the turn
	 * @param player1
	 * @param player2
	 * @param towersP1
	 * @param towersP2
	 */
	public void changeTurn(Player player1, Player player2, Tower[][] towersP1, Tower[][] towersP2){
		if(player1.isOnTurn()){
			player2.setOnTurn(true);
			player1.setOnTurn(false);
			this.disableTowers(towersP1);
			this.enableTowers(towersP2);
		} else{
			player1.setOnTurn(true);
			player2.setOnTurn(false);
			this.enableTowers(towersP1);
			this.disableTowers(towersP2);
		}
	}
	
	/**
	 * This method disables all towers of the tower array chosen
	 * @param towers
	 */
	public void disableTowers(Tower[][] towers){
		for(int y = 0; y < 8; y++){
			for(int x = 0; x < 8; x++){
				if(towers[x][y]!=null){
					towers[x][y].setDisable(true);
					}}}
	}

	/**
	 * This method enables all towers of the tower array chosen
//	 * Achtung Änderung LKu
	 * @param towers
	 */
	public void enableTowers(Tower[][] towers){
		if (gameStart){
		for(int y = 0; y < 8; y++){
			for(int x = 0; x < 8; x++){
				if(towers[x][y]!=null){
					towers[x][y].setDisable(false);
					}}}} else if(!gameStart){
						for(int y = 0; y < 8; y++){
							for(int x = 0; x < 8; x++){
								if(towers[x][y]!=null && towers[x][y].getColor().equals(colorField)){
									towers[x][y].setDisable(false);
								}}}
		}
	}

	/**
	 * This method disable all fields. Is used after a turn is finished
	 * @param fields
	 */
	public void disableFields(Field[][]fields){
		for(int y = 0; y < 8; y++){
			for(int x = 0; x < 8; x++){
				fields[x][y].setDisable(true);
			}}
	}
}