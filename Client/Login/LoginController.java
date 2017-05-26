package Login;

import java.util.logging.Logger;

import Message.Message;
import Message.Message.MessageType;
import WindChuckers_Main.GameMenu_Model;
import WindChuckers_Main.WindChuckers;
import abstractClasses.Controller;
import commonClasses.ServiceLocator;
import javafx.event.ActionEvent;
import javafx.scene.control.Dialog;

public class LoginController extends Controller<GameMenu_Model, LoginView> {
	private GameMenu_Model model;
	private LoginView view;
	private LoginModel loginModel;
	private ServiceLocator serviceLocator = ServiceLocator.getServiceLocator();
	private Logger logger = serviceLocator.getLogger();
	
	public LoginController (final WindChuckers main, GameMenu_Model model, LoginView view, LoginModel loginModel){
		super(model,view);
		this.loginModel = loginModel;
		
		view.menuFileExitLogin.setOnAction(e -> {
			view.stop();
		});
		
		view.btnLogin.setOnAction(e -> {
			if(loginModel.passwordCheck(view.password.getText())){
				main.startApp();
				view.stop();
			}
		});
		
		view.btnCheckUserName.setOnAction(e -> {
			model.sendMessage(new Message(MessageType.DBMessage,0));
		});
		
		//Watch for the DB-Request
		model.getDBRequest().addListener((observable, oldValue, newValue) -> {
			logger.info("DB-Request is here");
			view.btnCheckUserName.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");			
		});
		
	}

}
