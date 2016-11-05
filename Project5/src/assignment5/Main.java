package assignment5;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;


public class Main extends Application{
	static ScrollPane sp = new ScrollPane();
	static Pane p = new Pane();
	
	public static Shape[][] shapes = new Shape[Params.world_height][Params.world_width];
	Rectangle [][] rec = new Rectangle [Params.world_height][Params.world_width];
	
	public static final int cellSize = 26;
	public static final int shapeSize = 20;
	
	@Override
	public void start(Stage primaryStage) {
		try {						
			sp.setPrefSize(800, 800);
			sp.setContent(p);
			
			for(int i = 0; i < Params.world_height; i++){
				for(int j = 0; j < Params.world_width; j++){
					rec[i][j] = new Rectangle();
					rec[i][j].setX(i * cellSize);
		            rec[i][j].setY(j * cellSize);
					rec[i][j].setWidth(cellSize);
		            rec[i][j].setHeight(cellSize);
		            rec[i][j].setFill(null);
		            rec[i][j].setStroke(Color.BLACK);
		            p.getChildren().add(rec[i][j]);
				}
			}
			
			
			Scene scene = new Scene(sp);
			
			primaryStage.setScene(scene);
			
			primaryStage.show();
			
			// Control Stage
			Stage controlStage = new Stage();
			// Scene scene2 = new Scene();
			controlStage.show();
			
			displayIcons();
			
		} catch(Exception e) {
			e.printStackTrace();		
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
		for(int i = 0; i < Params.world_height; i++){
			for(int j = 0; j < Params.world_width; j++){
	            p.getChildren().remove(shapes[i][j]);
			}
		}
	}
	
	public static void main(String[] args) {
		
		
		int i = 0;
    	while(i < 10){ 
    		try {
				Critter.makeCritter("Algae");
				Critter.makeCritter("AlgaephobicCritter");
			} catch (Exception e) {
			}
    		i++;
    	}
    	Critter.displayWorld();
		launch(args);
	}

}
