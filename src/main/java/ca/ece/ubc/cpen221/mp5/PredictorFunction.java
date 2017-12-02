package ca.ece.ubc.cpen221.mp5;

import java.util.function.ToDoubleBiFunction;

/**
 * PredictorFunction - represents a function. tailored to a specific user, which
 * predicts the rating that user will give to a specific restaurant based on
 * previous ratings and the restaurant's price. The database allows to access
 * the specific business for which a client would wish to predict a rating for.
 * The function then performs a linear regression using data passed on to the
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
 * b is the y intercept calculated from the least square regression analysis
 *
 */
public class PredictorFunction implements ToDoubleBiFunction<MP5Db<Object>, String> {

	private double s_xx;
	private double s_yy;
	private double s_xy;
	private double meanX;
	private double meanY;

	/**
	 * Constructs an instance of this PredictorFunction
	 *
	 * @param s_xx
	 *            Sum of squares s_xx
	 * @param s_yy
	 *            Sum of squares s_yy
	 * @param s_xy
	 *            Sum of squares s_xy
	 * @param meanX
	 *            Average price for restaurants in the user's rating
	 * @param MeanY
	 *            Average rating the user gave on their reviews
	 */
	public PredictorFunction(double s_xx, double s_yy, double s_xy, double meanX, double meanY) {
		this.s_xx = s_xx;
		this.s_xy = s_xy;
		this.s_yy = s_yy;
		this.meanX = meanX;
		this.meanY = meanY;
	}

	/**
	 * Computes the rating that will be given to the business with a matching
	 * business ID based on its price. The database inputed into this method gives
	 * the data necessary to fine the price of the business. The price is then used
	 * (through least square regression analysis computed from the data of the user
	 * chosen for the PredictorFunction) to compute the predicted rating the user
	 * will give to this business based on a linear function. If there is not enough
	 * data from the user to compute a prediction (i.e. one of the instance fields
	 * is 0), then the function is undefined and throws an
	 * UnsupportedOperationException. If businessID does not correspond to a
	 * business that exists in database, throws an IllegalArgumentException. If the
	 * rating predicted is above 5, the function returns 5. If the predicted rating
	 * is below 1, the function returns 1.
	 *
	 * @param database
	 *            the database containing the data required to acquire the price of
	 *            the business for which the rating is predicted
	 * 
	 * @param businessID
	 *            the business ID for which the rating is predicted
	 * 
	 * @return The predicated rating, between 1 and 5, that the user will give the
	 *         business
	 * 
	 */
	@Override
	public double applyAsDouble(MP5Db<Object> database, String businessID) {
		if (this.s_xx == 0 || this.s_xy == 0 || this.s_yy == 0) {
			throw new UnsupportedOperationException("not enough data to compute a prediction");
		}

		double b = this.s_xy / this.s_xx;
		double a = this.meanY - b * this.meanX;

		YelpDB db = (YelpDB) database;

		double x = db.getBusinessSet().stream().filter(business -> business.getBusinessID().equals(businessID))
				.map(business -> business.getPrice()).reduce(0, (y, z) -> y + z);

		if (x == 0) {
			throw new IllegalArgumentException("business does not exist in the database");
		}

		double y = a * x + b;

		if (y > 5) {
			return 5;
		}
		if (y < 1) {
			return 1;
		}

		return y;

	}

}
