package Lobby;

import Client.ClientThreadForServer;
import Friends.FriendsController;
import Friends.FriendsView;
import WindChuckers_Main.GameMenu_Model;
import WindChuckers_Main.GameMenu_View;
import WindChuckers_Main.WindChuckers;
import abstractClasses.Controller;
import commonClasses.ServiceLocator;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;

public class LobbyController extends Controller<GameMenu_Model, LobbyView> {
	private GameMenu_Model model;
	private LobbyView view;
	private volatile boolean pressed = false;
	
	ClientThreadForServer clientServer = ClientThreadForServer.getClientServer();
	
	public LobbyController(GameMenu_Model model, LobbyView view){
		super(model,view);
		
		/**
		 * Hide the View
		 * @author L.Weber
		 */
		view.menuFileExitLobby.setOnAction(e -> {
			view.stop();
		});
		
		// Send-Button send Message to Server
		view.btnSend.setOnAction(e -> {
			String input = model.getUserNameString();
			input += ": " + view.txtChat.getText();
			model.messageConstructorForBuildCapsule(FriendsController.getHashCode(), input);
			view.txtChat.clear();
		});
		
		// Enter for send the chat message
		view.txtChat.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER)) {
				String input = model.getUserNameString();
				input += ": " + view.txtChat.getText();
				model.messageConstructorForBuildCapsule(FriendsController.getHashCode(), input);
				view.txtChat.clear();
			}
		});
		
		// Watch the client for ChatMessage
		clientServer.getChatMessageProperty().addListener((obervable, oldValue, newValue) -> {
			ServiceLocator.getServiceLocator().getLogger().info("Message from Chat is arrived");
			view.txtMessages.appendText("\n" + clientServer.getChatMessageInString());
		});
		
		view.btnGo.setOnAction(e -> {
			if(pressed) {
				pressed = false;
				view.btnGo.setStyle("-fx-background-color: #000000");
			} else {
				pressed = true;
				view.btnGo.setStyle("-fx-background-color: #8fbc8f");
			}
			if(pressed) {
				//send ready to server
				model.messageConstructorForWaiter(FriendsController.getHashCode());
				model.setPlayer(ClientThreadForServer.getHashCodeThis(), ClientThreadForServer.getHashCodeFriend());
			} else {
				//send not ready to server
				model.messageConstructorForWaiter(FriendsController.getHashCode());
			}
		});
		
	}

}
