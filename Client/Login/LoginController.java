package Login;

import WindChuckers_Main.GameMenu_Model;
import abstractClasses.Controller;

public class LoginController extends Controller<GameMenu_Model, LoginView> {
	private GameMenu_Model model;
	private LoginView view;
	
	public LoginController (GameMenu_Model model, LoginView view){
		super(model,view);
		
		view.menuFileExitLogin.setOnAction(e -> {
			view.stop();
		});
	}

}
