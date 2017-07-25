package MainMenu;

import Friends.FriendsView;
import WindChuckers_Main.GameMenu_Model;
import WindChuckers_Main.WindChuckers;
import abstractClasses.Controller;

public class MainMenuController extends Controller<GameMenu_Model, MainMenuView> {
	private GameMenu_Model model;
	private MainMenuView view;
	private WindChuckers windChuckers;

	public MainMenuController(final WindChuckers main, GameMenu_Model model, MainMenuView view) {
		super(model,view);
		
		/**
		 * @author Cyrill Füglister
		 */
		view.menuFileExitMainMenu.setOnAction(e -> {
			view.getStage().hide();
		});
		
		
		view.btnSingleGame.setOnAction(e -> {
			model.setGameMode(1);
			main.startApp();
			view.getStage().hide();
		});
		
		
		view.btnStandardGame.setOnAction(e -> {
			model.setGameMode(3);
			main.startApp();
			view.getStage().hide();
		});
		
		view.btnLongGame.setOnAction(e -> {
			model.setGameMode(7);
			main.startApp();
			view.getStage().hide();
		});
		
		view.btnMarathonGame.setOnAction(e -> {
			model.setGameMode(15);
			main.startApp();
			view.getStage().hide();
		});
		
		
		view.btnFriends.setOnAction(e -> {
			windChuckers = WindChuckers.getWindChuckers();
			windChuckers.startFriends();
			view.getStage().hide();
		});
		
	}

}
