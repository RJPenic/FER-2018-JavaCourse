package hr.fer.zemris.java.vector;

import java.util.Map;
import java.util.Objects;

/**
 * Represents a TFIDF vector that is used when calculating matching factor
 * between the query and the data source entry
 * 
 * @author Rafael Josip Penić
 *
 */
public class TFIDFVector {

	/**
	 * Vector coefficients
	 */
	private Map<String, Double> tfIdfValues;

	/**
	 * Constructor that receives Map with vector coefficients
	 * 
	 * @param tfIdfValues
	 *            vector coefficients
	 */
	public TFIDFVector(Map<String, Double> tfIdfValues) {
		Objects.requireNonNull(tfIdfValues, "Given value map reference is null.");

		this.tfIdfValues = tfIdfValues;
	}

	/**
	 * Calculates and returns module of the vector
	 * 
	 * @return vectors module
	 */
	public double module() {
		double mod = 0;

		for (double val : tfIdfValues.values()) {
			mod += val * val;
		}

		return Math.sqrt(mod);
	}

	/**
	 * Multiplies this vector with the given vector
	 * 
	 * @param vector
	 *            vector with which this vector will be multiplied
	 * @return result of multiplication
	 */
	public double multiply(TFIDFVector vector) {
		double result = 0;

		for (Map.Entry<String, Double> entry : vector.tfIdfValues.entrySet()) {
			Double val = tfIdfValues.get(entry.getKey());
			if (val == null)
				continue;

			result += val * entry.getValue();
		}

		return result;
	}
}
