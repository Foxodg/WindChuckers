package Server;

import java.util.logging.Logger;

import org.h2.mvstore.db.ValueDataType;

import AI.Board;
import AI.Kamisado;
import AI.Move;
import AI.PlayerType;
import AI.Tile;
import Message.Message.MessageType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

import Message.Message;
import Message.Message.MessageType;
import Message.Message.Value;

public class ServerController {
    
    final private ServerModel model;
    final private ServerView view;
    final private Kamisado kamisado;
    final Logger logger = Logger.getLogger("");
    private Board board = Board.getBoard();
    
    protected ServerController(ServerModel model, ServerView view) {
        this.model = model;
        this.view = view;
        this.kamisado = Kamisado.getKamisado();
        
        // register ourselves to listen for button clicks
        view.btnGo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Integer port = new Integer(view.txtPort.getText());
                model.startServerSocket(port);
            }
        });

        // register ourselves to handle window-closing event
        view.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                view.stop();     // closes the GUI
                Platform.exit(); // ends any JavaFX activities
                System.exit(0);  // end all activities (our server task) - not good code
            }
        });
        
		// Watch the model for changing
		kamisado.getValueProperty().addListener((obervable, oldValue, newValue) -> {
			logger.info("A new Move is arrived");
			Move move = kamisado.getMove();
			Board board = Board.getBoard();
			PlayerType playerType = board.getTile(move.getX2(), move.getY2()).getTower().getPlayerType();
			ServerThreadForClient.sendMessageBackToClient(new Message(MessageType.Coordinate,move.getX1(),move.getY1(),move.getX2(),move.getY2(), playerConverter(playerType)));
		});
		
		//Watch the board for an update
		board.getUpgrade().addListener((observable, oldValue, newValue) -> {
			PlayerType playerType = board.getTile(board.getXCoordinateUpgrade(), board.getYCoordinateUpgrade()).getTower().getPlayerType();
			//when its empty not send it
			ServerThreadForClient.sendMessageBackToClient(new Message(MessageType.Update, board.getXCoordinateUpgrade(), board.getYCoordinateUpgrade(), board.getGems(), playerConverter(playerType)));
		});
    }
    
    public static int playerConverter(PlayerType playerType){
    	if(playerType == PlayerType.ONE){
    		return 2;
    	} else {
    		return 1;
    	}
    }
    
    public static PlayerType playerConverter(int player) {
    	if(player == 1){
    		return PlayerType.TWO;
    	} else {
    		return PlayerType.ONE;
    	}
    }
}
