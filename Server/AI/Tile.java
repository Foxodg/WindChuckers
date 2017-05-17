package AI;

public class Tile {
	private boolean occupied;
	private Tower tower;
	private Color color;
	
	public Tile() {
		occupied = false;
	}
	
	public Tile(Tile tile, Color color){
		this.occupied = tile.isOccupied();
		this.tower = tile.isOccupied() ? tile.getTower() : null;
		this.color = color;
	}
	
	public Tile(Color color){
		this.color = color;
	}
	
	public Tile(Tower tower, Color color) {
		occupied = true;
		this.tower = tower;
		this.color = color;
	}

	@Override
	public String toString() {
		if(occupied){
			return tower.toString();
		} else {
			switch (this.color){
			case Blue: return "□";
			case Orange: return "□";
			case Purple: return "□";
			case Pink: return "□";
			case Yellow: return "□";
			case Red: return "□";
			case Green: return "□";
			case Brown: return "□";
			}
			return ".";
		}
	}
	
	public Tower getTower(){
		return tower;
	}
	
	public void setTower(Tower tower) {
		this.tower = tower;
	}
	
	public boolean isOccupied() {
		return occupied;
	}
	
	public Color getColor(){
		return this.color;
	}
	
	public boolean hasTower(Tile[][] board, int x, int y){
		if(board[x][y].getTile(x, y).isOccupied()){
			return true;
		} else {
			return false;
		}
	}

	private Tile getTile(int x, int y) {
		return this;
	}

}
