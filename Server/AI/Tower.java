package AI;
import java.util.ArrayList;

public class Tower {
	public Color color;
	public Type type;
	public PlayerType playerType;
	public int gems;

	// possible moves

	public Tower(Color color, Type type, PlayerType playerType, int gems) {
		this.color = color;
		this.type = type;
		this.playerType = playerType;
		this.gems = gems;
	}

	public Color getColor() {
		return this.color;
	}

	public String toString() {
		if (this.getType() == Type.normalTower) {
			// this is for the normal-Tower
			switch (this.playerType) {
			case ONE:
				switch (this.color) {
				case Blue:
					return "۞";
				case Orange:
					return "۞";
				case Purple:
					return "۞";
				case Pink:
					return "۞";
				case Yellow:
					return "۞";
				case Red:
					return "۞";
				case Green:
					return "۞";
				case Brown:
					return "۞";
				}
			case TWO:
				switch (this.color) {
				case Blue:
					return "۝";
				case Orange:
					return "۝";
				case Purple:
					return "۝";
				case Pink:
					return "۝";
				case Yellow:
					return "۝";
				case Red:
					return "۝";
				case Green:
					return "۝";
				case Brown:
					return "۝";
				}
			}
		} else {
			// this is for the sumo-Tower
			switch (this.playerType) {
			case ONE:
				switch (this.color) {
				case Blue:
					return "۩";
				case Orange:
					return "۩";
				case Purple:
					return "۩";
				case Pink:
					return "۩";
				case Yellow:
					return "۩";
				case Red:
					return "۩";
				case Green:
					return "۩";
				case Brown:
					return "۩";
				}
			case TWO:
				switch (this.color) {
				case Blue:
					return "Ӂ";
				case Orange:
					return "Ӂ";
				case Purple:
					return "Ӂ";
				case Pink:
					return "Ӂ";
				case Yellow:
					return "Ӂ";
				case Red:
					return "Ӂ";
				case Green:
					return "Ӂ";
				case Brown:
					return "Ӂ";
				}
			}
		}

		return ".";
	}

	public ArrayList<Move> getMoves(Board board, int x, int y) {
		ArrayList<Move> moves = new ArrayList<Move>();

		int possibleMoves = getGems(board.getTile(x, y).getTower());

		if (playerType == PlayerType.ONE) {
			// up
			for (int i = 1; i < possibleMoves; i++) {
				if (valid(x, y + i)) {
					if (possibleMoves == 8) {
						if (!board.getTile(x, y + i).isOccupied()) {
							moves.add(new Move(x, y, x, y + i));
						} else {
							// not possible to push
							break;
						}
					} else {
						//then it must be an Sumo-Tower and it can push put not if the field behind is not valid
						if (!board.getTile(x, y + i).isOccupied() && valid(x, y + i+1)) {
							moves.add(new Move(x, y, x, y + i));
						} else {
							// push is possible also get the last move then skip
							moves.add(new Move(x, y, x, y + i));
							break;
						}
					}
				}
			}
			// NE
			for (int i = 1; i < possibleMoves; i++) {
				if (valid(x + i, y + i)) {
					if (!board.getTile(x + i, y + i).isOccupied()) {
						moves.add(new Move(x, y, x + i, y + i));
					} else {
						// not possible to push
						break;
					}
				}
			}

			// NW
			for (int i = 1; i < possibleMoves; i++) {
				if (valid(x - i, y + i)) {
					if (!board.getTile(x - i, y + i).isOccupied()) {
						moves.add(new Move(x, y, x - i, y + i));
					} else {
						// not possible to push
						break;
					}

				}
			}

		}
		if (playerType == PlayerType.TWO) {
			// down
			for (int i = 1; i < possibleMoves; i++) {
				if (valid(x, y - i)) {
					if (possibleMoves == 8) {
						if (!board.getTile(x, y - i).isOccupied()) {
							moves.add(new Move(x, y, x, y - i));
						} else {
							// not possible to push
							break;
						}
					} else {
						//then it must be an Sumo-Tower and it can push put not if the field behind is not valid
						if (!board.getTile(x, y - i).isOccupied() && valid(x, y - i-1)) {
							moves.add(new Move(x, y, x, y - i));
						} else {
							// push is possible also get the last move then skip
							moves.add(new Move(x, y, x, y - i));
							break;
						}
					}

				}
			}

			// SE
			for (int i = 1; i < possibleMoves; i++) {
				if (valid(x + i, y - i)) {
					if (!board.getTile(x + i, y - i).isOccupied()) {
						moves.add(new Move(x, y, x + i, y - i));
					} else {
						// not possible to push
						break;
					}
				}
			}

			// SW
			for (int i = 1; i < possibleMoves; i++) {
				if (valid(x - i, y - i)) {
					if (!board.getTile(x - i, y - i).isOccupied()) {
						moves.add(new Move(x, y, x - i, y - i));
					} else {
						// not possible to push
						break;
					}
				}
			}
		}
		return moves;
	}

	/**
	 * @param b
	 *            Board
	 * @param x
	 *            x location of piece
	 * @param y
	 *            y location of piece
	 * @return
	 */
	static public boolean valid(int x, int y) {
		if (x < 0 || x > 7 || y < 0 || y > 7)
			return false;
		else
			return true;
	}

	public PlayerType getPlayerType() {
		return this.playerType;
	}

	public Type getType() {
		return this.type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setGems(int gems) {
		this.gems = gems;
	}

	public int getGems() {
		return this.gems;
	}

	/**
	 * For the possible Moves size
	 * 
	 * @param tower
	 * @return number of possible moves
	 */
	public int getGems(Tower tower) {
		switch (tower.getGems()) {
		case 0:
			// normal Tower
			return 8;
		case 1:
			// sumo Tower with one Gems
			return 5;
		case 2:
			// sumo Tower with two Gems
			return 4;
		case 3:
			// sumo Tower with tree Gems
			return 3;
		case 4:
			// sumo Tower with four Gems
			return 2;
		case 5:
			// sumo Tower with five Gems
			return 1;
		}
		return gems;
	}
}
