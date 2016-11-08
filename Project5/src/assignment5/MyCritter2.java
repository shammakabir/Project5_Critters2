package assignment5;


public class  MyCritter2 extends Critter {
	private int dir;
	
	public MyCritter2() {
		dir = Critter.getRandomInt(8);

	}
	
	@Override
	public void doTimeStep () {
		look(dir, false);
		//always runs
		run(dir);
		
		//random movements
		int rand = Critter.getRandomInt(4);
		if (rand == 0) { dir = 1; }
		if (rand == 1) { dir = 3; }
		if (rand == 2) { dir = 5; }
		if (rand == 3) { dir = 7; }
		

		//random reproduction 
		if (this.getEnergy() > 89) {
			MyCritter2 baby = new MyCritter2();
			reproduce(baby, 3);
		}
		
		
		
	}

	@Override
	public boolean fight(String oponent) {
		return true;
	}
	
	public String toString() {
		return "2";
	}
	@Override
	public CritterShape viewShape() { return CritterShape.DIAMOND; }

	@Override
	public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.INDIGO; }

}
