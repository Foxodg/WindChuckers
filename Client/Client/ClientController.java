package Client;

import java.util.logging.Logger;

import Message.Message;
import Message.Message.MessageType;
import Message.Message.Value;
import WindChuckers_Main.GameMenu_Model;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

public class ClientController {

	private final Logger logger = Logger.getLogger("");
	final private GameMenu_Model model;
	final private ClientView view;
	final private ClientThreadForServer clientserver;

	public ClientController(GameMenu_Model model, ClientView view, ClientThreadForServer clientserver) {
        this.model = model;
        this.view = view;
        this.clientserver = clientserver;
        
        // register ourselves to listen for button clicks
        view.btnGo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String ip = view.txtIP.getText();
                Integer port = new Integer(view.txtPort.getText());
                if (model.connect(ip, port)) {
                    view.txtMessages.setText("Connection established");
                } else {
                    view.txtMessages.setText("Failed to establish connection");
                }
            }
        });
        
        view.btnSendOther.setOnAction(event -> {
        	if (view.cbchoice.getSelectionModel().getSelectedItem() == MessageType.ChatMessage){
        		String input = view.txtChat.getText();
        		model.messageContructorForChat(input);
        	}
        	else if(view.cbchoice.getSelectionModel().getSelectedItem() == MessageType.Coordinate){
        		int xCoordinate = 1;
        		int yCoordinate = 2;
        		Value value = Value.Player1;
        		model.messageConstructorForCoordinate(xCoordinate, yCoordinate, value);
        	}
        	else if(view.cbchoice.getSelectionModel().getSelectedItem() == MessageType.DBMessage){
        		int dbMessage = 1;
        		model.messageConstructorForDB(dbMessage);
        	}
        	else if(view.cbchoice.getSelectionModel().getSelectedItem() == MessageType.WinMessage){
        		boolean win = true;
        		model.messageConstructorForWin(win);
        	}
        	else {
        		model.messageConstructorForError();
        	}
        });

        // register ourselves to handle window-closing event
        view.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                view.stop();
                Platform.exit();
            }
        });
        
		// Watch the client for ChatMessage
		clientserver.getChatMessageProperty().addListener((obervable, oldValue, newValue) -> {
			logger.info("Message from Chat is arrived");
			updateGUI(newValue);
		});
	}
	
		private void updateGUI(String newValue) {
		Platform.runLater(() -> {
			view.txtMessages.appendText("\n"+clientserver.getChatMessageInString());
		});
		
	}
}
