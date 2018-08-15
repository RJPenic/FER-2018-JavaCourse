package hr.fer.zemris.java.gui.calc;

/**
 * Interface representing unary operations like finding logarithm, cosine and
 * similar.
 * 
 * @author Rafael Josip Penić
 *
 */
public interface UnaryOperation {
	
	/**
	 * Method that performs the unary operation on the given calculator model
	 * 
	 * @param calculator
	 *            calculator model on which the operation will be done
	 */
	void performAction(CalcModelImpl calculator);
}
