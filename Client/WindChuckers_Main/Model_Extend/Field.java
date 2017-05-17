package WindChuckers_Main.Model_Extend;

import WindChuckers_Main.GameMenu_Model;
import javafx.scene.control.Button;

public class Field extends Button{
	
	private String color;
	private int yPosition;
	private int xPosition;
	private boolean empty = true;
	private int number;
	
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
	public static Field getOrangeField(int xPosition, int yPosition){
			Field orangeField = new Field("orange");
			
			orangeField.setxPosition(xPosition);
			orangeField.setyPosition(yPosition);
			orangeField.setStyle(" -fx-background-color: #FF8C00;");
			orangeField.setId("gameField");
			return orangeField;
			
	}
	public static Field getBlueField(int xPosition, int yPosition){
		Field blueField = new Field("blue");
		blueField.setxPosition(xPosition);
		blueField.setyPosition(yPosition);
		blueField.setStyle(" -fx-background-color: #4169E1;");
		blueField.setId("gameField");
		return blueField;
}
	public static Field getVioletField(int xPosition, int yPosition){
		Field violetField = new Field("violet");
		violetField.setxPosition(xPosition);
		violetField.setyPosition(yPosition);
		violetField.setStyle(" -fx-background-color: #663399;");
		violetField.setId("gameField");
		return violetField;
}
	public static Field getPinkField(int xPosition, int yPosition){
		Field pinkField = new Field("pink");
		pinkField.setxPosition(xPosition);
		pinkField.setyPosition(yPosition);
		pinkField.setStyle(" -fx-background-color: #FF69B4;");
		pinkField.setId("gameField");
		return pinkField;
}
	public static Field getYellowField(int xPosition, int yPosition){
		Field yellowField = new Field("yellow");
		yellowField.setxPosition(xPosition);
		yellowField.setyPosition(yPosition);
		yellowField.setStyle(" -fx-background-color: #FFD700;");
		yellowField.setId("gameField");
		return yellowField;
}
	public static Field getRedField(int xPosition, int yPosition){
		Field redField = new Field("red");
		redField.setxPosition(xPosition);
		redField.setyPosition(yPosition);
		redField.setStyle(" -fx-background-color: #B22222;");
		redField.setId("gameField");
		return redField;
}
	public static Field getGreenField(int xPosition, int yPosition){
		Field greenField = new Field("green");
		greenField.setxPosition(xPosition);
		greenField.setyPosition(yPosition);
		greenField.setStyle(" -fx-background-color: #008000;");
		greenField.setId("gameField");
		return greenField;
}
	public static Field getBrownField(int xPosition, int yPosition){
		Field brownField = new Field("brown");
		brownField.setxPosition(xPosition);
		brownField.setyPosition(yPosition);
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

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

}
