package AI;

import java.util.logging.Logger;

import WindChuckers_Main.GameMenu_Model;
import abstractClasses.View;
import commonClasses.ServiceLocator;
import commonClasses.Translator;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AI_View extends View<GameMenu_Model>{
	private GameMenu_Model model;
	private Stage stage;
	
	private Menu menuFileAI;
	protected MenuItem menuFileAIExit;
	
	//Top
	CheckBox cb;


	//Left side Player
	protected BorderPane leftPane;
	protected Label lblWeightProgressL;
	protected Label lblWeightMovesL;
	protected Label lblWeightBlockL;
	protected Label lblWeightSumoWinL;
	protected Label lblWeightSumoBlockL;
	protected Label lblWeightWinL;
	
	protected Label lblPlayerONE;
	protected TextField txtWeightProgressLeft;
	protected TextField txtWeightMovesLeft;
	protected Slider sliderWeightBlockLeft;
	protected Slider sliderWeightSumoBlockLeft;
	protected Slider sliderWeightSumoWinLeft;
	protected Slider sliderWinLeft;
	
    protected Label lblblockValueL;
    protected Label lblSumoBlockValueL;
    protected Label lblSumoWinValueL;
    protected Label lblWinValueL;
	
	
	//Right side Player
	protected Label lblWeightProgressR;
	protected Label lblWeightMovesR;
	protected Label lblWeightBlockR;
	protected Label lblWeightSumoWinR;
	protected Label lblWeightSumoBlockR;
	protected Label lblWeightWinR;
	
	protected Label lblPlayerTWO;
	protected TextField txtWeightProgressRight;
	protected TextField txtWeightMovesRight;
	protected Slider sliderWeightBlockRight;
	protected Slider sliderWieghtSumoBlockRight;
	protected Slider sliderWeightSumoWinRight;
	protected Slider sliderWinRight;
	
    protected Label lblblockValueR;
    protected Label lblSumoBlockValueR;
    protected Label lblSumoWinValueR;
    protected Label lblWinValueR;
    
    //Button
    protected Button btnSingePlayer;
    protected Button btnMultiPlayer;

	public AI_View(Stage stage, GameMenu_Model model) {
		super(stage, model);
		
		stage.setTitle("AI-Configurator");
	}

	
	@Override
	protected Scene create_GUI() {
		ServiceLocator serviceLocator = ServiceLocator.getServiceLocator();
		Logger logger = serviceLocator.getLogger();
		
		MenuBar menuBar = new MenuBar();
		menuFileAI = new Menu();
		menuFileAIExit = new MenuItem();
		menuBar.getMenus().add(menuFileAI);
		cb = new CheckBox();
		cb.setSelected(true);
		

		//Left side
		lblWeightProgressL = new Label();
		lblWeightMovesL = new Label();
		lblWeightBlockL = new Label();
		lblWeightSumoWinL = new Label();
		lblWeightSumoBlockL = new Label();
		lblWeightWinL = new Label();
		
		lblPlayerONE = new Label();
		txtWeightProgressLeft = new TextField("100");
		txtWeightMovesLeft = new TextField("100");
		sliderWeightBlockLeft = new Slider();
		sliderWeightSumoBlockLeft = new Slider();
		sliderWeightSumoWinLeft = new Slider();
		sliderWinLeft = new Slider();
		
		//Right side
		lblWeightProgressR = new Label();
		lblWeightMovesR = new Label();
		lblWeightBlockR = new Label();
		lblWeightSumoWinR = new Label();
		lblWeightSumoBlockR = new Label();
		lblWeightWinR = new Label();
		
		lblPlayerTWO = new Label();
		txtWeightProgressRight = new TextField("100");
		txtWeightMovesRight = new TextField("100");
		sliderWeightBlockRight = new Slider();
		sliderWieghtSumoBlockRight = new Slider();
		sliderWeightSumoWinRight = new Slider();
		sliderWinRight = new Slider();
		
		
		leftPane = new BorderPane();
		
		Region spacer1 = new Region();
		Region spacer2 = new Region();
		Region spacer3 = new Region();
		Region spacer4 = new Region();
		Region spacer5 = new Region();
		Region spacer6 = new Region();
		
		HBox hBoxL1 = new HBox();
		hBoxL1.getChildren().addAll(this.lblWeightMovesL, spacer1, this.txtWeightMovesLeft);
		hBoxL1.setHgrow(spacer1, Priority.ALWAYS);
		HBox hBoxL2 = new HBox();
		hBoxL2.getChildren().addAll(this.lblWeightProgressL, spacer2, this.txtWeightProgressLeft);
		hBoxL2.setHgrow(spacer2, Priority.ALWAYS);
		HBox hBoxL3 = new HBox();
		hBoxL3.getChildren().addAll(this.lblWeightBlockL, spacer3, this.sliderWeightBlockLeft);
		hBoxL3.setHgrow(spacer3, Priority.ALWAYS);
		HBox hBoxL4 = new HBox();
		hBoxL4.getChildren().addAll(this.lblWeightSumoBlockL, spacer4, this.sliderWeightSumoBlockLeft);
		hBoxL4.setHgrow(spacer4, Priority.ALWAYS);
		HBox hBoxL5 = new HBox();
		hBoxL5.getChildren().addAll(this.lblWeightSumoWinL, spacer5, this.sliderWeightSumoWinLeft);
		hBoxL5.setHgrow(spacer5, Priority.ALWAYS);
		HBox hBoxL6 = new HBox();
		hBoxL6.getChildren().addAll(this.lblWeightWinL, spacer6, this.sliderWinLeft);
		hBoxL6.setHgrow(spacer6, Priority.ALWAYS);

        
        //slider options
        this.sliderWeightBlockLeft.setMin(0.1);
        this.sliderWeightBlockLeft.setMax(100);
        this.sliderWeightBlockLeft.setValue(15);
        
        this.sliderWeightSumoBlockLeft.setMin(0.1);
        this.sliderWeightSumoBlockLeft.setMax(100);
        this.sliderWeightSumoBlockLeft.setValue(15);
        
        this.sliderWeightSumoWinLeft.setMin(0.1);
        this.sliderWeightSumoWinLeft.setMax(100);
        this.sliderWeightSumoWinLeft.setValue(25);
        
        this.sliderWinLeft.setMin(0.1);
        this.sliderWinLeft.setMax(100);
        this.sliderWinLeft.setValue(Double.POSITIVE_INFINITY);
        
        lblblockValueL = new Label(Double.toString(sliderWeightBlockLeft.getValue()));
        lblSumoBlockValueL = new Label(Double.toString(sliderWeightSumoBlockLeft.getValue()));
        lblSumoWinValueL = new Label(Double.toString(sliderWeightSumoWinLeft.getValue()));
        lblWinValueL = new Label(Double.toString(sliderWinLeft.getValue()));

        
		GridPane leftGrid = new GridPane();
		leftGrid.add(lblPlayerONE, 0, 0);
		leftGrid.add(hBoxL1, 0, 1);
		leftGrid.add(hBoxL2, 0, 2);
		leftGrid.add(hBoxL3, 0, 3);
		leftGrid.add(hBoxL4, 0, 4);
		leftGrid.add(hBoxL5, 0, 5);
		leftGrid.add(hBoxL6, 0, 6);
        
		leftPane.setLeft(leftGrid);
		if(cb.isSelected()){
			leftPane.setVisible(false);
		}
		

		
		//right side
		
		BorderPane rightPane = new BorderPane();
		
		Region spacer7 = new Region();
		Region spacer8 = new Region();
		Region spacer9 = new Region();
		Region spacer10 = new Region();
		Region spacer11 = new Region();
		Region spacer12 = new Region();

		HBox hBoxR1 = new HBox();
		hBoxR1.getChildren().addAll(this.lblWeightMovesR, spacer7, this.txtWeightMovesRight);
		hBoxR1.setHgrow(spacer7, Priority.ALWAYS);
		HBox hBoxR2 = new HBox();
		hBoxR2.getChildren().addAll(this.lblWeightProgressR, spacer8, this.txtWeightProgressRight);
		hBoxR2.setHgrow(spacer8, Priority.ALWAYS);
		HBox hBoxR3 = new HBox();
		hBoxR3.getChildren().addAll(this.lblWeightBlockR, spacer9, this.sliderWeightBlockRight);
		hBoxR3.setHgrow(spacer9, Priority.ALWAYS);
		HBox hBoxR4 = new HBox();
		hBoxR4.getChildren().addAll(this.lblWeightSumoBlockR, spacer10, this.sliderWieghtSumoBlockRight);
		hBoxR4.setHgrow(spacer10, Priority.ALWAYS);
		HBox hBoxR5 = new HBox();
		hBoxR5.getChildren().addAll(this.lblWeightSumoWinR, spacer11, this.sliderWeightSumoWinRight);
		hBoxR5.setHgrow(spacer11, Priority.ALWAYS);
		HBox hBoxR6 = new HBox();
		hBoxR6.getChildren().addAll(this.lblWeightWinR, spacer12, this.sliderWinRight);
		hBoxR6.setHgrow(spacer12, Priority.ALWAYS);

        
        //slider options
        this.sliderWeightBlockRight.setMin(0.1);
        this.sliderWeightBlockRight.setMax(100);
        this.sliderWeightBlockRight.setValue(15);
        
        this.sliderWieghtSumoBlockRight.setMin(0.1);
        this.sliderWieghtSumoBlockRight.setMax(100);
        this.sliderWieghtSumoBlockRight.setValue(15);
        
        this.sliderWeightSumoWinRight.setMin(0.1);
        this.sliderWeightSumoWinRight.setMax(100);
        this.sliderWeightSumoWinRight.setValue(25);
        
        this.sliderWinRight.setMin(0.1);
        this.sliderWinRight.setMax(100);
        this.sliderWinRight.setValue(Double.POSITIVE_INFINITY);
        
        lblblockValueR = new Label(Double.toString(sliderWeightBlockRight.getValue()));
        lblSumoBlockValueR = new Label(Double.toString(sliderWieghtSumoBlockRight.getValue()));
        lblSumoWinValueR = new Label(Double.toString(sliderWeightSumoWinRight.getValue()));
        lblWinValueR = new Label(Double.toString(sliderWinRight.getValue()));
        
        
		GridPane rightGrid = new GridPane();
		rightGrid.add(lblPlayerTWO, 0, 0);
		rightGrid.add(hBoxR1, 0, 1);
		rightGrid.add(hBoxR2, 0, 2);
		rightGrid.add(hBoxR3, 0, 3);
		rightGrid.add(hBoxR4, 0, 4);
		rightGrid.add(hBoxR5, 0, 5);
		rightGrid.add(hBoxR6, 0, 6);
        

		rightPane.setRight(rightGrid);
		
		HBox bottomBox = new HBox();
		btnSingePlayer = new Button();
		btnMultiPlayer = new Button();
		Region spacerButton = new Region();
		bottomBox.getChildren().addAll(btnSingePlayer, spacerButton, btnMultiPlayer);
		bottomBox.setHgrow(spacerButton, Priority.ALWAYS);
		
		BorderPane endPane = new BorderPane();
		VBox centerVBox = new VBox();
		centerVBox.setPrefWidth(20);
		endPane.setLeft(leftPane);
		endPane.setCenter(centerVBox);
		endPane.setRight(rightPane);
		endPane.autosize();
		
		HBox spacerBox = new HBox();
		spacerBox.setPrefHeight(10);
		
		HBox topBox = new HBox();
		topBox.getChildren().add(cb);
		
		menuFileAI.getItems().add(menuFileAIExit);
		
		GridPane root = new GridPane();
		root.setPrefSize(800, 200);
		root.add(menuBar, 0, 0);
		root.add(topBox, 2, 1);
		root.add(endPane, 2, 2);
		root.add(spacerBox, 0, 3);
		root.add(bottomBox, 2, 4);
		
		updateTexts();
		
		Scene scene = new Scene(root);
        scene.getStylesheets().add(
                getClass().getResource("Example.css").toExternalForm());
		return scene;
	}



	private void updateTexts() {
		Translator t = ServiceLocator.getServiceLocator().getTranslator();
		
		//Menu strings
		menuFileAI.setText(t.getString("AI.menu.file"));
		menuFileAIExit.setText(t.getString("AI.menu.file.exit"));
		
		//Labels
		lblWeightProgressL.setText(t.getString("lblWeightProgress"));
		lblWeightMovesL.setText(t.getString("lblWeightMoves"));
		lblWeightBlockL.setText(t.getString("lblWeightBlock"));
		lblWeightSumoWinL.setText(t.getString("lblWieghtSumoWin"));
		lblWeightSumoBlockL.setText(t.getString("lblWeightSumoBlock"));
		lblWeightWinL.setText(t.getString("lblWeightWin"));
		lblWeightProgressR.setText(t.getString("lblWeightProgress"));
		lblWeightMovesR.setText(t.getString("lblWeightMoves"));
		lblWeightBlockR.setText(t.getString("lblWeightBlock"));
		lblWeightSumoWinR.setText(t.getString("lblWieghtSumoWin"));
		lblWeightSumoBlockR.setText(t.getString("lblWeightSumoBlock"));
		lblWeightWinR.setText(t.getString("lblWeightWin"));
		lblPlayerONE.setText(t.getString("lblPlayerONE"));
		lblPlayerTWO.setText(t.getString("lblPlayerTWO"));
		
		//Buttons
		btnSingePlayer.setText(t.getString("btnSinglePlayer"));
		btnMultiPlayer.setText(t.getString("btnMultiPlayer"));
		
		//checkbox
		cb.setText(t.getString("cb"));
		
	}

}
