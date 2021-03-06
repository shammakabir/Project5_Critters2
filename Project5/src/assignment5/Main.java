package assignment5;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.converter.IntegerStringConverter;


public class Main extends Application{
	static ScrollPane sp = new ScrollPane();
	static Pane p = new Pane();
	static GridPane grid = new GridPane();
	
	public static Shape[][] shapes = new Shape[Params.world_width][Params.world_height];
	static Rectangle [][] rec = new Rectangle [Params.world_width][Params.world_height];
	
	public static int cellSize = 850 / Math.max(Params.world_width, Params.world_height);; // smallest is 8
	public static final int shapeSize = cellSize - 2;
	
	static boolean mcIsDropdownLegal = false;

	static boolean mcIsTextfieldLegal = true;
	private static int FrameSteps = 1;
	//private Animate animation = new Animate();
	
	
	public static String newSeed = "none";

	//static boolean mcIsTextfieldLegal = true;
	
	public static ArrayList<String> critterNames = new ArrayList<String>();
	public static ArrayList<Boolean> cCheckList = new ArrayList<Boolean>();
	
	private static final TextArea textArea = new TextArea();
	
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
			sp.setPrefSize(900, 900);
			sp.setContent(p);
			
			initSquares();
			showSquares();
			
			
			Scene scene = new Scene(sp);
			// sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
			// sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
			//scene.widthProperty().addListener(observable -> clearIcons(),displayIcons());
			// scene.heightProperty().addListener(observable -> clearIcons(),displayIcons());
			
			// while (Params.world_width * cellSize < )
			
			
			if (cellSize <= 8){
				cellSize = 8;
			}
			
			
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Grid");
			primaryStage.show();

			
			//Control Stage
			Stage controlStage = new Stage();
			
			grid.setHgap(10);
			grid.setVgap(10);
			
			Stage console = new Stage();
			
//**************************************** MAKE CRITTER STUFF **************************************************//
			
			Label makeCritter = new Label("Make Critter");
			

			makeCritter.setFont(Font.font("Verdana", FontPosture.ITALIC, 15));
			makeCritter.setTextFill(Color.BLUE);
			
			
			
			grid.add(makeCritter, 0, 0);
			
			/* NEED TO MAKE IT DYNAMIC */
			ComboBox<String> critterDropdown = new ComboBox<String>();
			
			ArrayList<String> classNames = new ArrayList<String>();
			
			
			
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


			
			grid.add(critterDropdown, 0, 1);
			
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
			
			
			
			grid.add(makeCritterNumber, 1, 1);
			
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
			grid.add(makeCritterSubmit, 2, 1);
			



//******************************************* TIME STEP STUFF **************************************************//
			
			//title
			Label titleStep = new Label("TimeStep Funcitons");
			
			titleStep.setFont(Font.font("Verdana", FontPosture.ITALIC, 15));
			titleStep.setTextFill(Color.BLUE);
			
			grid.add(titleStep, 0, 2);
			
			Label timeStep = new Label("Input TimeStep");

			grid.add(timeStep, 0, 4);
			
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
			
			
			
			grid.add(timeStepSet, 1, 4);
			
			grid.add(time, 2, 4);
			
			
			Button time_one = new Button("TimeStep + 1");
			
			Button time_hundred = new Button("TimeStep + 50");
			
			Button time_thousand = new Button("TimeStep + 100");
			
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
					for (int i = 0; i < 50; i ++) {
						Critter.worldTimeStep();
					}
					Critter.displayWorld();
				}
			});
			
			
			
			time_thousand.setOnAction(new EventHandler<ActionEvent>() {
				@Override 
				public void handle(ActionEvent event) {
					for (int i = 0; i < 100; i ++) {
						Critter.worldTimeStep();
					}
					Critter.displayWorld();
				}
			});
			
			grid.add(time_one, 0, 3);
			grid.add(time_hundred, 1, 3);
			grid.add(time_thousand, 2, 3);

		
			
			
