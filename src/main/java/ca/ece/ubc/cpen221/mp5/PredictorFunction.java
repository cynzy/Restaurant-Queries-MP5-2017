package ca.ece.ubc.cpen221.mp5;

import java.util.function.ToDoubleBiFunction;

public class PredictorFunction implements ToDoubleBiFunction<MP5Db<Object>, String> {

	private double s_xx;
	private double s_yy;
	private double s_xy;
	private double meanX;
	private double meanY;

	public PredictorFunction(double s_xx, double s_yy, double s_xy, double meanX, double meanY) {
		this.s_xx = s_xx;
		this.s_xy = s_xy;
		this.s_yy = s_yy;
		this.meanX = meanX;
		this.meanY = meanY;
	}

	@Override
	public double applyAsDouble(MP5Db<Object> database, String businessID) {
		if (this.s_xx == 0 || this.s_xy == 0 || this.s_yy == 0) {
			return 0;
		}

		double b = this.s_xy / this.s_xx;
		double a = this.meanY - b * this.meanX;
		
		YelpDB db = (YelpDB) database;

		double x = db.getBusinessSet().stream().filter(business -> business.getBusinessID().equals(businessID))
				.map(business -> business.getPrice()).reduce(0, (y, z) -> y + z);

		if (x == 0) {
			return 0;
		}

		return a * x + b;
	}

}
