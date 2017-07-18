package WindChuckers_Main.Model_Extend;

import org.omg.Messaging.SyncScopeHelper;

import com.sun.media.jfxmedia.logging.Logger;

import WindChuckers_Main.GameMenu_Model;
import abstractClasses.Model;
import commonClasses.ServiceLocator;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class Tower extends Button {
	private int playerNumber;
	private GameMenu_Model model = GameMenu_Model.getGameModel();
	private String color;
	private int yPosition;
	private int xPosition;
		
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
	 * This method will show all possible moves and enable all possible fields
	 * @param fields
	 * @param gridPane
	 * @param towersP1
	 * @param towersP2
	 * @author robin
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
	 * @author robin
	 */
	public void move(Field[][] fields, GridPane gameBoard, Tower[][] towersP1, Tower[][] towersP2, Field field, Player player1, Player player2) {

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
		this.setText(field.getxPosition()+"."+field.getyPosition());
		
		// The Tower will be moved on the GridPane
		GridPane.setColumnIndex(this, newColumnGridPane);
		GridPane.setRowIndex(this, newRowGridPane);
		this.setPlayerSign();
		
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
		this.checkWin(fields, player1, player2, this, this.xPosition, this.yPosition, 2, towersP1, towersP2);

		// Towers of other player will be enabled
		this.changeTurn(player1, player2, towersP1, towersP2, field);
	}
	
	/**
	 * This method will activate the opponents towers and change the turn
	 * @param player1
	 * @param player2
	 * @param towersP1
	 * @param towersP2
	 * @author robin
	 */
	public void changeTurn(Player player1, Player player2, Tower[][] towersP1, Tower[][] towersP2, Field field){
		if(player1.isOnTurn()){
			player2.setOnTurn(true);
			player1.setOnTurn(false);
			this.disableTowers(towersP1);
			this.enableTowers(towersP2, field);
		} else{
			player1.setOnTurn(true);
			player2.setOnTurn(false);
			this.enableTowers(towersP1, field);
			this.disableTowers(towersP2);
		}
	}
	
	/**
	 * This method disables all towers of the tower array chosen
	 * @param towers
	 * @author robin
	 */
	public void disableTowers(Tower[][] towers){
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
	public void enableTowers(Tower[][] towers, Field field){
		if (model.gameStart){
		for(int y = 0; y < 8; y++){
			for(int x = 0; x < 8; x++){
				if(towers[x][y]!=null){
					towers[x][y].setDisable(false);
					}}}} else if(!model.gameStart){
						for(int y = 0; y < 8; y++){
							for(int x = 0; x < 8; x++){
								if(towers[x][y]!=null && towers[x][y].getColor().equals(field.getColor())){
									towers[x][y].setDisable(false);
								}}}
		}
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
			
			changeTurn(player1, player2, towersP1, towersP2, fields[newX][newY]);
			
		}
	}

	public void upgradeTower(Field[][] fields, Tower tower, int xCoordinateUpgrade, int yCoordinateUpgrade, int gems, Tower[][] towersP1, Tower[][] towersP2) {
//		int column = GridPane.getColumnIndex(fields[xCoordinateUpgrade][yCoordinateUpgrade]);
//		int row = GridPane.getRowIndex(fields[xCoordinateUpgrade][yCoordinateUpgrade]);
//		
//		SumoTower sumoTower = new SumoTower(this.getColor());
//		sumoTower.setxPosition(this.getxPosition());
//		sumoTower.setyPosition(this.getyPosition());
//		sumoTower.setPlayerNumber(this.getPlayerNumber());
//		sumoTower.setStyle(" -fx-background-color: #FF8C00;");
//		
//		
//		
//		
//		if(this.getPlayerNumber()==1){
//			for(int y = 0; y < 8; y++){
//				for(int x = 0; x < 8; x++){
//					if(fields[x][y].isEmpty() == false){
//						for(int x1 = 0; x1 < 8; x1++)
//							if (this.getxPosition() == towersP1[x1][7].getxPosition() && this.getyPosition() == towersP1[x1][7].getyPosition()){
////								towersP1[x1][7] = null;
//								towersP1[x1][7] = sumoTower;
//								towersP1[x1][7].setText("\u2163");
//								
//			
//		}}}}} else{
//			for(int y = 0; y < 8; y++){
//				for(int x = 0; x < 8; x++){
//					if(fields[x][y].isEmpty() == false){
//						for(int x1 = 0; x1 < 8; x1++)
//							if (this.getxPosition() == towersP2[x1][0].getxPosition() && this.getyPosition() == towersP2[x1][0].getyPosition()){
////								towersP2[x1][0] = null;
//								towersP2[x1][0] = sumoTower;
//								towersP2[x1][0].setText("\u2163");
//			
//		}}}}}
		

	
	}

	// Anmerkung LKu (Ich muss falsche Koordinaten prüfen)
	private void checkWin(Field[][] fields, Player player1, Player player2, Tower tower, int xPosition, int yPosition, int gems, Tower[][] towersP1, Tower[][] towersP2) {
			if (player1.isOnTurn() == true && yPosition == 0){
			this.upgradeTower(fields, tower, this.xPosition, this.yPosition, gems, towersP1, towersP2);
			model.Winner.set(true);
			System.out.println("We have a Winner");
			} else if(player2.isOnTurn() == true && yPosition == 7){
			this.upgradeTower(fields, tower, this.xPosition, this.yPosition, gems, towersP1, towersP2);
			model.Winner.set(true);
			System.out.println("We have a Winner");
			}
		}
	
	public static void moveRight() {
		// TODO Auto-generated method stub
		
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
	
	public void setPlayerSign(){
		if (this.getPlayerNumber() == 1){
			this.setText("\u2160");
	} else if(this.getPlayerNumber() == 2){
		this.setText("\u2161");
	}}
		
	public String toString(){
		String s = "X: "+this.getxPosition()+"\nY: "+this.getyPosition();
		return "Tower Koordinaten:\n"+s; 
	}
}
