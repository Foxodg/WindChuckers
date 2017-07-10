package UserMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Logger;

import Message.Message;
import Message.Message.MessageType;
import WindChuckers_Main.GameMenu_Model;
import abstractClasses.Controller;
import commonClasses.ServiceLocator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class UserMenuController extends Controller<GameMenu_Model, UserMenuView> {
	ServiceLocator serviceLocator = ServiceLocator.getServiceLocator();
	Logger logger = serviceLocator.getLogger();

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
			fillPersonList();
		});

		view.btnChangeUser.setOnAction(e -> {
			view.txtPreName.setPromptText("Please set a Prename");
			view.txtSurName.setPromptText("Please set a Surname");

			if (view.txtPreName.getText() != null && view.txtSurName.getText() != null) {
				for (Entry<Integer, ArrayList<String>> ee : userMap.entrySet()) {
					ArrayList<String> searchList = ee.getValue();
					// there is no entry
					if (!searchList.get(1).equals(view.txtPreNameEnter.getText())
							&& !searchList.get(2).equals(view.txtSurNameEnter.getText())
							&& !searchList.get(0).equals(view.txtUserNameEnter.getText())) {
						// Old password must be there
						if (searchList.get(3).equals(view.pwfOldPassword.getText())) {
							// 2 for an Update
							if (!view.txtIdDis.getText().isEmpty()) {

								boolean username = false;
								boolean prename = false;
								boolean surname = false;
								boolean password = false;

								if (!view.txtUserNameEnter.getText().isEmpty()) {
									username = true;
								}
								if (!view.txtPreNameEnter.getText().isEmpty()) {
									prename = true;
								}
								if (!view.txtSurNameEnter.getText().isEmpty()) {
									surname = true;
								}
								if (!view.pwfPasswordEnter.getText().isEmpty()) {
									password = true;
								}

								if (username == true) {
									if (prename == true) {
										if (surname == true) {
											if (password == true) {
												model.messageConstructorForDBInsertUpdate(2,
														Integer.parseInt(view.txtIdDis.getText()),
														view.txtUserNameEnter.getText(), view.txtPreNameEnter.getText(),
														view.txtSurNameEnter.getText(),
														view.pwfPasswordEnter.getText());
											} else {
												// no password
												model.messageConstructorForDBInsertUpdate(2,
														Integer.parseInt(view.txtIdDis.getText()),
														view.txtUserNameEnter.getText(), view.txtPreNameEnter.getText(),
														view.txtSurNameEnter.getText(), view.pwfOldPassword.getText());
											}
											// no surname
										} else {
											// but a password?
											if (password = true) {
												model.messageConstructorForDBInsertUpdate(2,
														Integer.parseInt(view.txtIdDis.getText()),
														view.txtUserNameEnter.getText(), view.txtPreNameEnter.getText(),
														view.txtSurNameDis.getText(), view.pwfPasswordEnter.getText());
											} else {
												// no password and no surname
												model.messageConstructorForDBInsertUpdate(2,
														Integer.parseInt(view.txtIdDis.getText()),
														view.txtUserNameEnter.getText(), view.txtPreNameEnter.getText(),
														view.txtSurNameDis.getText(), view.pwfOldPassword.getText());
											}
										}
									} else {
										// is nor prename there
										if (surname == true) {
											// but a surname?
											if (password == true) {
												// or a password?
												model.messageConstructorForDBInsertUpdate(2,
														Integer.parseInt(view.txtIdDis.getText()),
														view.txtUserNameEnter.getText(), view.txtPreNameDis.getText(),
														view.txtSurNameEnter.getText(),
														view.pwfPasswordEnter.getText());
											} else {
												// no password no prename
												model.messageConstructorForDBInsertUpdate(2,
														Integer.parseInt(view.txtIdDis.getText()),
														view.txtUserNameEnter.getText(), view.txtPreNameDis.getText(),
														view.txtSurNameEnter.getText(), view.pwfOldPassword.getText());
											}
										} else {
											// no prename no password no surname
											model.messageConstructorForDBInsertUpdate(2,
													Integer.parseInt(view.txtIdDis.getText()),
													view.txtUserNameEnter.getText(), view.txtPreNameDis.getText(),
													view.txtSurNameDis.getText(), view.pwfOldPassword.getText());
										}
									}
								} else {
									// no username there
									if (prename == true) {
										// but a prename=
										if (surname == true) {
											// or a surname
											if (password == true) {
												// or a password
												model.messageConstructorForDBInsertUpdate(2,
														Integer.parseInt(view.txtIdDis.getText()),
														view.txtUserNameDis.getText(), view.txtPreNameEnter.getText(),
														view.txtSurNameEnter.getText(),
														view.pwfPasswordEnter.getText());
											} else {
												// no username no password
												model.messageConstructorForDBInsertUpdate(2,
														Integer.parseInt(view.txtIdDis.getText()),
														view.txtUserNameDis.getText(), view.txtPreNameEnter.getText(),
														view.txtSurNameEnter.getText(), view.pwfOldPassword.getText());
											}
										} else {
											// no username no password no
											// surname
											model.messageConstructorForDBInsertUpdate(2,
													Integer.parseInt(view.txtIdDis.getText()),
													view.txtUserNameDis.getText(), view.txtPreNameEnter.getText(),
													view.txtSurNameDis.getText(), view.pwfOldPassword.getText());
										}
									} else {
										// no username no password no surname
										// noprename
										// give a value - there is no value
										view.lblStatus.setText("Nothing is selected");
										view.lblStatus.setStyle("-fx-text-fill: #ff3300");
									}
								}
							} else {
								// no racord is active
								view.lblStatus.setText("Select an Entry");
								view.lblStatus.setStyle("-fx-text-fill: #ff3300");
							}
						} else {
							// old password is not fill in
							view.lblStatus.setText("Please enter the old Password");
							view.lblStatus.setStyle("-fx-text-fill: #ff3300");
						}
					} else {
						// entry is there
						view.lblStatus.setText("Entry is there");
						view.lblStatus.setStyle("-fx-text-fill: #ff3300");
					}
				}
			}

		});

		model.getDBRequest().addListener((observable, oldValue, newValue) -> {
			fillPersonList();
		});

		view.btnGetAll.setOnAction(e -> {

			model.sendMessage(new Message(MessageType.DBMessage, 0));
			userMap = model.getUserMap();

			ListView<ArrayList<String>> list = new ListView<ArrayList<String>>();
			ObservableList<ArrayList<String>> items = FXCollections.observableArrayList();

			for (Entry<Integer, ArrayList<String>> ee : userMap.entrySet()) {
				ArrayList<String> searchList = ee.getValue();
				items.add(searchList);
			}

			list.setItems(items);
			Stage stage = new Stage();
			GridPane root = new GridPane();

			root.add(list, 0, 0);
			Scene scene = new Scene(root, 200, 200);
			stage.setScene(scene);
			scene.getStylesheets().add(getClass().getResource("Example.css").toExternalForm());
			stage.show();

		});

		view.btnCreateUser.setOnAction(e -> {
			view.txtPreName.setPromptText("Please set a Prename");
			view.txtSurName.setPromptText("Please set a Surname");
			int idcounter = 0;

			// get the last id
			for (Entry<Integer, ArrayList<String>> ee : userMap.entrySet()) {
				idcounter = ee.getKey();
			}

			if (view.txtPreName.getText() != null && view.txtSurName.getText() != null) {
				for (Entry<Integer, ArrayList<String>> ee : userMap.entrySet()) {
					ArrayList<String> searchList = ee.getValue();
					// there is no entry
					if (!searchList.get(1).equals(view.txtPreNameEnter.getText())
							&& !searchList.get(2).equals(view.txtSurNameEnter.getText())
							&& !searchList.get(0).equals(view.txtUserNameEnter.getText())
							&& !view.pwfPasswordEnter.getText().isEmpty()) {
						// 1 for an Insert
						model.messageConstructorForDBInsertUpdate(1, idcounter + 1, view.txtUserNameEnter.getText(),
								view.txtPreNameEnter.getText(), view.txtSurNameEnter.getText(),
								view.pwfPasswordEnter.getText());
						model.messageConstructorForDB(0);
						view.lblStatus.setText("Is created");
						view.lblStatus.setStyle("-fx-text-fill: #33cc33");
					} else {
						view.lblStatus.setText("Entry is there, or please give a password");
						view.lblStatus.setStyle("-fx-text-fill: #ff3300");
					}
				}
			} else {
				view.lblStatus.setText("Fill Prename and Surname");
				view.lblStatus.setStyle("-fx-text-fill: #ff3300");
			}
			updateGUI();
		});

		view.btnDeleteUser.setOnAction(e -> {
			// 3 for Delete
			if (view.pwfOldPassword.getText() != null) {
				//is a password given?
				for (Entry<Integer, ArrayList<String>> ee : userMap.entrySet()) {
					ArrayList<String> searchList = ee.getValue();
					if (searchList.get(3).equals(view.pwfOldPassword.getText()) && searchList.get(0).equals(view.txtUserNameDis.getText())) {
						//is the password correct and the user is the correct
						model.messageConstructorForDBUpdate(3, Integer.parseInt(view.txtIdDis.getText()));
						updateGUI();
						view.lblStatus.setText("Is deleted");
						view.lblStatus.setStyle("-fx-text-fill: #33cc33");
					} else {
						view.lblStatus.setText("Fill the correct Password");
						view.lblStatus.setStyle("-fx-text-fill: #ff3300");
					}
				}
			} else {
				view.lblStatus.setText("Fill Password");
				view.lblStatus.setStyle("-fx-text-fill: #ff3300");
			}
		});

		//Listener for collapse
		view.updatePanel.heightProperty().addListener((observable, oldValue, newValue) -> {
			view.stage.sizeToScene();
		});
	}

	public void fillPersonList() {
		for (Entry<Integer, ArrayList<String>> ee : userMap.entrySet()) {
			ArrayList<String> searchList = ee.getValue();
			logger.info("Fill Person List");
			// is for searching a entry$
			try {
				if (searchList.get(1).equals(view.txtPreName.getText())
						|| searchList.get(2).equals(view.txtSurName.getText())
						|| searchList.get(0).equals(view.txtUserName.getText())) {
					view.txtIdDis.setText(ee.getKey().toString());
					view.txtUserNameDis.setText(searchList.get(0));
					view.txtPreNameDis.setText(searchList.get(1));
					view.txtSurNameDis.setText(searchList.get(2));
					view.txtWinsDis.setText(searchList.get(4));
					view.lblStatus.setText("Found");
					view.lblStatus.setStyle("-fx-text-fill: #33cc33");
					view.btnChangeUser.setVisible(true);
					view.btnDeleteUser.setVisible(true);
					break;
				} else {
					view.lblStatus.setText("Not found");
					view.lblStatus.setStyle("-fx-text-fill: #ff3300");
					view.btnChangeUser.setVisible(false);
					view.btnDeleteUser.setVisible(false);
					view.txtPreNameDis.clear();
					view.txtSurNameDis.clear();
					view.txtWinsDis.clear();
					view.txtIdDis.clear();
					view.txtUserNameDis.clear();
				}

			} catch (Exception ex) {
				logger.warning("Not found");
			}
		}
	}

	private void updateGUI() {
		Platform.runLater(() -> {
			// acutal. the Map from DB
			logger.info("Update GUI");
			model.sendMessage(new Message(MessageType.DBMessage, 0));
			userMap = model.getUserMap();

			// fill all in
			for (Entry<Integer, ArrayList<String>> ee : userMap.entrySet()) {
				ArrayList<String> searchList = ee.getValue();
				view.txtIdDis.setText(ee.getKey().toString());
				view.txtUserNameDis.setText(searchList.get(0));
				view.txtPreNameDis.setText(searchList.get(1));
				view.txtSurNameDis.setText(searchList.get(2));
				view.txtWinsDis.setText(searchList.get(4));
				view.lblStatus.setText("Found");
				view.lblStatus.setStyle("-fx-text-fill: #33cc33");
				view.btnChangeUser.setVisible(true);
				view.btnDeleteUser.setVisible(true);
			}
		});
	}
}
