package WindChuckers_Main.Model_Extend;

import org.omg.Messaging.SyncScopeHelper;

import com.sun.media.jfxmedia.logging.Logger;

import WindChuckers_Main.GameMenu_Model;
import abstractClasses.Model;
import commonClasses.ServiceLocator;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class Tower extends Button {
	private int playerNumber;
	private GameMenu_Model model = GameMenu_Model.getGameModel();
	private String color;
	private int yPosition;
	private int xPosition;
	private int gems; 
	private boolean sumoTower = false;
	private int saveSumoMove = 0;
	
	protected Tower (String color){
		super();
		this.color = color;
	}

	/**
	 * Following method will provide the towers in each color
	 * @return
	 * @author robin
	 */
	public static Tower getOrangeTower(){
		Tower orangeTower = new Tower("orange");
		orangeTower.setStyle(" -fx-background-color: "+GameMenu_Model.ORANGE+";"); 
		return orangeTower;
	}
	
	/**
	 * Following methods will provide the towers in each color
	 * @return
	 * @author robin
	 */
	public static Tower getBlueTower(){
		Tower blueTower = new Tower("blue");
		blueTower.setStyle(" -fx-background-color: "+GameMenu_Model.BLUE+";"); 
		return blueTower;
	}
	
	/**
	 * Following methods will provide the towers in each color
	 * @return
	 * @author robin
	 */
	public static Tower getVioletTower(){
		Tower violetTower = new Tower("violet");
		violetTower.setStyle(" -fx-background-color: "+GameMenu_Model.VIOLET+";"); 
		return violetTower;
	}
	
	/**
	 * Following methods will provide the towers in each color
	 * @return
	 * @author robin
	 */
	public static Tower getPinkTower(){
		Tower pinkTower = new Tower("pink");
		pinkTower.setStyle(" -fx-background-color: "+GameMenu_Model.PINK+";"); 
		return pinkTower;
	}
	
	/**
	 * Following methods will provide the towers in each color
	 * @return
	 * @author robin
	 */
	public static Tower getYellowTower(){
		Tower yellowTower = new Tower("yellow");
		yellowTower.setStyle(" -fx-background-color: "+GameMenu_Model.YELLOW+";"); 
		return yellowTower;
	}
	
	/**
	 * Following methods will provide the towers in each color
	 * @return
	 * @author robin
	 */
	public static Tower getRedTower(){
		Tower redTower = new Tower("red");
		redTower.setStyle(" -fx-background-color: "+GameMenu_Model.RED+";"); 
		return redTower;
	}
	
	/**
	 * Following methods will provide the towers in each color
	 * @return
	 * @author robin
	 */
	public static Tower getGreenTower(){
		Tower greenTower = new Tower("green");
		greenTower.setStyle(" -fx-background-color: "+GameMenu_Model.GREEN+";"); 
		return greenTower;
	}
	
	/**
	 * Following methods will provide the towers in each color
	 * @return
	 * @author robin
	 */
	public static Tower getBrownTower(){
		Tower brownTower = new Tower("brown");
		brownTower.setStyle(" -fx-background-color: "+GameMenu_Model.BROWN+";"); 
		return brownTower;
	}

	/**
	 * This method checks if a pat situation is existing. If there are two pat situations in a row, the player who caused the first pat looses the round
	 * @param fields
	 * @param towersP1
	 * @param towersP2
	 * @author robin
	 */
	private void checkPat(Field[][] fields, Tower[][] towersP1, Tower[][] towersP2) {
		int possibleMoves = 0;
		Alert firstAlert = new Alert(AlertType.INFORMATION);
		firstAlert.setTitle("Information");
		firstAlert.setHeaderText("Pat Situation!");
		Alert secondAlert = new Alert(AlertType.INFORMATION);
		secondAlert.setTitle("Information");

		for(int x = 0; x < GameMenu_Model.DIMENSION; x++){
			for(int y = 0; y < GameMenu_Model.DIMENSION; y++){
				if(!fields[x][y].isDisabled()){
					possibleMoves++;
					model.getPlayer1().setCausedPat(false);
					model.getPlayer2().setCausedPat(false);
				}
			}
		}
	
		if(possibleMoves==0 && (model.getPlayer1().causedPat()||model.getPlayer2().causedPat())){
			if(model.getPlayer1().causedPat()){
				secondAlert.setHeaderText("Pat Situation! Player 2 wins!");
				secondAlert.showAndWait();
				GameMenu_Model.Winner.set(2);
			} else{
				secondAlert.setHeaderText("Pat Situation! Player 1 wins!");
				secondAlert.showAndWait();
				GameMenu_Model.Winner.set(1);
			}
		}	
	
		if(possibleMoves==0 && !model.getPlayer1().causedPat() && !model.getPlayer2().causedPat()){
			if(model.getPlayer1().isOnTurn()){
				model.getPlayer2().setCausedPat(true);
				firstAlert.showAndWait();
				this.changeTurn(fields, model.getPlayer1(), model.getPlayer2(), towersP1, towersP2, fields[this.getxPosition()][this.getyPosition()]);
			} else {
				model.getPlayer1().setCausedPat(true);
				firstAlert.showAndWait();
				this.changeTurn(fields, model.getPlayer1(), model.getPlayer2(), towersP1, towersP2, fields[this.getxPosition()][this.getyPosition()]);
			}
	}
}

	public void showSumoMoves(Field[][] fields, GridPane gridPane, Tower[][] towersP1, Tower[][] towersP2){
		
		if(this.getPlayerNumber()==1){
			this.disableTowers(towersP1);
			
			if(gems == 3 || gems == 2 || gems == 1){
				// Check if the sumo hit is possible
				if (towersP2[this.getxPosition()][this.getyPosition()-1] != null && towersP2[this.getxPosition()][this.getyPosition()-1].getGems() < 2 && this.getyPosition()-1 >= 0 && fields[this.getxPosition()][this.getyPosition()-2].isEmpty()) {
				fields[this.getxPosition()][this.getyPosition()-1].setDisable(false);
				}
			}
						
			if(gems == 3 || gems == 2){
				// check if the double sumo hit is possible
				// check if fields have towers from P2 and movement isn't out of range
				if (this.getyPosition()-3 >= 0 && towersP2[this.getxPosition()][this.getyPosition()-1] !=null && towersP2[this.getxPosition()][this.getyPosition()-2] !=null){
					// check if there is no double sumo in the way
					if (towersP2[this.getxPosition()][this.getyPosition()-1].getGems() < 2 && towersP2[this.getxPosition()][this.getyPosition()-2].getGems() < 2){
						// checks if the 3 field is empty
						if(fields[this.getxPosition()][this.getyPosition()-3].isEmpty()){
							fields[this.getxPosition()][this.getyPosition()-1].setDisable(false);
							this.saveSumoMove = 2;
						}
					}		
				}
			}
			
			if(gems == 3){
				// check if the triple sumo hit is possible
				// check if fields have towers from P2 and movement isn't out of range
				if (this.getyPosition()-4 >= 0 && towersP2[this.getxPosition()][this.getyPosition()-1] != null && towersP2[this.getxPosition()][this.getyPosition()-2] !=null && towersP2[this.getxPosition()][this.getyPosition()-3] !=null){
					// check if there is no triple sumo in the way
					if (towersP2[this.getxPosition()][this.getyPosition()-1].getGems() < 3 && towersP2[this.getxPosition()][this.getyPosition()-2].getGems() < 3 && towersP2[this.getxPosition()][this.getyPosition()-3].getGems() < 3){
						// checks if the 4 field is empty
						if(fields[this.getxPosition()][this.getyPosition()-4].isEmpty()){
							fields[this.getxPosition()][this.getyPosition()-1].setDisable(false);
							this.saveSumoMove = 3;
						}
					}
				}
			}
		}
		
		
		if(this.getPlayerNumber()==2){
			// Disable Towers of Player2
			this.disableTowers(towersP2);
				
			if(gems == 3 || gems == 2 || gems == 1){
				// Check if the sumo hit is possible
				if (towersP1[this.getxPosition()][this.getyPosition()+1] != null && towersP1[this.getxPosition()][this.getyPosition()+1].getGems() < 2 && this.getyPosition()+2 <= fields.length - 1 && fields[this.getxPosition()][this.getyPosition()+2].isEmpty()){
					fields[this.getxPosition()][this.getyPosition()+1].setDisable(false);
				}
			}
				
			if(gems == 3 || gems == 2){
				// check if the double sumo hit is possible
				// check if fields have towers from P1 and movement isn't out of range
				if (this.getyPosition()+3 <= fields.length - 1 && towersP1[this.getxPosition()][this.getyPosition()+1] !=null && towersP1[this.getxPosition()][this.getyPosition()+2] !=null){
					// check if there is no double sumo in the way
					if (towersP1[this.getxPosition()][this.getyPosition()+1].getGems() < 2 && towersP1[this.getxPosition()][this.getyPosition()+2].getGems() < 2){
						// checks if the 3 field is empty
						if(fields[this.getxPosition()][this.getyPosition()+3].isEmpty()){
							fields[this.getxPosition()][this.getyPosition()+1].setDisable(false);
							this.saveSumoMove = 2;
						}
					}
				}
			}
				
			if(gems == 3){
				// check if the triple sumo hit is possible
				// check if fields have towers from P2 and movement isn't out of range
				if (this.getyPosition()+4 <= fields.length - 1  && towersP1[this.getxPosition()][this.getyPosition()+1] != null && towersP1[this.getxPosition()][this.getyPosition()+2] !=null && towersP1[this.getxPosition()][this.getyPosition()+3] !=null){
					// check if there is no triple sumo in the way
					if (towersP1[this.getxPosition()][this.getyPosition()+1].getGems() < 3 && towersP1[this.getxPosition()][this.getyPosition()+2].getGems() < 3 && towersP1[this.getxPosition()][this.getyPosition()+3].getGems() < 3){
						// checks if the 4 field is empty
						if(fields[this.getxPosition()][this.getyPosition()+4].isEmpty()){
							fields[this.getxPosition()][this.getyPosition()+1].setDisable(false);
							this.saveSumoMove = 3;
						}
					}
				}
			}
		}
	}
	
	
	/**
	 * This method will show the possible Tower moves and enable all possible fields
	 * @param fields
	 * @param gridPane
	 * @param towersP1
	 * @param towersP2
	 * @author robin
	 */
	public void showMoves(Field[][] fields, GridPane gridPane, Tower[][] towersP1, Tower[][] towersP2) {
		
		int anzahlFelder = 7;
		
		if(this.sumoTower) {
			if (gems == 1){
				anzahlFelder = 5;
			} 
		
			if (gems == 2){
				anzahlFelder = 3;
			}
	
			if (gems == 3){
				anzahlFelder = 1;
			}
		this.showSumoMoves(fields, gridPane, towersP1, towersP2);
		}
		
		
		if(this.getPlayerNumber()==1){
			
			this.disableTowers(towersP1);
			
			
			// Normal down Move
			for(int i = 1; i<= anzahlFelder ; i++){
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
			for(int i = 1; i<= anzahlFelder; i++){
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
				for(int i = 1; i<= anzahlFelder; i++){
					if((this.getxPosition()-i<0) || (this.getyPosition()-i<0)){
						break outerloop;
					} else if (fields[this.getxPosition()-i][this.getyPosition()-i].isEmpty()){
							fields[this.getxPosition()-i][this.getyPosition()-i].setDisable(false);
							} if(!fields[this.getxPosition()-i][this.getyPosition()-i].isEmpty()){
								break outerloop;
							}	}	}
		
		
		
		
		
		if(this.getPlayerNumber()==2){
			// Disable Towers of Player2
			this.disableTowers(towersP2);
			
			 
			// Normal up move
			for(int i = 1; i<= anzahlFelder ; i++){
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
			for(int i = 1; i<= anzahlFelder; i++){
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
				for(int i = 1; i<= anzahlFelder; i++){
					if((this.getxPosition()-i<0) || (this.getyPosition()+i>7)){
						break outerloop;
					} else if (fields[this.getxPosition()-i][this.getyPosition()+i].isEmpty()){
							fields[this.getxPosition()-i][this.getyPosition()+i].setDisable(false);
							} if(!fields[this.getxPosition()-i][this.getyPosition()+i].isEmpty()){
								break outerloop;
							}
						}
				}
		this.checkPat(fields, towersP1, towersP2);
	}

	/**
	 * This method will change the position of the nomal towers on the GridPane
	 * @param fields
	 * @param gameBoard
	 * @param towersP1
	 * @param towersP2
	 * @param field
	 * @param player1
	 * @param player2
	 * @author robin
	 */
	public void move(Field[][] fields, GridPane gameBoard, Tower[][] towersP1, Tower[][] towersP2, Field field, Player player1, Player player2) {
		
		int x = field.getxPosition();
		int y = field.getyPosition();
		
		if (!field.isEmpty() && this.getGems() >= 2 && this.saveSumoMove == 3){
				this.sumo3Move(fields, gameBoard, player1, player2, field, towersP1, towersP2);
			}
		else if (!field.isEmpty() && this.getGems() >= 2 && this.saveSumoMove == 2){
			this.sumo2Move(fields, gameBoard, player1, player2, field, towersP1, towersP2);
		}
		else if (!field.isEmpty() && this.getGems() >= 1){
			this.saveSumoMove = 1;
			this.sumo1Move(fields, gameBoard, player1, player2, field, towersP1, towersP2);
			}
		
		
		else{
		int oldX = this.getxPosition();
		int newX = field.getxPosition();
		int oldY = this.getyPosition();
		int newY = field.getyPosition();

		int newColumnGridPane = GridPane.getColumnIndex(field);
		int newRowGridPane = GridPane.getRowIndex(field);

		
		// Send the move to the server
		ServiceLocator.getServiceLocator().getLogger().info("Move send to server: " + field.getxPosition() + " " + field.getyPosition() + " " + this.getxPosition() + " " + this.getyPosition() + " " + this.getPlayerNumber());
		model.messageConstructorForCoordinate(oldX, oldY, newX, newY, this.getPlayerNumber());
		
		// The old field is empty and the new field is busy
		fields[oldX][oldY].setEmpty(true);
		field.setEmpty(false);
				
		// The coordinates of the tower will be changed
		this.setxPosition(newX);
		this.setyPosition(newY);

		// The Tower will be moved on the GridPane
		GridPane.setColumnIndex(this, newColumnGridPane);
		GridPane.setRowIndex(this, newRowGridPane);
		//this.setPlayerSign();
		
		// The tower will be set on the right position in the tower array
		if(model.getPlayer1().isOnTurn()){
			towersP1[newX][newY]=this;
			towersP1[oldX][oldY]= null;
		}
		
		if(model.getPlayer2().isOnTurn()){
			towersP2[newX][newY]=this;
			towersP2[oldX][oldY]= null;
		}
		
		// The turn is finished, disable all fields
		Field.disableFields(fields);
		
		// After the first move the boolean gameStart is false
		model.gameStart = false;
		
		// We check if a tower reached the last row
		this.checkWin(fields, player1, player2, this, towersP1, towersP2);
		
		// Towers of other player will be enabled
		if(model.Winner.get() == 0){
		this.changeTurn(fields, player1, player2, towersP1, towersP2, field);
		}

		
	}}
	/**
	 * This method will change the position of the Sumo3 and the three hit tower on the GridPane
	 * @param fields
	 * @param gameBoard
	 * @param towersP1
	 * @param towersP2
	 * @param field
	 * @param player1
	 * @param player2
	 * @author lukas.k
	 */
	private void sumo3Move(Field[][] fields, GridPane gameBoard, Player player1, Player player2, Field field, Tower[][] towersP1, Tower[][] towersP2) {
		int oldX = this.getxPosition();
		int newX = field.getxPosition();
		int oldY = this.getyPosition();
		int newY = field.getyPosition();
		
		int newColumnGridPane = GridPane.getColumnIndex(field);
		int newRowGridPane = GridPane.getRowIndex(field);

		 
			 if (this.playerNumber == 1){

				 // The old field from the sumoTower is empty and the new field from the rearmost hit-tower is busy
				fields[oldX][oldY].setEmpty(true);
				fields[newX][newY-3].setEmpty(false);
						
				// The coordinates from the hit towers will be changed
				towersP2[newX][newY].setxPosition(newX);
				towersP2[newX][newY].setyPosition(newY-1);
				
				towersP2[newX][newY-1].setxPosition(newX);
				towersP2[newX][newY-1].setyPosition(newY-2);
				
				towersP2[newX][newY-2].setxPosition(newX);
				towersP2[newX][newY-2].setyPosition(newY-3);
				
				// The coordinates of the sumoTower will be changed
				this.setxPosition(newX);
				this.setyPosition(newY);

				// The Towers will be moved on the GridPane
				GridPane.setColumnIndex(this, newColumnGridPane);
				GridPane.setRowIndex(this, newRowGridPane);
				GridPane.setColumnIndex(towersP2[newX][newY], newColumnGridPane);
				GridPane.setRowIndex(towersP2[newX][newY], newRowGridPane+1);
				GridPane.setColumnIndex(towersP2[newX][newY-1], newColumnGridPane);
				GridPane.setRowIndex(towersP2[newX][newY-1], newRowGridPane+2);
				GridPane.setColumnIndex(towersP2[newX][newY-2], newColumnGridPane);
				GridPane.setRowIndex(towersP2[newX][newY-2], newRowGridPane+3);
				
				
				// The towers will be set on the right position in the tower array
				towersP1[newX][newY]=this;
				towersP1[oldX][oldY]= null;
				towersP2[newX][newY-3] = towersP2[newX][newY-2]; 
				towersP2[newX][newY-2] = null;
				towersP2[newX][newY-2] = towersP2[newX][newY-1]; 
				towersP2[newX][newY-1] = null;
				towersP2[newX][newY-1] = towersP2[newX][newY]; 
				towersP2[newX][newY] = null;
				
					
					
				} else if (this.playerNumber == 2){
					
					
					// The old field from the sumoTower is empty and the new field from the rearmost hit-tower is busy
					fields[oldX][oldY].setEmpty(true);
					fields[newX][newY+3].setEmpty(false);
							
					// The coordinates from the hit towers will be changed
					towersP1[newX][newY].setxPosition(newX);
					towersP1[newX][newY].setyPosition(newY+1);
					
					towersP1[newX][newY+1].setxPosition(newX);
					towersP1[newX][newY+1].setyPosition(newY+2);
					
					towersP1[newX][newY+2].setxPosition(newX);
					towersP1[newX][newY+2].setyPosition(newY+3);
					
					// The coordinates of the sumoTower will be changed
					this.setxPosition(newX);
					this.setyPosition(newY);

					// The Towers will be moved on the GridPane
					GridPane.setColumnIndex(towersP1[newX][newY], newColumnGridPane);
					GridPane.setRowIndex(towersP1[newX][newY], newRowGridPane-1);
					GridPane.setColumnIndex(towersP1[newX][newY+2], newColumnGridPane);
					GridPane.setRowIndex(towersP1[newX][newY+2], newRowGridPane-3);
					GridPane.setColumnIndex(towersP1[newX][newY+1], newColumnGridPane);
					GridPane.setRowIndex(towersP1[newX][newY+1], newRowGridPane-2);
					GridPane.setColumnIndex(this, newColumnGridPane);
					GridPane.setRowIndex(this, newRowGridPane);
					
					
					// The towers will be set on the right position in the tower array
					towersP2[newX][newY]=this;
					towersP2[oldX][oldY]= null;
					towersP1[newX][newY+3] = towersP1[newX][newY+2];
					towersP1[newX][newY+2] = null;
					towersP1[newX][newY+2] = towersP1[newX][newY+1];
					towersP1[newX][newY+1] = null;
					towersP1[newX][newY+1] = towersP1[newX][newY];
					towersP1[newX][newY] = null;
						 
				}
				
				
				// The turn is finished, disable all fields
				Field.disableFields(fields);
				
				// We check if a tower reached the last row
				this.checkWin(fields, player1, player2, this, towersP1, towersP2);
				
				// Towers of other player will be enabled
				if(model.Winner.get() == 0){
				this.notChangeTurnSumo(fields, player1, player2, towersP1, towersP2, field);
			}}
	/**
	 * This method will change the position of the Sumo2 and the two hit towers on the GridPane
	 * @param fields
	 * @param gameBoard
	 * @param towersP1
	 * @param towersP2
	 * @param field
	 * @param player1
	 * @param player2
	 * @author lukas.k
	 */
	private void sumo2Move(Field[][] fields, GridPane gameBoard, Player player1, Player player2, Field field,Tower[][] towersP1, Tower[][] towersP2) {

			int oldX = this.getxPosition();
			int newX = field.getxPosition();
			int oldY = this.getyPosition();
			int newY = field.getyPosition();
			
			int newColumnGridPane = GridPane.getColumnIndex(field);
			int newRowGridPane = GridPane.getRowIndex(field);

			 
				 if (this.playerNumber == 1){

					 // The old field from the sumoTower is empty and the new field from the rearmost hit-tower is busy
					fields[oldX][oldY].setEmpty(true);
					fields[newX][newY-2].setEmpty(false);
							
					// The coordinates from the hit towers will be changed
					towersP2[newX][newY].setxPosition(newX);
					towersP2[newX][newY].setyPosition(newY-1);
					
					towersP2[newX][newY-1].setxPosition(newX);
					towersP2[newX][newY-1].setyPosition(newY-2);
					
					// The coordinates of the sumoTower will be changed
					this.setxPosition(newX);
					this.setyPosition(newY);

					// The Towers will be moved on the GridPane
					GridPane.setColumnIndex(this, newColumnGridPane);
					GridPane.setRowIndex(this, newRowGridPane);
					GridPane.setColumnIndex(towersP2[newX][newY], newColumnGridPane);
					GridPane.setRowIndex(towersP2[newX][newY], newRowGridPane+1);
					GridPane.setColumnIndex(towersP2[newX][newY-1], newColumnGridPane);
					GridPane.setRowIndex(towersP2[newX][newY-1], newRowGridPane+2);
					
					
					// The towers will be set on the right position in the tower array
					towersP1[newX][newY]=this;
					towersP1[oldX][oldY]= null;
					towersP2[newX][newY-2] = towersP2[newX][newY-1]; 
					towersP2[newX][newY-1] = null;
					towersP2[newX][newY-1] = towersP2[newX][newY]; 
					towersP2[newX][newY] = null;
					
						
						
					} else if (this.playerNumber == 2){
						
						
						// The old field from the sumoTower is empty and the new field from the rearmost hit-tower is busy
						fields[oldX][oldY].setEmpty(true);
						fields[newX][newY+2].setEmpty(false);
								
						// The coordinates from the hit towers will be changed
						towersP1[newX][newY].setxPosition(newX);
						towersP1[newX][newY].setyPosition(newY+1);
						
						towersP1[newX][newY+1].setxPosition(newX);
						towersP1[newX][newY+1].setyPosition(newY+2);
						
						// The coordinates of the sumoTower will be changed
						this.setxPosition(newX);
						this.setyPosition(newY);

						// The Towers will be moved on the GridPane
						GridPane.setColumnIndex(towersP1[newX][newY], newColumnGridPane);
						GridPane.setRowIndex(towersP1[newX][newY], newRowGridPane-1);
						GridPane.setColumnIndex(towersP1[newX][newY+1], newColumnGridPane);
						GridPane.setRowIndex(towersP1[newX][newY+1], newRowGridPane-2);
						GridPane.setColumnIndex(this, newColumnGridPane);
						GridPane.setRowIndex(this, newRowGridPane);
						
						
						// The towers will be set on the right position in the tower array
						towersP2[newX][newY]=this;
						towersP2[oldX][oldY]= null;
						towersP1[newX][newY+2] = towersP1[newX][newY+1];
						towersP1[newX][newY+1] = null;
						towersP1[newX][newY+1] = towersP1[newX][newY];
						towersP1[newX][newY] = null;
							 
					}
					
					
					// The turn is finished, disable all fields
					Field.disableFields(fields);
					
					// We check if a tower reached the last row
					this.checkWin(fields, player1, player2, this, towersP1, towersP2);
					
					// Towers of other player will be enabled
					if(model.Winner.get() == 0){
					this.notChangeTurnSumo(fields, player1, player2, towersP1, towersP2, field);
				}}
	/**
	 * This method will change the position of the Sumo and Hit Tower on the GridPane
	 * @param fields
	 * @param gameBoard
	 * @param towersP1
	 * @param towersP2
	 * @param field
	 * @param player1
	 * @param player2
	 * @author lukas.k
	 */
	private void sumo1Move(Field[][] fields, GridPane gameBoard, Player player1, Player player2, Field field,
		Tower[][] towersP1, Tower[][] towersP2) {
		int oldX = this.getxPosition();
		int newX = field.getxPosition();
		int oldY = this.getyPosition();
		int newY = field.getyPosition();
		
		int newColumnGridPane = GridPane.getColumnIndex(field);
		int newRowGridPane = GridPane.getRowIndex(field);

		 
			 if (this.playerNumber == 1){

		// The old field is empty and the new field from hit tower is busy
				fields[oldX][oldY].setEmpty(true);
				fields[newX][newY-1].setEmpty(false);
						
				// The coordinates of the hit tower will be changed
				towersP2[newX][newY].setxPosition(newX);
				towersP2[newX][newY].setyPosition(newY-1);
				
				// The coordinates of the sumoTower will be changed
				this.setxPosition(newX);
				this.setyPosition(newY);

				// The Towers will be moved on the GridPane
				GridPane.setColumnIndex(this, newColumnGridPane);
				GridPane.setRowIndex(this, newRowGridPane);
				GridPane.setColumnIndex(towersP2[newX][newY], newColumnGridPane);
				GridPane.setRowIndex(towersP2[newX][newY], newRowGridPane+1);
				
				//this.setPlayerSign();
				
				// The towers will be set on the right position in the tower array
					towersP1[newX][newY]=this;
					towersP1[oldX][oldY]= null;
					towersP2[newX][newY-1] = towersP2[newX][newY]; 
					towersP2[newX][newY] = null;
					
					
				} else if (this.playerNumber == 2){
					
					
					// The old field is empty and the new field from hit tower is busy
					fields[oldX][oldY].setEmpty(true);
					fields[newX][newY+1].setEmpty(false);
							
					// The coordinates of the hit tower will be changed
					towersP1[newX][newY].setxPosition(newX);
					towersP1[newX][newY].setyPosition(newY+1);
					
					// The coordinates of the sumoTower will be changed
					this.setxPosition(newX);
					this.setyPosition(newY);

					// The Towers will be moved on the GridPane
					GridPane.setColumnIndex(towersP1[newX][newY], newColumnGridPane);
					GridPane.setRowIndex(towersP1[newX][newY], newRowGridPane-1);
					GridPane.setColumnIndex(this, newColumnGridPane);
					GridPane.setRowIndex(this, newRowGridPane);
					
					//this.setPlayerSign();
					
					// The towers will be set on the right position in the tower array
						towersP2[newX][newY]=this;
						towersP2[oldX][oldY]= null;
						towersP1[newX][newY+1] = towersP1[newX][newY];
						towersP1[newX][newY] = null;
						 
				}
				
			 
				
				// The turn is finished, disable all fields
				Field.disableFields(fields);
				
				// We check if a tower reached the last row
				this.checkWin(fields, player1, player2, this, towersP1, towersP2);
				
				// Towers of other player will be enabled
				if(model.Winner.get() == 0){
				this.notChangeTurnSumo(fields, player1, player2, towersP1, towersP2, field);
			}}
	
	/**
	 * This method will activate the own towers after a sumoHit and NOT change the turn
	 * @param fields
	 * @param player1
	 * @param player2
	 * @param towersP1
	 * @param towersP2
	 * @author lukas
	 */
	private void notChangeTurnSumo(Field[][] fields, Player player1, Player player2, Tower[][] towersP1, Tower[][] towersP2, Field field) {

		if(player1.isOnTurn()){
			player2.setOnTurn(false);
			player1.setOnTurn(true);
			this.disableTowers(towersP2);
			this.enableTowerAfterSumoMove(fields, towersP1, field);
		} else{
			player2.setOnTurn(true);
			player1.setOnTurn(false);
			this.enableTowerAfterSumoMove(fields, towersP2, field);
			this.disableTowers(towersP1);
		}
	}
	/**
	 *  This method will enable the own tower with the right color after a SumHit 
	 * @param fields
	 * @param towers
	 * @param field
	 * @author lukas
	 */
	private void enableTowerAfterSumoMove(Field[][] fields, Tower[][] towers, Field field) {
		if(this.playerNumber == 1){
				for(int y = 0; y < 8; y++){
				for(int x = 0; x < 8; x++){
					if(towers[x][y] != null && towers[x][y].getColor().equals(fields[field.getxPosition()][field.getyPosition()- this.saveSumoMove].getColor())){
						towers[x][y].setDisable(false);
											
			}}}}else if (this.playerNumber == 2){
						for(int x = 0; x < 8; x++){
						for(int y = 0; y < 8; y++){
							if(towers[x][y] != null && towers[x][y].getColor().equals(fields[field.getxPosition()][field.getyPosition()+ this.saveSumoMove].getColor())){
								towers[x][y].setDisable(false);	
				}}}}
			this.saveSumoMove = 0;
			}
	
	/**
	 * This method will activate the opponents towers and change the turn
	 * @param player1
	 * @param player2
	 * @param towersP1
	 * @param towersP2
	 * @author robin
	 */
	public void changeTurn(Field[][] fields, Player player1, Player player2, Tower[][] towersP1, Tower[][] towersP2, Field field){
		if(player1.isOnTurn()){
			player1.setOnTurn(false);
			player2.setOnTurn(true);
			Tower.disableTowers(towersP1);
			this.enableTowers(fields, towersP2, field);
		} else{
			player1.setOnTurn(true);
			player2.setOnTurn(false);
			Tower.disableTowers(towersP2);
			this.enableTowers(fields, towersP1, field);
		}
	}
	
	/**
	 * This method disables all towers of the tower array chosen
	 * @param towers
	 * @author robin
	 */
	public static void disableTowers(Tower[][] towers){
		for(int y = 0; y < 8; y++){
			for(int x = 0; x < 8; x++){
				if(towers[x][y]!=null){
					towers[x][y].setDisable(true);
					}}}
	}

	/**
	 * This method enables all towers of the tower array chosen. If it is the first round, all towers are enabled. Otherwise only the tower with the matching color
	 * @param towers
	 * @author robin
	 */
	public void enableTowers(Field[][] fields, Tower[][] towers, Field field){
		if (model.gameStart){
			for(int y = 0; y < 8; y++){
				for(int x = 0; x < 8; x++){
					if(towers[x][y]!=null){
						towers[x][y].setDisable(false);
						
			}}}} else{
					for(int x = 0; x < 8; x++){
					for(int y = 0; y < 8; y++){
						if(towers[x][y]!=null && towers[x][y].getColor().equals(field.getColor())){
							towers[x][y].setDisable(false);
		}}}}
	}

	/**
	 * This method make a move coming from the Server
	 * @param startColumn
	 * @param startRow
	 * @param endColumn
	 * @param endRow
	 * @param playerType
	 */
	public void move(Field[][] fields, GridPane gameBoard, Player player1, Player player2, Tower[][] towersP1, Tower[][] towersP2, int oldX, int oldY, int newX, int newY, int playerType) {

		int column = GridPane.getColumnIndex(fields[newX][newY]);
		int row = GridPane.getRowIndex(fields[newX][newY]);
		
		// Only make the move if its not done
		if(!fields[oldX][oldY].isEmpty()){
			this.setxPosition(newX);
			this.setyPosition(newY);
			this.setText(newX + "." + newY);
			
			fields[oldX][oldY].setEmpty(true);
			fields[newX][newY].setEmpty(false);
			
			GridPane.setColumnIndex(this, column);
			GridPane.setRowIndex(this, row);
			
			Field.disableFields(fields);
			model.gameStart = false;

			// The tower will be set on the right position in the tower array
			if(model.getPlayer1().isOnTurn()){
				towersP1[newX][newY]=this;
				towersP1[oldX][oldY]= null;
			}
			
			if(model.getPlayer2().isOnTurn()){
				towersP2[newX][newY]=this;
				towersP2[oldX][oldY]= null;
			}
			
			changeTurn(fields, player1, player2, towersP1, towersP2, fields[newX][newY]);
			
		}
	}

	/**
	 * This method will upgrade a tower to different Sumo Towers
	 * @param fields
	 * @param tower
	 * @param xCoordinateUpgrade
	 * @param yCoordinateUpgrade
	 * @param gems
	 * @param towersP1
	 * @param towersP2
	 */
	public void upgradeTower(Field[][] fields, Tower tower, int xCoordinateUpgrade, int yCoordinateUpgrade, int gems, Tower[][] towersP1, Tower[][] towersP2) {
		int column = GridPane.getColumnIndex(fields[xCoordinateUpgrade][yCoordinateUpgrade]);
		int row = GridPane.getRowIndex(fields[xCoordinateUpgrade][yCoordinateUpgrade]);
		
		
		if(this.gems == 0){
			this.sumoTower = true;
			this.setText("\u2160");
			this.gems++;
		}else if (this.gems == 1){
			this.setText("\u2161");
			this.gems++;
		}else if (this.gems == 2){
			this.setText("\u2162");
			this.gems++;
	}}

	/**
	 * This method checks if the tower reach a winning position and upgrade the tower if yes
	 * @param 
	 * @author lukas.k
	 */
	private void checkWin(Field[][] fields, Player player1, Player player2, Tower tower, Tower[][] towersP1, Tower[][] towersP2) {
			if (player1.isOnTurn()&& yPosition == 0){
			this.upgradeTower(fields, tower, this.getxPosition(), this.getyPosition(), this.getGems(), towersP1, towersP2);
			this.setDisable(false);
			GameMenu_Model.Winner.set(1);
			} else if(player2.isOnTurn()&& yPosition == 7){
			this.upgradeTower(fields, tower, this.xPosition, this.yPosition, gems, towersP1, towersP2);
			this.setDisable(false);
			GameMenu_Model.Winner.set(2);
			
			}
		}


	// Getters and Setters
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
	
	public int getGems() {
		return gems;
	}

	public void setGems(int gems) {
		this.gems = gems;
	}
	
	public void setSumoTower(boolean SumoTower){
		this.sumoTower = SumoTower;
	}
	
	public boolean getSumoTower(){
		return this.sumoTower;
		
}


}
