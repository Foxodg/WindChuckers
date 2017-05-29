package Login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Logger;

import Message.Message;
import Message.Message.MessageType;
import WindChuckers_Main.GameMenu_Model;
import WindChuckers_Main.WindChuckers;
import abstractClasses.Controller;
import commonClasses.ServiceLocator;


public class LoginController extends Controller<GameMenu_Model, LoginView> {
	private GameMenu_Model model;
	private LoginView view;
	private LoginModel loginModel;
	private ServiceLocator serviceLocator = ServiceLocator.getServiceLocator();
	private Logger logger = serviceLocator.getLogger();
	private WindChuckers windChuckers;

	private HashMap<Integer, ArrayList<String>> userMap;

	public LoginController (final WindChuckers main, GameMenu_Model model, LoginView view, LoginModel loginModel){
		super(model,view);
		this.loginModel = loginModel;
		
		/**
		 * Stops the view
		 * @author L.Weber
		 */
		view.menuFileExitLogin.setOnAction(e -> {
			view.stop();
		});
		
		view.btnLogin.setOnAction(e -> {
			if(loginModel.passwordCheck(view.password.getText())){
				main.startMainMenu();
				view.stop();
			}
		});
		
		/**
		 * Make a new DB-Request
		 * @author L.Weber
		 */
		view.btnRefresh.setOnAction(e -> {
			//Get a new DB-Request to get all Names in the PlayerTable
			model.sendMessage(new Message(MessageType.DBMessage,0));
		});
		
		view.username.textProperty().addListener((observable, oldaValue, newValue) -> {
			
			for(Entry<Integer, ArrayList<String>> ee : userMap.entrySet()){
				ArrayList<String> searchList = ee.getValue();
				if(searchList.get(0).equals(view.username.getText())){
					view.username.setStyle("-fx-text-inner-color: red;");
				} else {
					view.username.setStyle("-fx-text-inner-color: green;");
				}
			}
		});
		
		/**
		 * Watch for the DB-Request
		 * @author L.Weber
		 */
		model.getDBRequest().addListener((observable, oldValue, newValue) -> {
			logger.info("DB-Request is here");
			view.btnLogin.setVisible(true);
			view.username.setVisible(true);
			view.password.setVisible(true);
			//fills the userName-Map
			userMap = model.getUserMap();
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
		
		view.btnUserMenu.setOnAction(e -> {
			windChuckers = WindChuckers.getWindChuckers();
			windChuckers.startUserMenu();
		});
		
	}

}
