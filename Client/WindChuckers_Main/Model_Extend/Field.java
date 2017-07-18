package WindChuckers_Main.Model_Extend;

import WindChuckers_Main.GameMenu_Model;
import javafx.scene.control.Button;

public class Field extends Button{
	
	private String color;
	private int yPosition;
	private int xPosition;
	private boolean empty = true;
	
	public Field(String color){
		super();
		this.color=color;		
	}

	/**
	 * Following method provides the fields in each color
	 * @return
	 * @author robin
	 */
	public static Field getOrangeField(){
		Field orangeField = new Field("orange");
		orangeField.setStyle(" -fx-background-color: "+GameMenu_Model.ORANGE+";"); 
		orangeField.setId("gameField");
		return orangeField;
		}
	
	/**
	 * Following method provides the fields in each color
	 * @return
	 * @author robin
	 */
	public static Field getBlueField(){
		Field blueField = new Field("blue");
		blueField.setStyle(" -fx-background-color: "+GameMenu_Model.BLUE+";"); 
		blueField.setId("gameField");
		return blueField;
		}
	
	/**
	 * Following method provides the fields in each color
	 * @return
	 * @author robin
	 */
	public static Field getVioletField(){
		Field violetField = new Field("violet");
		violetField.setStyle(" -fx-background-color: "+GameMenu_Model.VIOLET+";"); 
		violetField.setId("gameField");
		return violetField;
}
	
	/**
	 * Following method provides the fields in each color
	 * @return
	 * @author robin
	 */
	public static Field getPinkField(){
		Field pinkField = new Field("pink");
		pinkField.setStyle(" -fx-background-color: "+GameMenu_Model.PINK+";"); 
		pinkField.setId("gameField");
		return pinkField;
}
	
	/**
	 * Following method provides the fields in each color
	 * @return
	 * @author robin
	 */
	public static Field getYellowField(){
		Field yellowField = new Field("yellow");
		yellowField.setStyle(" -fx-background-color: "+GameMenu_Model.YELLOW+";"); 
		yellowField.setId("gameField");
		return yellowField;
}
	
	/**
	 * Following method provides the fields in each color
	 * @return
	 * @author robin
	 */
	public static Field getRedField(){
		Field redField = new Field("red");
		redField.setStyle(" -fx-background-color: "+GameMenu_Model.RED+";"); 
		redField.setId("gameField");
		return redField;
}
	
	/**
	 * Following method provides the fields in each color
	 * @return
	 * @author robin
	 */
	public static Field getGreenField(){
		Field greenField = new Field("green");
		greenField.setStyle(" -fx-background-color: "+GameMenu_Model.GREEN+";"); 
		greenField.setId("gameField");
		return greenField;
}
	
	/**
	 * Following method provides the fields in each color
	 * @return
	 * @author robin
	 */
	public static Field getBrownField(){
		Field brownField = new Field("brown");
		brownField.setStyle(" -fx-background-color: "+GameMenu_Model.BROWN+";"); 
		brownField.setId("gameField");
		return brownField;
}
	
	/**
	 * This static method disables all fields. Is used after a turn is finished
	 * @author robin
	 * @return
	 */
	public static void disableFields(Field[][]fields){
		for(int y = 0; y < 8; y++){
			for(int x = 0; x < 8; x++){
				fields[x][y].setDisable(true);
			}}
	}
	
	// Getter and Setter
	public String getColor() {
		return color;
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

	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

}
