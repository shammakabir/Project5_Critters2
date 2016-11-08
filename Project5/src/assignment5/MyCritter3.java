package assignment5;


public class MyCritter3 extends Critter {
	@Override
	public void doTimeStep() {
	}
	
	@Override
	/**
	 *always fights but then like reproduces
	 */
	public boolean fight(String opp) {
		MyCritter3 child = new MyCritter3();
		this.reproduce(child, 0);
		return true;
	}
	
	
	public String toString() {
		return "3";
	}
	
	@Override
	public CritterShape viewShape() { return CritterShape.CIRCLE; }

	@Override
	public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.RED; }


}
