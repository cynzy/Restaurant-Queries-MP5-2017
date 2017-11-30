package ca.ece.ubc.cpen221.mp5;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Cluster {

	private Set<Business> businessSet;
	private Coordinates centroid;
	private boolean isFinished;

	public Cluster(double x, double y) {
		this.businessSet = new HashSet<Business>();
		this.centroid = new Coordinates(x, y);
		this.isFinished = false;
	}

	public Set<Business> getBusinessSet() {
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
	
	public boolean isEmpty() {
		return this.businessSet.isEmpty();
	}

	public void adjustCentroid() {

		List<Coordinates> coordinateList = this.businessSet.stream().map(business -> business.getLocation())
				.map(location -> location.getCoordinates()).collect(Collectors.toList());

		double longAverage = coordinateList.stream().map(coordinate -> coordinate.getlongitude()).reduce(0.0,
				(x, y) -> x + y) / this.businessSet.size();
		double latAverage = coordinateList.stream().map(coordinate -> coordinate.getlatitude()).reduce(0.0,
				(x, y) -> x + y) / this.businessSet.size();

		Coordinates previousCentroid = new Coordinates(this.centroid.getlongitude(), this.centroid.getlatitude());
		this.centroid = new Coordinates(longAverage, latAverage);

		this.isFinished = this.centroid.equals(previousCentroid);
	}
	
	public boolean isFinished() {
		return this.isFinished;
	}

	public Coordinates getCentroid() {
		Coordinates copy = new Coordinates(this.centroid.getlongitude(), this.centroid.getlatitude());
		return copy;
	}

}
