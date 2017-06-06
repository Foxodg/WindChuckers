package Tutorial;

import java.io.File;
import java.net.MalformedURLException;

import WindChuckers_Main.GameMenu_Model;
import WindChuckers_Main.WindChuckers;
import abstractClasses.View;
import commonClasses.ServiceLocator;
import commonClasses.Translator;
import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class StandardView extends View<GameMenu_Model> {
	protected Label lblHeader;
	protected Label lblBody1;
	protected Label lblBody2;
	protected Button btnOk;	
	
	protected String sBody1 = null;
	protected String sBody2 = null;
	
	public StandardView(Stage stage, GameMenu_Model model) {
		super(stage, model);
		
		stage.setTitle("Standard Play");
	}
    

	@Override
	protected Scene create_GUI() {
		
		String localUrl = null;
		
		try {
			File file = new File("dragon-animated-gif-17.gif");
			localUrl = file.toURI().toURL().toString();
			Image localImage = new Image(localUrl, false);
		} catch (MalformedURLException ex) {
		}
		
		Image img = new Image(localUrl);
		ImageView imgView = new ImageView(img);
		lblHeader = new Label();
		lblBody1 = new Label();
		lblBody2 = new Label();
		Label lblfirstSpace = new Label();
		Label lblSpace = new Label();
		btnOk = new Button();
		
		HBox hBox = new HBox();
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        hBox.getChildren().addAll(spacer, btnOk);
		
		
		VBox vBox = new VBox();
		vBox.getChildren().addAll(lblfirstSpace, lblBody1, lblSpace, lblBody2);
		
		
		BorderPane pane = new BorderPane();
		pane.setTop(lblHeader);
		pane.setRight(imgView);
		pane.setCenter(vBox);
		pane.setBottom(hBox);
		
		updateTexts();

		GridPane root = new GridPane();

		root.add(pane, 0, 0);
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
		
		return scene;
	}
	
	protected void updateTexts() {
		Translator t = ServiceLocator.getServiceLocator().getTranslator();

		//Labels
		this.lblHeader.setText(t.getString("lblHeader"));
		this.sBody1 = t.getString("sBody1");
		this.sBody2 = t.getString("sBody2");
		
		//Button
		this.btnOk.setText(t.getString("btnOk"));
		
	}
	
	public void giveText(){
		updateTexts();
		AnimateText(lblBody1, sBody1);
		Task<Void> sleeper = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				try {
					Thread.sleep(8000);
				} catch (InterruptedException e){
				}
				return null;
			}
		};
		sleeper.setOnSucceeded(e -> {
			AnimateText(lblBody2, sBody2);
		});
		new Thread(sleeper).start();;
	}
	
	public void AnimateText(Label lbl, String descImp) {
	    String content = descImp;
	    final Animation animation = new Transition() {
	        {
	            setCycleDuration(Duration.millis(8000));
	        }

	        protected void interpolate(double frac) {
	            final int length = content.length();
	            final int n = Math.round(length * (float) frac);
	            lbl.setText(content.substring(0, n));
	        }
	    };
	    animation.play();

	}

}
