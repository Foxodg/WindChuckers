package Friends;

import WindChuckers_Main.GameMenu_Model;
import abstractClasses.Controller;

public class FriendsController extends Controller<GameMenu_Model, FriendsView>{
	private GameMenu_Model model;
	private FriendsView view;
	
	public FriendsController (GameMenu_Model model, FriendsView view){
		super(model,view);
		
		view.menuFileExitFriends.setOnAction(e -> {
			view.stop();
		});
	}

}
