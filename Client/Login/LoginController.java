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
	private WindChuckers windChuckers;
	
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
		
		/**
		 * Watch for the DB-Request
		 * @author L.Weber
		 */
		model.getDBRequest().addListener((observable, oldValue, newValue) -> {
			logger.info("DB-Request is here");
			for(int i = 0; i < model.getUserList().size(); i++) {
				if(loginModel.checkUserList(model.getUserList().get(i),view.username.getText())){
					view.btnCheckUserName.setStyle("-fx-background-color: #00ff00");
				}
			}
		});
		
		/**
		 * Start the Client-View
		 * @author L.Weber
		 */
		view.menuClientGUI.setOnAction(e -> {
			serviceLocator.getLogger().info("Start Client");
			windChuckers = WindChuckers.getWindChuckers();
			windChuckers.getStartetClient();
		});
		
	}

}
