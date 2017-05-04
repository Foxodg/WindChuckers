package Friends;

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

public class AddFriendsView extends View<GameMenu_Model> {
	private GameMenu_Model model;
	private Stage stage;
	
	private Menu menuFileAddFriends;
	protected MenuItem menuFileExitAddFriends;

	public AddFriendsView(Stage stage, GameMenu_Model model) {
		super(stage, model);

		stage.setTitle("Add Friends");
	}

	@Override
	protected Scene create_GUI() {
		ServiceLocator sl = ServiceLocator.getServiceLocator();
		Logger logger = sl.getLogger();
		Translator t = sl.getTranslator();
		
		MenuBar menuBar = new MenuBar();
		menuFileAddFriends = new Menu();
		menuFileExitAddFriends = new MenuItem();
		menuBar.getMenus().add(menuFileAddFriends);
		
		menuFileAddFriends.getItems().addAll(menuFileExitAddFriends);
		
		GridPane root = new GridPane();
		root.add(menuBar, 0, 0);
		
		updateTexts();
		
        Scene scene = new Scene(root);
		return scene;
	}

	private void updateTexts() {
		Translator t = ServiceLocator.getServiceLocator().getTranslator();
		
		//Menu Strings
		menuFileAddFriends.setText(t.getString("addfriends.menu.file"));
		menuFileExitAddFriends.setText(t.getString("addfriends.menu.file.exit"));
		
	}

}
