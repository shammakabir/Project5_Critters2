package assignment5;

import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.TimerTask;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javafx.animation.Animation;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;


public class Main extends Application{
	static ScrollPane sp = new ScrollPane();
	static Pane p = new Pane();
	static GridPane grid = new GridPane();
	
	public static Shape[][] shapes = new Shape[Params.world_height][Params.world_width];
	static Rectangle [][] rec = new Rectangle [Params.world_height][Params.world_width];
	
	public static final int cellSize = 26;
	public static final int shapeSize = 20;
	
	static boolean mcIsDropdownLegal = false;

	static boolean mcIsTextfieldLegal = false;
	private static int FrameSteps = 1;
	private Animate animation = new Animate();

	//static boolean mcIsTextfieldLegal = true;
	
	private static String myPackage;

	
	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
		static {
			myPackage = Main.class.getPackage().toString().split(" ")[1];
		}
		
	//private Animate animation = new Animate();

	
	@Override
	public void start(Stage primaryStage) {
		try {
			
//**************************************** CREATE BOTH STAGES ********************************************//
			sp.setPrefSize(800, 800);
			sp.setContent(p);
			
			makeSquares();
			
			
			Scene scene = new Scene(sp);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Grid");
			primaryStage.show();

			
			//Control Stage
			Stage controlStage = new Stage();
			
			grid.setHgap(10);
			grid.setVgap(10);
			
			
//**************************************** MAKE CRITTER STUFF **************************************************//
			
			Label makeCritter = new Label("Make Critter");
			grid.add(makeCritter, 0, 0);
			
			/* NEED TO MAKE IT DYNAMIC */
			ComboBox<String> critterDropdown = new ComboBox<String>();
			
			ArrayList<String> classNames = new ArrayList<String>();
			ArrayList<String> critterNames = new ArrayList<String>();
			
			
			File curDir = new File("./src/assignment5");
			File[] filesList = curDir.listFiles();
			for(File f : filesList){
				
				if (f.getName().endsWith(".java")) {  					
	                // Remove the .class extension  
	                classNames.add(f.getName().substring(0, f.getName().length() - 5));
				}
			}
			
			
			for(int i = 0; i < classNames.size(); i++){
				Class<?> myClass = null;
				
				try {
					myClass = Class.forName(myPackage + "." + classNames.get(i));
					
					if (Critter.class.isAssignableFrom(myClass)){
						
						if (!classNames.get(i).equals("Critter")){
							critterNames.add(classNames.get(i));
						}
						
						
					}					
				} catch (Exception e) {
					// TODO Auto-generated catch block
				}
			}
			
			
			critterDropdown.getItems().addAll(
	            critterNames
	        );


			
			grid.add(critterDropdown, 1, 0);
			
			TextField makeCritterNumber = new TextField();
			
			Pattern validIntText = Pattern.compile("\\d*");
			
			TextFormatter<Integer> textFormatter = new TextFormatter<Integer>(new IntegerStringConverter(), 1, 
		            change -> {
		                String newText = change.getControlNewText() ;
		                if (validIntText.matcher(newText).matches()) {
		                    return change ;
		                } else return null ;
		            });
			
			makeCritterNumber.setTextFormatter(textFormatter);
			
			
			
			grid.add(makeCritterNumber, 2, 0);
			
			Button makeCritterSubmit = new Button("Submit");
			
			// Dropdown new selection enables the button
			critterDropdown.setOnAction(new EventHandler<ActionEvent>(){
				@Override public void handle(ActionEvent e){
					
					if (critterDropdown.getValue() != null && !critterDropdown.getValue().toString().isEmpty()){
						mcIsDropdownLegal = true;
			    	}
					else{
						mcIsDropdownLegal = false;
					}
					
					makeCritterSubmit.setDisable(isMCDisable());
				}
			});
			
			makeCritterNumber.textProperty().addListener((observable, oldValue, newValue) -> {
				
				if (newValue != null && !newValue.isEmpty()){
					int num = Integer.parseInt(newValue);
				    
				    if (num >= 1){
				    	mcIsTextfieldLegal = true;
				    }
				    else{
				    	mcIsTextfieldLegal = false;
				    }
				}
				else{
					mcIsTextfieldLegal = false;
				}
				
			    
			    
			    makeCritterSubmit.setDisable(isMCDisable());
			});
			
			
			makeCritterSubmit.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    	try {
			    		
			    		int count = Integer.parseInt(makeCritterNumber.getText());
			    		String name = critterDropdown.getValue();
			    		
			    		
			    		for(int i = 0; i < count; i++){
			    			Critter.makeCritter(name);
			    		}
					} catch (Exception e1) {
					}
			    	Critter.displayWorld();
			    }
			});
			
			makeCritterSubmit.setDisable(true);
			grid.add(makeCritterSubmit, 3, 0);
			



