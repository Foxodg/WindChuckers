package MainMenu;

import WindChuckers_Main.GameMenu_Model;
import abstractClasses.Controller;

public class MainMenuController extends Controller<GameMenu_Model, MainMenuView> {
	private GameMenu_Model model;
	private MainMenuView view;
	
	public MainMenuController(GameMenu_Model model, MainMenuView view) {
		super(model,view);
		
		view.menuFileExitMainMenu.setOnAction(e -> {
			view.stop();
		});
	}

}
