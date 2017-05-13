package WindChuckers_Main.Model_Extend;

import abstractClasses.Model;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class Board extends Model {

	public Board(){
	
	}
	
	public GridPane getBoard(){
		Button button = new Button();
		GridPane board = new GridPane();
		
		board.add(button, 1, 1);
		
		return board;
	}
	
	}
	
