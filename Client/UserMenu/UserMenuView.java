package UserMenu;


import java.util.logging.Logger;

import WindChuckers_Main.GameMenu_Model;
import abstractClasses.View;
import commonClasses.ServiceLocator;
import commonClasses.Translator;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
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
	protected Button btnCreateUser;
	protected Button btnDeleteUser;
	protected Button btnChangeUser;
	protected Button btnSearchUser;
	protected Button btnGetAll;
	
	// TexFields
	protected TextField txtPreName;
	protected TextField txtSurName;
	protected TextField txtUserName;
	
	protected TextField txtIdDis;
	protected TextField txtUserNameDis;
	protected TextField txtPreNameDis;
	protected TextField txtSurNameDis;
	protected TextField txtWinsDis;
	
	protected TextField txtPreNameEnter;
	protected TextField txtSurNameEnter;
	protected TextField txtUserNameEnter;
	protected PasswordField pwfPasswordEnter;
	protected PasswordField pwfOldPassword;
	
	//Label
	protected Label lblPreName;
	protected Label lblSurName;
	protected Label lblUserName;
	protected Label lblIdDis;
	protected Label lblUserNameDis;
	protected Label lblPreNameDis;
	protected Label lblSurNameDis;
	protected Label lblWinsDis;
	protected Label lblStatus;
	protected Label lblSearch;
	
	//Panel
	protected VBox rightVBox;
	protected TitledPane updatePanel;
	protected Stage stage;


	public UserMenuView(Stage stage, GameMenu_Model model) {
		super(stage, model);
		this.stage = stage;

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
		menuFileUserMenu.getItems().add(menuFileExitUserMenu);
		menuBar.getMenus().add(menuFileUserMenu);
		
		SplitPane mainWindow = new SplitPane();
		SplitPane centerPane = new SplitPane();
		SplitPane leftPanel = new SplitPane();
		SplitPane rightPanel = new SplitPane();
		updatePanel = new TitledPane();
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
		centerPane.setDividerPosition(0, 0.30);
		centerPane.setPrefSize(200, 200);
		
		this.btnChangeUser = new Button();
		btnChangeUser.setVisible(false);
		this.btnCreateUser = new Button();
		this.btnDeleteUser = new Button();
		btnDeleteUser.setVisible(false);
		this.btnSearchUser = new Button();
		this.btnGetAll = new Button();
		
		Label seperator1 = new Label();
		Label seperator2 = new Label();
		Label seperator3 = new Label();
		Label seperator4 = new Label();
		
		leftPanel.setOrientation(Orientation.VERTICAL);
		leftPanel.getItems().addAll(this.btnSearchUser,seperator1, this.btnCreateUser,seperator2, this.btnChangeUser,seperator3, this.btnDeleteUser,seperator4, btnGetAll);
		leftPanel.setDividerPosition(0, 0.1);
		leftPanel.setPrefSize(200, 200);
		
		lblPreName = new Label();
		lblSurName = new Label();
		lblUserName = new Label();
		lblSearch = new Label();
		txtPreName = new TextField();
		txtSurName = new TextField();
		txtUserName = new TextField();
		
		rightPanel.setOrientation(Orientation.VERTICAL);
		rightPanel.getItems().addAll(lblSearch, lblPreName,txtPreName,lblSurName,txtSurName, lblUserName, txtUserName );
		rightPanel.setDividerPosition(0, 0.30);
		rightPanel.setPrefSize(200, 200);
		
		this.txtPreNameEnter = new TextField();
		this.txtSurNameEnter = new TextField();
		this.txtUserNameEnter = new TextField();
		this.pwfPasswordEnter = new PasswordField();
		this.pwfOldPassword = new PasswordField();
		
		VBox vBox = new VBox();
		vBox.getChildren().addAll(txtPreNameEnter, txtSurNameEnter, txtUserNameEnter, pwfPasswordEnter, pwfOldPassword);
		updatePanel.setContent(vBox);
		updatePanel.setExpanded(false);
		updatePanel.setText("Change User / create User");
				
		
		HBox bottomBox = new HBox();
		lblStatus = new Label();
		lblStatus.setFont(new Font(30.0));
		bottomBox.getChildren().addAll(lblStatus);
		
		GridPane root = new GridPane();
		root.add(menuBar, 0, 0);
		root.add(mainWindow, 0, 1);
		root.add(bottomBox, 0, 2);
		root.add(updatePanel, 0, 3);

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
		btnGetAll.setText(t.getString("btnGetAll"));
		
		//Labels
		lblPreName.setText(t.getString("lblPreName"));
		lblSurName.setText(t.getString("lblSurName"));
		lblUserNameDis.setText(t.getString("lblUserNameDis"));
		lblPreNameDis.setText(t.getString("lblPreName"));
		lblSurNameDis.setText(t.getString("lblSurName"));
		lblWinsDis.setText(t.getString("lblWinsDis"));
		lblIdDis.setText(t.getString("lblIdDis"));
		lblSearch.setText(t.getString("lblSearch"));
		lblUserName.setText(t.getString("lblUserName"));
		
		//TextField
		txtPreNameEnter.setPromptText(t.getString("txtPreNameEnter"));
		txtSurNameEnter.setPromptText(t.getString("txtSurNameEnter"));
		txtUserNameEnter.setPromptText(t.getString("txtUserNameEnter"));
		pwfPasswordEnter.setPromptText(t.getString("pwfPasswordEnter"));
		pwfOldPassword.setPromptText(t.getString("pwfOldPassword"));

	}


}
