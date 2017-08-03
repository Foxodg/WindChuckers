package AI;

import java.util.logging.Logger;

import Login.LoginModel;
import Message.Message.MessageType;
import WindChuckers_Main.GameMenu_Model;
import abstractClasses.Controller;
import commonClasses.ServiceLocator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class AI_Controller extends Controller<GameMenu_Model, AI_View> {
	private GameMenu_Model model;
	private AI_View view;
	private ServiceLocator serviceLocator = ServiceLocator.getServiceLocator();
	private Logger logger = serviceLocator.getLogger();
	
	public AI_Controller(GameMenu_Model model, AI_View view) {
		super(model, view);
		
		/**
		 * Quits the view
		 * @author L.Weber
		 */
		view.menuFileAIExit.setOnAction(e -> {
			view.stop();
		});
		
		/**
		 * One Player Start
		 * @author L.Weber
		 */
		view.btnSingePlayer.setOnAction(e -> {
			model.messageContstructorForAISingle(Double.parseDouble(view.txtWeightProgressRight.getText()), Double.parseDouble(view.txtWeightMovesRight.getText()),
					view.sliderWeightBlockRight.getValue(), view.sliderWieghtSumoBlockRight.getValue(), view.sliderWeightSumoWinRight.getValue(), view.sliderWinRight.getValue());
			
			//set the right Flags
			LoginModel.setSingleAI(true);
			LoginModel.setDoubleAI(false);
		});
		
		/**
		 * Double AI Start
		 * @author L.Weber
		 */
		view.btnMultiPlayer.setOnAction(e -> {
			model.messageContstructorForAIDouble(Double.parseDouble(view.txtWeightProgressLeft.getText()), Double.parseDouble(view.txtWeightMovesLeft.getText()),
					view.sliderWeightBlockLeft.getValue(), view.sliderWeightSumoBlockLeft.getValue(), view.sliderWeightSumoWinLeft.getValue(), view.sliderWinLeft.getValue(),
					Double.parseDouble(view.txtWeightProgressRight.getText()), Double.parseDouble(view.txtWeightMovesRight.getText()),
					view.sliderWeightBlockRight.getValue(), view.sliderWieghtSumoBlockRight.getValue(), view.sliderWeightSumoWinRight.getValue(), view.sliderWinRight.getValue());
			
			//set the right Flags
			LoginModel.setSingleAI(false);
			LoginModel.setDoubleAI(true);
		});
		
		view.cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val){
				if(new_val == false){
					logger.info("Double AI is enabled");
					view.leftPane.setVisible(true);
					view.btnMultiPlayer.setVisible(true);
				} else {
					logger.info("Single AI is enabled");
					view.leftPane.setVisible(false);
					view.btnMultiPlayer.setVisible(false);
				}

			}
		});
		
		/**
		 * Observe the Block-Slider left
		 * @author L.Weber
		 */
		view.sliderWeightBlockLeft.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val){
				logger.info("BlockSlider has changed");
				view.lblblockValueL.setText(String.format("%.2f", new_val));
			}
		});
		
		/**
		 * Observe the Block-Slider right
		 * @author L.Weber
		 */
		view.sliderWeightBlockRight.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val){
				logger.info("BlockSlider has changed");
				view.lblblockValueR.setText(String.format("%.2f", new_val));
			}
		});
		
		/**
		 * Observe the SumoBlock-Slider left
		 * @author L.Weber
		 */
		view.sliderWeightSumoBlockLeft.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val){
				logger.info("BlockSlider has changed");
				view.lblSumoBlockValueL.setText(String.format("%.2f", new_val));
			}
		});
		
		/**
		 * Observe the SumoBlock-Slider right
		 * @author L.Weber
		 */
		view.sliderWieghtSumoBlockRight.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val){
				logger.info("BlockSlider has changed");
				view.lblSumoBlockValueR.setText(String.format("%.2f", new_val));
			}
		});
		
		/**
		 * Observe the SumoWin-Slider left
		 * @author L.Weber
		 */
		view.sliderWeightSumoWinLeft.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val){
				logger.info("BlockSlider has changed");
				view.lblSumoWinValueL.setText(String.format("%.2f", new_val));
			}
		});
		
		/**
		 * Observe the SumoWin-Slider right
		 * @author L.Weber
		 */
		view.sliderWeightSumoWinRight.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val){
				logger.info("BlockSlider has changed");
				view.lblSumoWinValueR.setText(String.format("%.2f", new_val));
			}
		});
		
		/**
		 * Observe the Win-Slider left
		 * @author L.Weber
		 */
		view.sliderWinLeft.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val){
				logger.info("BlockSlider has changed");
				view.lblWinValueL.setText(String.format("%.2f", new_val));
			}
		});
		
		/**
		 * Observe the Win-Slider right
		 * @author L.Weber
		 */
		view.sliderWinRight.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val){
				logger.info("BlockSlider has changed");
				view.lblWinValueR.setText(String.format("%.2f", new_val));
			}
		});
		
		/**
		 * For the txtField, only numbers allowed
		 * @author L.Weber
		 */
		view.txtWeightMovesLeft.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String old_val, String new_val){
				if(!new_val.matches("\\d*")) {
					view.txtWeightMovesLeft.setText(new_val.replaceAll("[^\\d]", ""));
				}
			}
		});
		
		/**
		 * For the txtField, only numbers allowed
		 * @author L.Weber
		 */
		view.txtWeightMovesRight.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String old_val, String new_val){
				if(!new_val.matches("\\d*")) {
					view.txtWeightMovesRight.setText(new_val.replaceAll("[^\\d]", ""));
				}
			}
		});
		
		/**
		 * For the txtField, only numbers allowed
		 * @author L.Weber
		 */
		view.txtWeightProgressLeft.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String old_val, String new_val){
				if(!new_val.matches("\\d*")) {
					view.txtWeightProgressLeft.setText(new_val.replaceAll("[^\\d]", ""));
				}
			}
		});
		
		/**
		 * For the txtField, only numbers allowed
		 * @author L.Weber
		 */
		view.txtWeightProgressRight.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String old_val, String new_val){
				if(!new_val.matches("\\d*")) {
					view.txtWeightProgressRight.setText(new_val.replaceAll("[^\\d]", ""));
				}
			}
		});
		
		/**
		 * @author L.Weber
		 */
		view.tfDepth.focusedProperty().addListener((oberservable, oldValue, newValue)-> {
			if(newValue){
			} else {
				serviceLocator.getLogger().info("Depth is: " + view.tfDepth.getText());
				model.messageConstructorForDBUpdate(90, Integer.parseInt(view.tfDepth.getText()));
			}
		});
		
		/**
		 * @author L.Weber
		 */
		view.tfDepth.textProperty().addListener((observable, oldValue, newValue)-> {
			if(!newValue.matches("\\d")) {
				view.tfDepth.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});
		
	}
	
	
	

}
