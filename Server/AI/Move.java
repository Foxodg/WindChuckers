package AI;

public class Move {
	private int x1, y1, x2, y2, depth;
	private boolean prov = false; //prov. Move for check the posibilities - true prov / false real
	
	public Move(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public Move(int x1, int y1, int x2, int y2, int depth) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.depth = depth;
	}
	
	public Move(int x1, int y1, int x2, int y2, boolean prov) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.prov = prov;
	}
	
	public Move(int x, int y){
		this.x1 = x;
		this.y1 = y;
	}
	
	public Move(Move move, boolean prov){
		this.x1 = move.getX1();
		this.x2 = move.getX2();
		this.y1 = move.getY1();
		this.y2 = move.getY2();
		this.prov = prov;
	}
	
	public String toString(){ // TODO change to a1 to b4 etc
		return x1 + " " + y1 + " " + x2 + " " + y2;
		//return (char)('A'+x1) + "" + (y1+1) + " " + (char)('A'+x2) + "" + (y2+1);
	}
	
	public boolean equals(Object o){
		Move op = (Move) o;
		
		if(op.getX1() == x1 && op.getY1() == y1 && op.getX2() == x2 && op.getY2() == y2){
			return true;
		}
		else
			return false;
	}
	
	
	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}
	
	public boolean isProv() {
		return prov;
	}
	
	public void setDepth(int depth){
		this.depth = depth;
	}
	
	public int getDepth(){
		return this.depth;
	}

}
