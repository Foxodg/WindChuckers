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
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;



public class LoginController extends Controller<GameMenu_Model, LoginView> {

	private GameMenu_Model model;
	private LoginView view;
	private LoginModel loginModel;
	private ServiceLocator serviceLocator = ServiceLocator.getServiceLocator();
	private Logger logger = serviceLocator.getLogger();
	private WindChuckers windChuckers;

	private int passwordCounter = 1;

	private HashMap<Integer, ArrayList<String>> userMap;
	private HashMap<String, Integer> counter;
	private HashMap<String, String> password;

	private ArrayList<String> searchList;
	
	public LoginController(final WindChuckers main, GameMenu_Model model, LoginView view, LoginModel loginModel) {
		super(model, view);
		this.loginModel = loginModel;

		counter = new HashMap<String, Integer>();
		password = new HashMap<String, String>();
		searchList = new ArrayList<String>();

		/**
		 * Stops the view
		 * 
		 * @author L.Weber
		 */
		view.menuFileExitLogin.setOnAction(e -> {
			view.stop();
		});

		view.btnLogin.setOnAction(e -> {
			try{
				String password = null;
				for (Entry<Integer, ArrayList<String>> ee : userMap.entrySet()) {
					ArrayList<String> searchList = ee.getValue();
					if (searchList.get(0).equals(view.username.getText())) {
						String name = searchList.get(0);
						if (this.counter.get(name) == null) {
							this.counter.put(name, new Integer(passwordCounter));
							this.password.put(name, searchList.get(3));
							LoginModel.setSurname(searchList.get(2));
							LoginModel.setWins(Integer.parseInt(searchList.get(4)));
						}
					}
				}
				loginModel.setPassword(this.password.get(view.username.getText()));
				//when the password is three times false
				if (this.counter.get(view.username.getText()) <= 3) {
					if (loginModel.passwordCheck(view.password.getText())) {
						model.setUserName(view.username.getText());
						main.startMainMenu();
						view.stop();
					} else {
						view.password.clear();
						view.password.setPromptText("Please set correct password");
						int counter = this.counter.get(view.username.getText());
						this.counter.replace(view.username.getText(), ++counter);
					}
				} else {
					view.password.clear();
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Password failed");
					alert.setHeaderText("Password more than 3 times false");
					alert.setContentText("Please contact the administrator");
					alert.showAndWait();
				}
			} catch (Exception ex){
			 logger.warning("Server not available");
				main.startMainMenu();
				view.stop();
			}
		});
		

		/**
		 * Make a new DB-Request
		 * 
		 * @author L.Weber
		 */
		view.btnRefresh.setOnAction(e -> {
			// Get a new DB-Request to get all Names in the PlayerTable
			model.sendMessage(new Message(MessageType.DBMessage, 0));
		});

		/**
		 * Set the username green if it exit or red for not existing
		 * 
		 * @author L.Weber
		 */
		view.username.textProperty().addListener((observable, oldValue, newValue) -> {
			for (int i = 0; i < searchList.size(); i++) {
				if (searchList.get(i).equals(view.username.getText())) {
					view.username.setStyle("-fx-text-inner-color: green;");
					view.btnLogin.setDisable(false);
					view.btnRefresh.setDisable(false);
					break;
				} else  {
					view.username.setStyle("-fx-text-inner-color: red;");
					view.btnLogin.setDisable(true);
					view.btnRefresh.setDisable(true);
				}
			}
		});

		/**
		 * Watch for the DB-Request
		 * 
		 * @author L.Weber
		 */
		model.getDBRequest().addListener((observable, oldValue, newValue) -> {
			logger.info("DB-Request is here");
			view.btnLogin.setVisible(true);
			view.username.setVisible(true);
			view.password.setVisible(true);
			// fills the userName-Map
			userMap = model.getUserMap();

			for (Entry<Integer, ArrayList<String>> ee : userMap.entrySet()) {
				ArrayList<String> addList = new ArrayList<String>();
				addList.addAll(ee.getValue());

				searchList.add(addList.get(0));
				addList.clear();
			}
		});

		/**
		 * Start the Client-View
		 * 
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
