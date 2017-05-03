package MainMenu;

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

public class MainMenuView extends View <GameMenu_Model>{
	private GameMenu_Model model;
    private Stage stage;
	
	private Menu menuFileMainMenu;
	protected MenuItem menuFileExitMainMenu;
    
	public MainMenuView (Stage stage, GameMenu_Model model){
		super(stage,model);
		
		stage.setTitle("Lobby");

	}

	@Override
	protected Scene create_GUI() {
		ServiceLocator sl = ServiceLocator.getServiceLocator();
		Logger logger = sl.getLogger();
		Translator t = sl.getTranslator();
		
		MenuBar menuBar = new MenuBar();
		menuFileMainMenu = new Menu();
		menuFileExitMainMenu = new MenuItem();
		menuBar.getMenus().add(menuFileMainMenu);
		
		menuFileMainMenu.getItems().add(menuFileExitMainMenu);
		
		GridPane root = new GridPane();
		root.add(menuBar, 0, 0);
		
		updateTexts();
		
        Scene scene = new Scene(root);
		return scene;
	}
	
	protected void updateTexts() {
		Translator t = ServiceLocator.getServiceLocator().getTranslator();

		// Menu strings
		menuFileMainMenu.setText(t.getString("mainmenu.menu.file"));
		menuFileExitMainMenu.setText(t.getString("mainmenu.menu.file.exit"));
	}

}
