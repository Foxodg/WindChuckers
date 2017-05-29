package Lobby;

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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LobbyView extends View<GameMenu_Model> {
	private GameMenu_Model model;
    private Stage stage;
    
	private Menu menuFileLobby;
	protected MenuItem menuFileExitLobby;
	

	
	public LobbyView (Stage stage, GameMenu_Model model){
		super(stage,model);
		
		stage.setTitle("Lobby");

	}

	@Override
	protected Scene create_GUI() {
		ServiceLocator sl = ServiceLocator.getServiceLocator();
		Logger logger = sl.getLogger();
		Translator t = sl.getTranslator();
		
		MenuBar menuBar = new MenuBar();
		menuFileLobby = new Menu();
		menuFileExitLobby = new MenuItem();
		menuBar.getMenus().add(menuFileLobby);
		
		menuFileLobby.getItems().add(menuFileExitLobby);
		
		GridPane root = new GridPane();
		root.add(menuBar, 0, 0);
		
		updateTexts();
		
        Scene scene = new Scene(root);
		return scene;
	}
	
	protected void updateTexts() {
		Translator t = ServiceLocator.getServiceLocator().getTranslator();

		// Menu strings
		menuFileLobby.setText(t.getString("lobby.menu.file"));
		menuFileExitLobby.setText(t.getString("lobby.menu.file.exit"));
		
	}

}
