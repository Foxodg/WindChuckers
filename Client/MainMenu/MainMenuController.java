package MainMenu;

import com.sun.media.jfxmedia.logging.Logger;

import Friends.FriendsController;
import Friends.FriendsView;
import Login.LoginModel;
import WindChuckers_Main.GameMenu_Controller;
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
			main.getLoginView().getStage().show();
		});
		
		
		view.btnSingleGame.setOnAction(e -> {
			//set this Player on Player1
			LoginModel.setForcePlayer(true);
			//send a Message to the Server that we need an AI
			model.messageContstructorForAISingle(100, 100, 15, 15, 25, Double.POSITIVE_INFINITY);
			//also set the SingleAI-Flag
			LoginModel.setSingleAI(true);
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
			//set this Player on Player1
			LoginModel.setForcePlayer(true);
			//send a Message to the Server that we need an AI
			model.messageContstructorForAISingle(100, 100, 15, 15, 25, Double.POSITIVE_INFINITY);
			//also set the SingleAI-Flag
			LoginModel.setSingleAI(true);
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
			//set this Player on Player1
			LoginModel.setForcePlayer(true);
			//send a Message to the Server that we need an AI
			model.messageContstructorForAISingle(100, 100, 15, 15, 25, Double.POSITIVE_INFINITY);
			//also set the SingleAI-Flag
			LoginModel.setSingleAI(true);
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
			//set this Player on Player1
			LoginModel.setForcePlayer(true);
			//send a Message to the Server that we need an AI
			model.messageContstructorForAISingle(100, 100, 15, 15, 25, Double.POSITIVE_INFINITY);
			//also set the SingleAI-Flag
			LoginModel.setSingleAI(true);
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
			//set the DoubleAI-Flag to false
			LoginModel.setDoubleAI(false);
			//set the SingleAI-Flag to false
			LoginModel.setSingleAI(false);
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
