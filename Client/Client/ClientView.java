package Client;

import WindChuckers_Main.GameMenu_Controller;
import WindChuckers_Main.GameMenu_Model;
import commonClasses.ServiceLocator;
import commonClasses.Translator;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * 
 * @author L.Weber
 *
 */
public class ClientView {
	private GameMenu_Model model;
    private Stage stage;
    private ServiceLocator serviceLocator = ServiceLocator.getServiceLocator();
    
    private Menu menuFileClient;
    protected MenuItem menuFileExitClient;

    //Label
    protected Label lblIP;
    protected Label lblPort;
    protected Label lblremind;
    
    //TextField
    protected TextField txtIP;
    protected TextField txtPort;
    protected TextField txtChat;
    
    //Button
    protected Button btnGo;
    protected Button btnSendOther;
    
    //Other
    protected TextArea txtMessages;
    protected ComboBox cbchoice;
    protected CheckBox cbAutomaticLogin;
    
    public ClientView getClientView(){
    	return this;
    }
    
    public ClientView(Stage stage, GameMenu_Model model, TextArea txtLog) {
        this.stage = stage;
        this.model = model;
        txtMessages = txtLog;
        
        //Label
        lblIP = new Label();
        lblPort = new Label();
        lblremind = new Label();
        
        //TextField
        txtIP = new TextField(serviceLocator.getConfiguration().getOption("WebServer"));
        txtPort = new TextField(serviceLocator.getConfiguration().getOption("Port"));
        txtChat = new TextField();
        
        //Buttons
        btnGo = new Button();
        btnSendOther = new Button();
        btnSendOther.setDisable(true);
        
        //Ohter
        txtMessages = new TextArea();
        txtMessages.setEditable(false);
        cbchoice = new ComboBox();
        cbchoice.setDisable(true);
        cbAutomaticLogin = new CheckBox();
        
        stage.setTitle("Client");
        
        GridPane root = new GridPane();
        
//        BorderPane root = new BorderPane();
        
        MenuBar menuBar = new MenuBar();
        menuFileClient = new Menu("File");
        menuFileExitClient = new MenuItem("Exit");
        menuFileClient.getItems().add(menuFileExitClient);
        menuBar.getMenus().add(menuFileClient);
        
        root.add(menuBar, 0, 0);

        HBox toptopBox = new HBox();
        toptopBox.setId("ReminderBox");
        if(serviceLocator.getConfiguration().getOption("StartClient").equals("true")){
        	this.cbAutomaticLogin.setSelected(true);
        }
        toptopBox.getChildren().addAll(this.lblremind, this.cbAutomaticLogin);
        root.add(toptopBox, 0, 1);
        
        HBox topBox = new HBox();
        topBox.setId("TopBox");
        Region spacer = new Region();
        Region spacer2 = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        root.add(topBox, 0, 2);
        cbchoice.getItems().addAll(
        		Message.Message.MessageType.ChatMessage,
        		Message.Message.MessageType.Coordinate, 
        		Message.Message.MessageType.DBMessage, 
        		Message.Message.MessageType.Error, 
        		Message.Message.MessageType.WinMessage,
        		Message.Message.MessageType.Update
        		);
        cbchoice.getSelectionModel().selectFirst();
        
        topBox.getChildren().addAll(lblIP, txtIP, spacer, lblPort, txtPort, spacer2, btnGo);
        txtIP.setId("IP");
        txtPort.setId("Port");
        topBox.getStyleClass().add("box");
        
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        root.add(scrollPane, 0, 3);
        scrollPane.setContent(txtMessages);
        txtMessages.setWrapText(true);
        txtMessages.getStyleClass().add("text-area");

        HBox bottomBox = new HBox();
        topBox.setId("BottomBox");
        bottomBox.getChildren().addAll(txtChat,cbchoice, btnSendOther);
        bottomBox.getStyleClass().add("box");
        HBox.setHgrow(spacer, Priority.ALWAYS);
        root.add(bottomBox, 0, 4);
        
    	updateTexts();
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add(
                getClass().getResource("Style.css").toExternalForm());
        stage.setScene(scene);;
    }
    
    public void start() {
        stage.show();
    }
    
    /**
     * Stopping the view - just make it invisible
     */
    public void stop() {
    	String selected;
    	if(this.cbAutomaticLogin.isSelected()){
    		selected = "true";
    	} else {
    		selected = "false";
    	}
    	serviceLocator.getConfiguration().setLocalOption("StartClient", selected);
    	serviceLocator.getConfiguration().setLocalOption("WebServer", this.txtIP.getText());
    	serviceLocator.getConfiguration().setLocalOption("Port", this.txtPort.getText());
    	serviceLocator.getConfiguration().save();
        stage.hide();
    }
    
    /**
     * Getter for the stage, so that the controller can access window events
     */
    public Stage getStage() {
        return stage;
    }
    
    protected void updateTexts(){
		Translator t = ServiceLocator.getServiceLocator().getTranslator();
    	
        //Label
        lblIP.setText(t.getString("lblIP"));
        lblPort.setText(t.getString("lblPort"));
        lblremind.setText(t.getString("lblremind"));
        
        //Buttons
        btnGo.setText(t.getString("btnGo"));
        btnSendOther.setText(t.getString("btnSendOther"));
    }
}
