package Tutorial;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.logging.Logger;

import WindChuckers_Main.GameMenu_Model;
import abstractClasses.View;
import commonClasses.ServiceLocator;
import commonClasses.Translator;
import javafx.animation.Animation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * 
 * @author L.Weber
 *
 */
public class TutorialView extends View<GameMenu_Model> {
	private TutorialModel tutorialModel;

	private Menu menuFileTutorialMenu;
	public MenuItem menuFileExitTutorialMenu;

	//Label 
	private Label welcome;
	private Label description;

	//Button
	protected Button btnOk;
	protected Button btnBasicRules;
	protected Button btnLongPlay;
	protected Button btnMarathonPlay;
	
	//Urls
	private String localUrl;

	public TutorialView(Stage stage, GameMenu_Model model, TutorialModel tutorialModel) {
		super(stage, model);
		this.tutorialModel = tutorialModel;
		
		stage.setTitle("Tutorial");

	}

	@Override
	protected Scene create_GUI() {
		ServiceLocator sl = ServiceLocator.getServiceLocator();
		Logger logger = sl.getLogger();
		Translator t = sl.getTranslator();

		//Label
		welcome = new Label();
		description = new Label();

		//Button
		btnOk = new Button();
		btnBasicRules = new Button();
		btnLongPlay = new Button();
		btnMarathonPlay = new Button();

		MenuBar menuBar = new MenuBar();
		menuFileTutorialMenu = new Menu();
		menuFileExitTutorialMenu = new MenuItem();
		menuFileTutorialMenu.getItems().add(menuFileExitTutorialMenu);
		menuBar.getMenus().add(menuFileTutorialMenu);

		try {
			File file = new File("dragon-animated-gif-5.gif");
			localUrl = file.toURI().toURL().toString();
			Image localImage = new Image(localUrl, false);

		} catch (MalformedURLException ex) {
		}
		
		Image img = new Image(localUrl);
		ImageView imgView = new ImageView(img);
		
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
		
		HBox btnBox = new HBox();
		btnBox.getChildren().addAll(btnBasicRules,btnLongPlay,btnMarathonPlay,spacer,btnOk);
		
		BorderPane pane = new BorderPane();
		pane.setTop(welcome);
		pane.setCenter(imgView);
		pane.setBottom(description);

		GridPane root = new GridPane();
		root.add(menuBar, 0, 0);
		root.add(pane, 0, 1);
		root.add(btnBox, 0, 3);

		updateTexts();

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());

		return scene;
	}

	protected void updateTexts() {
		Translator t = ServiceLocator.getServiceLocator().getTranslator();

		// Menu
		menuFileTutorialMenu.setText(t.getString("menuFileTutorialMenu"));
		menuFileExitTutorialMenu.setText(t.getString("menuFileExitTutorialMenu"));

		// Label
		description.setText(t.getString("description"));
		welcome.setText(t.getString("welcome"));

		// Button
		btnOk.setText(t.getString("btnOk"));
		btnBasicRules.setText(t.getString("btnBasicRules"));
		btnLongPlay.setText(t.getString("btnLongPlay"));
		btnMarathonPlay.setText(t.getString("btnMarathonPlay"));
		

	}

}
