package WindChuckers_Main.newRoundPopup;

import Lobby.LobbyView;
import WindChuckers_Main.GameMenu_Model;
import abstractClasses.Controller;

public class newRoundController extends Controller<GameMenu_Model, newRoundView> {
	private GameMenu_Model model;
	private newRoundView view;

	public newRoundController(GameMenu_Model model, newRoundView view) {
		super(model, view);

	/**
	 * 
	 * @author robin
	 */
		view.leftPlay.setOnAction(e -> {
			System.out.println("leftPlay");
		});
		
		view.rightPlay.setOnAction(e -> {
			System.out.println("rightPlay");
		});
		
		
	}

}