//***************************SETTING SEED**************************//
			
			Label seed = new Label("Seed");
			

			seed.setFont(Font.font("Verdana", FontPosture.ITALIC, 15));
			seed.setTextFill(Color.BLUE);
			
			
			grid.add(seed, 0, 7);
			
			
			Label setSeed = new Label("Set Seed: ");
			grid.add(setSeed, 0, 8);
			
			TextField seedInput = new TextField();
			
			Pattern validLongText = Pattern.compile("-?\\d*");
			
			TextFormatter<Integer> textFormatter3 = new TextFormatter<Integer>(new IntegerStringConverter(), 1, 
		            change -> {
		                String newText = change.getControlNewText() ;
		                if (validLongText.matcher(newText).matches()) {
		                    return change ;
		                } else return null ;
		            });
			
			seedInput.setTextFormatter(textFormatter3);
			
			
			
			
			grid.add(seedInput, 1, 8);
			
			Label currSeed = new Label("Current Seed: " + newSeed);
			
			grid.add(currSeed, 3, 8);
			
			Button seedSubmit = new Button("Submit");
			
			seedSubmit.setOnAction(new EventHandler<ActionEvent>() {
				@Override 
				public void handle(ActionEvent event) {
					long seed = Integer.parseInt(seedInput.getText());
					
					Critter.setSeed(seed);
					newSeed = seedInput.getText();
					currSeed.setText("Current Seed: " + newSeed);
				}
			});
			
			
			seedInput.textProperty().addListener((observable, oldValue, newValue) -> {
				
				if (newValue != null && !newValue.isEmpty()){
					seedSubmit.setDisable(false);
				}
				else{
					seedSubmit.setDisable(true);
				}
			});
			
			
			grid.add(seedSubmit, 2, 8);
			
			
			
//************************************************ ANIMATION STUFF **************************************************//	
			
			Label ani = new Label("Animation");
			

			ani.setFont(Font.font("Verdana", FontPosture.ITALIC, 15));
			ani.setTextFill(Color.BLUE);
			
			
			grid.add(ani, 0, 5);
			
			Slider aniSlide = new Slider();
			aniSlide.setMin(1);
			aniSlide.setMax(100);
			aniSlide.setValue(40);
			aniSlide.setShowTickLabels(true);
			aniSlide.setShowTickMarks(true);
			aniSlide.setMajorTickUnit(50);
			aniSlide.setMinorTickCount(5);
			aniSlide.setBlockIncrement(10);
			aniSlide.valueProperty().addListener((observable, oldValue, newValue) -> {
				aniSlide.setValue(Math.round(aniSlide.getValue()));
				FrameSteps = (int)aniSlide.getValue();
			});
			
			Label sliderNums = new Label("FrameSpeed of Animation: ");
			
			
			Timeline animation = new Timeline(new KeyFrame(
			        Duration.millis(1000),
			        new EventHandler<ActionEvent>() {
			        @Override	
			        	public void handle(ActionEvent event) {
			        		for(int i = 0; i < FrameSteps; i++) {
			        			Critter.worldTimeStep();
			        		}
			        		Critter.displayWorld();
			        		autoRunStats();
			        	}
					}));
			
			
			Button aniBtnStop = new Button("Stop Animation");
			aniBtnStop.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					
				//enable everything 
				makeCritterSubmit.setDisable(false);
				time_one.setDisable(false);
				time_hundred.setDisable(false);
				time_thousand.setDisable(false);
				time.setDisable(false);
				seedSubmit.setDisable(false);
				animation.stop();
				
				}
			});
			
			aniBtnStop.setDisable(true);
			
			
			Button aniBtn = new Button("Start Animation");
			aniBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {

					// disable everything
					makeCritterSubmit.setDisable(true);
					time_one.setDisable(true);
					time_hundred.setDisable(true);
					time_thousand.setDisable(true);
					time.setDisable(true);
					seedSubmit.setDisable(true);
					animation.setCycleCount(Animation.INDEFINITE);
					animation.play();
					aniBtnStop.setDisable(false);
				}
			});
			
			
			aniBtn.setDisable(true);

	
			aniSlide.valueProperty().addListener((observable, oldValue, newValue) -> {
				sliderNums.setText("FrameSpeed of Animation: " + aniSlide.getValue());
				aniBtn.setDisable(false);
			});
		
			grid.add(sliderNums, 0, 6);
			grid.add(aniSlide, 1, 6);
			grid.add(aniBtn, 2, 6);	
			grid.add(aniBtnStop, 3, 6);
			
//***************************Run Stats**************************//
			Label rStats = new Label("Run Stats");
			

			rStats.setFont(Font.font("Verdana", FontPosture.ITALIC, 15));
			rStats.setTextFill(Color.BLUE);
			
			grid.add(rStats, 0, 9);
			
			
			for(int i = 0; i < critterNames.size(); i++){
				cCheckList.add(false);
			}
			
			
			for(int i = 0; i < critterNames.size(); i++){
				Button b = new Button(critterNames.get(i));
				
				b.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						Class<?> myClass = null;
						
						try {
							myClass = Class.forName(myPackage + "." + b.getText());
														
							java.util.List<Critter> inst = Critter.getInstances(b.getText());
							
							Class<?>[] types = {java.util.List.class};
							
							Object mobj = myClass.newInstance();
							
							
							Method m=mobj.getClass().getMethod("runStats", types);
							m.invoke(null, inst);
						}
						catch(Exception e){
						}
					}
					
				});
				
				grid.add(b, 1, 10 + i);
				
				CheckBox cb = new CheckBox(critterNames.get(i));
				
				cb.setOnAction((event) -> {
					int j = 0;
					while(!critterNames.get(j).equals(cb.getText())){
						j++;
					}
					
					cCheckList.set(j, cb.isSelected());
				});
				
				
				
				
				grid.add(cb, 0, 10 + i);
			}
			
			
