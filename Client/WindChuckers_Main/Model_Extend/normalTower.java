package WindChuckers_Main.Model_Extend;

public class normalTower extends Tower{

	public normalTower(String color) {
		super(color);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Following methods will provide the towers in each color
	 * @param xPosition
	 * @param yPosition
	 * @return
	 * @author robin
	 */
	public static normalTower getOrangeTower(){
		normalTower orangeTower = new normalTower("orange");
		orangeTower.setStyle(" -fx-background-color: #FF8C00;");
		return orangeTower;
	}
	public static normalTower getBlueTower(){
		normalTower blueTower = new normalTower("blue");
		blueTower.setStyle(" -fx-background-color: #4169E1;");
		return blueTower;
	}
	public static normalTower getVioletTower(){
		normalTower violetTower = new normalTower("violet");
		violetTower.setStyle(" -fx-background-color: #663399 ;");
		return violetTower;
	}
	public static normalTower getPinkTower(){
		normalTower pinkTower = new normalTower("pink");
		pinkTower.setStyle(" -fx-background-color: #FF69B4;");
		return pinkTower;
	}
	public static normalTower getYellowTower(){
		normalTower yellowTower = new normalTower("yellow");
		yellowTower.setStyle(" -fx-background-color: #FFD700;");
		return yellowTower;
	}
	public static normalTower getRedTower(){
		normalTower redTower = new normalTower("red");
		redTower.setStyle(" -fx-background-color: #B22222;");
		return redTower;
	}
	public static normalTower getGreenTower(){
		normalTower greenTower = new normalTower("green");
		greenTower.setStyle(" -fx-background-color: #008000;");
		return greenTower;
	}
	public static normalTower getBrownTower(){
		normalTower brownTower = new normalTower("brown");
		brownTower.setStyle(" -fx-background-color: #8B4513;");
		return brownTower;
	}

}
