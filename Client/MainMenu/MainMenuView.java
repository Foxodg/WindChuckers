 package MainMenu;

import java.util.logging.Logger;

import WindChuckers_Main.GameMenu_Model;
import abstractClasses.View;
import commonClasses.ServiceLocator;
import commonClasses.Translator;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenuView extends View <GameMenu_Model>{
	private GameMenu_Model model;
    private Stage stage;
	
    /**
     * @author Cyrill Füglister
     */
    
	private Menu menuFileMainMenu;
	protected MenuItem menuFileExitMainMenu;
	
	protected Button btnSingleGame;
	protected Button btnStandardGame;
	protected Button btnLongGame;
	protected Button btnMarathonGame;
	protected Button btnFriends;
	
	public MainMenuView (Stage stage, GameMenu_Model model){
		super(stage,model);
		
		stage.setTitle("Main Menu");

	}

	@Override
	protected Scene create_GUI() {
		ServiceLocator sl = ServiceLocator.getServiceLocator();
		Logger logger = sl.getLogger();
		Translator t = sl.getTranslator();
		
		MenuBar menuBar = new MenuBar();
		menuFileMainMenu = new Menu();
		menuFileExitMainMenu = new MenuItem();
		menuBar.getMenus().add(menuFileMainMenu);
		
		menuFileMainMenu.getItems().add(menuFileExitMainMenu);
		
		VBox menuBox = new VBox();

		
		//Button definition and sizing
		btnSingleGame = new Button();
		btnStandardGame = new Button();
		btnLongGame = new Button();
		btnMarathonGame = new Button();
		btnFriends = new Button();
		
		btnSingleGame.setPrefWidth(175);
		btnStandardGame.setPrefWidth(175);
		btnLongGame.setPrefWidth(175);
		btnMarathonGame.setPrefWidth(175);
		btnFriends.setPrefWidth(175);
		
		
		//Add buttons to menubox
		menuBox.getChildren().add(btnSingleGame);
		menuBox.getChildren().add(btnStandardGame);
		menuBox.getChildren().add(btnLongGame);
		menuBox.getChildren().add(btnMarathonGame);
		menuBox.getChildren().add(btnFriends);
		
		
		//Attach menubar and menubox to gridpane
		GridPane root  = new GridPane();
		root.add(menuBar, 0, 0);
		root.add(menuBox, 0, 1);

		
		updateTexts();
		
        Scene scene = new Scene (root);
        scene.getStylesheets().add(
                getClass().getResource("Style.css").toExternalForm());
		return scene;
	}
	
	protected void updateTexts() {
		Translator t = ServiceLocator.getServiceLocator().getTranslator();

		// Menu strings
		menuFileMainMenu.setText(t.getString("mainmenu.menu.file"));
		menuFileExitMainMenu.setText(t.getString("mainmenu.menu.file.exit"));
		
		
		//Textupdate Button
		btnSingleGame.setText(t.getString("btnSingleGame"));
		btnStandardGame.setText(t.getString("btnStandardGame"));
		btnLongGame.setText(t.getString("btnLongGame"));
		btnMarathonGame.setText(t.getString("btnMarathonGame"));
		btnFriends.setText(t.getString("btnFriends"));
		
	}

}