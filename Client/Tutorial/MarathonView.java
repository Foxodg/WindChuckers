package Tutorial;

import java.io.File;
import java.net.MalformedURLException;

import WindChuckers_Main.GameMenu_Model;
import WindChuckers_Main.WindChuckers;
import abstractClasses.View;
import commonClasses.ServiceLocator;
import commonClasses.Translator;
import javafx.animation.Animation;
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

/**
 * 
 * @author L.Weber
 *
 */
public class MarathonView extends View<GameMenu_Model> {
	protected Label lblHeaderMarathon;
	protected Label lblBodyMarathon1;
	protected Label lblBodyMarathon2;
	protected Button btnOkMarathon;	
	
	protected String sBodyMarathon1 = null;
	protected String sBodyMarathon2 = null;
	
	public MarathonView(Stage stage, GameMenu_Model model) {
		super(stage, model);
		
		stage.setTitle("Marathon Play");
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
		lblHeaderMarathon = new Label();
		lblBodyMarathon1 = new Label();
		lblBodyMarathon2 = new Label();
		Label lblfirstSpace = new Label();
		Label lblSpace = new Label();
		btnOkMarathon = new Button();
		
		HBox hBox = new HBox();
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        hBox.getChildren().addAll(spacer, btnOkMarathon);
		
		
		VBox vBox = new VBox();
		vBox.getChildren().addAll(lblfirstSpace, lblBodyMarathon1, lblSpace, lblBodyMarathon2);
		
		
		BorderPane pane = new BorderPane();
		pane.setTop(lblHeaderMarathon);
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
		this.lblHeaderMarathon.setText(t.getString("lblHeaderMarathon"));
		this.sBodyMarathon1 = t.getString("sBodyMarathon1");
		this.sBodyMarathon2 = t.getString("sBodyMarathon2");
		
		//Button
		this.btnOkMarathon.setText(t.getString("btnOk"));
		
	}
	
	public void giveText(){
		updateTexts();
		AnimateText(lblBodyMarathon1, sBodyMarathon1);
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
			AnimateText(lblBodyMarathon2, sBodyMarathon2);
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
