package AI;
import java.util.ArrayList;
import java.util.Random;

public class Board {
	public static final int a = 0, b = 1, c = 2, d = 3, e = 4, f = 5, g = 6, h = 7;
	private Tile[][] board;
	private Move lastMove = null;
	Random rand = new Random();
	private MiniMaxAlphaBeta minimax;

	public Board(Tile[][] board) {
		this.board = board;
	}

	public Board() {
		board = new Tile[8][8];
		// First Line from Bottom
		board[a][1 - 1] = new Tile(new Tower(Color.Brown, Type.normalTower, PlayerType.ONE,0), Color.Brown);
		board[b][1 - 1] = new Tile(new Tower(Color.Green, Type.normalTower, PlayerType.ONE,0), Color.Green);
		board[c][1 - 1] = new Tile(new Tower(Color.Red, Type.normalTower, PlayerType.ONE,0), Color.Red);
		board[d][1 - 1] = new Tile(new Tower(Color.Yellow, Type.normalTower, PlayerType.ONE,0), Color.Yellow);
		board[e][1 - 1] = new Tile(new Tower(Color.Pink, Type.normalTower, PlayerType.ONE,0), Color.Pink);
		board[f][1 - 1] = new Tile(new Tower(Color.Purple, Type.normalTower, PlayerType.ONE,0), Color.Purple);
		board[g][1 - 1] = new Tile(new Tower(Color.Blue, Type.normalTower, PlayerType.ONE,0), Color.Blue);
		board[h][1 - 1] = new Tile(new Tower(Color.Orange, Type.normalTower, PlayerType.ONE,0), Color.Orange);

		// 2 Line from Bottom
		board[a][2 - 1] = new Tile(Color.Purple);
		board[b][2 - 1] = new Tile(Color.Brown);
		board[c][2 - 1] = new Tile(Color.Yellow);
		board[d][2 - 1] = new Tile(Color.Blue);
		board[e][2 - 1] = new Tile(Color.Green);
		board[f][2 - 1] = new Tile(Color.Pink);
		board[g][2 - 1] = new Tile(Color.Orange);
		board[h][2 - 1] = new Tile(Color.Red);

		// 3 Line from Bottom
		board[a][3 - 1] = new Tile(Color.Blue);
		board[b][3 - 1] = new Tile(Color.Yellow);
		board[c][3 - 1] = new Tile(Color.Brown);
		board[d][3 - 1] = new Tile(Color.Purple);
		board[e][3 - 1] = new Tile(Color.Red);
		board[f][3 - 1] = new Tile(Color.Orange);
		board[g][3 - 1] = new Tile(Color.Pink);
		board[h][3 - 1] = new Tile(Color.Green);

		// 4 Line from Bottom
		board[a][4 - 1] = new Tile(Color.Yellow);
		board[b][4 - 1] = new Tile(Color.Red);
		board[c][4 - 1] = new Tile(Color.Green);
		board[d][4 - 1] = new Tile(Color.Brown);
		board[e][4 - 1] = new Tile(Color.Orange);
		board[f][4 - 1] = new Tile(Color.Blue);
		board[g][4 - 1] = new Tile(Color.Purple);
		board[h][4 - 1] = new Tile(Color.Pink);

		// 4 Line from Top
		board[a][5 - 1] = new Tile(Color.Pink);
		board[b][5 - 1] = new Tile(Color.Purple);
		board[c][5 - 1] = new Tile(Color.Blue);
		board[d][5 - 1] = new Tile(Color.Orange);
		board[e][5 - 1] = new Tile(Color.Brown);
		board[f][5 - 1] = new Tile(Color.Green);
		board[g][5 - 1] = new Tile(Color.Red);
		board[h][5 - 1] = new Tile(Color.Yellow);

		// 3 Line from Top
		board[a][6 - 1] = new Tile(Color.Green);
		board[b][6 - 1] = new Tile(Color.Pink);
		board[c][6 - 1] = new Tile(Color.Orange);
		board[d][6 - 1] = new Tile(Color.Red);
		board[e][6 - 1] = new Tile(Color.Purple);
		board[f][6 - 1] = new Tile(Color.Brown);
		board[g][6 - 1] = new Tile(Color.Yellow);
		board[h][6 - 1] = new Tile(Color.Blue);

		// 2 Line from Top
		board[a][7 - 1] = new Tile(Color.Red);
		board[b][7 - 1] = new Tile(Color.Orange);
		board[c][7 - 1] = new Tile(Color.Pink);
		board[d][7 - 1] = new Tile(Color.Green);
		board[e][7 - 1] = new Tile(Color.Blue);
		board[f][7 - 1] = new Tile(Color.Yellow);
		board[g][7 - 1] = new Tile(Color.Brown);
		board[h][7 - 1] = new Tile(Color.Purple);

		// First Line from Top
		board[a][8 - 1] = new Tile(new Tower(Color.Orange, Type.normalTower, PlayerType.TWO,0), Color.Orange);
		board[b][8 - 1] = new Tile(new Tower(Color.Blue, Type.normalTower, PlayerType.TWO,0), Color.Blue);
		board[c][8 - 1] = new Tile(new Tower(Color.Purple, Type.normalTower, PlayerType.TWO,0), Color.Purple);
		board[d][8 - 1] = new Tile(new Tower(Color.Pink, Type.normalTower, PlayerType.TWO,0), Color.Pink);
		board[e][8 - 1] = new Tile(new Tower(Color.Yellow, Type.normalTower, PlayerType.TWO,0), Color.Yellow);
		board[f][8 - 1] = new Tile(new Tower(Color.Red, Type.normalTower, PlayerType.TWO,0), Color.Red);
		board[g][8 - 1] = new Tile(new Tower(Color.Green, Type.normalTower, PlayerType.TWO,0), Color.Green);
		board[h][8 - 1] = new Tile(new Tower(Color.Brown, Type.normalTower, PlayerType.TWO,0), Color.Brown);
	}

