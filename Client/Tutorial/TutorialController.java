package Tutorial;

import java.io.File;
import java.net.MalformedURLException;

import WindChuckers_Main.GameMenu_Model;
import abstractClasses.Controller;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TutorialController extends Controller<GameMenu_Model, TutorialView> {
	private TutorialModel tutorialModel;

	public TutorialController(GameMenu_Model model, TutorialView view, TutorialModel tutorialModel) {
		super(model, view);
		this.tutorialModel = tutorialModel;
		
		
		view.btnOk.setOnAction(e -> {
			view.stop();
		});
		
		view.btnBasicRules.setOnAction(e -> {
			String localUrl = null;
			
			try {
				File file = new File("Standard.gif");
				localUrl = file.toURI().toURL().toString();
				Image localImage = new Image(localUrl, false);
			} catch (MalformedURLException ex) {
			}
			
			Image img = new Image(localUrl);
			ImageView imgView = new ImageView(img);
			
			Stage stage = new Stage();
			GridPane root = new GridPane();

			root.add(imgView, 0, 0);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		});
		
		
		view.btnLongPlay.setOnAction(e -> {
			String localUrl = null;
			
			try {
				File file = new File("Long.gif");
				localUrl = file.toURI().toURL().toString();
				Image localImage = new Image(localUrl, false);
			} catch (MalformedURLException ex) {
			}
			
			Image img = new Image(localUrl);
			ImageView imgView = new ImageView(img);
			
			Stage stage = new Stage();
			GridPane root = new GridPane();

			root.add(imgView, 0, 0);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.sizeToScene();
			stage.show();
		});
		
		view.btnMarathonPlay.setOnAction(e -> {
			String localUrl = null;
			
			try {
				File file = new File("Marathon.gif");
				localUrl = file.toURI().toURL().toString();
				Image localImage = new Image(localUrl, false);
			} catch (MalformedURLException ex) {
			}
			
			Image img = new Image(localUrl);
			ImageView imgView = new ImageView(img);
			
			Stage stage = new Stage();
			GridPane root = new GridPane();

			root.add(imgView, 0, 0);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		});
		
		view.menuFileExitTutorialMenu.setOnAction(e -> {
			view.stop();
		});
	}

}
