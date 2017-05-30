package Client;

import java.util.logging.Logger;

import Message.Message;
import Message.Message.MessageType;
import Message.Message.Value;
import WindChuckers_Main.GameMenu_Model;
import commonClasses.ServiceLocator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

public class ClientController {

	private ServiceLocator serviceLocator = ServiceLocator.getServiceLocator();
	private final Logger logger = serviceLocator.getLogger();
	final private GameMenu_Model model;
	final private ClientView view;
	final private ClientThreadForServer clientserver;
	
	public ClientController getClientController(){
		return this;
	}
	

	public ClientController(GameMenu_Model model, ClientView view, ClientThreadForServer clientserver) {
        this.model = model;
        this.view = view;
        this.clientserver = clientserver;
        
        //is the AutomaticButton true then login   
        if(view.cbAutomaticLogin.isSelected()){
        	String ip = view.txtIP.getText();
            Integer port = new Integer(view.txtPort.getText());
            if (model.connect(ip, port)) {
                view.txtMessages.setText("Connection established");
        		//Do a Request to the DB for all the Users
        		model.setMoveProperty(false);
                model.sendMessage(new Message(MessageType.DBMessage,0));
                view.stop();
            } else {
                view.txtMessages.setText("Failed to establish connection");
            }
        } else {
            view.start();
        }
        
        // register ourselves to listen for button clicks
        view.btnGo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String ip = view.txtIP.getText();
                Integer port = new Integer(view.txtPort.getText());
                if (model.connect(ip, port)) {
                    view.txtMessages.setText("Connection established");
            		//Do a Request to the DB for all the Users
            		model.setMoveProperty(false);
                    model.sendMessage(new Message(MessageType.DBMessage,0));
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
        		boolean singlePlayer = true;
        		int xCoordinate1 = 0;
        		int yCoordinate1 = 0;
        		int xCoordinate2 = 0;
        		int yCoordinate2 = 4;
        		Value value = Value.Player1;
        		model.messageConstructorForCoordinate(singlePlayer, xCoordinate1, yCoordinate1, xCoordinate2, yCoordinate2, value);
        	}
        	else if(view.cbchoice.getSelectionModel().getSelectedItem() == MessageType.Update){
        		boolean update = true;
        		int xCoordinate1 = 1;
        		int yCoordinate1 = 2;
        		int xCoordinate2 = 1;
        		int yCoordinate2 = 5;
        		int gems = 1;
        		model.messageConstructorForUpdate(update, xCoordinate1, yCoordinate1, xCoordinate2, yCoordinate2, gems);
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
//                Platform.exit();
            }
        });
        
        //Watch the client for Moves
        clientserver.getValue().addListener((observable, oldValue, newValue) -> {
        	logger.info("Client: New Move from Server is here");
        	model.makeMove(clientserver.getStartColumn(),clientserver.getStartRow(), clientserver.getEndColumn(), clientserver.getEndRow());
        });
        
		//Watch for the DB-Request
		clientserver.getDBRequest().addListener((observable, oldValue, newValue) -> {
			logger.info("DB-Request is here");
			model.setDBRequest(false);
			model.setUserList(clientserver.getUserList());
			model.makeUserList(clientserver.getUserList());
			model.setDBRequest(true);
		});
        
		// Watch the client for ChatMessage
		clientserver.getChatMessageProperty().addListener((obervable, oldValue, newValue) -> {
			logger.info("Message from Chat is arrived");
			updateGUI(newValue);
		});
		
		view.menuFileExitClient.setOnAction(e -> {
			view.stop();
		});
	}
		
	
		private void updateGUI(String newValue) {
		Platform.runLater(() -> {
			view.txtMessages.appendText("\n"+clientserver.getChatMessageInString());
		});
		
	}
		
}
