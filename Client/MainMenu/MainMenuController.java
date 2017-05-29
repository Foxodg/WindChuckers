package MainMenu;

import WindChuckers_Main.GameMenu_Model;
import WindChuckers_Main.WindChuckers;
import abstractClasses.Controller;

public class MainMenuController extends Controller<GameMenu_Model, MainMenuView> {
	private GameMenu_Model model;
	private MainMenuView view;
	
	public MainMenuController(final WindChuckers main, GameMenu_Model model, MainMenuView view) {
		super(model,view);
		
		view.menuFileExitMainMenu.setOnAction(e -> {
			view.stop();
		});
		
		view.btnStartGame.setOnAction(e -> {
			main.startApp();
			view.stop();
		});
	}

}
