package MainMenu;

import com.sun.media.jfxmedia.logging.Logger;

import Friends.FriendsView;
import WindChuckers_Main.GameMenu_Model;
import WindChuckers_Main.WindChuckers;
import abstractClasses.Controller;
import commonClasses.ServiceLocator;

public class MainMenuController extends Controller<GameMenu_Model, MainMenuView> {
	private GameMenu_Model model;
	private MainMenuView view;
	private WindChuckers windChuckers;

	public MainMenuController(final WindChuckers main, GameMenu_Model model, MainMenuView view) {
		super(model,view);
		
		/**
		 * @author Cyrill Füglister
		 */
		view.menuFileExitMainMenu.setOnAction(e -> {
			view.getStage().hide();
		});
		
		
		view.btnSingleGame.setOnAction(e -> {
			//set option Sinlge-Game
			model.setGameMode(1);
			main.startApp();
			//close the MainMenu_View
			view.getStage().hide();
			//set the SimpleBooleanProperty to start without more options
			model.setDisableSingeleGame(true);
			ServiceLocator.getServiceLocator().getLogger().info("SingleGame Disables all edits");
			model.setDisableSingeleGame(false);
		});
		
		
		view.btnStandardGame.setOnAction(e -> {
			//set option Standard-Game
			model.setGameMode(3);
			main.startApp();
			//close the MainMenu_View
			view.getStage().hide();
			//set the SimpleBooleanProperty to start without more options
			model.setDisableSingeleGame(true);
			ServiceLocator.getServiceLocator().getLogger().info("StandardGame Disables all edits");
			model.setDisableSingeleGame(false);
		});
		
		view.btnLongGame.setOnAction(e -> {
			//set option Long-Game
			model.setGameMode(7);
			main.startApp();
			//close the MainMenu_View
			view.getStage().hide();
			//set the SimpleBooleanProperty to start without more options
			model.setDisableSingeleGame(true);
			ServiceLocator.getServiceLocator().getLogger().info("LongGame Disables all edits");
			model.setDisableSingeleGame(false);
		});
		
		view.btnMarathonGame.setOnAction(e -> {
			//set option Marathon-Game
			model.setGameMode(15);
			//start the main-App
			main.startApp();
			//close the MainMenu_View
			view.getStage().hide();
			//set the SimpleBooleanProperty to start without more options
			model.setDisableSingeleGame(true);
			ServiceLocator.getServiceLocator().getLogger().info("MarathonGame Disables all edits");
			model.setDisableSingeleGame(false);
		});
		
		
		view.btnFriends.setOnAction(e -> {
			windChuckers = WindChuckers.getWindChuckers();
			windChuckers.startFriends();
			view.getStage().hide();
			//start the main-App only in the Background
			main.startApp();
			main.getGameMenuView().getStage().close();
			//set the SimpleBooleanProperty to start the game with options
			model.setEnableFriendsGame(true);
			ServiceLocator.getServiceLocator().getLogger().info("Friends start for a Friend-Game set edits in game");
			model.setEnableFriendsGame(false);
		});
		
	}

}
