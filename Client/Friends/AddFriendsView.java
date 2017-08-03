package Friends;

import java.util.logging.Logger;

import WindChuckers_Main.GameMenu_Model;
import abstractClasses.View;
import commonClasses.ServiceLocator;
import commonClasses.Translator;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * 
 * @author T.Bosshard
 *
 */
public class AddFriendsView extends View<GameMenu_Model> {
	private GameMenu_Model model;
	private Stage stage;
	
	private Menu menuFileAddFriends;
	protected MenuItem menuFileExitAddFriends;
	protected Button btnAddFriend[];
	protected TextField txtfldSearch;
	protected Label lblSearchResults[]; 

	public AddFriendsView(Stage stage, GameMenu_Model model) {
		super(stage, model);

		stage.setTitle("Add Friends");
	}

	@Override
	protected Scene create_GUI() {
		ServiceLocator sl = ServiceLocator.getServiceLocator();
		Logger logger = sl.getLogger();
		Translator t = sl.getTranslator();
		
		
		btnAddFriend = new Button[10];
		for (int i = 0; i < btnAddFriend.length; i++) {
			btnAddFriend[i] = new Button();
 		}
		
		txtfldSearch = new TextField();
		
		lblSearchResults = new Label[10];
		for (int i = 0; i < lblSearchResults.length; i++) {
			lblSearchResults[i] = new Label();
 		}
		
		
		MenuBar menuBar = new MenuBar();
		menuFileAddFriends = new Menu();
		menuFileExitAddFriends = new MenuItem();
		menuBar.getMenus().add(menuFileAddFriends);
		
		menuFileAddFriends.getItems().addAll(menuFileExitAddFriends);
		
		GridPane friendsPane = new GridPane();
		for (int i = 0; i < btnAddFriend.length; i++) {
			friendsPane.add(btnAddFriend[i], 1, i+2);
 		}
		
		friendsPane.add(txtfldSearch, 0, 0);
		
		for (int i = 0; i < lblSearchResults.length; i++) {
			friendsPane.add(lblSearchResults[i], 0, i+2);
 		}
		
		
		GridPane root = new GridPane();
		root.add(menuBar, 0, 0);
		root.add(friendsPane, 0, 1);

		
		
		updateTexts();
		
        Scene scene = new Scene(root);
        scene.getStylesheets().add(
                getClass().getResource("Style.css").toExternalForm());
		return scene;
	}

	private void updateTexts() {
		Translator t = ServiceLocator.getServiceLocator().getTranslator();
		
		//Menu Strings
		menuFileAddFriends.setText(t.getString("addfriends.menu.file"));
		menuFileExitAddFriends.setText(t.getString("addfriends.menu.file.exit"));

		for (int i = 0; i < btnAddFriend.length; i++) {
			btnAddFriend[i].setText(t.getString("friends.button.btnAddFriend"));
			btnAddFriend[i].setVisible(false);
 		}
		
	}

}
