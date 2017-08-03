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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class FriendsView extends View<GameMenu_Model>{
	private GameMenu_Model model;
    private Stage stage;
    
	private Menu menuFileFriends;
	protected MenuItem menuFileAddFriends;
	protected MenuItem menuFileExitFriends;
	protected Button btnAddFriend;
	protected Label lblFriendsAndRequests[];
	protected Button btnAcceptFriendsRequest[];
	protected Button btnRefuseFriendsRequest[];
	protected Button btnPlayAgainstFriend[];
	protected Button btnRemoveFriend[];
    
    public FriendsView (Stage stage, GameMenu_Model model){
    	super(stage,model);
		
		stage.setTitle("Friends");

	}
    
	@Override
	protected Scene create_GUI() {
		//@autor T.Bosshard
		ServiceLocator sl = ServiceLocator.getServiceLocator();
		Logger logger = sl.getLogger();
		Translator t = sl.getTranslator();
		btnAddFriend = new Button();
		
		lblFriendsAndRequests = new Label[20];
		for (int i = 0; i < lblFriendsAndRequests.length; i++) {
 			lblFriendsAndRequests[i] = new Label();
 		}
		
		btnAcceptFriendsRequest = new Button[20];
		for (int i = 0; i < btnAcceptFriendsRequest.length; i++) {
			btnAcceptFriendsRequest[i] = new Button();
 		}
		
		
		btnRefuseFriendsRequest = new Button[20];
		for (int i = 0; i < btnRefuseFriendsRequest.length; i++) {
			btnRefuseFriendsRequest[i] = new Button();
 		}
		
		btnPlayAgainstFriend = new Button[20];
		for (int i = 0; i < btnPlayAgainstFriend.length; i++) {
			btnPlayAgainstFriend[i] = new Button();
 		}
		
		btnRemoveFriend = new Button[20];
		for (int i = 0; i < btnRemoveFriend.length; i++) {
			btnRemoveFriend[i] = new Button();
 		}
		
		MenuBar menuBar = new MenuBar();
		menuFileFriends = new Menu();
		menuFileAddFriends = new MenuItem();
		menuFileExitFriends = new MenuItem();
		menuBar.getMenus().add(menuFileFriends);
		
		menuFileFriends.getItems().addAll(menuFileAddFriends, menuFileExitFriends);
		
		GridPane friendPane = new GridPane();
		friendPane.add(btnAddFriend, 0, 0);
		
		for (int i = 0; i < (lblFriendsAndRequests.length)*2; i=i+2) {
			friendPane.add(lblFriendsAndRequests[i/2], 0, i+2);
 		}
		for (int i = 0; i < (btnAcceptFriendsRequest.length)*2; i=i+2) {
			friendPane.add(btnAcceptFriendsRequest[i/2], 1, i+2);
 		}
		for (int i = 0; i < (btnPlayAgainstFriend.length)*2; i=i+2) {
			friendPane.add(btnPlayAgainstFriend[i/2], 2, i+3);
 		}
		for (int i = 0; i < (btnRemoveFriend.length)*2; i=i+2) {
			friendPane.add(btnRemoveFriend[i/2], 2, i+2);
 		}
		for (int i = 0; i < (btnRefuseFriendsRequest.length)*2; i=i+2) {
			friendPane.add(btnRefuseFriendsRequest[i/2], 1, i+3);
 		}
		
		
		GridPane root = new GridPane();
		root.add(menuBar, 0, 0);
		root.add(friendPane, 0, 1);
		
		
		updateTexts();
		
        Scene scene = new Scene(root);
        scene.getStylesheets().add(
                getClass().getResource("Style.css").toExternalForm());
		return scene;
	}
	
	protected void updateTexts() {
		//@autor T.Bosshard
		Translator t = ServiceLocator.getServiceLocator().getTranslator();

		// Menu strings
		menuFileFriends.setText(t.getString("friends.menu.file"));
		menuFileAddFriends.setText(t.getString("friends.menu.file.addfriends"));
		menuFileExitFriends.setText(t.getString("friends.menu.file.exit"));
		
		// Buttons
		btnAddFriend.setText(t.getString("friends.button.btnAddFriend"));
	
		
		for (int i = 0; i < btnAcceptFriendsRequest.length; i++) {
			btnAcceptFriendsRequest[i].setText(t.getString("friends.button.btnAcceptFriendsRequest"));
			btnAcceptFriendsRequest[i].setVisible(false);
 		}
		for (int i = 0; i < btnPlayAgainstFriend.length; i++) {
			btnPlayAgainstFriend[i].setText(t.getString("friends.button.btnPlayAgainstFriend"));
			btnPlayAgainstFriend[i].setVisible(false);
 		}
		for (int i = 0; i < btnRemoveFriend.length; i++) {
			btnRemoveFriend[i].setText(t.getString("friends.button.btnRemoveFriend"));
			btnRemoveFriend[i].setVisible(false);
 		}
		for (int i = 0; i < btnRefuseFriendsRequest.length; i++) {
			btnRefuseFriendsRequest[i].setText(t.getString("friends.button.btnRefuseFriendsRequest"));
			btnRefuseFriendsRequest[i].setVisible(false);
 		}		
	}
}