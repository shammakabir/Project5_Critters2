/* CRITTERS Critter.java
 * EE422C Project 5 submission by
 * Replace <...> with your actual data.
 * Jia-luen Yang
 * JY8435
 * 16455
 * Shamma Kabir
 * SK38422
 * 16475
 * Slip days used: <0>
 * Fall 2016
 */
package assignment5;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.lang.*;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */
public abstract class Critter {
	
	/* NEW FOR PROJECT 5 */
	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR
	}
	
	/* the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the default
	 * color to be the same as you background 
	 * 
	 * critters must override at least one of the following three methods, it is not 
	 * proper for critters to remain invisible in the view
	 * 
	 * If a critter only overrides the outline color, then it will look like a non-filled 
	 * shape, at least, that's the intent. You can edit these default methods however you 
	 * need to, but please preserve that intent as you implement them. 
	 */
	public javafx.scene.paint.Color viewColor() { 
		return javafx.scene.paint.Color.WHITE; 
	}
	
	public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
	public javafx.scene.paint.Color viewFillColor() { return viewColor(); }
	
	
	public abstract CritterShape viewShape(); 
	
	protected String look(int direction, boolean steps) {
		StackTraceElement[] traces = Thread.currentThread().getStackTrace();
		boolean method = false;
		String method_called = new String();
		boolean occupied = false;
		String crit = new String();
		int pos_x = 0;
		int pos_y = 0;
	    for(StackTraceElement element : traces) {
	    	if(method) {
	    		method_called = element.getMethodName();
	    	}
	    	if (element.getMethodName().equals("look")) {
	    		method = true;
	    	}
	    }
	    
	    if (method_called.equals("fight")) {
	    	if (steps == false) {
	    		if (direction == 0) { // direction is to the right (x + 1)
	    			pos_x = move_x(1);
	    		}
	    		else if (direction == 1) { //right one & up one (x + 1 and y + 1)
	    			pos_x = move_x(1);
	    			pos_y = move_y(1);			
	    		}
	    		else if (direction == 2) { //up (y + 1)
	    			pos_y = move_y(1);
	    		}
	    		else if (direction == 3) { //left one & up one (x - 1, y + 1)
	    			pos_x = move_x(-1);
	    			pos_y = move_y(1);
	    		}
	    		else if (direction == 4) { //just left (x - 1)
	    			pos_x = move_x(-1);
	    		}
	    		else if (direction == 5) { //left one & down one (x - 1, y - 1)
	    			pos_x = move_x(-1);
	    			pos_y = move_y(-1);
	    		}
	    		else if (direction == 6) { //down (y-1)
	    			pos_y = move_y(-1);
	    		}
	    		else if (direction == 7) { //down one & right one (x + 1, y - 1)
	    			pos_x = move_x(1);
	    			pos_y = move_y(-1);
			}
	    }
	    	
	    	else if (steps == true) {
	    		if (direction == 0) { // direction is to the right (x + 1)
	    			pos_x = move_x(2);
	    		}
	    		else if (direction == 1) { //right one & up one (x + 1 and y + 1)
	    			pos_x = move_x(2);
	    			pos_y = move_y(2);			
	    		}
	    		else if (direction == 2) { //up (y + 1)
	    			pos_y = move_y(2);
	    		}
	    		else if (direction == 3) { //left one & up one (x - 1, y + 1)
	    			pos_x = move_x(-2);
	    			pos_y = move_y(2);
	    		}
	    		else if (direction == 4) { //just left (x - 1)
	    			pos_x = move_x(-2);
	    		}
	    		else if (direction == 5) { //left one & down one (x - 1, y - 1)
	    			pos_x = move_x(-2);
	    			pos_y = move_y(-2);
	    		}
	    		else if (direction == 6) { //down (y-1)
	    			pos_y = move_y(-2);
	    		}
	    		else if (direction == 7) { //down one & right one (x + 1, y - 1)
	    			pos_x = move_x(2);
	    			pos_y = move_y(-2);
			}
	    }
	    }
	    
	    else {
	    	if (steps == false) {
	    		if (direction == 0) { // direction is to the right (x + 1)
	    			pos_x = move_prev_x(1);
	    		}
	    		else if (direction == 1) { //right one & up one (x + 1 and y + 1)
	    			pos_x = move_prev_x(1);
	    			pos_y = move_prev_y(1);			
	    		}
	    		else if (direction == 2) { //up (y + 1)
	    			pos_y = move_prev_y(1);
	    		}
	    		else if (direction == 3) { //left one & up one (x - 1, y + 1)
	    			pos_x = move_prev_x(-1);
	    			pos_y = move_prev_y(1);
	    		}
	    		else if (direction == 4) { //just left (x - 1)
	    			pos_x = move_prev_x(-1);
	    		}
	    		else if (direction == 5) { //left one & down one (x - 1, y - 1)
	    			pos_x = move_prev_x(-1);
	    			pos_y = move_prev_y(-1);
	    		}
	    		else if (direction == 6) { //down (y-1)
	    			pos_y = move_prev_y(-1);
	    		}
	    		else if (direction == 7) { //down one & right one (x + 1, y - 1)
	    			pos_x = move_prev_x(1);
	    			pos_y = move_prev_y(-1);
	    		}
	    	}
	    	
	    	else if (steps == true) {
	    		if (direction == 0) { // direction is to the right (x + 1)
	    			pos_x = move_prev_x(2);
	    		}
	    		else if (direction == 1) { //right one & up one (x + 1 and y + 1)
	    			pos_x = move_prev_x(2);
	    			pos_y = move_prev_y(2);			
	    		}
	    		else if (direction == 2) { //up (y + 1)
	    			pos_y = move_prev_y(2);
	    		}
	    		else if (direction == 3) { //left one & up one (x - 1, y + 1)
	    			pos_x = move_prev_x(-2);
	    			pos_y = move_prev_y(2);
	    		}
	    		else if (direction == 4) { //just left (x - 1)
	    			pos_x = move_prev_x(-2);
	    		}
	    		else if (direction == 5) { //left one & down one (x - 1, y - 1)
	    			pos_x = move_prev_x(-2);
	    			pos_y = move_prev_y(-2);
	    		}
	    		else if (direction == 6) { //down (y-1)
	    			pos_y = move_prev_y(-2);
	    		}
	    		else if (direction == 7) { //down one & right one (x + 1, y - 1)
	    			pos_x = move_prev_x(2);
	    			pos_y = move_prev_y(-2);
	    		}
	    	}
	    }
	    
	    if (method_called.equals("fight")) {
	    	for (Critter a : population) {
	    		if (a.x_coord == pos_x) {
	    			if (a.y_coord == pos_y) {
	    				occupied = true;
	    				crit = a.toString();
	    			}
	    		}
	    	}
	    }
	    
	    else {
	    	for (Critter a : population) {
	    		if (a.x_coord == pos_x) {
	    			if (a.y_coord == pos_y) {
	    				occupied = true;
	    				crit = a.toString();
	    			}
	    		}
	    	}
	    	
	    }
	    energy -= Params.look_energy_cost;
	    
	    if (occupied) { 
	    	return crit; 
	    }
	    else 
	    	return null;
	    
	    
	    
	}
	
	
	
	/* End of Project 5 New Stuff*/
	
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	private boolean move;
	private int x_coord;
	private int y_coord;
	private int x_coord_prev;
	private int y_coord_prev;
	
	
	private final int move_prev_y(int move) {
		if ((y_coord_prev + move) > (Params.world_height - 1)) { //incase you go off the world you need wrap around
			return (move - 1); //so if i'm at the very end of the world and i take one step up, you need to come all the way down
		}
		else if ((y_coord_prev + move) < 0) { //you were at the very bottom but then you just moved back, so you need to go to very top
			return (Params.world_height + move);
		}
		else { //your move is valid & there's no need to wrap around
			return (y_coord_prev + move);
		}
	}
	
	private final int move_prev_x(int move) {
		if ((x_coord_prev + move) > (Params.world_width - 1)) { //incase you go off the world you need wrap around
			return (move - 1); //so if i'm at the very end of the world and i take one step to the right you need to be back at 0
		}
		else if ((x_coord_prev + move) < 0) { //you were at the very left but then you just moved back, so you need to go to the other side of the world
			return (Params.world_width + move);
		}
		else { //your move is valid & there's no need to wrap around
			return (x_coord_prev + move);
		}
	}
	
	
	
	
	private final int move_y(int move) {
		if ((y_coord + move) > (Params.world_height - 1)) { //incase you go off the world you need wrap around
			return (move - 1); //so if i'm at the very end of the world and i take one step up, you need to come all the way down
		}
		else if ((y_coord + move) < 0) { //you were at the very bottom but then you just moved back, so you need to go to very top
			return (Params.world_height + move);
		}
		else { //your move is valid & there's no need to wrap around
			return (y_coord + move);
		}
	}
	
	private final int move_x(int move) {
		if ((x_coord + move) > (Params.world_width - 1)) { //incase you go off the world you need wrap around
			return (move - 1); //so if i'm at the very end of the world and i take one step to the right you need to be back at 0
		}
		else if ((x_coord + move) < 0) { //you were at the very left but then you just moved back, so you need to go to the other side of the world
			return (Params.world_width + move);
		}
		else { //your move is valid & there's no need to wrap around
			return (x_coord + move);
		}
	}
	
	protected final void walk(int direction) {
		x_coord_prev = x_coord;
		y_coord_prev = y_coord;
		if (energy <= 0){
			return;
		}
		
		if (move == true) {
			energy -= Params.walk_energy_cost;
			return;
		}

		if (direction == 0) { // direction is to the right (x + 1)
			x_coord = move_x(1);
		}
		else if (direction == 1) { //right one & up one (x + 1 and y + 1)
			x_coord = move_x(1);
			y_coord = move_y(1);			
		}
		else if (direction == 2) { //up (y + 1)
			y_coord = move_y(1);
		}
		else if (direction == 3) { //left one & up one (x - 1, y + 1)
			x_coord = move_x(-1);
			y_coord = move_y(1);
		}
		else if (direction == 4) { //just left (x - 1)
			x_coord = move_x(-1);
		}
		else if (direction == 5) { //left one & down one (x - 1, y - 1)
			x_coord = move_x(-1);
			y_coord = move_y(-1);
		}
		else if (direction == 6) { //down (y-1)
			y_coord = move_y(-1);
		}
		else if (direction == 7) { //down one & right one (x + 1, y - 1)
			x_coord = move_x(1);
			y_coord = move_y(-1);
		}
		
		energy -= Params.walk_energy_cost;
		move = true;
	}
	
	protected final void run(int direction) {
		x_coord_prev = x_coord;
		y_coord_prev = y_coord;
		if (energy <= 0){
			return;
		}
		if (move == true) {
			energy -= Params.run_energy_cost;
			return;
		}
		if (direction == 0) { // direction is to the right (x + 2)
			x_coord = move_x(2);
			
		}
		else if (direction == 1) { //right one & up one (x + 2 and y + 2)
			x_coord = move_x(2);
			y_coord = move_y(2);			
		}
		else if (direction == 2) { //up (y + 2)
			y_coord = move_y(2);
		}
		else if (direction == 3) { //left one & up one (x - 2, y + 2)
			x_coord = move_x(-2);
			y_coord = move_y(2);
		}
		else if (direction == 4) { //just left (x - 2)
			x_coord = move_x(-2);
		}
		else if (direction == 5) { //left one & down one (x - 2, y - 2)
			x_coord = move_x(-2);
			y_coord = move_y(-2);
		}
		else if (direction == 6) { //down (y-1)
			y_coord = move_y(-2);
		}
		else if (direction == 7) { //down one & right one (x + 2, y - 2)
			x_coord = move_x(2);
			y_coord = move_y(-2);
		}

		
		energy -= Params.run_energy_cost;
		move = true;
	}
	
	protected final void reproduce(Critter offspring, int direction) {
		if (this.energy < Params.min_reproduce_energy) {
			return;
		}
					
		//assign energy
		if (this.energy % 2 != 0) {
			offspring.energy = (this.energy/2) + 1;
			this.energy = (this.energy/2) + 1;
		}
		else {
			offspring.energy = (this.energy/2);
			this.energy = (this.energy/2);
		}
		
		//update direction
		offspring.x_coord = this.x_coord;
		offspring.y_coord = this.y_coord;
		if (direction == 0) { 
			offspring.x_coord = move_x(1);
			
		}
		else if (direction == 1) { 
			offspring.x_coord = move_x(1);
			offspring.y_coord = move_y(1);			
		}
		else if (direction == 2) { 
			offspring.y_coord = move_y(1);
		}
		else if (direction == 3) { 
			offspring.x_coord = move_x(-1);
			offspring.y_coord = move_y(1);
		}
		else if (direction == 4) { 
			offspring.x_coord = move_x(-1);
		}
		else if (direction == 5) { 
			offspring.x_coord = move_x(-1);
			offspring.y_coord = move_y(-1);
		}
		else if (direction == 6) { 
			offspring.y_coord = move_y(-1);
		}
		else if (direction == 7) { 
			offspring.x_coord = move_x(1);
			offspring.y_coord = move_y(-1);
		}
		
		babies.add(offspring);
				
		
	}
		

	public abstract void doTimeStep();
	
	
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {

		Class<?> myClass = null;
		
		
		try {
			myClass = Class.forName(myPackage + "." + critter_class_name);
			
			if (!Critter.class.isAssignableFrom(myClass)){
				throw new InvalidCritterException(critter_class_name);
			}
			
			Critter c = (Critter) myClass.newInstance();
			
			c.energy = Params.start_energy;
			c.x_coord = getRandomInt(Params.world_width);
			c.y_coord = getRandomInt(Params.world_height);
			
			population.add(c);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		Class<?> myClass = null;
		
		try {
			myClass = Class.forName(myPackage + "." + critter_class_name);
			if (!Critter.class.isAssignableFrom(myClass)){
				throw new InvalidCritterException(critter_class_name);
			}
			
			List<Critter> list = new java.util.ArrayList<Critter>();
			
			for(int i = 0; i < population.size(); i++) {
				Critter check = population.get(i);
				if(myClass.isInstance(check)) {
					list.add(check);
				}
			}
			
			return list;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		population.clear();
		babies.clear();
	}
	
	public static void worldTimeStep() {
		
		// doTimeSteps();
		for(int i = 0; i < population.size(); i++){
			population.get(i).doTimeStep();;
		}
		
		// Fights
		for(int i = 0; i < Params.world_width; i++){
			for(int j = 0; j < Params.world_height; j++){
				
				ArrayList<Integer> index = new ArrayList<>();
				
				int k = 0;
				while(k < population.size()){
					if (population.get(k).energy > 0 &&
						population.get(k).x_coord == i &&
						population.get(k).y_coord == j){
						
						index.add(k);
						
					}
					k++;
				}
				
				while(index.size() > 1){
					
					encounter(index.get(0), index.get(1));
					
					
					if (population.get(index.get(1)).energy <= 0 || 
					   (population.get(index.get(1)).x_coord != i && population.get(index.get(1)).y_coord != j)){
					    index.remove(1);
					}
					
					if (population.get(index.get(0)).energy <= 0 || 
					   (population.get(index.get(0)).x_coord != i && population.get(index.get(0)).y_coord != j)){
						index.remove(0);
					}
					
				}
			}
		}
		
		
		// Subtract Rest Energy
		for(int i = 0; i < population.size(); i++){
			population.get(i).energy -= Params.rest_energy_cost;
		}
		
		
		// Add new Algae
		try {
			for(int j = 0; j < Params.refresh_algae_count; j++){
				makeCritter("Algae");
			}
		} catch (InvalidCritterException e) {
			// TODO Auto-generated catch block
		}
		
		
		// Move babies to population
		population.addAll(babies);
		babies.clear();
		
		
		
		// Kill dead Critters
		int i = 0;
		while(i < population.size()){
			if (population.get(i).energy <= 0){
				population.remove(i);
			}
			else{
				i++;
			}
		}
		
		// All Critters reset their move boolean
		for(int k = 0; k < population.size(); k++){
			population.get(k).move = false;
		}
	}
	
	public static void displayWorld() {
		// Main.clearIcons();
		
		
		for(int i = 0; i < Params.world_height; i++){
			for(int j = 0; j < Params.world_width; j++){
	            Main.shapes[i][j] = new Circle();
			}
		}
		
		for(int i = 0; i < population.size(); i++){
			
			Critter c = population.get(i);
			
			int x = c.x_coord;
			int y = c.y_coord;
			
			Shape s = null;
			
			switch(c.viewShape()){
			case CIRCLE:
				s = new Circle(x * Main.cellSize + Main.cellSize/2, y * Main.cellSize + Main.cellSize/2, 
							   Main.shapeSize/2);
				s.setFill(c.viewFillColor());
				s.setStroke(c.viewOutlineColor());
				break;
			case SQUARE:
				s = new Rectangle(x * Main.cellSize + (Main.cellSize - Main.shapeSize)/2, 
								  y * Main.cellSize + (Main.cellSize - Main.shapeSize)/2, 
								  Main.shapeSize, Main.shapeSize);
				s.setFill(c.viewFillColor());
				s.setStroke(c.viewOutlineColor());
				break;
			case TRIANGLE:
				Polygon triangle = new Polygon();
				
				Double[] tpts = {0.0, -0.5 * Main.shapeSize,
					    		 0.5 * Main.shapeSize, 0.4 * Main.shapeSize,
					    		 -0.5 * Main.shapeSize, 0.4 * Main.shapeSize };
				
				for(int j = 0; j < tpts.length; j++){
					tpts[j] += Main.cellSize / 2;
				}
				for(int j = 0; j < tpts.length; j+=2){
					tpts[j] += x * Main.cellSize;
				}
				for(int j = 1; j < tpts.length; j+=2){
					tpts[j] += y * Main.cellSize;
				}
				
				triangle.getPoints().addAll(tpts);
				
				s = triangle;
				s.setFill(c.viewFillColor());
				s.setStroke(c.viewOutlineColor());
				break;
			case DIAMOND:	
				Polygon diamond = new Polygon();
				
				Double[] dpts = {0.0, (double) Main.shapeSize / 2 - 1, 
								 (double) Main.shapeSize / 2.5, 0.0,
								 0.0, (double) -Main.shapeSize / 2 + 1,
								 (double) -Main.shapeSize / 2.5, 0.0};
				
				for(int j = 0; j < dpts.length; j++){
					dpts[j] += Main.cellSize / 2;
				}
				for(int j = 0; j < dpts.length; j+=2){
					dpts[j] += x * Main.cellSize;
				}
				for(int j = 1; j < dpts.length; j+=2){
					dpts[j] += y * Main.cellSize;
				}
				
				
				diamond.getPoints().addAll(dpts);
				
				s = diamond;
				
				s.setFill(c.viewFillColor());
				s.setStroke(c.viewOutlineColor());
				break;
			case STAR:
				Polygon star = new Polygon();
				
				Double[] spts = {0.0, -1.0,
								 0.25, -0.3,
								 0.9, -0.3,
								 0.35, 0.125,
								 0.65, 0.75,
								 0.0, 0.4,
								 -0.65, 0.75,
								 -0.35, 0.125,
								 -0.9, -0.3,
								 -0.25, -0.3};
				
				for(int j = 0; j < spts.length; j++){
					spts[j] *= Main.shapeSize / 2;
					spts[j] += Main.cellSize / 2;
				}
				for(int j = 0; j < spts.length; j+=2){
					spts[j] += x * Main.cellSize;
				}
				for(int j = 1; j < spts.length; j+=2){
					spts[j] += y * Main.cellSize;
				}
				
				star.getPoints().addAll(spts);
				
				s = star;
				s.setFill(c.viewFillColor());
				s.setStroke(c.viewOutlineColor());
				break;
			default:
				break;
			}
			
			Main.shapes[x][y] = s;
			
			
		}
		
		Main.displayIcons();
		
	}
	
	
	/**
	 * Flips a coin to see which Critter wins or loses
	 * @param c1 index in population of first Critter
	 * @param c2 index in population of second Critter
	 */
	private static void flipCoin(int c1, int c2){
		int r = getRandomInt(1);
		if (r == 1){
			population.get(c1).energy += population.get(c2).energy / 2;
			population.get(c2).energy = 0;
		}
		else{
			population.get(c2).energy += population.get(c1).energy / 2;
			population.get(c1).energy = 0;
		}
	}
	
	/**
	 * Check to see if a Critter can truly run away
	 * @param origX is the original x coord
	 * @param origY is the original y coord
	 * @param c is the index in population of the current Critter
	 * @return true if the Critter can actually move away, false if it cannot
	 */
	private static boolean isLegalRun(int origX, int origY, int c){
		// Didn't move because it has moved previously in time step
		if (origX == population.get(c).x_coord && origY == population.get(c).y_coord){
			return false;
		}
		
		// Check new position to see if new position has another Critter in it already
		int i = 0;
		while (i < population.size()){
			
			if (population.get(i).x_coord == population.get(c).x_coord &&
				population.get(i).y_coord == population.get(c).y_coord &&
				i != c){
				
				// Reset the position back to original
				population.get(c).x_coord = origX;
				population.get(c).y_coord = origY;
				return false;
			}
			
			i++;
		}
		
		// Successfully ran away
		return true;
	}
	
	/**
	 * Have two Critters fight each other until only one remains
	 * @param c1 is the index in population of the first Critter
	 * @param c2 is the index in population of the second Critter
	 * @param c1Energy is the energy of c1 after randomizing if needed
	 * @param c2Energy is the energy of c2 after randomizing if needed
	 */
	private static void fighting(int c1, int c2, int c1Energy, int c2Energy){
		if (population.get(c1).energy <= 0 || population.get(c2).energy <= 0){
			return;
		}
		
		if (c1Energy > c2Energy){
			population.get(c1).energy += population.get(c2).energy / 2;
			population.get(c2).energy = 0;
		}
		else if (c2Energy > c1Energy){
			population.get(c2).energy += population.get(c1).energy / 2;
			population.get(c1).energy = 0;
		}
		else if (c1Energy == c2Energy){
			flipCoin(c1, c2);
		}
	}
	
	
	/**
	 * Fight the two Critters
	 * @param c1 is the index of a Critter
	 * @param c2 is the index of a Critter
	 * @return 0 if c1 loses, 1 if c2 loses, 2 if only c1 runs away, 3 if only c2 runs away, 4 if both run away
	 */
	private static void encounter(int c1, int c2){
		
		// Original coordinates should be the same for c1 and c2
		int origX = population.get(c1).x_coord;
		int origY = population.get(c1).y_coord;
		
		/*
		// Special Case: Algae vs Algae
		if (population.get(c1) instanceof Algae && population.get(c2) instanceof Algae){
			// Flip coin
			flipCoin(c1, c2);
			return;
		}	
		
		// Special Case: Algae vs Critter
		if ((population.get(c1) instanceof Algae && !(population.get(c2) instanceof Algae)) ||
			(!(population.get(c1) instanceof Algae) && population.get(c2) instanceof Algae)){
			
			int alg = -1;
			int c = -1;
			if (population.get(c1) instanceof Algae){
				alg = c1;
				c = c2;
			}
			else if (population.get(c2) instanceof Algae){
				alg = c2;
				c = c1;
			}
			
			
			boolean willCfight = population.get(c).fight(population.get(alg).toString());
			
			
			// Critter chooses to fight Algae
			if (willCfight){
				fighting(c, alg, getRandomInt(population.get(c).energy), 0);
				return;
			}
			else{
				// Critter chooses to run away
				if (isLegalRun(origX, origY, c)){
					return;
				}
				else{
					fighting(c, alg, getRandomInt(population.get(c).energy), 0);
					return;
				}
				
			}
			
		}*/
		
		
		boolean willC1fight = population.get(c1).fight(population.get(c2).toString());
		boolean isC1LegalRun = true;
		if (!willC1fight){
			isC1LegalRun = isLegalRun(origX, origY, c1);
		}
		
		boolean willC2fight = population.get(c2).fight(population.get(c1).toString());
		boolean isC2LegalRun = true;
		if (!willC2fight){
			isC1LegalRun = isLegalRun(origX, origY, c2);
		}
		
		
		// Two Critters will fight
		if (willC1fight && willC2fight){
			
			int l1 = getRandomInt(population.get(c1).energy);
			int l2 = getRandomInt(population.get(c2).energy);
			
			fighting(c1, c2, l1, l2);
			return;
		}
		else{
			// At least one of them tries to run away.
			
			// If their coordinates are the same, we know that there must be a fight
			if (population.get(c1).x_coord == population.get(c2).x_coord &&
				population.get(c1).y_coord == population.get(c2).y_coord &&
				population.get(c1).energy > 0 && population.get(c2).energy > 0){
				
				int l1 = getRandomInt(population.get(c1).energy);
				int l2 = getRandomInt(population.get(c2).energy);
				
				fighting(c1, c2, l1, l2);
				return;
				
			}
			else{
				// If their coordinates are not the same, then we know that one of them successfully escaped or both of them did.
				return;
			}
		}
		
		
	}
}
