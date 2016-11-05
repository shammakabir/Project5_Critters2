package assignment5;

import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.regex.Pattern;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javafx.application.Application;
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
	
	
	@Override
	public void start(Stage primaryStage) {
		try {						
			sp.setPrefSize(800, 800);
			sp.setContent(p);
			
			makeSquares();
			
			
			Scene scene = new Scene(sp);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Grid");
			primaryStage.show();
			
			// Control Stage
			Stage controlStage = new Stage();
			
			grid.setHgap(10);
			grid.setVgap(10);
			
			Label makeCritter = new Label("Make Critter");
			grid.add(makeCritter, 0, 0);
			
			/* NEED TO MAKE IT DYNAMIC */
			ComboBox<String> critterDropdown = new ComboBox<String>();
			critterDropdown.getItems().addAll(
	            "Algae",
	            "Craig"
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
			
			makeCritterNumber.textProperty().addListener((observable, oldValue, newValue) -> {
			    int num = Integer.parseInt(newValue);
			    
			    if (num >= 1){
			    	mcIsTextfieldLegal = true;
			    }
			    else{
			    	mcIsTextfieldLegal = false;
			    }
			    
			    
			});
			
			grid.add(makeCritterNumber, 2, 0);
			
			Button makeCritterSubmit = new Button("Submit");
			
			// Dropdown new selection enables the button
			critterDropdown.setOnAction(new EventHandler<ActionEvent>(){
				@Override public void handle(ActionEvent e){
					makeCritterSubmit.setDisable(false);
				}
			});
			
			
			makeCritterSubmit.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    	if (critterDropdown.getValue() != null && !critterDropdown.getValue().toString().isEmpty()){
			    		
			    	}
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
			
			Scene scene2 = new Scene(grid, 600, 600);
			controlStage.setScene(scene2);
			controlStage.setTitle("Controls");
			controlStage.show();
			
			
			
			
		} catch(Exception e) {	
		}
	}
	
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
	
	public static void displayIcons(){
		clearIcons();
		for(int i = 0; i < Params.world_height; i++){
			for(int j = 0; j < Params.world_width; j++){
	            p.getChildren().add(shapes[i][j]);
			}
		}
	}
	
	
	public static void clearIcons(){
		p.getChildren().clear();
		makeSquares();
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
	
	public static boolean canMCSubmit(){
		if (mcIsTextfieldLegal && mcIsDropdownLegal){
			return true;
		}
		return false;
		
	}

}
