package AI;

import javafx.beans.property.SimpleBooleanProperty;

public class Kamisado {
	private static Kamisado kamisado;
	private Move move;
	
	private double weightVerticalOne = 100;
	private double weightPossibleOne = 100;
	private double weightBlockOne = 15;
	private double weightSumoWinOne = 25;
	private double weightSumoBlockOne = 15;
	private double winOne = Double.POSITIVE_INFINITY;
	
	private double weightVerticalTwo = 100;
	private double weightPossibleTwo = 100;
	private double weightBlockTwo = 15;
	private double weightSumoWinTwo = 25;
	private double weightSumoBlockTwo = 15;
	private double winTwo = Double.POSITIVE_INFINITY;
	
	// SimpleBooleanProperty for overwatching the a new return value
	private SimpleBooleanProperty moveHere = new SimpleBooleanProperty();
	
	public static Kamisado getKamisado() {
		if(kamisado == null){
			kamisado = new Kamisado();
		}
		return kamisado;
	}

//	public static void main(String[] args) {
//		Board board = Board.getBoard();
////		board.makeMove(new Move(0, 0, 0, 1, true));
//		setPlayConfiguration(false, 5, 100, 100, 15, 25, 15, Double.POSITIVE_INFINITY);
//	}

	public Move setPlayConfiguration(boolean singlePlayer, int depth, int player) {
		Board board = Board.getBoard();

		//has to change the playerType, because this here is the opponent
		if (singlePlayer) {
			PlayerType playerType;
			if(player == 1){
				playerType = PlayerType.ONE;
			} else {
				playerType = PlayerType.TWO;
			}
			AlphaBetaPlayer player2 = new AlphaBetaPlayer(playerType, depth, weightVerticalTwo, weightPossibleTwo,
					weightBlockTwo, weightSumoWinTwo, weightSumoBlockTwo, winTwo);
			return play(player2, board, playerType);
		} else {
			AlphaBetaPlayer player1 = new AlphaBetaPlayer(PlayerType.ONE, depth, weightVerticalOne, weightPossibleOne,
					weightBlockOne, weightSumoWinOne, weightSumoBlockOne, winOne);
			AlphaBetaPlayer player2 = new AlphaBetaPlayer(PlayerType.TWO, depth, weightVerticalTwo, weightPossibleTwo,
					weightBlockTwo, weightSumoWinTwo, weightSumoBlockTwo, winTwo);
			play(player1, player2, board);
			return null;
		}

	}

	private Move play(AlphaBetaPlayer player2, Board board, PlayerType playerType) {
		long start;
		long end;
		Move move = null;

		if (board.isWinSituation() == false) {
			start = System.currentTimeMillis();
			move = board.makeMove(player2.getNextMove(board, playerType));
			end = System.currentTimeMillis();
			System.out.println("time: " + (end - start));
			
		}
		return move;
	}

	public void play(AlphaBetaPlayer player1, AlphaBetaPlayer player2, Board board) {
		long start;
		long end;

		while (true) {
			move = board.firstMove(PlayerType.ONE);
			while (board.isWinSituation() == false) {
				start = System.currentTimeMillis();
				setValue(false);
				move = board.makeMove(player1.getNextMove(board, PlayerType.TWO));
				setValue(true);
				end = System.currentTimeMillis();
				System.out.println("time: " + (end - start));
				if (board.isWinSituation() == false) {
					start = System.currentTimeMillis();
					setValue(false);
					move = board.makeMove(player2.getNextMove(board, PlayerType.ONE));
					setValue(true);
					end = System.currentTimeMillis();
					System.out.println("time: " + (end - start));
				}
			}
			board.newRound(NewRound.Left);
			System.out.println(board);
		}
	}
	
	public SimpleBooleanProperty getValueProperty() {
		return this.moveHere;
	}
	
	// setter for the SimpleBooleanProperty
	public void setValue(Boolean newValue) {
		try{
			moveHere.setValue(newValue);;
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	//normal getter when recognised that simpleBooleanProperty is true
	public Move getMove(){
		return this.move;
	}
	
	// setter for the AI-Properties
	public void setProgressOne(double weight){
		this.weightVerticalOne = weight;
	}
	
	public void setProgressTwo(double weight){
		this.weightVerticalTwo = weight;
	}
	
	public void setMovesOne(double weight){
		this.weightPossibleOne = weight;
	}
	
	public void setMovesTwo(double weight){
		this.weightPossibleTwo = weight;
	}
	
	public void setBlockOne(double weight){
		this.weightBlockOne = weight;
	}
	
	public void setBlockTwo(double weight){
		this.weightBlockTwo = weight;
	}
	
	public void setSumoBlockOne(double weight){
		this.weightSumoBlockOne = weight;
	}
	
	public void setSumoBlockTwo(double weight){
		this.weightSumoBlockTwo = weight;
	}
	
	public void setSumoWinOne(double weight){
		this.weightSumoWinOne = weight;
	}
	
	public void setSumoWinTwo(double weight) {
		this.weightSumoWinTwo = weight;
	}
	
	public void setWinOne(double weight) {
		this.winOne = weight;
	}
	
	public void setWinTwo(double weight) {
		this.winTwo = weight;
	}
}
