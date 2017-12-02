package ca.ece.ubc.cpen221.mp5;

import java.util.function.ToDoubleBiFunction;

/**
 * PredictorFunction - represents a function. taylored to a specific user, which
 * predicts the rating that user will give to a specific restaurant based on
 * previous ratings and the restaurant's price. The database allows to access
 * the specific business for which a client would wish to predict a rating for.
 * The function then preforms a linear regression using data passed on to the
 * class to predict a rating given to that business.
 * 
 * Representation Invariants:
 *
 * - all instance fields are not null, and are the result of least square
 * regression analysis calculations done for the specific user the function
 * predicts a rating for.
 * 
 * Abstraction Function:
 * 
 * AF( s_xx, s_yy. s_xy, meanX, meanY ) = x -> a*X + b, where a is the slope and
 * b is the y intercept calcualted from the least square regression analysis
 *
 */
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
