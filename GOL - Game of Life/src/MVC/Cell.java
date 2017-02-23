package MVC;

public class Cell {
	
	private boolean alive;
	private double coordinate;
	
	public Cell(boolean b, double d) {
		
		alive = b;
		coordinate = d;
	}
	
	public boolean getAlive() {
		return alive;
	}
	
	public void setAlive(boolean b) {
		alive = b;
	}
	
	/*public double getCrdinate() {
		return coordinate;
	}*/
	

}