	@Override
	public String toString() {
		String str = "";
		for (int i = 7; i >= 0; i--) {
			str += (i + 1) + "  ";
			for (int j = 0; j < 8; j++) {
				str += board[j][i] + " ";
			}
			str += "\n";
		}

		str += "\n   a b c d e f g h";

		return str;
	}

	private Tile getTileColor(int x, int y) {
		return board[x][y];
	}

	public Tile getTile(int x, int y) {
		return board[x][y];
	}

	public ArrayList<Move> topBottomList() {
		// List for Bottom or Top Line
		ArrayList<Move> topBottomLine = new ArrayList<Move>();

		// bottom
		for (int i = 0; i < 8; i++) {
			topBottomLine.add(new Move(i, 1));
		}

		// top
		for (int i = 0; i < 8; i++) {
			topBottomLine.add(new Move(i, 8));
		}
		return topBottomLine;
	}
	
	// split the list in top or bottom
	// true for topList, false for bottomList
	public ArrayList<Move> splitTopBottomList(ArrayList<Move> topBottomList, boolean topBottom){
		ArrayList<Move> bottomList = new ArrayList<Move>();
		ArrayList<Move> topList = new ArrayList<Move>();		
		
		for (int i = 0; i < topBottomList.size(); i++) {
			if (topBottomList.get(i).getY1() == 1) {
				bottomList.add(topBottomList.get(i));
			} else {
				topList.add(topBottomList.get(i));
			}
		}
		if(topBottom == true){
			return topList;
		} else {
			return bottomList;
		}
	}

	public Move makeMove(Move move) {
		Tile oldTile = board[move.getX1()][move.getY1()];
		Tile goalTile = board[move.getX2()][move.getY2()];

		if(!goalTile.isOccupied()){
			board[move.getX2()][move.getY2()] = new Tile(oldTile.getTower(), board[move.getX2()][move.getY2()].getColor());
			board[move.getX1()][move.getY1()] = new Tile(board[move.getX1()][move.getY1()].getColor());
			System.out.println(getTempBoard());

			if (move.isProv() != true) {
				board[move.getX1()][move.getY1()] = oldTile;
				board[move.getX2()][move.getY2()] = new Tile(board[move.getX2()][move.getY2()].getColor()); // restore
																											// all
			} else {
				// save the last Move when its effectiv done
				lastMove = new Move(move.getX1(), move.getY1(), move.getX2(), move.getY2());
			}

			return new Move(move.getX1(), move.getY1(), move.getX2(), move.getY2());
		} else {
			System.err.println("***************** SUMO-PUSH **********************");
			System.err.println("The move was to an other tower -> must be an Sumo");
			System.err.println(board[move.getX1()][move.getY1()].getTower() + " -->> " + board[move.getX2()][move.getY2()].getTower());
			System.err.println("Move from: " + move.getX1() + " " + move.getY1() + " Move to: " + move.getX2() + " " + move.getY2());
			System.err.println("***************** SUMO-PUSH **********************");
			
			// make the move 
			board[move.getX2()][move.getY2()] = new Tile(oldTile.getTower(), board[move.getX2()][move.getY2()].getColor());
			// set the beginner Tile back 
			board[move.getX1()][move.getY1()] = new Tile(board[move.getX1()][move.getY1()].getColor());
			//make the sumo push
			if(move.getY1()< move.getY2()){
				//push Nord
				board[move.getX2()][move.getY2()+1] = new Tile(goalTile.getTower(), board[move.getX2()][move.getY2()+1].getColor());
			} else {
				//push South
				board[move.getX2()][move.getY2()-1] = new Tile(goalTile.getTower(), board[move.getX2()][move.getY2()-1].getColor());
			}

			
			System.out.println(getTempBoard());
			
		}
		return move;

	}

