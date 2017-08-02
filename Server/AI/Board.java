package AI;

import java.util.ArrayList;
import java.util.Random;

import Message.Message;
import Message.Message.MessageType;
import Server.ServerThreadForClient;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;

public class Board {
	public static final int a = 0, b = 1, c = 2, d = 3, e = 4, f = 5, g = 6, h = 7;
	private Tile[][] board;
	private Move lastMove = null;
	Random rand = new Random();
	private MiniMaxAlphaBeta minimax;
	private static Board boardSingleton;
	private static boolean wantDoubleAI;
	private static boolean wantAI;
	
	private int gems;
	private int xCoordinationUpgrade;
	private int yCoordinationUpgrade;
	
	public SimpleBooleanProperty upgrade = new SimpleBooleanProperty();

	// these are here, for optimising the code, don't build every time new -
	// it's always the same top or bottom Line
	ArrayList<Move> topBottomLine = topBottomList();
	ArrayList<Move> bottomLine = splitTopBottomList(topBottomLine, false);
	ArrayList<Move> topLine = splitTopBottomList(topBottomLine, true);
	
	public static Board getBoard() {
		if(boardSingleton == null){
			boardSingleton = new Board();
		}
		return boardSingleton;
	}

	public Board(Tile[][] board) {
		this.board = board;
	}

	public Board() {
		board = new Tile[8][8];
		// First Line from Bottom
		board[a][1 - 1] = new Tile(new Tower(Color.Brown, Type.normalTower, PlayerType.ONE, 0), Color.Brown);
		board[b][1 - 1] = new Tile(new Tower(Color.Green, Type.normalTower, PlayerType.ONE, 0), Color.Green);
		board[c][1 - 1] = new Tile(new Tower(Color.Red, Type.normalTower, PlayerType.ONE, 0), Color.Red);
		board[d][1 - 1] = new Tile(new Tower(Color.Yellow, Type.normalTower, PlayerType.ONE, 0), Color.Yellow);
		board[e][1 - 1] = new Tile(new Tower(Color.Pink, Type.normalTower, PlayerType.ONE, 0), Color.Pink);
		board[f][1 - 1] = new Tile(new Tower(Color.Purple, Type.normalTower, PlayerType.ONE, 0), Color.Purple);
		board[g][1 - 1] = new Tile(new Tower(Color.Blue, Type.normalTower, PlayerType.ONE, 0), Color.Blue);
		board[h][1 - 1] = new Tile(new Tower(Color.Orange, Type.normalTower, PlayerType.ONE, 0), Color.Orange);

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
		board[a][8 - 1] = new Tile(new Tower(Color.Orange, Type.normalTower, PlayerType.TWO, 0), Color.Orange);
		board[b][8 - 1] = new Tile(new Tower(Color.Blue, Type.normalTower, PlayerType.TWO, 0), Color.Blue);
		board[c][8 - 1] = new Tile(new Tower(Color.Purple, Type.normalTower, PlayerType.TWO, 0), Color.Purple);
		board[d][8 - 1] = new Tile(new Tower(Color.Pink, Type.normalTower, PlayerType.TWO, 0), Color.Pink);
		board[e][8 - 1] = new Tile(new Tower(Color.Yellow, Type.normalTower, PlayerType.TWO, 0), Color.Yellow);
		board[f][8 - 1] = new Tile(new Tower(Color.Red, Type.normalTower, PlayerType.TWO, 0), Color.Red);
		board[g][8 - 1] = new Tile(new Tower(Color.Green, Type.normalTower, PlayerType.TWO, 0), Color.Green);
		board[h][8 - 1] = new Tile(new Tower(Color.Brown, Type.normalTower, PlayerType.TWO, 0), Color.Brown);
	}

	/** *************************************************************************************************************************************** **/

	/**
	 * GamePlay-Methods
	 * 
	 * @author
	 */

	/**
	 * Do a Move
	 * 
	 * @author L.Weber
	 * @param move
	 * @return Move
	 */
	public Move makeMove(Move move) {
		Tile oldTile = board[move.getX1()][move.getY1()];
		Tile goalTile = board[move.getX2()][move.getY2()];

		if (!goalTile.isOccupied()) {
			board[move.getX2()][move.getY2()] = new Tile(oldTile.getTower(),
					board[move.getX2()][move.getY2()].getColor());
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
			// print the PUSH out
			getSumoMessage(move);
			// make the move
			board[move.getX2()][move.getY2()] = new Tile(oldTile.getTower(),
					board[move.getX2()][move.getY2()].getColor());
			// set the beginner Tile back
			board[move.getX1()][move.getY1()] = new Tile(board[move.getX1()][move.getY1()].getColor());
			// make the sumo push
			if (move.getY1() < move.getY2()) {
				// push Nord
				board[move.getX2()][move.getY2() + 1] = new Tile(goalTile.getTower(),
						board[move.getX2()][move.getY2() + 1].getColor());
				// when the Sumo-Push is done the Player that PUSH has the next
				// try with the color that the opposite tower was pushed
				makeMove(minimax.decision(getTempBoard(), oldTile.getTower().getPlayerType()));
				System.err.println("The same Player get a new Move " + lastMove);
			} else {
				// push South
				board[move.getX2()][move.getY2() - 1] = new Tile(goalTile.getTower(),
						board[move.getX2()][move.getY2() - 1].getColor());
				// when the Sumo-Push is done the Player that PUSH has the next
				// try with the color that the opposite tower was pushed
				makeMove(minimax.decision(getTempBoard(), oldTile.getTower().getPlayerType()));
				System.err.println("The same Player get a new Move " + lastMove);
			}
			System.out.println(getTempBoard());

		}
		return move;

	}

