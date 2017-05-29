package Lobby;

import WindChuckers_Main.GameMenu_Model;
import WindChuckers_Main.WindChuckers;
import abstractClasses.Controller;

public class LobbyController extends Controller<GameMenu_Model, LobbyView> {
	private GameMenu_Model model;
	private LobbyView view;
	
	public LobbyController(GameMenu_Model model, LobbyView view){
		super(model,view);
		
		/**
		 * Hide the View
		 * @author L.Weber
		 */
		view.menuFileExitLobby.setOnAction(e -> {
			view.stop();
		});
		
	}

}