	private Move getARandomMove(PlayerType playerType) {
		ArrayList<Move> randomMoves = getAllMoves(playerType);
		int random = rand.nextInt(randomMoves.size());
		return randomMoves.get(random);
	}

	public Move checkIsWinChance(PlayerType playerType, Tower tower) {
		if (playerType == PlayerType.TWO) {
			for (int i = 0; i < 8; i++) {
				if (!this.board[i][1 - 1].isOccupied() && this.board[i][1].getColor() == tower.getColor()) {
					return new Move(i, 1);
				}
			}
		} else {
			for (int i = 0; i < 8; i++) {
				if (!this.board[i][8 - 1].isOccupied() && this.board[i][8].getColor() == tower.getColor()) {
					return new Move(i, 8);
				}
			}
		}
		return null;
	}

	public Board getTempBoard() {
		Tile[][] temp = new Tile[8][8];

		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				temp[x][y] = new Tile(this.board[x][y], this.board[x][y].getColor());
			}
		}

		return new Board(temp);
	}

	public ArrayList<Move> getAllMoves(PlayerType playerType) {
		ArrayList<Move> allPossibleMoves = new ArrayList<Move>();

		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				// pick a own tower && check ithe possible Move with it
				if (board[y][x].isOccupied() && board[y][x].getTower().getPlayerType() == playerType) {
					allPossibleMoves.addAll(board[y][x].getTower().getMoves(this, y, x));
				}
			}
		}
		return allPossibleMoves;
	}

	//when none of the Player made a move before
	public void firstMove(PlayerType playerType) {

		ArrayList<Move> thisPlayerMoves = new ArrayList<Move>();

		thisPlayerMoves = getAllMoves(playerType);
		int randomPick = rand.nextInt(thisPlayerMoves.size());
		Move move = new Move(thisPlayerMoves.get(randomPick), true);
		makeMove(move);
	}

	//calling by the buildHeuristic and decision class for getting the possible Moves
	public ArrayList<Move> getNextPossibleMoves(PlayerType playerType) {
		ArrayList<Move> thisPlayerMoves = new ArrayList<Move>();

		// Check this players moves
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				// pick a own tower && check the possible Moves with it
				// the tile must be occupied && the same playerType && the color
				// of the new Tower must have the same color like the last-Move
				// Tile color
				if (board[y][x].isOccupied() && board[y][x].getTower().getPlayerType() == playerType
						&& board[y][x].getTower().getColor() == board[lastMove.getX2()][lastMove.getY2()].getColor()) {
					thisPlayerMoves.addAll(board[y][x].getTower().getMoves(this, y, x));
				}
			}
		}
		if(thisPlayerMoves.isEmpty()){
			//there is no move less for this tower - patt-Situation
			//like the rules is now the turn for the other player with the same tower-color
			System.err.println("No more moves for the tower: " + board[lastMove.getX2()][lastMove.getY2()].getTower() + " X: " + lastMove.getX2() + " Y: " + lastMove.getY2());
			thisPlayerMoves = getNextPossibleMoves(changePlayerType(playerType));
		}

		return thisPlayerMoves;
	}

	// calling by the maxValue and the minValue in the MiniMaxAlphaBeta-Pruning
	public ArrayList<Move> getNextPossibleMoves(PlayerType playerType, ArrayList<Move> moves) {

		ArrayList<Move> possibleMoves = new ArrayList<Move>();

		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				// pick a own tower && check the possible Moves with it
				// the tile must be occupied && the same playerType && the color
				// of the new Tower must have the same color like the last-Move
				// Tile color
				if (board[x][y].isOccupied() && board[x][y].getTower().getPlayerType() == playerType
						&& board[x][y].getTower().getColor() == board[lastMove.getX2()][lastMove.getY2()].getColor()) {
					possibleMoves.addAll(board[x][y].getTower().getMoves(this, x, y));
				}
			}
		}
		return possibleMoves;
	}

	public ArrayList<Move> getNextPossibleMovesWithOneMove(PlayerType playerType, Move move) {
		ArrayList<Move> moves = new ArrayList<Move>();

		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				if (board[x][y].isOccupied() && board[x][y].getTower().getPlayerType() == playerType
						&& board[x][y].getTower().getColor() == board[move.getX2()][move.getY2()].getColor()) {
					moves.addAll(board[x][y].getTower().getMoves(this, x, y));
				}
			}
		}
		return moves;
	}

	public boolean isWinSituation() {
		boolean winning = false;
		ArrayList<Move> topBottomList = topBottomList();
		ArrayList<Move> bottomList = splitTopBottomList(topBottomList,false);
		ArrayList<Move> topList = splitTopBottomList(topBottomList,true);

		
		// is the Tile occupied && is the PlayerType in the opposite then is a
		// winning Situation
		for (int j = 0; j < topList.size(); j++) {
			if (board[topList.get(j).getX1()][topList.get(j).getY1()-1].isOccupied()
					&& board[topList.get(j).getX1()][topList.get(j).getY1()-1].getTower()
							.getPlayerType() == PlayerType.ONE) {
				winning = true;
				// also update the tower
				upgradeTower(board[topList.get(j).getX1()][topList.get(j).getY1()-1].getTower());
			}
		}
		for (int i = 0; i < bottomList.size(); i++) {
			if (board[bottomList.get(i).getX1()][bottomList.get(i).getY1()-1].isOccupied()
					&& board[bottomList.get(i).getX1()][bottomList.get(i).getY1()-1].getTower()
							.getPlayerType() == PlayerType.TWO) {
				winning = true;
				//also update the tower
				System.err.println("Upgrade now: " + board[bottomList.get(i).getX1()][bottomList.get(i).getY1()-1].getTower());
				System.err.println("Position: " + bottomList.get(i).getX1() + " " + bottomList.get(i).getY1());
				upgradeTower(board[bottomList.get(i).getX1()][bottomList.get(i).getY1()-1].getTower());					
			}
		}
		return winning;

	}
	
	/**
	 * for update the tower
	 * @param tower
	 */
	public void upgradeTower(Tower tower){
		// is it a normal Tower - then upgrade and get one gem
		System.out.println(tower.getType());
		if(tower.getType() == Type.normalTower){
			tower.setType(Type.sumoTower);
			tower.setGems(1);
		} else {
			// the tower is a sumoTower so we can give him only more gems
			int gems = tower.getGems();
			tower.setGems(gems++);
		}
	}
	

	/**
	 * for the f1 heuristic number of vertical moves of the tower
	 * 
	 * @param move
	 * @param playerType
	 * @return vertical moves
	 */
	public int numberOfMovesVertical(Move move, PlayerType playerType) {
		int vertical;

		if (playerType == PlayerType.ONE) {
			vertical = move.getY2();
		} else {
			vertical = 7 - move.getY2();
		}

		return vertical;
	}

	/**
	 * For the f2 heuristic number of possible moves
	 * 
	 * @param moves
	 * @return
	 */
	public int numberOfPossibleMoves(PlayerType playerType, Move move) {
		ArrayList<Move> movesTemp = new ArrayList<Move>();

		movesTemp = getNextPossibleMovesWithOneMove(playerType, move);

		return movesTemp.size();
	}

	/**
	 * For the f3 heuristic Moves that possible to win
	 * 
	 * @param moves
	 * @return
	 */
	public int isItAWinningMove(Move move) {
		ArrayList<Move> topBottomList = topBottomList();

		// is the Move in the top or Bottom line?
		for(int i = 0; i < topBottomList.size(); i++){
			if(topBottomList.get(i).getX1() == move.getX2() && topBottomList.get(i).getY1()-1 == move.getY2()){
				return 15;
			} else {
				return 0;
			}
		}
		return 0;
	}

	/**
	 * For the f4 heuristic Move that possible to block
	 * 
	 * @param playerType
	 * @param move
	 * @return
	 */
	public int isABlockMove(PlayerType playerType, Move move) {
		ArrayList<Move> blockMove = new ArrayList<Move>();
		ArrayList<Move> nextPossibleMoves = getNextPossibleMovesWithOneMove(playerType, move);
		ArrayList<Move> topBottomLine = topBottomList();
		ArrayList<Move> possibleMovesForThisTower = board[move.getX1()][move.getY1()].getTower()
				.getMoves(getTempBoard(), move.getX1(), move.getY1());

		for (int i = 0; i < nextPossibleMoves.size(); i++) {
			// is one of the nextPossibleMoves in the topBottomList
			if (topBottomLine.containsAll(nextPossibleMoves)) {
				// is the Goal for the nextPossibleMoves free
				if (!board[nextPossibleMoves.get(i).getX2()][nextPossibleMoves.get(i).getY2()].isOccupied()) {
					// is this move between the next Possible Moves
					if (board[nextPossibleMoves.get(i).getX2()][nextPossibleMoves.get(i)
							.getY2()] == board[possibleMovesForThisTower.get(i).getX2()][possibleMovesForThisTower
									.get(i).getY2()]) {
						return 10;
					}
				}
			}
		}
		return 0;

	}

	// Check is one Tile free for the other Player when doing the Move
	public ArrayList<Move> removeMoves(PlayerType playerType, Move move) {
		ArrayList<Move> removeThis = new ArrayList<Move>();

		// List for Bottom or Top Line
		ArrayList<Move> topBottomLine = topBottomList();
		ArrayList<Move> nextPossibleMoves = getNextPossibleMovesWithOneMove(playerType, move);

		for (int i = 0; i < nextPossibleMoves.size(); i++) {
			// is the start-position then empty
			// is the move in the top or bottom line
			// is the start-tile the same like the goal-tile after the move
			if (!board[move.getX1()][move.getY1()].isOccupied()
					&& !board[move.getX1()][move.getY1()].equals(topBottomLine) && board[move.getX1()][move
							.getY1()] == board[nextPossibleMoves.get(i).getX2()][nextPossibleMoves.get(i).getY2()]) {
				removeThis.add(move);
			}
		}
		return removeThis;
	}
	
	public void newRound(PlayerType playerType, NewRound newRound){
		ArrayList<Tile> bottomTiles = new ArrayList<Tile>();
		ArrayList<Tile> topTiles = new ArrayList<Tile>();
		ArrayList<Tower> towerListPlayerONE = new ArrayList<Tower>();
		ArrayList<Tower> towerListPlayerTWO = new ArrayList<Tower>();
		
		//need the Tiles from the board
		for(int i = 0; i < 8; i++){
			bottomTiles.add(board[i][0]);
			topTiles.add(board[i][7]);
		}
		
		if(newRound == NewRound.Left){
			//the choice is left
			for (int x = 0; x < 8; x++){
				for (int y = 0; y < 8; y++){
					// is on this Tile a Tower?
					if(board[x][y].hasTower(board, x, y)){
						//is this tower a Player.ONE or a Player.TWO
						if(board[x][y].getTower().getPlayerType() == PlayerType.ONE){
							//its a Player.ONE && set the Tile back without a tower
							towerListPlayerONE.add(board[x][y].getTower());
							board[x][y] = new Tile(board[x][y].getColor());
						} else {
							//its a Player.TWO && set the Tile back without a tower
							towerListPlayerTWO.add(board[x][y].getTower());
							board[x][y] = new Tile(board[x][y].getColor());
						}
					}
				}
			}
			// fill the towers
			for(int i = 0; i < 8; i++){
				// this is the bottomLine
				board[i][0] = new Tile(towerListPlayerONE.get(i), bottomTiles.get(i).getColor());
				// this is for the topLine
				board[i][7] = new Tile(towerListPlayerTWO.get(i), topTiles.get(i).getColor());
			}
		} else {
			//the choice is right
			for(int y = 8; y > 0; y--){
				for(int x = 0; x > 0; x--){
					// is on this Tile a Tower?
					if(board[x][y].hasTower(board, x, y)){
						//is this tower a Player.ONE or a Player.TWO
						if(board[x][y].getTower().getPlayerType() == PlayerType.ONE){
							//its a Player.ONE && set the Tile back without a tower
							towerListPlayerONE.add(board[x][y].getTower());
							board[x][y] = new Tile(board[x][y].getColor());
						} else {
							//its a Player.TWO && set the Tile back without a tower
							towerListPlayerTWO.add(board[x][y].getTower());
							board[x][y] = new Tile(board[x][y].getColor());
						}
					}
				}
			}
			// fill the towers
			for(int i = 0; i < 8; i++){
				// this is the bottomLine
				board[i][0] = new Tile(towerListPlayerONE.get(i), bottomTiles.get(i).getColor());
				// this is for the topLine
				board[i][7] = new Tile(towerListPlayerTWO.get(i), topTiles.get(i).getColor());
			}
		}
	}
	
	public static PlayerType changePlayerType(PlayerType playerType) {
		if (playerType == PlayerType.ONE) {
			playerType = PlayerType.TWO;
		} else {
			playerType = PlayerType.ONE;
		}
		return playerType;
	}
}
