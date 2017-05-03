package WindChuckers_Main;

import WindChuckers_Main.WindChuckers;
import abstractClasses.Controller;
import commonClasses.ServiceLocator;
import commonClasses.Translator;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public class GameMenu_Controller extends Controller<GameMenu_Model, GameMenu_View> {
	ServiceLocator serviceLocator;
	Translator t;
	WindChuckers windChuckers;

	public GameMenu_Controller(GameMenu_Model model, GameMenu_View view) {
		super(model, view);


		
		// register for menu item "Help:About"
		view.menuHelpAbout.setOnAction((event) -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(t.getString("HelpAboutTitle"));
			alert.setHeaderText(t.getString("HelpAboutHeader"));
			alert.setContentText(t.getString("HelpAboutMessage"));
			alert.showAndWait();
		});

		serviceLocator = ServiceLocator.getServiceLocator();
		t = serviceLocator.getTranslator();
		serviceLocator.getLogger().info("Application controller initialized");
		
		
		
		
		/*
		 * Client-Server written by L.Weber
		 */
		
		// Start the Client-View
		view.menuClientGUI.setOnAction(e -> {
			windChuckers = new WindChuckers();
			windChuckers.startClient();
		});
	}

}
