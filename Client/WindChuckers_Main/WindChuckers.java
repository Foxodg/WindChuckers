package WindChuckers_Main;


import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import AI.AI_Controller;
import AI.AI_View;
import Client.ClientController;
import Client.ClientThreadForServer;
import Client.ClientView;
import Friends.AddFriendsView;
import Friends.FriendsController;
import Friends.FriendsView;
import Lobby.LobbyController;
import Lobby.LobbyView;
import Login.LoginController;
import Login.LoginModel;
import Login.LoginView;
import MainMenu.MainMenuController;
import MainMenu.MainMenuView;
import Server.TextAreaHandler;
import Tutorial.LongView;
import Tutorial.MarathonView;
import Tutorial.StandardView;
import Tutorial.TutorialController;
import Tutorial.TutorialModel;
import Tutorial.TutorialView;
import UserMenu.UserMenuController;
import UserMenu.UserMenuView;
import WindChuckers_Main.Model_Extend.Board;
import WindChuckers_Main.Model_Extend.Movement;
import WindChuckers_Main.Model_Extend.Player;
import WindChuckers_Main.Model_Extend.Position;
import WindChuckers_Main.Model_Extend.SumoTower;
import commonClasses.ServiceLocator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import splashScreen.Splash_Controller;
import splashScreen.Splash_Model;
import splashScreen.Splash_View;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public class WindChuckers extends Application {
    private static WindChuckers mainProgram; // singleton
    
    //Splash
    private Splash_View splashView;

    //Views
    private LobbyView lobbyView;
    private LoginView loginView;
    private FriendsView friendsView;
    private AddFriendsView addFriendsView;
    private MainMenuView mainMenuView;
    private WindChuckers_Main.newRoundPopup.newRoundView newRoundView;
    
    //Game
    private GameMenu_View view;
    private GameMenu_Controller controller;
    private GameMenu_Model model;
    
    //client
    private ClientView clientView;
    private ClientThreadForServer clientserver;
    private ClientController clientController;
    
    //AI
    private AI_View aiView;
    private AI_Controller aiController;
    
    //Model extended
    private Board board;
    private Movement movement;
    private Position position;
    private Player player;
    
    
    //Friends
    private FriendsController friendscontroller;
    
    //Login
    private LoginModel loginModel;
    
    //User Menu
    private UserMenuView userMenuView;
    
    private ServiceLocator serviceLocator; // resources, after initialization

    public static void main(String[] args) {
        launch(args);
    }
    
    public WindChuckers(){
    	
    }

	/**
	 * Factory method for returning the singleton board
	 * 
	 * @param mainClass
	 *            The main class of this program
	 * @return WindChuckers
	 */
	public static WindChuckers getWindChuckers() {
		if (mainProgram == null)
			mainProgram = new WindChuckers();
		return mainProgram;
	}
    
    /**
     * Note: This method is called on the main thread, not the JavaFX
     * Application Thread. This means that we cannot display anything to the
     * user at this point. Since we want to show a splash screen, this means
     * that we cannot do any real initialization here.
     * 
     * This implementation ensures that the application is a singleton; only one
     * per JVM-instance. On client installations this is not necessary (each
     * application runs in its own JVM). However, it can be important on server
     * installations.
     * 
     * Why is it important that only one instance run in the JVM? Because our
     * initialized resources are a singleton - if two programs instances were
     * running, they would use (and overwrite) each other's resources!
     */
    @Override
    public void init() {
        if (mainProgram == null) {
            mainProgram = this;
        } else {
            Platform.exit();
        }
    }

    /**
     * This method is called after init(), and is called on the JavaFX
     * Application Thread, so we can display a GUI. We have two GUIs: a splash
     * screen and the application. Both of these follow the MVC model.
     * 
     * We first display the splash screen. The model is where all initialization
     * for the application takes place. The controller updates a progress-bar in
     * the view, and (after initialization is finished) calls the startApp()
     * method in this class.
     */
    @Override
    public void start(Stage primaryStage) {
        // Create and display the splash screen and model
        Splash_Model splashModel = new Splash_Model();
        splashView = new Splash_View(primaryStage, splashModel);
        new Splash_Controller(this, splashModel, splashView);
        splashView.start();

        // Display the splash screen and begin the initialization
        splashModel.initialize();
    }

    /**
     * This method is called when the splash screen has finished initializing
     * the application. The initialized resources are in a ServiceLocator
     * singleton. Our task is to now create the application MVC components, to
     * hide the splash screen, and to display the application GUI.
     * 
     * Multitasking note: This method is called from an event-handler in the
     * Splash_Controller, which means that it is on the JavaFX Application
     * Thread, which means that it is allowed to work with GUI components.
     * http://docs.oracle.com/javafx/2/threads/jfxpub-threads.htm
     */
    public void startApp() {
        Stage appStage = new Stage();

        // Initialize the application MVC components. Note that these components
        // can only be initialized now, because they may depend on the
        // resources initialized by the splash screen
        model = GameMenu_Model.getGameModel();
        view = new GameMenu_View(appStage, model,board,movement,position, player);
        controller = new GameMenu_Controller(model, view, newRoundView, board,movement,position, player);
        
        // Resources are now initialized
        serviceLocator = ServiceLocator.getServiceLocator();

        // Close the splash screen, and set the reference to null, so that all
        // Splash_XXX objects can be garbage collected
        splashView.stop();
        splashView = null;

        view.start();
    }
    
    /**
     * Start the View and Controller for the Client
     * @author L.Weber
     */
    public void startClient() {
    	serviceLocator = ServiceLocator.getServiceLocator();
    	
        TextAreaHandler textAreaHandler = new TextAreaHandler();
        textAreaHandler.setLevel(Level.INFO);
        Logger defaultLogger = serviceLocator.getLogger();
        defaultLogger.addHandler(textAreaHandler);
    	
    	Stage clientStage = new Stage();
    	model = GameMenu_Model.getGameModel();
    	clientserver = ClientThreadForServer.getClientServer();
    	clientView = new ClientView(clientStage, model, textAreaHandler.getTextArea());
    	clientController = new ClientController(model, clientView, clientserver);
    	   	
//    	clientView.start();
    }
    
    /**
     * Client must start again and again
     * Singleton with the view and the controller is impossible
     * @author L.Weber
     */
    public void getStartetClient() {
    	serviceLocator = ServiceLocator.getServiceLocator();
    	
        TextAreaHandler textAreaHandler = new TextAreaHandler();
        textAreaHandler.setLevel(Level.INFO);
        Logger defaultLogger = serviceLocator.getLogger();
        defaultLogger.addHandler(textAreaHandler);
    	
    	Stage clientStage = new Stage();
    	model = GameMenu_Model.getGameModel();
    	clientserver = ClientThreadForServer.getClientServer();
    	clientView = clientView.getClientView();
    	clientController = clientController.getClientController();
    	   	
    	clientView.start();
    }
    
    /**
     * Start the View and Controller for the AI Controller
     * @author L.Weber
     */
    public void startAI() {
    	serviceLocator = ServiceLocator.getServiceLocator();
    	
    	Stage aiStage = new Stage();
    	model = GameMenu_Model.getGameModel();
    	aiView = new AI_View(aiStage, model);
    	aiController = new AI_Controller(model, aiView);
    	
    	aiView.start();
    }
    
    /**
     * Start the View and Controller for the Lobby
     * @author L.Weber
     */
    public void startLobby() {
    	Stage lobbyStage = new Stage();
    	model = GameMenu_Model.getGameModel();
    	lobbyView = new LobbyView(lobbyStage, model);
    	new LobbyController(model,lobbyView);
    	
    	serviceLocator = ServiceLocator.getServiceLocator();
    	
    	lobbyView.start();
    }
    
    /**
     * Start the View and Controller for the Login
     * @author L.Weber
     */
    public void startLogin() {
    	Stage loginStage = new Stage();
    	model = GameMenu_Model.getGameModel();
    	loginModel = new LoginModel();
    	loginView = new LoginView(loginStage, model);
    	new LoginController(this,model,loginView,loginModel);
    	
    	serviceLocator = ServiceLocator.getServiceLocator();
    	
    	loginView.start();
    	
    }
    
    /**
     * Start the View and Controller for the FriendMenu
     * @author L.Weber
     */
    public void startFriends() {
    	Stage friendsStage = new Stage();
    	Stage addFriendsStage = new Stage();
    	model = GameMenu_Model.getGameModel();
    	friendsView = new FriendsView(friendsStage, model);
    	addFriendsView = new AddFriendsView(addFriendsStage, model);
    	friendscontroller = new FriendsController(model,friendsView, addFriendsView);

    	
    	serviceLocator = ServiceLocator.getServiceLocator();
    	
    	friendsView.start();
    }
    
    /**
     * Start the View and Controller for the Main Menu
     * @author L.Weber
     */
	public void startMainMenu() {
		Stage mainMenuStage = new Stage();
		model = GameMenu_Model.getGameModel();
		mainMenuView = new MainMenuView(mainMenuStage, model);
		new MainMenuController(this,model,mainMenuView);
		
		serviceLocator = ServiceLocator.getServiceLocator();
		
    	//get the friends
		model.messageConstructorForDB(4);
		
		mainMenuView.start();
		
	}
	
	/**
	 * Start the View and Controller for the UserMenu
	 * @author L.Weber
	 */
	public void startUserMenu(){
		Stage userMenuStage = new Stage();
		model = GameMenu_Model.getGameModel();
		userMenuView = new UserMenuView(userMenuStage, model);
		new UserMenuController(model,userMenuView);
		
		serviceLocator = ServiceLocator.getServiceLocator();
		
		userMenuView.start();
	}
	
	/**
	 * Start the Tutorial
	 * @author L.Weber
	 */
	public void startTutorial() {
		Stage tutorialStage = new Stage();
		Stage standardStage = new Stage();
		Stage longStage = new Stage();
		Stage marathonStage = new Stage();
		model = GameMenu_Model.getGameModel();
		TutorialModel tutorialModel = new TutorialModel();
		TutorialView tutorialView = new TutorialView(tutorialStage, model, tutorialModel);	
		StandardView sView = new StandardView(standardStage, model);
		LongView lView = new LongView(longStage, model);
		MarathonView mView = new MarathonView(marathonStage, model);
		new TutorialController(model, tutorialView, tutorialModel, sView, lView, mView);
		
		serviceLocator = ServiceLocator.getServiceLocator();
		tutorialView.start();
	}

	/**
	 * A pop up will be shown. The Winner can choose in which direction the towers will be ordered
	 * @author robin
	 */
	public void  startNewRound() {
		Stage newRoundStage = new Stage();
    	model = GameMenu_Model.getGameModel();
    	newRoundView = new WindChuckers_Main.newRoundPopup.newRoundView(newRoundStage, model);	
    	newRoundView.start();
	}
	
    /**
     * The stop method is the opposite of the start method. It provides an
     * opportunity to close down the program, including GUI components. If the
     * start method has never been called, the stop method may or may not be
     * called.
     * 
     * Make the GUI invisible first. This prevents the user from taking any
     * actions while the program is ending.
     */
    @Override
    public void stop() {
        serviceLocator.getConfiguration().save();
        if (view != null) {
            // Make the view invisible
        	Platform.exit();
        }

        // More cleanup code as needed

        serviceLocator.getLogger().info("Application terminated");
    }

    // Static getter for a reference to the main program object
    protected static WindChuckers getMainProgram() {
        return mainProgram;
    }
    
    public void startMainMenuView() {
    	this.mainMenuView.getStage().show();
    }
    
    public void startGameMenuViewe() {
    	this.view.getStage().show();
    }

	


}