	/**
	 * Get all Moves that possible for this Player and is the right color
	 * 
	 * @author L.Weber
	 * @param playerType
	 * @return ArrayList<Move> for the possible Moves
	 */
	public ArrayList<Move> getAllMoves(PlayerType playerType) {
		ArrayList<Move> allPossibleMoves = new ArrayList<Move>();

		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				// pick a own tower && check ithe possible Move with it
				if (board[x][y].isOccupied() && board[x][y].getTower().getPlayerType() == playerType) {
					allPossibleMoves.addAll(board[x][y].getTower().getMoves(this, x, y));
				}
			}
		}
		return allPossibleMoves;
	}

	/**
	 * When none of the Player made a move before Do the Move direct
	 * 
	 * @author L.Weber
	 * @param playerType
	 * @return 
	 */
	public Move firstMove(PlayerType playerType) {

		ArrayList<Move> thisPlayerMoves = new ArrayList<Move>();

		thisPlayerMoves = getAllMoves(playerType);
		int randomPick = rand.nextInt(thisPlayerMoves.size());
		Move move = new Move(thisPlayerMoves.get(randomPick), true);
		int player;
		if(playerType == PlayerType.ONE) {
			player = 2;
		} else {
			player = 1;
		}
		if(this.wantDoubleAI) {
			ServerThreadForClient.sendMessageBackToClient(new Message(MessageType.Coordinate,move.getX1(),move.getY1(),move.getX2(),move.getY2(), player));
		}
		return makeMove(move);
	}

	/**
	 * Check is this a winning Situation for the while-loop
	 * 
	 * @author L.Weber
	 * @return
	 */
	public boolean isWinSituation() {
		boolean winning = false;

		// is the Tile occupied && is the PlayerType in the opposite then is a
		// winning Situation
		for (int j = 0; j < topLine.size(); j++) {
			if (board[topLine.get(j).getX1()][topLine.get(j).getY1() - 1].isOccupied()
					&& board[topLine.get(j).getX1()][topLine.get(j).getY1() - 1].getTower()
							.getPlayerType() == PlayerType.ONE) {
				winning = true;
				// also update the tower
				if(this.getDoubleAI()) {
					//This is for the Double-AI-Game - one way direction - send always upgrades
					upgradeTower(board[topLine.get(j).getX1()][topLine.get(j).getY1() - 1].getTower());
					//is there no human player this massage must send
					int player;
					if(board[topLine.get(j).getX1()][topLine.get(j).getY1() - 1].getTower().getPlayerType() == PlayerType.ONE) {
						player = 2;
					} else {
						player = 1;
					}
					ServerThreadForClient.sendMessageBackToClient(new Message(MessageType.Update,topLine.get(j).getX1(),(topLine.get(j).getY1()-1),board[topLine.get(j).getX1()][(topLine.get(j).getY1()-1)].getTower().getGems(), player));
					
				} else if(this.getWantAI()) {
					//This is for a Single-AI-Game the towers has to upgrade ond we has to send the message back to the client
					upgradeTower(board[topLine.get(j).getX1()][topLine.get(j).getY1() - 1].getTower());
					this.newRound(NewRound.Right);
					//the sending for update and new round is in the ServerThreadForClient
				}
				this.xCoordinationUpgrade = topLine.get(j).getX1();
			    this.yCoordinationUpgrade = topLine.get(j).getY1() - 1;
			}
		}
		for (int i = 0; i < bottomLine.size(); i++) {
			if (board[bottomLine.get(i).getX1()][bottomLine.get(i).getY1() - 1].isOccupied()
					&& board[bottomLine.get(i).getX1()][bottomLine.get(i).getY1() - 1].getTower()
							.getPlayerType() == PlayerType.TWO) {
				winning = true;
				// also update the tower
				System.err.println(
						"Upgrade now: " + board[bottomLine.get(i).getX1()][bottomLine.get(i).getY1() - 1].getTower());
				System.err.println("Position: " + (bottomLine.get(i).getX1()) + " " + (bottomLine.get(i).getY1() - 1));
				if(this.getDoubleAI()) {
					upgradeTower(board[bottomLine.get(i).getX1()][bottomLine.get(i).getY1() - 1].getTower());
					
					int player;
					if(board[bottomLine.get(i).getX1()][bottomLine.get(i).getY1() - 1].getTower().getPlayerType() == PlayerType.ONE) {
						player = 2;
					} else {
						player = 1;
					}
					ServerThreadForClient.sendMessageBackToClient(new Message(MessageType.Update,bottomLine.get(i).getX1(),(bottomLine.get(i).getY1()-1),board[bottomLine.get(i).getX1()][(bottomLine.get(i).getY1()-1)].getTower().getGems(), player));
				}
				if(this.wantAI) {
					upgradeTower(board[bottomLine.get(i).getX1()][bottomLine.get(i).getY1() - 1].getTower());
					
					int player;
					if(board[bottomLine.get(i).getX1()][bottomLine.get(i).getY1() - 1].getTower().getPlayerType() == PlayerType.ONE) {
						player = 2;
					} else {
						player = 1;
					}
					ServerThreadForClient.sendMessageBackToClient(new Message(MessageType.Update,bottomLine.get(i).getX1(),(bottomLine.get(i).getY1()-1),board[bottomLine.get(i).getX1()][(bottomLine.get(i).getY1()-1)].getTower().getGems(), player));
				}
				this.xCoordinationUpgrade = bottomLine.get(i).getX1();
				this.yCoordinationUpgrade = bottomLine.get(i).getY1() -1;
			}
		}
		return winning;

	}

	/**
	 * for update the tower
	 * 
	 * @author L.Weber
	 * @param tower
	 */
	public void upgradeTower(Tower tower) {
		// is it a normal Tower - then upgrade and get one gem
		System.out.println(tower.getType());
		if (tower.getType() == Type.normalTower) {
			tower.setType(Type.sumoTower);
			tower.setGems(1);
		} else {
			// the tower is a sumoTower so we can give him only more gems
			int gems = tower.getGems();
			tower.setGems(++gems);
		}
		this.gems = tower.getGems();
		this.setUpgrade(true);
		System.out.println(getTempBoard());
	}

	/** *************************************************************************************************************************************** **/

	/**
	 * Heuristic-Methods
	 * 
	 * @author L.Weber
	 */

	/**
	 * Heuristic-Method Get the possible Moves calling by the buildHeuristic and
	 * decision class
	 * 
	 * @author L.Weber
	 * @param playerType
	 * @return ArrayList<Move>
	 */
	public ArrayList<Move> getNextPossibleMoves(PlayerType playerType) {
		ArrayList<Move> thisPlayerMoves = new ArrayList<Move>();

		// Check this players moves
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				// pick a own tower && check the possible Moves with it
				// the tile must be occupied && the same playerType && the color
				// of the new Tower must have the same color like the last-Move
				// Tile color
				if (board[x][y].isOccupied() && board[x][y].getTower().getPlayerType() == playerType
						&& board[x][y].getTower().getColor() == board[lastMove.getX2()][lastMove.getY2()].getColor()) {
					thisPlayerMoves.addAll(board[x][y].getTower().getMoves(this, x, y));
				}
			}
		}
		if (thisPlayerMoves.isEmpty()) {
			// there is no move less for this tower - patt-Situation
			// like the rules is now the turn for the other player with the same
			// tower-color
			System.err.println("No more moves for the tower: " + board[lastMove.getX2()][lastMove.getY2()].getTower()
					+ " X: " + lastMove.getX2() + " Y: " + lastMove.getY2());
			System.err.print(getTempBoard());
			if(this.getDoubleAI()) {
				//if there no human player - change the player
				thisPlayerMoves = getNextPossibleMoves(changePlayerType(playerType));
			}
			if(this.getWantAI()) {
				//For Single-Player - send a zero-Message with Player 9 to send a message to the client, thats now a pat-situtaion
				ServerThreadForClient.sendMessageBackToClient(new Message(MessageType.Coordinate,0,0,0,0,9));
			}

		}

		return thisPlayerMoves;
	}

	/**
	 * Heuristic-Method Is for check with the Algorithm MniMax Alpha Beta
	 * calling by the maxValue and the minValue in the MiniMaxAlphaBeta-Pruning
	 * 
	 * @author L.Weber
	 * @param playerType
	 * @param moves
	 * @return
	 */
	public ArrayList<Move> getNextPossibleMoves(PlayerType playerType, ArrayList<Move> moves) {

		ArrayList<Move> possibleMoves = new ArrayList<Move>();

		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				// pick a own tower && check the possible Moves with it
				// the tile must be occupied && the same playerType && the color
				// of the new Tower must have the same color like the last-Move
				// Tile color
				if (board[x][y].isOccupied()) {
					if (board[x][y].getTower().getPlayerType() == playerType && board[x][y].getTower()
							.getColor() == board[lastMove.getX2()][lastMove.getY2()].getColor()) {
						possibleMoves.addAll(board[x][y].getTower().getMoves(this, x, y));
					}

				}
			}
		}
		return possibleMoves;
	}

	/**
	 * Heuristic-Method called by isBlockMove, numberOfPossibleMoves,
	 * removeMoves Give the Moves beginning from one move
	 * 
	 * @author L.Weber
	 * @param playerType
	 * @param move
	 * @return ArrayList<Move>
	 */
	public ArrayList<Move> getNextPossibleMovesWithOneMove(PlayerType playerType, Move move) {
		ArrayList<Move> moves = new ArrayList<Move>();

		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				if (board[x][y].isOccupied()) {
					if (board[x][y].getTower().getPlayerType() == playerType
							&& board[x][y].getTower().getColor() == board[move.getX2()][move.getY2()].getColor()) {
						moves.addAll(board[x][y].getTower().getMoves(this, x, y));
					}

				}
			}
		}
		return moves;
	}

	/**
	 * Heuristic-Method called by isBlockMove, numberOfPossibleMoves,
	 * removeMoves Give the Moves beginning from one move without a block from
	 * the opposite Tower
	 * 
	 * @author L.Weber
	 * @param playerType
	 * @param move
	 * @return ArrayList<Move>
	 */
	public ArrayList<Move> getNextPossibleMovesWithOneMoveIgnore(PlayerType playerType, Move move) {
		ArrayList<Move> moves = new ArrayList<Move>();

		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				if (board[x][y].isOccupied()) {
					if (board[x][y].getTower().getPlayerType() == playerType
							&& board[x][y].getTower().getColor() == board[move.getX2()][move.getY2()].getColor()) {
						moves.addAll(board[x][y].getTower().getMovesIgnore(this, x, y));
					}
				}
			}
		}
		return moves;
	}

	/**
	 * Heuristic-Method for the f1 heuristic number of vertical moves of the
	 * tower
	 * 
	 * @author L.Weber
	 * @param move
	 * @param playerType
	 * @return vertical moves
	 */
	public double numberOfMovesVertical(Move move, PlayerType playerType, double weightVertical) {
		double vertical;
		double result;

		if (playerType == PlayerType.ONE) {
			vertical = move.getY2();
		} else {
			vertical = 7 - move.getY2();
		}
		result = vertical / 100 * weightVertical;
		return result;
	}

	/**
	 * Heuristic-Method For the f2 heuristic number of possible moves
	 * 
	 * @author L.Weber
	 * @param moves
	 * @return
	 */
	public double numberOfPossibleMoves(PlayerType playerType, Move move, double weightPossible) {
		double length;
		double result;

		ArrayList<Move> movesTemp = new ArrayList<Move>();
		movesTemp = getNextPossibleMovesWithOneMove(playerType, move);

		length = movesTemp.size();
		result = length / 100 * weightPossible;

		return result;
	}

	/**
	 * Heuristic-Method For the question is it a Winning-Move
	 * 
	 * @author L.Weber
	 * @param moves
	 * @return
	 */
	public double isItAWinningMove(Move move, double weight) {

		// is the Move in the top or Bottom line?
		for (int i = 0; i < topBottomLine.size(); i++) {
			if (topBottomLine.get(i).getX1() == move.getX2() && topBottomLine.get(i).getY1() - 1 == move.getY2()) {
				return weight;
			} else {
				return weight;
			}
		}
		return weight;
	}

	/**
	 * Heuristic-Method For the question is it a Winning-Move
	 * 
	 * @author L.Weber
	 * @param moves
	 * @return
	 */
	public boolean isItAWinningMoveBool(Move move) {

		// is the Move in the top or Bottom line?
		for (int i = 0; i < topBottomLine.size(); i++) {
			if (topBottomLine.get(i).getX1() == move.getX2() && topBottomLine.get(i).getY1() - 1 == move.getY2()) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * Heuristic-Method For the f4 heuristic Move that possible to block
	 * 
	 * @author L.Weber
	 * @param playerType
	 * @param move
	 * @return
	 */
	public double isABlockMove(PlayerType playerType, Move move, double weightBlock) {
		ArrayList<Move> nextPossibleMoves = getNextPossibleMovesWithOneMove(changePlayerType(playerType), move);
		ArrayList<Move> possibleMovesForThisTower = null;

		for (int i = 0; i < nextPossibleMoves.size(); i++) {
			possibleMovesForThisTower = getNextPossibleMovesWithOneMoveIgnore(playerType, nextPossibleMoves.get(i));
		}

		for (int i = 0; i < nextPossibleMoves.size(); i++) {
			// is one of the nextPossibleMoves in the topBottomList
			if (topBottomLine.get(i).getX1() == nextPossibleMoves.get(i).getX2()
					&& topBottomLine.get(i).getY1() - 1 == nextPossibleMoves.get(i).getY2()) {
				// is the Goal for the nextPossibleMoves free
				if (!board[nextPossibleMoves.get(i).getX2()][nextPossibleMoves.get(i).getY2()].isOccupied()) {
					// is this move between the next Possible Moves
					for (int j = 0; j < possibleMovesForThisTower.size(); j++) {
						if (board[nextPossibleMoves.get(i).getX2()][nextPossibleMoves.get(i)
								.getY2()] == board[possibleMovesForThisTower.get(i).getX2()][possibleMovesForThisTower
										.get(i).getY2()]) {
							// is also in the right line
							if (board[nextPossibleMoves.get(i).getX1()][nextPossibleMoves.get(i).getY1()].getTower()
									.getPlayerType() == PlayerType.ONE
									// Player.ONE protect the bottomLine
									&& bottomLine.get(i).getX1() == nextPossibleMoves.get(i).getX2()
									&& bottomLine.get(i).getY1() - 1 == nextPossibleMoves.get(i).getY2()) {
								return weightBlock;
							}
							if (board[nextPossibleMoves.get(i).getX1()][nextPossibleMoves.get(i).getY1()].getTower()
									.getPlayerType() == PlayerType.TWO
									// Player.TWO protect the topLine
									&& topLine.get(i).getX1() == nextPossibleMoves.get(i).getX2()
									&& topLine.get(i).getY1() - 1 == nextPossibleMoves.get(i).getY2()) {
								return weightBlock;
							}
						}
					}
				}
			}
		}
		return 0;

	}

	/**
	 * Heuristic-Method For the f5 heuristic Move Sumo-Push, only do it when it
	 * gives advantage
	 * 
	 * @author L.Weber
	 * @param move
	 * @param playerType
	 * @return
	 */
	public double sumoPush(Move move, PlayerType playerType, double weightSumoWin, double weightSumoBlock) {

		// Get the Moves normaly
		ArrayList<Move> nextPossibleMoves = new ArrayList<Move>();

		if (move.getY1() < move.getY2()) {
			// North
			if (Tower.valid(move.getX2(), move.getY2() + 1)) {
				nextPossibleMoves = getNextPossibleMovesWithOneMove(playerType,
						new Move(move.getX1(), move.getY1(), move.getX2(), move.getY2() + 1));
			}
		} else {
			// South
			if (Tower.valid(move.getX2(), move.getY2() - 1)) {
				nextPossibleMoves = getNextPossibleMovesWithOneMove(playerType,
						new Move(move.getX1(), move.getY1(), move.getX2(), move.getY2() - 1));
			}
		}

		// is the tower a sumo-Tower?
		if (board[move.getX1()][move.getY1()].isOccupied()) {
			if (board[move.getX1()][move.getY1()].getTower().getType() == Type.sumoTower) {
				// is it really the move who push
				if (board[move.getX2()][move.getY2()].isOccupied()) {
					if (board[move.getX2()][move.getY2()].getTower().getPlayerType() == changePlayerType(playerType)) {
						for (int i = 0; i < nextPossibleMoves.size(); i++) {
							// is a winning Move possible when i push it?
							if (isItAWinningMoveBool(nextPossibleMoves.get(i))) {
								return weightSumoWin;
							}
							// is a BlockMove possible when i push it?
							if (isABlockMoveBool(playerType, nextPossibleMoves.get(i))) {
								return weightSumoBlock;
							}
						}
					}
				}
			}
		}
		return 0;
	}

	/**
	 * Heuristic-Method For calculating new moves on a temp board
	 * 
	 * @author L.Weber
	 * @param moves
	 * @return
	 */
	public Tile[][] getTilesAfter(ArrayList<Move> moves) {

		// for calculating not in the real game make another temp-board
		Board b = getTempBoard();
		Tile[][] temp2 = null;

		// now make moves on the temp-board
		for (int i = 0; i < moves.size(); i++) {
			b.makeMove(moves.get(i));

			// make a second temp board and gives the values from the first one
			temp2 = new Tile[8][8];
			for (int y = 0; y < 8; y++) {
				for (int x = 0; x < 8; x++) {
					temp2[x][y] = new Tile(b.getTile(x, y), b.getTile(x, y).getColor());
				}
			}
		}
		return temp2;
	}

	/** *************************************************************************************************************************************** **/

	/**
	 * Help-Methods
	 * 
	 * @author L.Weber
	 */

	/**
	 * Help-Method Build a new Game
	 * 
	 * @author L.Weber
	 * @param playerType
	 * @param newRound
	 */
	public void newRound(NewRound newRound) {
		boolean newRoundIsBuild = false;
		for(int z = 0; z < 8; z++) {
			if(board[z][7].isOccupied() && board[z][0].isOccupied()) {
				newRoundIsBuild = true;
			} else {
				newRoundIsBuild = false;
				break;
			}
		}
		if(newRoundIsBuild == false) {
			ArrayList<Tile> bottomTiles = new ArrayList<Tile>();
			ArrayList<Tile> topTiles = new ArrayList<Tile>();
			ArrayList<Tower> towerListPlayerONE = new ArrayList<Tower>();
			ArrayList<Tower> towerListPlayerTWO = new ArrayList<Tower>();

			// need the Tiles from the board
			for (int i = 0; i < 8; i++) {
				bottomTiles.add(board[i][0]);
				topTiles.add(board[i][7]);
			}
				Tower[][] towersP1Temp = new Tower[8][8];
				Tower[][] towersP2Temp = new Tower[8][8];
				
				if (newRound == NewRound.Left) {
					// Player 1 towers add to temp array
					int i = 0;
					for (int y = 0; y < 8; y++) {
						for (int x = 0; x < 8; x++) {
							if(board[x][y].hasTower(board, x, y) && board[x][y].getTower().getPlayerType() == PlayerType.ONE) {
								towersP1Temp[7-i][0] = board[x][y].getTower();
								i++;
							}
						}
					}
					// Player 2 towers add to temp array
					int k = 0;
					for (int y = 0; y < 8; y++) {
						for (int x = 0; x < 8; x++) {
							if(board[x][y].hasTower(board, x, y) && board[x][y].getTower().getPlayerType() == PlayerType.TWO) {
								towersP2Temp[k][7] = board[x][y].getTower();
								k++;
							}
						}
					}
					//is there no human player this massage must send
					if(this.getDoubleAI()) {
						ServerThreadForClient.sendMessageBackToClient(new Message(MessageType.NewRound, false));
					}
				}
				else if(newRound == NewRound.Right) {
					//Player 1 towers add to temp array
					int i = 0;
					for (int y = 0; y < 8; y++) {
						for (int x = 0; x < 8; x++) {
							if(board[x][y].hasTower(board, x, y) && board[x][y].getTower().getPlayerType() == PlayerType.ONE) {
								towersP1Temp[i][0] = board[x][y].getTower();
								i++;
							}
						}
					}
					//Player 2 towers add to temp array
					int k = 0;
					for (int y = 0; y < 8; y++) {
						for (int x = 0; x < 8; x++) {
							if(board[x][y].hasTower(board, x, y) && board[x][y].getTower().getPlayerType() == PlayerType.TWO) {
								towersP2Temp[7-k][7] = board[x][y].getTower();
								k++;
							}
						}
					}
					//is there no human player this massage must send
					if(this.getDoubleAI()) {
						ServerThreadForClient.sendMessageBackToClient(new Message(MessageType.NewRound, false));
					}
					
				}
				
				// fill the towers
				new Board();
				for (int i = 0; i < 8; i++) {
					Tile[][] tmpTiles = this.getTempTile();
					// this is the bottomLine
					board[i][0] = new Tile(towersP1Temp[i][0], board[i][0].getColor());
					// this is for the topLine
					board[i][7] = new Tile(towersP2Temp[i][7], board[i][7].getColor());
					// set the rest of the board new
					board[i][1] = new Tile(board[i][1].getColor());
					board[i][2] = new Tile(board[i][2].getColor());
					board[i][3] = new Tile(board[i][3].getColor());
					board[i][4] = new Tile(board[i][4].getColor());
					board[i][5] = new Tile(board[i][5].getColor());
					board[i][6] = new Tile(board[i][6].getColor());
				}
				System.out.println(getTempBoard());
		}
		
		
//		} else {
//			if (newRound == NewRound.Left) {
//				// the choice is left
//				for (int x = 0; x < 8; x++) {
//					for (int y = 0; y < 8; y++) {
//						// is on this Tile a Tower?
//						if (board[x][y].hasTower(board, x, y)) {
//							// is this tower a Player.ONE or a Player.TWO
//							if (board[x][y].getTower().getPlayerType() == PlayerType.ONE) {
//								// its a Player.ONE && set the Tile back without a
//								// tower
//								towerListPlayerONE.add(board[x][y].getTower());
//								board[x][y] = new Tile(board[x][y].getColor());
//							} else {
//								// its a Player.TWO && set the Tile back without a
//								// tower
//								towerListPlayerTWO.add(board[x][y].getTower());
//								board[x][y] = new Tile(board[x][y].getColor());
//							}
//						}
//					}
//				}
//				// fill the towers
//				for (int i = 0; i < 8; i++) {
//					// this is the bottomLine
//					board[i][0] = new Tile(towerListPlayerONE.get(i), bottomTiles.get(i).getColor());
//					// this is for the topLine
//					board[i][7] = new Tile(towerListPlayerTWO.get(i), topTiles.get(i).getColor());
//				}
//			} else {
//				// the choice is right
//				for (int y = 8; y > 0; y--) {
//					for (int x = 0; x > 0; x--) {
//						// is on this Tile a Tower?
//						if (board[x][y].hasTower(board, x, y)) {
//							// is this tower a Player.ONE or a Player.TWO
//							if (board[x][y].getTower().getPlayerType() == PlayerType.ONE) {
//								// its a Player.ONE && set the Tile back without a
//								// tower
//								towerListPlayerONE.add(board[x][y].getTower());
//								board[x][y] = new Tile(board[x][y].getColor());
//							} else {
//								// its a Player.TWO && set the Tile back without a
//								// tower
//								towerListPlayerTWO.add(board[x][y].getTower());
//								board[x][y] = new Tile(board[x][y].getColor());
//							}
//						}
//					}
//				}
//				// fill the towers
//				for (int i = 0; i < 8; i++) {
//					// this is the bottomLine
//					board[i][0] = new Tile(towerListPlayerONE.get(i), bottomTiles.get(i).getColor());
//					// this is for the topLine
//					board[i][7] = new Tile(towerListPlayerTWO.get(i), topTiles.get(i).getColor());
//				}
//			}
//		}
//		System.out.println(getTempBoard());
	}
		
		
		

		

	/**
	 * Help-Method Check is one Tile free for the other Player when doing the
	 * Move
	 * 
	 * @author L.Weber
	 * @param playerType
	 * @param move
	 * @return
	 */
	public ArrayList<Move> removeMoves(PlayerType playerType, Move move) {
		ArrayList<Move> removeThis = new ArrayList<Move>();

		// List for Bottom or Top Line
		ArrayList<Move> nextPossibleMoves = getNextPossibleMovesWithOneMove(playerType, move);

		for (int i = 0; i < nextPossibleMoves.size(); i++) {
			// is the start-position then empty
			// is the move in the top or bottom line
			// is the start-tile the same like the goal-tile after the move
			for (int j = 0; j < topBottomLine.size(); j++) {
				if (!board[move.getX1()][move.getY1()].isOccupied() && move.getX1() == topBottomLine.get(j).getX1()
						&& move.getY1() == topBottomLine.get(j).getY1() - 1
						&& board[move.getX1()][move
								.getY1()] == board[nextPossibleMoves.get(i).getX2()][nextPossibleMoves.get(i)
										.getY2()]) {
					removeThis.add(move);
				}
			}

		}
		return removeThis;
	}

	/**
	 * Help-Method for check the Win-Chance
	 * 
	 * @author L.Weber
	 * @param playerType
	 * @param tower
	 *            - the actual Tower
	 * @return Move
	 */
	public Move checkIsWinChance(PlayerType playerType, Tower tower) {
		if (playerType == PlayerType.TWO) {
			for (int i = 0; i < 8; i++) {
				if (!this.board[i][1 - 1].isOccupied() && this.board[i][1].getColor() == tower.getColor()) {
					return new Move(i, 0);
				}
			}
		} else {
			for (int i = 0; i < 8; i++) {
				if (!this.board[i][8 - 1].isOccupied() && this.board[i][8].getColor() == tower.getColor()) {
					return new Move(i, 7);
				}
			}
		}
		return null;
	}

	/**
	 * Help-Method for get the top and bottom Line
	 * 
	 * @author L.Weber
	 * @return ArrayList<Move> Top and Bottom List
	 */
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

	/**
	 * Help-Method for Splitting to top and bottom Line
	 * 
	 * @author L.Weber
	 * @param topBottomList
	 * @param topBottom
	 *            true = topList / false = bottomList
	 * @return
	 */
	public ArrayList<Move> splitTopBottomList(ArrayList<Move> topBottomList, boolean topBottom) {
		ArrayList<Move> bottomList = new ArrayList<Move>();
		ArrayList<Move> topList = new ArrayList<Move>();

		for (int i = 0; i < topBottomList.size(); i++) {
			if (topBottomList.get(i).getY1() == 1) {
				bottomList.add(topBottomList.get(i));
			} else {
				topList.add(topBottomList.get(i));
			}
		}
		if (topBottom == true) {
			return topList;
		} else {
			return bottomList;
		}
	}

	/**
	 * Help-Method Change the Playe
	 * 
	 * @author L.Weber
	 * @param playerType
	 * @return
	 */
	public static PlayerType changePlayerType(PlayerType playerType) {
		if (playerType == PlayerType.ONE) {
			playerType = PlayerType.TWO;
		} else {
			playerType = PlayerType.ONE;
		}
		return playerType;
	}

	/**
	 * Help-Method is it a Block-Move
	 * 
	 * @author L.Weber
	 * @param playerType
	 * @param move
	 * @return
	 */
	public boolean isABlockMoveBool(PlayerType playerType, Move move) {
		ArrayList<Move> nextPossibleMoves = getNextPossibleMovesWithOneMove(changePlayerType(playerType), move);
		ArrayList<Move> possibleMovesForThisTower = null;

		for (int i = 0; i < nextPossibleMoves.size(); i++) {
			possibleMovesForThisTower = getNextPossibleMovesWithOneMoveIgnore(playerType, nextPossibleMoves.get(i));
		}

		for (int i = 0; i < nextPossibleMoves.size(); i++) {
			// is one of the nextPossibleMoves in the topBottomList
			if (topBottomLine.get(i).getX1() == nextPossibleMoves.get(i).getX2()
					&& topBottomLine.get(i).getY1() - 1 == nextPossibleMoves.get(i).getY2()) {
				// is the Goal for the nextPossibleMoves free
				if (!board[nextPossibleMoves.get(i).getX2()][nextPossibleMoves.get(i).getY2()].isOccupied()) {
					// is this move between the next Possible Moves
					for (int j = 0; j < possibleMovesForThisTower.size(); j++) {
						if (board[nextPossibleMoves.get(i).getX2()][nextPossibleMoves.get(i)
								.getY2()] == board[possibleMovesForThisTower.get(i).getX2()][possibleMovesForThisTower
										.get(i).getY2()]) {
							// is also in the right line
							if (board[nextPossibleMoves.get(i).getX1()][nextPossibleMoves.get(i).getY1()].getTower()
									.getPlayerType() == PlayerType.ONE
									// Player.ONE protect the bottomLine
									&& bottomLine.get(i).getX1() == nextPossibleMoves.get(i).getX2()
									&& bottomLine.get(i).getY1() - 1 == nextPossibleMoves.get(i).getY2()) {
								return true;
							}
							if (board[nextPossibleMoves.get(i).getX1()][nextPossibleMoves.get(i).getY1()].getTower()
									.getPlayerType() == PlayerType.TWO
									// Player.TWO protect the topLine
									&& topLine.get(i).getX1() == nextPossibleMoves.get(i).getX2()
									&& topLine.get(i).getY1() - 1 == nextPossibleMoves.get(i).getY2()) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;

	}

	/** *************************************************************************************************************************************** **/

	/**
	 * Visualise the Board
	 */

	/**
	 * Help-Method for getting The Board to printing
	 * 
	 * @author L.Weber
	 * @return Tile[][]
	 */
	public Board getTempBoard() {
		Tile[][] temp = new Tile[8][8];

		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				temp[x][y] = new Tile(this.board[x][y], this.board[x][y].getColor());
			}
		}

		return new Board(temp);
	}

	/**
	 * Help-Method for getting Tiles
	 * 
	 * @author L.Weber
	 * @return
	 */
	public Tile[][] getTempTile() {
		Tile[][] temp = new Tile[8][8];

		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				temp[x][y] = new Tile(this.board[x][y], this.board[x][y].getColor());
			}
		}

		return temp;
	}

	/**
	 * For Visualise the Sumo-PUSH
	 * 
	 * @author L.Weber
	 * @param move
	 */
	public void getSumoMessage(Move move) {
		System.err.println("***************** SUMO-PUSH **********************");
		System.err.println("The move was to an other tower -> must be an Sumo");
		System.err.println(
				board[move.getX1()][move.getY1()].getTower() + " -->> " + board[move.getX2()][move.getY2()].getTower());
		System.err.println(
				"Move from: " + move.getX1() + " " + move.getY1() + " Move to: " + move.getX2() + " " + move.getY2());
		System.err.println("***************** SUMO-PUSH **********************");
	}

	/**
	 * To giv the board out in the console
	 * 
	 * @author L.Weber
	 */
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

	/** *************************************************************************************************************************************** **/

	/**
	 * Getter and Setter
	 * 
	 * @author L.Weber
	 */

	private Tile getTileColor(int x, int y) {
		return board[x][y];
	}

	public Tile getTile(int x, int y) {
		return board[x][y];
	}
	
	public Move getLastMove(){
		return this.lastMove;
	}
	
	public void setLastMove(Move lastMove) {
		this.lastMove = lastMove;
	}
	
	public SimpleBooleanProperty getUpgrade(){
		return this.upgrade;
	}
	
	public void setUpgrade(boolean upgrade) {
		this.upgrade.set(upgrade);
	}
	
	public int getGems(){
		return this.gems;
	}
	
	public int getXCoordinateUpgrade(){
		return this.xCoordinationUpgrade;
	}
	
	public int getYCoordinateUpgrade() {
		return this.yCoordinationUpgrade;
	}
	
	public static void setDoubleAI(boolean doubleAI) {
		wantDoubleAI = doubleAI;
	}
	
	public static boolean getDoubleAI() {
		return wantDoubleAI;
	}
	
	public static boolean getWantAI() {
		return wantAI;
	}
	
	public static void setWandAI(boolean ai) {
		wantAI = ai;
	}
}
