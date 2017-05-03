package Friends;

import WindChuckers_Main.GameMenu_Model;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AddFriendsView {
	private GameMenu_Model model;
    private Stage stage;
    
    public AddFriendsView (Stage stage, GameMenu_Model model){
		this.model = model;
		this.stage = stage;
		
		stage.setTitle("Friends");
		
        BorderPane root = new BorderPane();
		
        Scene scene = new Scene(root);
        stage.setScene(scene);
	}
	
	public void start() {
        stage.show();
    }
    
    /**
     * Stopping the view - just make it invisible
     */
    public void stop() {
        stage.hide();
    }
    
    /**
     * Getter for the stage, so that the controller can access window events
     */
    public Stage getStage() {
        return stage;
    }


}
