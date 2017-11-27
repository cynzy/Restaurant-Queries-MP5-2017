package ca.ece.ubc.cpen221.mp5;

import java.util.HashSet;
import java.util.Set;

public class Cluster {

	Set<Business> businessSet;
	Coordinates centroid;

	public Cluster(double x, double y) {
		this.businessSet = new HashSet<Business>();
		this.centroid = new Coordinates(x, y);
	}
	
	public Set<Business> getBusinessSet(){
		Set<Business> copy = new HashSet<Business>();
		copy.addAll(this.businessSet);
		
		return copy;
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
			sumX += b.getLocation().getCoordinates().getlongitude();
			sumY += b.getLocation().getCoordinates().getlatitude();
		}

		this.centroid = new Coordinates(sumX / businessSet.size(), sumY / businessSet.size());
	}
	
	public Coordinates getCentroid() {
		Coordinates copy = new Coordinates( this.centroid.getlongitude(), this.centroid.getlatitude() );
		return copy;
	}

}
