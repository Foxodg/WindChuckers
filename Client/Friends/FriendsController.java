package Friends;

import WindChuckers_Main.GameMenu_Model;
import WindChuckers_Main.WindChuckers;
import abstractClasses.Controller;

public class FriendsController extends Controller<GameMenu_Model, FriendsView>{
	private GameMenu_Model model;
	private FriendsView view;
	private WindChuckers windChuckers;
	private AddFriendsView addview;
	
	public FriendsController (GameMenu_Model model, FriendsView view){
		super(model,view);
		
		/**
		 * Close the Friend Menu
		 * @author L.Weber
		 */
		view.menuFileExitFriends.setOnAction(e -> {
			view.stop();
			addview.stop();
		});
		
		/**
		 * Start the AddFriendMenu
		 * @author L.Weber
		 */
		view.menuFileAddFriends.setOnAction(e -> {
			addview.start();
		});

		
	}
	
	/**
	 * Setter for the AddFriendView
	 * @param addview
	 * @author L.Weber
	 */
	public void setAddFriends(AddFriendsView addview){
		this.addview = addview;
	}

}
