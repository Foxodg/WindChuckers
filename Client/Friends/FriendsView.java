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

public class FriendsView extends View<GameMenu_Model>{
	private GameMenu_Model model;
    private Stage stage;
    
	private Menu menuFileFriends;
	protected MenuItem menuFileAddFriends;
	protected MenuItem menuFileExitFriends;
    
    public FriendsView (Stage stage, GameMenu_Model model){
    	super(stage,model);
		
		stage.setTitle("Friends");

	}
    
	@Override
	protected Scene create_GUI() {
		ServiceLocator sl = ServiceLocator.getServiceLocator();
		Logger logger = sl.getLogger();
		Translator t = sl.getTranslator();
		
		MenuBar menuBar = new MenuBar();
		menuFileFriends = new Menu();
		menuFileAddFriends = new MenuItem();
		menuFileExitFriends = new MenuItem();
		menuBar.getMenus().add(menuFileFriends);
		
		menuFileFriends.getItems().addAll(menuFileAddFriends, menuFileExitFriends);
		
		GridPane root = new GridPane();
		root.add(menuBar, 0, 0);
		
		updateTexts();
		
        Scene scene = new Scene(root);
		return scene;
	}
	
	protected void updateTexts() {
		Translator t = ServiceLocator.getServiceLocator().getTranslator();

		// Menu strings
		menuFileFriends.setText(t.getString("friends.menu.file"));
		menuFileAddFriends.setText(t.getString("friends.menu.file.addfriends"));
		menuFileExitFriends.setText(t.getString("friends.menu.file.exit"));
	}
}
