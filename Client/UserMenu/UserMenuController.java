package UserMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.sun.prism.paint.Color;

import WindChuckers_Main.GameMenu_Model;
import abstractClasses.Controller;

public class UserMenuController extends Controller<GameMenu_Model, UserMenuView> {

	private HashMap<Integer, ArrayList<String>> userMap;

	public UserMenuController(GameMenu_Model model, UserMenuView view) {
		super(model, view);
		// get the Users
		userMap = model.getUserMap();
		/**
		 * Stops the view
		 * 
		 * @author L.Weber
		 */
		view.menuFileExitUserMenu.setOnAction(e -> {
			view.stop();
		});

		view.btnSearchUser.setOnAction(e -> {
			for (Entry<Integer, ArrayList<String>> ee : userMap.entrySet()) {
				ArrayList<String> searchList = ee.getValue();
				if (searchList.get(1).equals(view.txtPreName.getText())
						|| searchList.get(2).equals(view.txtSurName.getText())) {
					view.txtIdDis.setText(ee.getKey().toString());
					view.txtPreNameDis.setText(searchList.get(1));
					view.txtSurNameDis.setText(searchList.get(2));
					view.txtWinsDis.setText(searchList.get(4));
					view.lblStatus.setText("Found");
					view.lblStatus.setStyle("-fx-text-fill: #33cc33");
					view.btnChangeUser.setVisible(true);
					view.btnDeleteUser.setVisible(true);
				} else {
					view.lblStatus.setText("Not found");
					view.lblStatus.setStyle("-fx-text-fill: #ff3300");
					view.btnChangeUser.setVisible(false);
					view.btnDeleteUser.setVisible(false);
					view.txtPreNameDis.clear();
					view.txtSurNameDis.clear();
					view.txtWinsDis.clear();
				}
			}
		});

		view.btnChangeUser.setOnAction(e -> {		
			view.txtPreName.setPromptText("Please set a Prename");
			view.txtSurName.setPromptText("Please set a Surname");
			
			if (view.txtPreName.getText() != null && view.txtSurName.getText() != null ) {
				for (Entry<Integer, ArrayList<String>> ee : userMap.entrySet()) {
					ArrayList<String> searchList = ee.getValue();
					//there is no entry
					if(!searchList.get(1).equals(view.txtPreName.getText()) && !searchList.get(2).equals(view.txtSurName.getText())){
						// 2 for an Update
//						model.messageConstructorForDB(2, view.txtPreName.getText(), view.txtSurName.getText());
//						model.messageConstructorForDB(0);
					} else {
						view.lblStatus.setText("Entry is there");
						view.lblStatus.setStyle("-fx-text-fill: #ff3300");
					}
				}
			} else {
				view.lblStatus.setText("Fill Prename and Surname");
				view.lblStatus.setStyle("-fx-text-fill: #ff3300");
			}
			
		});

		view.btnCreateUser.setOnAction(e -> {
			view.txtPreName.setPromptText("Please set a Prename");
			view.txtSurName.setPromptText("Please set a Surname");
			int idcounter = 0;
			
			//get the last id
			for(int i = 0; i < userMap.size(); i++){
				idcounter++;
			}

			if (view.txtPreName.getText() != null && view.txtSurName.getText() != null ) {
				for (Entry<Integer, ArrayList<String>> ee : userMap.entrySet()) {
					ArrayList<String> searchList = ee.getValue();	
					//there is no entry
					if(!searchList.get(1).equals(view.txtPreName.getText()) && !searchList.get(2).equals(view.txtSurName.getText())){
						// 1 for an Insert
						model.messageConstructorForDBInsertUpdate(1, idcounter+1, view.txtUserNameEnter.getText(), view.txtPreNameEnter.getText(), view.txtSurNameEnter.getText(), view.pwfPasswordEnter.getText());
						model.messageConstructorForDB(0);
						view.lblStatus.setText("Is created");
						view.lblStatus.setStyle("-fx-text-fill: #33cc33");
					} else {
						view.lblStatus.setText("Entry is there");
						view.lblStatus.setStyle("-fx-text-fill: #ff3300");
					}
				}
			} else {
				view.lblStatus.setText("Fill Prename and Surname");
				view.lblStatus.setStyle("-fx-text-fill: #ff3300");
			}
		});
		
		view.btnDeleteUser.setOnAction(e -> {
			// 3 for Delete
//			model.messageConstructorForDB(3, Integer.parseInt(view.txtIdDis.getText()));
			view.lblStatus.setText("Is deleted");
			view.lblStatus.setStyle("-fx-text-fill: #33cc33");
		});
	}

}
