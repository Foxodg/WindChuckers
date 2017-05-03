package Login;

import java.util.logging.Logger;

import WindChuckers_Main.GameMenu_Model;
import abstractClasses.View;
import commonClasses.ServiceLocator;
import commonClasses.Translator;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginView extends View <GameMenu_Model>{
	private GameMenu_Model model;
	private Stage stage;
	
	private Menu menuFileLogin;
	protected MenuItem menuFileExitLogin;

	public LoginView(Stage stage, GameMenu_Model model) {
		super(stage,model);

		stage.setTitle("Login");


	}
	
	@Override
	protected Scene create_GUI() {
		ServiceLocator sl = ServiceLocator.getServiceLocator();
		Logger logger = sl.getLogger();
		Translator t = sl.getTranslator();
		
		MenuBar menuBar = new MenuBar();
		menuFileLogin = new Menu();
		menuFileExitLogin = new MenuItem();
		menuBar.getMenus().add(menuFileLogin);
		
		menuFileLogin.getItems().add(menuFileExitLogin);
		
		GridPane root = new GridPane();
		root.add(menuBar, 0, 0);
		
		updateTexts();
		
        Scene scene = new Scene(root);
		return scene;
	}
	
	protected void updateTexts() {
		Translator t = ServiceLocator.getServiceLocator().getTranslator();

		// Menu strings
		menuFileLogin.setText(t.getString("login.menu.file"));
		menuFileExitLogin.setText(t.getString("login.menu.file.exit"));
	}


}
