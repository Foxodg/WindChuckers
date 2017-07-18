package WindChuckers_Main.newRoundPopup;

import WindChuckers_Main.GameMenu_Model;
import abstractClasses.View;
import commonClasses.ServiceLocator;
import commonClasses.Translator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The view will be shown as soon as a tower reached the last line. The user can decide the direction in which the towers will be ordered the next round
 * @author robin
 */
public class newRoundView  extends View<GameMenu_Model> {
	private GameMenu_Model model;
    private Stage stage;
	protected Label explanation;
	public static Button leftPlay;
	public static Button rightPlay;

	public newRoundView(Stage stage, GameMenu_Model model) {
		super(stage, model);
		stage.setTitle("new Round");
		stage.setAlwaysOnTop(true);
		stage.setResizable(false);
		//stage.sizeToScene();
	}

	@Override
	protected Scene create_GUI() {
		
		BorderPane pane = new BorderPane();
		explanation = new Label();
		leftPlay = new Button();
		rightPlay = new Button();
		
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);
		hbox.setPadding(new Insets(0,0,30,0));
		hbox.getChildren().addAll(leftPlay, rightPlay);
		
		pane.setCenter(hbox);
		pane.setTop(explanation);
		
		this.updateTexts();
		
		GridPane root = new GridPane();
		root.add(pane, 0, 0);
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
		return scene;
	}
	
	protected void updateTexts() {
		Translator t = ServiceLocator.getServiceLocator().getTranslator();

		//Labels
		this.explanation.setText(t.getString("lblNewRoundPopUp"));
	
		//Button
		this.rightPlay.setText(t.getString("btnRightPlay"));
		this.leftPlay.setText(t.getString("btnLeftPlay"));
		
		
	}

}
