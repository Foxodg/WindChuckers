package Friends;

import java.util.ArrayList;
import java.util.Random;

import Client.ClientThreadForServer;
import Login.LoginModel;
import WindChuckers_Main.GameMenu_Model;
import WindChuckers_Main.GameMenu_View;
import WindChuckers_Main.WindChuckers;
import abstractClasses.Controller;
import abstractClasses.View;
import javafx.event.EventHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class FriendsController extends Controller<GameMenu_Model, FriendsView> {
	private GameMenu_Model model;
	private FriendsView view;
	private WindChuckers windChuckers;
	private AddFriendsView addview;
	ClientThreadForServer clientServer = ClientThreadForServer.getClientServer();
	
	private static long hashCode = ClientThreadForServer.hashCodeStatic;

	public FriendsController(GameMenu_Model model, FriendsView view, AddFriendsView addview) {
		super(model, view);
		this.addview = addview;
		
		//send the hash-code and the name to the server for key / value
		long hash = hashCode;
		model.messageConstructorForName(hash, LoginModel.getUserName());	
		// get the friendsList
		model.setFriends(clientServer.getFriendsList());

		/**
		 * Close the Friend Menu
		 * 
		 * @author L.Weber
		 */
		view.menuFileExitFriends.setOnAction(e -> {
			view.stop();
			addview.stop();
		});

		/**
		 * Start the AddFriendMenu
		 * 
		 * @author L.Weber
		 */
		view.menuFileAddFriends.setOnAction(e -> {
			addview.start();
		});

		view.btnAddFriend.setOnAction(event -> {
			addview.start();
		});

		addview.txtfldSearch.setOnKeyReleased(event -> {

			for (int i = 0; i < addview.btnAddFriend.length; i++) {
				addview.btnAddFriend[i].setVisible(false);
				addview.lblSearchResults[i].setText("");
			}

			ArrayList<String> addfriendsList = new ArrayList<String>();

			addfriendsList = model.getFriendsSearchResults(addview.txtfldSearch.getText());

			for (int i = 0; i < addfriendsList.size(); i++) {
				addview.lblSearchResults[i].setText(addfriendsList.get(i));
				addview.btnAddFriend[i].setVisible(true);
			}
		});

		// Start of FriendsView Part
		for (int i = 0; i < view.btnAcceptFriendsRequest.length; i++) {
			view.btnAcceptFriendsRequest[i].setOnAction(event -> {
				Button btnSource = (Button) event.getSource();
				for (int j = 0; j < view.btnAcceptFriendsRequest.length; j++) {
					if (btnSource == view.btnAcceptFriendsRequest[j]) {
						model.acceptFriendsRequest(view.lblFriendsAndRequests[j].getText(), LoginModel.getUserName());
						view.stop();
						rebuildFriendView();
					}
				}
			});
		}
		for (int i = 0; i < view.btnPlayAgainstFriend.length; i++) {
			view.btnPlayAgainstFriend[i].setOnAction(event -> {
				Button btnSource = (Button) event.getSource();
				for (int j = 0; j < view.btnPlayAgainstFriend.length; j++) {
					if (btnSource == view.btnPlayAgainstFriend[j]) {
						view.stop();
						String friendname[] = view.lblFriendsAndRequests[j].getText().split(" ");
						model.messageConstructorForBuildBinom(LoginModel.getUserName(), friendname[0]);
						WindChuckers windChuckers = WindChuckers.getWindChuckers();
						windChuckers.startLobby();
					}
				}
			});
		}
		for (int i = 0; i < view.btnRemoveFriend.length; i++) {
			view.btnRemoveFriend[i].setOnAction(event -> {
				Button btnSource = (Button) event.getSource();
				for (int j = 0; j < view.btnRemoveFriend.length; j++) {
					if (btnSource == view.btnRemoveFriend[j]) {
						model.removeFriend(view.lblFriendsAndRequests[j].getText());
						view.stop();
						rebuildFriendView();
					}
				}
			});
		}
		for (int i = 0; i < view.btnRefuseFriendsRequest.length; i++) {
			view.btnRefuseFriendsRequest[i].setOnAction(event -> {
				Button btnSource = (Button) event.getSource();
				for (int j = 0; j < view.btnRefuseFriendsRequest.length; j++) {
					if (btnSource == view.btnRefuseFriendsRequest[j]) {
						model.refuseFriendsRequest(view.lblFriendsAndRequests[j].getText());
						view.stop();
						rebuildFriendView();
					}
				}
			});
			
			view.stop();
		}

		ArrayList<String> friendsList = null;
		ArrayList<String> requestsList = null;

		friendsList = model.getFriends();
		requestsList = model.getFriendsRequests();

		for (int i = 0; i < friendsList.size(); i++) {
			view.lblFriendsAndRequests[i].setText(friendsList.get(i) + " (Friend) ");
			view.btnPlayAgainstFriend[i].setVisible(true);
			view.btnRemoveFriend[i].setVisible(true);
		}

		for (int i = friendsList.size(); i < (requestsList.size() + friendsList.size()); i++) {
			view.lblFriendsAndRequests[i].setText(requestsList.get(i - friendsList.size()) + " (Request)");
			view.btnAcceptFriendsRequest[i].setVisible(true);
			view.btnRefuseFriendsRequest[i].setVisible(true);
		}
		// End of FriendsView Part

		// Start of AddFriendsView Part

		// add friend Button for add a new Friend
		for (int i = 0; i < addview.btnAddFriend.length; i++) {
			addview.btnAddFriend[i].setOnAction(event -> {
				Button btnSource = (Button) event.getSource();
				for (int j = 0; j < addview.btnAddFriend.length; j++) {
					if (btnSource == addview.btnAddFriend[j]) {
						model.addFriend(addview.lblSearchResults[j].getText(), LoginModel.getUserName());
						view.stop();
						rebuildFriendView();
					}
				}
			});
			addview.txtfldSearch.setText("");
			model.getFriendsSearchResults(addview.txtfldSearch.getText());

		}

		/**
		 * Watch for the search-Field and get only the right person back
		 * 
		 * @author L.Weber / T.Bosshard
		 */
		addview.txtfldSearch.textProperty().addListener((observable, oldValue, newValue) -> {

			ArrayList<String> addfriendsList = new ArrayList<String>();

			addfriendsList = model.getFriendsSearchResults(addview.txtfldSearch.getText());

			for (int i = 0; i < addfriendsList.size(); i++) {
				addview.lblSearchResults[i].setText(addfriendsList.get(i));
				addview.btnAddFriend[i].setVisible(true);
			}

		});

		/**
		 * Always actuell
		 * 
		 * @author L.Weber / T.Bosshard
		 */
		addview.getStage().focusedProperty().addListener((observable, oldValue, newValue) -> {

			ArrayList<String> addfriendsList = new ArrayList<String>();

			addfriendsList = model.getFriendsSearchResults(addview.txtfldSearch.getText());

			for (int i = 0; i < addfriendsList.size(); i++) {
				addview.lblSearchResults[i].setText(addfriendsList.get(i));
				addview.btnAddFriend[i].setVisible(true);
			}
		});

	}

	public void rebuildFriendView() {
		WindChuckers windChuckers = WindChuckers.getWindChuckers();
		Platform.runLater(() -> {
			windChuckers.startFriends();
		});
	}
	
	public void setHashCode(long hash) {
		hashCode = hash;
	}
	
	public static long getHashCode() {
		return hashCode;
	}

}