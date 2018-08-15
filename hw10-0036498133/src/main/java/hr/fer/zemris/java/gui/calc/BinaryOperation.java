package hr.fer.zemris.java.gui.calc;

/**
 * Interface representing binary operations like addition, subtraction and
 * similar.
 * 
 * @author Rafael Josip Penić
 *
 */
public interface BinaryOperation {
	/**
	 * Method that performs the binary operation on the given calculator model
	 * 
	 * @param calculator
	 *            calculator model on which the operation will be done
	 */
	void performAction(CalcModelImpl calculator);
}