//******************************************* TIME STEP STUFF **************************************************//
			
			Label timeStep = new Label("Input TimeStep");

			grid.add(timeStep, 0, 3);
			
			TextField timeStepSet = new TextField();
			
			TextFormatter<Integer> textFormatter2 = new TextFormatter<Integer>(new IntegerStringConverter(), 1, 
		            change -> {
		                String newText = change.getControlNewText() ;
		                if (validIntText.matcher(newText).matches()) {
		                    return change ;
		                } else return null ;
		            });
			
			
			timeStepSet.setTextFormatter(textFormatter2);
			
			Button time = new Button("Submit");
			
			timeStepSet.textProperty().addListener((observable, oldValue, newValue) -> {
				
				if (newValue != null && !newValue.isEmpty()){
				
					int num = Integer.parseInt(newValue);
				    
				    if (num >= 1){
				    	time.setDisable(false);
				    }
				    else{
				    	time.setDisable(true);
				    }
				}
				else{
					time.setDisable(true);
				}
			    
			    
			    
			});
			
			
			
			time.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    	int number = Integer.parseInt(timeStepSet.getText());
			    	for (int i = 0; i < number; i ++) {
						Critter.worldTimeStep();
					}
					Critter.displayWorld();
				}
			});
			
			
			
			grid.add(timeStepSet, 1, 3);
			
			grid.add(time, 2, 3);
			
			
			Button time_one = new Button("TimeStep + 1");
			
			Button time_hundred = new Button("TimeStep + 100");
			
			Button time_thousand = new Button("TimeStep + 1000");
			
			time_one.setOnAction(new EventHandler<ActionEvent>() {
				@Override 
				public void handle(ActionEvent event) {
					Critter.worldTimeStep();
					Critter.displayWorld();
				}
			});
			
			
			
			
			time_hundred.setOnAction(new EventHandler<ActionEvent>() {
				@Override 
				public void handle(ActionEvent event) {
					for (int i = 0; i < 100; i ++) {
						Critter.worldTimeStep();
					}
					Critter.displayWorld();
				}
			});
			
			
			
			time_thousand.setOnAction(new EventHandler<ActionEvent>() {
				@Override 
				public void handle(ActionEvent event) {
					for (int i = 0; i < 1000; i ++) {
						Critter.worldTimeStep();
					}
					Critter.displayWorld();
				}
			});
			
			grid.add(time_one, 0, 2);
			grid.add(time_hundred, 1, 2);
			grid.add(time_thousand, 2, 2);

			
//************************************************ ANIMATION STUFF **************************************************//	
			
			
			
			

//********************************************* SETTING CONTROLLER *************************************************//


			Scene scene2 = new Scene(grid, 600, 600);
			controlStage.setScene(scene2);
			controlStage.setTitle("Controls");
			controlStage.show();
			
			
			
		} catch(Exception e) {	
		}
	}
	
	/**
	 * Make the squares base grid
	 */
	public static void makeSquares(){
		for(int i = 0; i < Params.world_height; i++){
			for(int j = 0; j < Params.world_width; j++){
				rec[i][j] = new Rectangle();
				rec[i][j].setX(i * cellSize);
	            rec[i][j].setY(j * cellSize);
				rec[i][j].setWidth(cellSize);
	            rec[i][j].setHeight(cellSize);
	            rec[i][j].setFill(Color.WHITE);
	            rec[i][j].setStroke(Color.BLACK);
	            p.getChildren().add(rec[i][j]);
			}
		}
	}
	
	/**
	 * Add all the icons of the Critters to the grid
	 */
	public static void displayIcons(){
		clearIcons();
		for(int i = 0; i < Params.world_height; i++){
			for(int j = 0; j < Params.world_width; j++){
	            p.getChildren().add(shapes[i][j]);
			}
		}
	}
	
	/**
	 * Clear the grid of everything, then add the squares back.
	 */
	public static void clearIcons(){
		p.getChildren().clear();
		makeSquares();
	}
	
	
	public class Animate extends TimerTask {
		@Override
		public void run() {
			Platform.runLater(() -> {
				for(int i = 0; i < FrameSteps; i++) {
					Critter.worldTimeStep();
				}
				Critter.displayWorld();
			});
		}
		
	}
	
	public static void main(String[] args) {
		
		/*
		int i = 0;
    	while(i < 10){ 
    		try {
				Critter.makeCritter("Algae");
				Critter.makeCritter("AlgaephobicCritter");
			} catch (Exception e) {
			}
    		i++;
    	}*/
    	
		launch(args);
	}
	
	/**
	 * Choose whether to enable or disable the Make Critter submit button
	 * @return false to enable, true to disable
	 */
	public static boolean isMCDisable(){
		if (mcIsTextfieldLegal && mcIsDropdownLegal){
			return false;
		}
		else{
			return true;
		}
		
	}

}
