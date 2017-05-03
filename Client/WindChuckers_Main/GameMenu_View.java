package WindChuckers_Main;

import java.util.Locale;
import java.util.logging.Logger;

import abstractClasses.View;
import commonClasses.ServiceLocator;
import commonClasses.Translator;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public class GameMenu_View extends View<GameMenu_Model> {
	Menu menuFile;
	Menu menuFileLanguage;
	Menu menuHelp;
	Menu menuClient;
	MenuItem menuHelpAbout;
	MenuItem menuFileRestart;
	MenuItem menuFileExit;
	MenuItem menuClientGUI;


	public GameMenu_View(Stage stage, GameMenu_Model model) {
		super(stage, model);
		stage.setTitle("WindChuckers Kamisado");

		ServiceLocator.getServiceLocator().getLogger().info("Application view initialized");
	}

	@Override
	protected Scene create_GUI() {
		ServiceLocator sl = ServiceLocator.getServiceLocator();
		Logger logger = sl.getLogger();
		Translator t = sl.getTranslator();

		MenuBar menuBar = new MenuBar();
		menuFile = new Menu();
		menuFileLanguage = new Menu();
		menuFileRestart = new MenuItem();
		menuFileExit = new MenuItem();
		menuFile.getItems().addAll(menuFileRestart,menuFileLanguage,menuFileExit);

		for (Locale locale : sl.getLocales()) {
			MenuItem language = new MenuItem(locale.getLanguage());
			menuFileLanguage.getItems().add(language);
			language.setOnAction(event -> {
				sl.setTranslator(new Translator(locale.getLanguage()));
				updateTexts();
			});
		}

		menuHelp = new Menu();
		menuClient = new Menu();
		menuBar.getMenus().addAll(menuFile, menuHelp, menuClient);

		menuHelpAbout = new MenuItem();
		menuHelp.getItems().add(menuHelpAbout);
		

		menuClientGUI = new MenuItem();
		menuClient.getItems().add(menuClientGUI);

		GridPane root = new GridPane();
		root.add(menuBar, 0, 0);
		
		updateTexts();

		Scene scene = new Scene(root);
		return scene;
		

	}

	protected void updateTexts() {
		Translator t = ServiceLocator.getServiceLocator().getTranslator();

		// Menu strings
		menuFile.setText(t.getString("program.menu.file"));
		menuFileRestart.setText(t.getString("program.menu.file.restart"));
		menuFileLanguage.setText(t.getString("program.menu.file.language"));
		menuFileExit.setText(t.getString("program.menu.file.exit"));
		menuHelp.setText(t.getString("program.menu.help"));
		menuHelpAbout.setText(t.getString("program.menu.help.helpabout"));
		menuClient.setText(t.getString("program.menu.client"));
		menuClientGUI.setText(t.getString("program.menu.clientGUI"));
		
		// Buttons

		// Labels

		// Other controls

	}
}