package Lobby;

import java.util.logging.Logger;

import WindChuckers_Main.GameMenu_Model;
import WindChuckers_Main.WindChuckers;
import abstractClasses.View;
import commonClasses.ServiceLocator;
import commonClasses.Translator;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LobbyView extends View<GameMenu_Model> {
	private static LobbyView lobbyView;
	
	private GameMenu_Model model;
    private Stage stage;
    
	private Menu menuFileLobby;
	protected MenuItem menuFileExitLobby;
	
	protected TitledPane chatPanel;
	protected VBox chatBox;
	protected Button btnSend;
    public TextArea txtMessages;
    protected TextField txtChat;
    
    protected Button btnGo;
	

	
	public LobbyView (Stage stage, GameMenu_Model model){
		super(stage,model);
		stage.setTitle("Lobby");
		
		lobbyView = this;

	}

	@Override
	protected Scene create_GUI() {
		ServiceLocator sl = ServiceLocator.getServiceLocator();
		Logger logger = sl.getLogger();
		Translator t = sl.getTranslator();
		
		MenuBar menuBar = new MenuBar();
		menuFileLobby = new Menu();
		menuFileExitLobby = new MenuItem();
		menuBar.getMenus().add(menuFileLobby);
		
		chatPanel = new TitledPane();
		chatPanel.setExpanded(true);
		chatPanel.setText("Chat");
		chatPanel.setContent(chatBox);
		
		chatBox = new VBox();
		btnSend = new Button();
		txtMessages = new TextArea();
		txtMessages.setEditable(false);
		txtChat = new TextField();
		btnGo = new Button();
		Region region = new Region();
		HBox btnBox = new HBox();
		btnBox.setHgrow(region, Priority.ALWAYS);
		btnBox.getChildren().addAll(btnSend, region, btnGo);
		chatBox.getChildren().addAll(txtMessages,txtChat,btnBox);
		
		chatPanel = new TitledPane();
		chatPanel.setExpanded(true);
		chatPanel.setText("Chat");
		chatPanel.setContent(chatBox);
		
		menuFileLobby.getItems().add(menuFileExitLobby);
		
		GridPane root = new GridPane();
		root.add(menuBar, 0, 0);
		root.add(chatPanel, 0, 1);
		
		updateTexts();
		
        Scene scene = new Scene(root);
        scene.getStylesheets().add(
                getClass().getResource("Style.css").toExternalForm());
		return scene;
	}
	
	protected void updateTexts() {
		Translator t = ServiceLocator.getServiceLocator().getTranslator();

		// Menu strings
		menuFileLobby.setText(t.getString("lobby.menu.file"));
		menuFileExitLobby.setText(t.getString("lobby.menu.file.exit"));
		
		//Button
		btnSend.setText(t.getString("program.btn.btnSend"));
		btnGo.setText(t.getString("btnGo"));
		
	}
	
	public static LobbyView getLobbyView() {
		return lobbyView;
	}

}
