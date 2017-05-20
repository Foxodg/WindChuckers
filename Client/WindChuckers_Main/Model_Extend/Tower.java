package WindChuckers_Main.Model_Extend;

import WindChuckers_Main.GameMenu_Model;
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

	public void showPossibleMoves(Field[][] fields, GridPane gridPane, Tower[][] towersP1, Tower[][] towersP2) {
		
		if(this.getPlayerNumber()==1){

				// Down move
				for(int y = this.getyPosition()-1; y>=0; y--){
						if(fields[this.getxPosition()][y].isEmpty()){
						fields[this.getxPosition()][y].setDisable(false);
						} if(!fields[this.getxPosition()][y].isEmpty()){
							break;
						}
					}
				
				// Diagonal move bottom right
				outerloop:
				for(int i = 1; i<8; i++){
						if(fields[this.getxPosition()+i][this.getyPosition()-i].isEmpty()&&(this.getxPosition()+i<8) && (this.getyPosition()-i >= 0)){
							fields[this.getxPosition()+i][this.getyPosition()-i].setDisable(false);
							} if(!fields[this.getxPosition()+i][this.getyPosition()-i].isEmpty() || (this.getxPosition()+i==7) || (this.getyPosition()-i == 0)){
								break outerloop;
							}
						}
					
				// Diagonal move bottom left
				outerloop:
					for(int i = 1; i<8; i++){
							if(fields[this.getxPosition()-i][this.getyPosition()-i].isEmpty() && (this.getxPosition()-i>=0) && (this.getyPosition()-i >= 0)){
								fields[this.getxPosition()-i][this.getyPosition()-i].setDisable(false);
								} if(!fields[this.getxPosition()-i][this.getyPosition()-i].isEmpty() || (this.getxPosition()-i==0) || (this.getyPosition()-i == 0)){
									break outerloop;
								}
							}
	
				// Disable Towers of Player1 and enable Towers of Player 2
				for(int y = 0; y < 8; y++){
					for(int x = 0; x < 8; x++){
						if(towersP1[x][y]!=null){
							towersP1[x][y].setDisable(true);
					}
						}
					}
				}
			

		if(this.getPlayerNumber()==2){

				// Up move
				for(int y = this.getyPosition()+1; y<=7; y++){
					if(fields[this.getxPosition()][y].isEmpty()){
					fields[this.getxPosition()][y].setDisable(false);
					} if(!fields[this.getxPosition()][y].isEmpty()){
						break;
					}
				}
						
				// Diagonal move top right
				outerloop:
					for(int i = 1; i<8; i++){
							if(fields[this.getxPosition()+i][this.getyPosition()+i].isEmpty() && (this.getxPosition()+i<=7) && (this.getyPosition()+i <= 7)){
								fields[this.getxPosition()+i][this.getyPosition()+i].setDisable(false);
								} if(!fields[this.getxPosition()+i][this.getyPosition()+i].isEmpty() || (this.getxPosition()+i == 7) || (this.getyPosition()+i == 7)){
									break outerloop;
								}
							}
					
				// Diagonal move top left
				outerloop:
					for(int i = 1; i<8; i++){
							if(fields[this.getxPosition()-i][this.getyPosition()+i].isEmpty() && (this.getxPosition()+i >= 0) && (this.getyPosition()+i <= 7)){
								fields[this.getxPosition()-i][this.getyPosition()+i].setDisable(false);
								} if(!fields[this.getxPosition()-i][this.getyPosition()+i].isEmpty() || (this.getxPosition()+i == 0) || (this.getyPosition()+i == 7)){
									break outerloop;
								}
							}
							
				// Disable Towers of Player2 and enable Towers of Player 1
				for(int y = 0; y < 8; y++){
					for(int x = 0; x < 8; x++){
						if(towersP2[x][y]!=null){
							towersP2[x][y].setDisable(true);
					}}}

		}
		
	}
}