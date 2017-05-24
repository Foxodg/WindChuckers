package Server;

import java.util.logging.Logger;

import org.h2.mvstore.db.ValueDataType;

import AI.Kamisado;
import AI.Move;
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
			ServerThreadForClient.sendMessageBackToClient(new Message(MessageType.Coordinate,move.getX1(),move.getY1(),move.getX2(),move.getY2(), Value.Player2));
		});
    }
}
