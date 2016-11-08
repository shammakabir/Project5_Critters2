package assignment5;


public class MyCritter4 extends Critter {
	
	@Override
	public void doTimeStep() {
		int weird = Critter.getRandomInt(2);
		if (weird == 0) {
			int dir = Critter.getRandomInt(8);
			walk(dir);
		}
		else if (weird == 1) {
			int dir = Critter.getRandomInt(8);
			run(dir);
		}
		else if (weird == 2) {
			MyCritter4 baby = new MyCritter4();
			reproduce(baby, Critter.getRandomInt(8));	
		}
	}

	@Override
	public boolean fight(String opponent) {
		if (opponent.equals("*")) {
			return true;
		}
		else if (opponent.equals("@")) {
			MyCritter4 baby = new MyCritter4();
			reproduce(baby, Critter.getRandomInt(8));
			return true;
		}
		else {
			int dir = Critter.getRandomInt(8);
			int weird = Critter.getRandomInt(1);
			if (weird == 1) { 
				look(dir, true);
				return true; 
				}
			else {
				look(dir, false);
				return false;
			}
		}

	}

	@Override
	public String toString () {
		return "4";
	}
	
	@Override
	public CritterShape viewShape() { return CritterShape.STAR; }

	@Override
	public javafx.scene.paint.Color viewColor() { return javafx.scene.paint.Color.YELLOW; }


}
