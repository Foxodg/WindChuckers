package Login;

import java.util.logging.Logger;

import WindChuckers_Main.GameMenu_Model;
import abstractClasses.View;
import commonClasses.ServiceLocator;
import commonClasses.Translator;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class LoginView extends View <GameMenu_Model>{
	private GameMenu_Model model;
	private Stage stage;
	
	private Menu menuFileLogin;
	protected MenuItem menuFileExitLogin;
	private Menu menuClient;
	public MenuItem menuClientGUI;

	protected TextField username;
	protected PasswordField password;
	protected Button btnLogin;
	protected Button btnUserMenu;
	protected Button btnRefresh;

	public LoginView(Stage stage, GameMenu_Model model) {
		super(stage,model);

		stage.setTitle("Login");

	}
	
	@Override
	protected Scene create_GUI() {
		ServiceLocator sl = ServiceLocator.getServiceLocator();
		Logger logger = sl.getLogger();
		Translator t = sl.getTranslator();
		
		btnLogin = new Button();
		btnLogin.setVisible(false);
		btnUserMenu = new Button();
		btnRefresh = new Button();
		password = new PasswordField();
		username = new TextField();
		username.setPromptText("username");
		username.setVisible(false);
		password.setPromptText("password");
		//TODO password 
		password.setText("test");
		password.setVisible(false);
		HBox loginBox = new HBox();
		HBox userBox = new HBox();
		loginBox.getChildren().addAll(username, password ,btnLogin);
		userBox.getChildren().addAll(btnUserMenu, btnRefresh);
		
		MenuBar menuBar = new MenuBar();
		menuFileLogin = new Menu();
		menuFileExitLogin = new MenuItem();
		menuFileLogin.getItems().add(menuFileExitLogin);
		menuClient = new Menu();
		menuClientGUI = new MenuItem();
		menuClient.getItems().add(menuClientGUI);
		menuBar.getMenus().addAll(menuFileLogin,menuClient);
		

		
		GridPane root = new GridPane();
		root.add(menuBar, 0, 0);
		root.add(userBox, 0, 1);
		root.add(loginBox, 0, 2);
		
		updateTexts();
		
        Scene scene = new Scene(root);
		return scene;
	}
	
	protected void updateTexts() {
		Translator t = ServiceLocator.getServiceLocator().getTranslator();

		// Menu strings
		menuFileLogin.setText(t.getString("login.menu.file"));
		menuFileExitLogin.setText(t.getString("login.menu.file.exit"));
		menuClient.setText(t.getString("program.menu.client"));
		menuClientGUI.setText(t.getString("program.menu.clientGUI"));
		
		//Buttons
		btnLogin.setText(t.getString("btnLogin"));
		btnUserMenu.setText(t.getString("btnUserMenu"));
		btnRefresh.setText(t.getString("btnRefresh"));
		
	}


}
