package ca.ece.ubc.cpen221.mp5;

public class Coordinates {

	private double x;
	private double y;

	public Coordinates(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void setX(double newX) {
		this.x = newX;
	}

	public void setY(double newY) {
		this.y = newY;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double getDistance(Coordinates other) {
		double xDistance = this.x - other.getX();
		double yDistance = this.y - other.getY();

		return Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
	}

}
