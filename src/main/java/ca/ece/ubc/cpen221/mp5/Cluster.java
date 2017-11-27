package ca.ece.ubc.cpen221.mp5;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

public class Cluster {

	Set<Business> businessSet;
	Coordinates centroid;

	public Cluster(double x, double y) {
		this.businessSet = new HashSet<Business>();
		this.centroid = new Coordinates(x, y);
	}

	public void addBusiness(Business business) {
		this.businessSet.add(business);
	}

	public void removeBusiness(Business business) {
		this.businessSet.remove(business);
	}

	public void adjustCentroid() {
		double sumX = 0;
		double sumY = 0;

		for (Business b : businessSet) {
			sumX += b.getLocation().getCoordinates().getX();
			sumY += b.getLocation().getCoordinates().getY();
		}

		this.centroid = new Coordinates(sumX / businessSet.size(), sumY / businessSet.size());
	}
	
	public Coordinates getCentroid() {
		Coordinates copy = new Coordinates( this.centroid.getX(), this.centroid.getY() );
		return copy;
	}

}
