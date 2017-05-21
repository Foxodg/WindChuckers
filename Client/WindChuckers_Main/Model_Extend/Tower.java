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

	public void showMoves(Field[][] fields, GridPane gridPane, Tower[][] towersP1, Tower[][] towersP2) {
		
		if(this.getPlayerNumber()==1){
			
				// Disable Towers of Player1
				for(int y = 0; y < 7; y++){
					for(int x = 0; x < 7; x++){
						if(towersP1[x][y]!=null){
							towersP1[x][y].setDisable(true);
							}}}
				
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
			for(int y = 0; y < 7; y++){
				for(int x = 0; x < 7; x++){
					if(towersP2[x][y]!=null){
						towersP2[x][y].setDisable(true);
				}}}

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

		// The Tower will be moved on the GridPane
		GridPane.setColumnIndex(this, column);
		GridPane.setRowIndex(this, row);
		
		// The turn is finished, disable all fields
		for(int y = 0; y < 8; y++){
			for(int x = 0; x < 8; x++){
				fields[x][y].setDisable(true);
			}}
		
		// Towers of other player will be enabled
		if(player1.isOnTurn()){
			player2.setOnTurn(true);
			player1.setOnTurn(false);
			for(int y = 0; y < 8; y++){
				for(int x = 0; x < 8; x++){
					if(towersP2[x][y]!=null){
						towersP2[x][y].setDisable(false);
					}
					if(towersP1[x][y]!=null){
						towersP1[x][y].setDisable(true);
					}}}
		} else{
			player1.setOnTurn(true);
			player2.setOnTurn(false);
			for(int y = 0; y < 8; y++){
				for(int x = 0; x < 8; x++){
					if(towersP1[x][y]!=null){
						towersP1[x][y].setDisable(false);
						}
					if(towersP2[x][y]!=null){
						towersP2[x][y].setDisable(true);
						}}}
		}
}
}