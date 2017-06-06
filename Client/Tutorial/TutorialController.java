package Tutorial;

import java.io.File;
import java.net.MalformedURLException;

import WindChuckers_Main.GameMenu_Model;
import abstractClasses.Controller;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import WindChuckers_Main.WindChuckers;

public class TutorialController extends Controller<GameMenu_Model, TutorialView> {
	private TutorialModel tutorialModel;
	private StandardView sView;
	private LongView lView;
	private MarathonView mView;

	public TutorialController(GameMenu_Model model, TutorialView view, TutorialModel tutorialModel, StandardView sView, LongView lView, MarathonView mView) {
		super(model, view);
		this.tutorialModel = tutorialModel;
		this.sView = sView;
		this.lView = lView;
		this.mView = mView;
		
		
		view.btnOk.setOnAction(e -> {
			view.stop();
		});
		
		view.btnBasicRules.setOnAction(e -> {
			sView.giveText();
			sView.start();
		});
		
		
		view.btnLongPlay.setOnAction(e -> {
			lView.giveText();
			lView.start();
		});
		
		view.btnMarathonPlay.setOnAction(e -> {
			mView.giveText();
			mView.start();
		});
		
		view.menuFileExitTutorialMenu.setOnAction(e -> {
			view.stop();
		});
		
		/**
		 * Adjusting the Stage in the sView with the text
		 * and also give an Action to the Button
		 * @author L.Weber
		 */
		sView.lblBody1.textProperty().addListener((observable, oldValue, newValue) -> {
			sView.getStage().sizeToScene();
		});
		
		sView.lblBody2.textProperty().addListener((observable, oldValue, newValue) -> {
			sView.getStage().sizeToScene();
		});
		
		sView.btnOk.setOnAction(e -> {
			sView.stop();
		});

		
		/**
		 * Adjusting the Stage in the lView with the text
		 * and also give an Action to the Button
		 * @author L.Weber
		 */
		lView.lblBodyLong1.textProperty().addListener((observable, oldValue, newValue) -> {
			lView.getStage().sizeToScene();
		});
		
		lView.lblBodyLong2.textProperty().addListener((observable, oldValue, newValue) -> {
			lView.getStage().sizeToScene();
		});
		
		lView.btnOkLong.setOnAction(e -> {
			lView.stop();
		});
		
		/**
		 * Adjusting the Stage in the mView with the text
		 * and also give an Action to the Button
		 * @author L.Weber
		 */
		mView.lblBodyMarathon1.textProperty().addListener((observable, oldValue, newValue) -> {
			mView.getStage().sizeToScene();
		});
		
		mView.lblBodyMarathon2.textProperty().addListener((observable, oldValue, newValue) -> {
			mView.getStage().sizeToScene();
		});
		
		mView.btnOkMarathon.setOnAction(e -> {
			mView.stop();
		});
	}

}
