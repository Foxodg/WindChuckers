package WindChuckers_Main.Model_Extend;



public class normalTower extends Tower{

	public normalTower(String color) {
		super(color);
		// TODO Auto-generated constructor stub
	}
	
	public static normalTower getOrangeTower(int xPosition, int yPosition){
		normalTower orangeTower = new normalTower("orange");
		orangeTower.setxPosition(xPosition);
		orangeTower.setyPosition(yPosition);
		orangeTower.setStyle(" -fx-background-color: #FF8C00;");
		return orangeTower;
	}
	public static normalTower getBlueTower(int xPosition, int yPosition){
		normalTower blueTower = new normalTower("blue");
		blueTower.setxPosition(xPosition);
		blueTower.setyPosition(yPosition);
		blueTower.setStyle(" -fx-background-color: #4169E1;");
		return blueTower;
	}
	public static normalTower getVioletTower(int xPosition, int yPosition){
		normalTower violetTower = new normalTower("violet");
		violetTower.setxPosition(xPosition);
		violetTower.setyPosition(yPosition);
		violetTower.setStyle(" -fx-background-color: #663399 ;");
		return violetTower;
	}
	public static normalTower getPinkTower(int xPosition, int yPosition){
		normalTower pinkTower = new normalTower("pink");
		pinkTower.setxPosition(xPosition);
		pinkTower.setyPosition(yPosition);
		pinkTower.setStyle(" -fx-background-color: #FF69B4;");
		return pinkTower;
	}
	public static normalTower getYellowTower(int xPosition, int yPosition){
		normalTower yellowTower = new normalTower("yellow");
		yellowTower.setxPosition(xPosition);
		yellowTower.setyPosition(yPosition);
		yellowTower.setStyle(" -fx-background-color: #FFD700;");
		return yellowTower;
	}
	public static normalTower getRedTower(int xPosition, int yPosition){
		normalTower redTower = new normalTower("red");
		redTower.setxPosition(xPosition);
		redTower.setyPosition(yPosition);
		redTower.setStyle(" -fx-background-color: #B22222;");
		return redTower;
	}
	public static normalTower getGreenTower(int xPosition, int yPosition){
		normalTower greenTower = new normalTower("green");
		greenTower.setxPosition(xPosition);
		greenTower.setyPosition(yPosition);
		greenTower.setStyle(" -fx-background-color: #008000;");
		return greenTower;
	}
	public static normalTower getBrownTower(int xPosition, int yPosition){
		normalTower brownTower = new normalTower("brown");
		brownTower.setxPosition(xPosition);
		brownTower.setyPosition(yPosition);
		brownTower.setStyle(" -fx-background-color: #8B4513;");
		return brownTower;
	}

}
