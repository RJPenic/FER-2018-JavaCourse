package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BiFunction;

/**
 * Class representing a Wrapper for a object value
 * 
 * @author Rafael Josip Penić
 *
 */
public class ValueWrapper {

	/**
	 * Value stored in wrapper
	 */
	private Object value;

	/**
	 * Constructor for ValueWrapper objects
	 * 
	 * @param value
	 *            value of the newly constructed ValueWrapper
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}

	/**
	 * Getter for a wrapper value
	 * 
	 * @return value of the wrapper
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Setter for the wrapper value
	 * 
	 * @param value
	 *            new value
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Method used for adding given object to a ValueWrapper value
	 * 
	 * @param incValue
	 *            object that will be added
	 * @throws RuntimeException
	 *             in case the addition cannot be done
	 */
	public void add(Object incValue) {
		value = calculate(identify(value), identify(incValue), (first, second) -> first + second);
	}

	/**
	 * Method used for subtracting given object from a ValueWrapper value
	 * 
	 * @param decValue
	 *            object for which the value will be subtracted
	 * @throws RuntimeException
	 *             in case the subtraction cannot be done
	 */
	public void subtract(Object decValue) {
		value = calculate(identify(value), identify(decValue), (first, second) -> first - second);
	}

	/**
	 * Method used of multiplying ValueWrapper value with the given value
	 * 
	 * @param mulValue
	 *            object with which the ValueWrapper value will be multiplied
	 * @throws RuntimeException
	 *             in case multiplification cannot be done
	 */
	public void multiply(Object mulValue) {
		value = calculate(identify(value), identify(mulValue), (first, second) -> first * second);
	}

	/**
	 * Method use for dividing ValueWrapper value with the given value
	 * 
	 * @param divValue
	 *            object with which the ValueWrapper value will be divided
	 * 
	 * @throws RuntimeException
	 *             in case division cannot be done
	 * @throws IllegalArgumentException
	 *             in case of dividing by zero and the result should be integer
	 */
	public void divide(Object divValue) {
		Number value1 = identify(value);
		Number value2 = identify(divValue);

		if (value1.getClass() == Integer.class && value2.getClass() == Integer.class
				&& ((Integer) value2).intValue() == 0)
			throw new IllegalArgumentException(
					"It is not allowed to divide with zero when the result should be integer.");

		value = calculate(value1, value2, (first, second) -> first / second);
	}

	/**
	 * Method used for comparing ValueWrapper value with the given object
	 * 
	 * @param withValue
	 *            object with which the value will be compared
	 * @return 1 if the value is greater than the given object, 0 if they are equal
	 *         and -1 otherwise
	 * @throws RuntimeException
	 *             in case the objects cannot be compared(e.g. comparing string with
	 *             integer)
	 */
	public int numCompare(Object withValue) {
		Number value1 = identify(value);
		Number value2 = identify(withValue);

		double first = value1.doubleValue();
		double second = value2.doubleValue();

		return Double.compare(first, second);
	}

	/**
	 * Method that calculates result of the given function with the given values
	 * 
	 * @param value1
	 *            first argument of the function
	 * @param value2
	 *            second argument of the function
	 * @param function
	 *            function which will "return" the result of the method
	 * @return result of the given function done on the 2 given values
	 */
	private static Object calculate(Number value1, Number value2, BiFunction<Double, Double, Double> function) {
		Double result = Double
				.valueOf(function.apply(value1.doubleValue(), value2.doubleValue()));

		if (value1.getClass() == Double.class || value2.getClass() == Double.class) {
			return result;
		}
		return Integer.valueOf(result.intValue());
	}

	/**
	 * Method that identifies which class is the given object and does necessary
	 * conversions(e.g. "1e-2" into double)
	 * 
	 * @param value
	 *            value to be identified
	 * @return value gotten as result of conversion
	 */
	private static Number identify(Object value) {
		if (value == null) {
			value = Integer.valueOf(0);
		} else if (value.getClass() == String.class) {
			String stringValue = (String) value;
			if (stringValue.contains("E") || stringValue.contains(".") || stringValue.contains("e")) {
				try {
					value = Double.valueOf(Double.parseDouble(stringValue));
				} catch (NumberFormatException ex) {
					throw new RuntimeException("Error while parsing string into double.");
				}
			} else {
				try {
					value = Integer.valueOf(Integer.parseInt(stringValue));
				} catch (NumberFormatException ex) {
					throw new RuntimeException("Error while parsing string into integer.");
				}
			}
		} else if (value.getClass() != Integer.class && value.getClass() != Double.class) {
			throw new RuntimeException("Value must be either String, Integer or Double.");
		}

		return (Number) value;
	}
}