//***************************EXITING OUT**************************//
			
			Label end = new Label("Exit Out");
			

			end.setFont(Font.font("Verdana", FontPosture.ITALIC, 15));
			end.setTextFill(Color.BLUE);
			
			grid.add(end, 4, 0);
			
			Button exit = new Button("Exit");
			exit.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.exit(0);
				}
				
			});
			
			grid.add(exit, 4, 1);
			

//********************************************* SETTING CONTROLLER *************************************************//


			Scene scene2 = new Scene(grid, 700, 800);
			controlStage.setScene(scene2);
			controlStage.setTitle("Controls");
			controlStage.show();
			
			textArea.setEditable(false);
			Scene scene3 = new Scene(textArea, 800, 500);
			console.setScene(scene3);
			console.setTitle("Console");
			console.show();
			
		} catch(Exception e) {	
		}
	}
	
	/**
	 * Make the squares base grid
	 */
	public static void showSquares(){
		for(int i = 0; i < Params.world_width; i++){
			for(int j = 0; j < Params.world_height; j++){
	            p.getChildren().add(rec[i][j]);
			}
		}
	}
	/**
	 * Make squares
	 */
	public static void initSquares(){
		for(int i = 0; i < Params.world_width; i++){
			for(int j = 0; j < Params.world_height; j++){
				rec[i][j] = new Rectangle();
				rec[i][j].setX(i * cellSize);
	            rec[i][j].setY(j * cellSize);
				rec[i][j].setWidth(cellSize);
	            rec[i][j].setHeight(cellSize);
	            rec[i][j].setFill(Color.WHITE);
	            rec[i][j].setStroke(Color.BLACK);
			}
		}
	}
	
	/**
	 * Add all the icons of the Critters to the grid
	 */
	public static void displayIcons(){
		clearIcons();
		for(int i = 0; i < Params.world_width; i++){
			for(int j = 0; j < Params.world_height; j++){
	            p.getChildren().add(shapes[i][j]);
			}
		}
	}
	
	/**
	 * Clear the grid of everything, then add the squares back.
	 */
	public static void clearIcons(){
		p.getChildren().clear();
		showSquares();
	}
	
	
	/*public class Animate extends TimerTask {
		@Override
		public void run() {
			Platform.runLater(() -> {
				for(int i = 0; i < FrameSteps; i++) {
					Critter.worldTimeStep();
				}
				Critter.displayWorld();
			});
		}
		
	}*/
	
	public static void main(String[] args) {
		
		PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
		System.setOut(printStream);
		System.setErr(printStream);
		
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
	
	/**
	 * Run all the auto checked Critter stats
	 */
	public static void autoRunStats(){
		for(int i = 0; i < cCheckList.size(); i++){
			if (cCheckList.get(i)){
				
				System.out.println(critterNames.get(i));
				
				Class<?> myClass = null;
				
				try {
					myClass = Class.forName(myPackage + "." + critterNames.get(i));
												
					java.util.List<Critter> inst = Critter.getInstances(critterNames.get(i));
					
					Class<?>[] types = {java.util.List.class};
					
					Object mobj = myClass.newInstance();
					
					
					Method m=mobj.getClass().getMethod("runStats", types);
					m.invoke(null, inst);
				}
				catch(Exception e){
				}
			}
		}
	}
	
	public static void println(String s){
	    Platform.runLater(new Runnable() {//in case you call from other thread
	        @Override
	        public void run() {
	            textArea.setText(textArea.getText()+s+"\n");
	            // System.out.println(s);//for echo if you want
	        }
	    });
	}
	
	
	public static class CustomOutputStream extends OutputStream {
	    private TextArea textArea;

	    public CustomOutputStream(TextArea textArea) {
	        this.textArea = textArea;
	    }

	    @Override
	    public void write(int b) throws IOException {
	        // redirects data to the text area
	        textArea.appendText(String.valueOf((char)b));
	        // scrolls the text area to the end of data
	        // textArea.setCaretPosition(textArea.getDocument().getLength());
	        // keeps the textArea up to date
	        // textArea.update(textArea.getGraphics());
	    }
	}

}
