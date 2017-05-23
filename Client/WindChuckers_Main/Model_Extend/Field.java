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
	 * Following methods will provide the fields in each color
	 * @param xPosition
	 * @param yPosition
	 * @return
	 * @author robin
	 */
	public static Field getOrangeField(){
			Field orangeField = new Field("orange");
			orangeField.setStyle(" -fx-background-color: #FF8C00;");
			orangeField.setId("gameField");
			return orangeField;
			
	}
	public static Field getBlueField(){
		Field blueField = new Field("blue");
		blueField.setStyle(" -fx-background-color: #4169E1;");
		blueField.setId("gameField");
		return blueField;
}
	public static Field getVioletField(){
		Field violetField = new Field("violet");
		violetField.setStyle(" -fx-background-color: #663399;");
		violetField.setId("gameField");
		return violetField;
}
	public static Field getPinkField(){
		Field pinkField = new Field("pink");
		pinkField.setStyle(" -fx-background-color: #FF69B4;");
		pinkField.setId("gameField");
		return pinkField;
}
	public static Field getYellowField(){
		Field yellowField = new Field("yellow");
		yellowField.setStyle(" -fx-background-color: #FFD700;");
		yellowField.setId("gameField");
		return yellowField;
}
	public static Field getRedField(){
		Field redField = new Field("red");
		redField.setStyle(" -fx-background-color: #B22222;");
		redField.setId("gameField");
		return redField;
}
	public static Field getGreenField(){
		Field greenField = new Field("green");
		greenField.setStyle(" -fx-background-color: #008000;");
		greenField.setId("gameField");
		return greenField;
}
	public static Field getBrownField(){
		Field brownField = new Field("brown");
		brownField.setStyle(" -fx-background-color: #8B4513;");
		brownField.setId("gameField");
		return brownField;
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
