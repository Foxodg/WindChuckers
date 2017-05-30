package UserMenu;


import java.util.logging.Logger;

import WindChuckers_Main.GameMenu_Model;
import abstractClasses.View;
import commonClasses.ServiceLocator;
import commonClasses.Translator;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class UserMenuView extends View<GameMenu_Model> {

	//Menu
	private Menu menuFileUserMenu;
	protected MenuItem menuFileExitUserMenu;
	
	//Buttons
	Button btnCreateUser;
	Button btnDeleteUser;
	Button btnChangeUser;
	Button btnSearchUser;
	
	// TexFields
	TextField txtPreName;
	TextField txtSurName;
	
	TextField txtIdDis;
	TextField txtUserNameDis;
	TextField txtPreNameDis;
	TextField txtSurNameDis;
	TextField txtWinsDis;
	
	TextField txtPreNameEnter;
	TextField txtSurNameEnter;
	TextField txtUserNameEnter;
	PasswordField pwfPasswordEnter;
	PasswordField pwfOldPassword;
	
	//Label
	Label lblPreName;
	Label lblSurName;
	Label lblIdDis;
	Label lblUserNameDis;
	Label lblPreNameDis;
	Label lblSurNameDis;
	Label lblWinsDis;
	Label lblStatus;
	Label lblnewUser;
	
	//Panel
	VBox rightVBox;


	public UserMenuView(Stage stage, GameMenu_Model model) {
		super(stage, model);

		stage.setTitle("User Menu");
	}

	@Override
	protected Scene create_GUI() {
		ServiceLocator sl = ServiceLocator.getServiceLocator();
		Logger logger = sl.getLogger();
		Translator t = sl.getTranslator();

		MenuBar menuBar = new MenuBar();
		menuFileUserMenu = new Menu();
		menuFileExitUserMenu = new MenuItem();
		
		SplitPane mainWindow = new SplitPane();
		SplitPane centerPane = new SplitPane();
		SplitPane leftPanel = new SplitPane();
		SplitPane rightPanel = new SplitPane();
		rightVBox = new VBox();
		
		mainWindow.setOrientation(Orientation.HORIZONTAL);
		mainWindow.getItems().addAll(leftPanel,centerPane, rightPanel);
		
		mainWindow.setDividerPosition(0, 0.2);
		
		this.txtPreNameDis = new TextField();
		txtPreNameDis.setDisable(true);
		this.txtSurNameDis = new TextField();
		txtSurNameDis.setDisable(true);
		this.txtWinsDis = new TextField();
		txtWinsDis.setDisable(true);
		this.txtIdDis = new TextField();
		txtIdDis.setDisable(true);
		this.txtUserNameDis = new TextField();
		txtUserNameDis.setDisable(true);
		
		lblIdDis = new Label();
		lblUserNameDis = new Label();
		lblPreNameDis = new Label();
		lblSurNameDis = new Label();
		lblWinsDis = new Label();
		
		centerPane.setOrientation(Orientation.VERTICAL);
		centerPane.getItems().addAll(lblIdDis, txtIdDis, lblUserNameDis, txtUserNameDis, lblPreNameDis, txtPreNameDis,lblSurNameDis, txtSurNameDis, lblWinsDis, txtWinsDis);
		centerPane.setDividerPosition(0, 0.20);
		centerPane.setPrefSize(200, 200);
		
		this.btnChangeUser = new Button();
		btnChangeUser.setVisible(false);
		this.btnCreateUser = new Button();
		this.btnDeleteUser = new Button();
		btnDeleteUser.setVisible(false);
		this.btnSearchUser = new Button();
		
		Label seperator1 = new Label();
		Label seperator2 = new Label();
		Label seperator3 = new Label();
		
		leftPanel.setOrientation(Orientation.VERTICAL);
		leftPanel.getItems().addAll(this.btnSearchUser,seperator1, this.btnCreateUser,seperator2, this.btnChangeUser,seperator3, this.btnDeleteUser);
		leftPanel.setDividerPosition(0, 0.1);
		leftPanel.setPrefSize(200, 200);
		
		lblnewUser = new Label();
		lblPreName = new Label();
		lblSurName = new Label();
		txtPreName = new TextField();
		txtSurName = new TextField();
		
		rightPanel.setOrientation(Orientation.VERTICAL);
		rightPanel.getItems().addAll(lblPreName,txtPreName,lblSurName,txtSurName );
		rightPanel.setDividerPosition(0, 0.20);
		rightPanel.setPrefSize(200, 200);
		
		this.txtPreNameEnter = new TextField();
		this.txtSurNameEnter = new TextField();
		this.txtUserNameEnter = new TextField();
		this.pwfPasswordEnter = new PasswordField();
		this.pwfOldPassword = new PasswordField();
		
		rightVBox.getChildren().addAll(lblnewUser, txtPreNameEnter, txtSurNameEnter, txtUserNameEnter, pwfPasswordEnter, pwfOldPassword);
		
		HBox bottomBox = new HBox();
		lblStatus = new Label();
		lblStatus.setFont(new Font(30.0));
		bottomBox.getChildren().addAll(lblStatus);
		
		GridPane root = new GridPane();
		root.add(menuBar, 0, 0);
		root.add(mainWindow, 0, 1);
		root.add(bottomBox, 0, 2);
		root.add(rightVBox, 1, 1);

		updateTexts();

		Scene scene = new Scene(root);
        scene.getStylesheets().add(
                getClass().getResource("Example.css").toExternalForm());
		return scene;
	};

	protected void updateTexts() {
		Translator t = ServiceLocator.getServiceLocator().getTranslator();

		// Menu strings
		menuFileUserMenu.setText(t.getString("usermenu.menu.file"));
		menuFileExitUserMenu.setText(t.getString("usermenu.menu.file.exit"));
		
		//Buttons
		btnChangeUser.setText(t.getString("btnChangeUser"));
		btnCreateUser.setText(t.getString("btnCreateUser"));
		btnDeleteUser.setText(t.getString("btnDeleteUser"));
		btnSearchUser.setText(t.getString("btnSearchUser"));
		
		//Labels
		lblPreName.setText(t.getString("lblPreName"));
		lblSurName.setText(t.getString("lblSurName"));
		lblUserNameDis.setText(t.getString("lblUserNameDis"));
		lblPreNameDis.setText(t.getString("lblPreName"));
		lblSurNameDis.setText(t.getString("lblSurName"));
		lblWinsDis.setText(t.getString("lblWinsDis"));
		lblIdDis.setText(t.getString("lblIdDis"));
		lblnewUser.setText(t.getString("lblnewUser"));
		
		//TextField
		txtPreNameEnter.setPromptText(t.getString("txtPreNameEnter"));
		txtSurNameEnter.setPromptText(t.getString("txtSurNameEnter"));
		txtUserNameEnter.setPromptText(t.getString("txtUserNameEnter"));
		pwfPasswordEnter.setPromptText(t.getString("pwfPasswordEnter"));
		pwfOldPassword.setPromptText(t.getString("pwfOldPassword"));

	}

}
