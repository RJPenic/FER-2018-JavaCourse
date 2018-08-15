package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

/**
 * Represents a simple calculator
 * 
 * @author Rafael Josip Penić
 *
 */
public interface CalcModel {

	/**
	 * Adds given listener into calculators listeners list
	 * 
	 * @param l
	 *            listener to be added into listeners list
	 */
	void addCalcValueListener(CalcValueListener l);

	/**
	 * Removes given listener from calculators listeners list
	 * 
	 * @param l
	 *            lister to be remove from listeners list
	 */
	void removeCalcValueListener(CalcValueListener l);

	String toString();

	/**
	 * Getter for current calculators value
	 * 
	 * @return current calculators value
	 */
	double getValue();

	/**
	 * Setter for calculators value
	 * 
	 * @param value
	 *            calculators value
	 */
	void setValue(double value);

	/**
	 * Clears current value of the calculator
	 */
	void clear();

	/**
	 * Clears current value, active operand and pending operation of the calculator
	 */
	void clearAll();

	/**
	 * Changes sign of the calculators current value
	 */
	void swapSign();

	/**
	 * Inserts decimal point in calculators value
	 */
	void insertDecimalPoint();

	/**
	 * Inserts given digit in calculators value
	 * 
	 * @param digit
	 *            digit that will be inserted in calculators value
	 */
	void insertDigit(int digit);

	/**
	 * Method that determines if the active operand is set or not
	 * 
	 * @return true if the active operand is set and false otherwise
	 */
	boolean isActiveOperandSet();

	/**
	 * Getter for calculators active operand
	 * 
	 * @return calculators current active operand
	 */
	double getActiveOperand();

	/**
	 * Setter for calculators active operand
	 * 
	 * @param activeOperand
	 *            new active operand
	 */
	void setActiveOperand(double activeOperand);

	/**
	 * Clears calculators active operand
	 */
	void clearActiveOperand();

	/**
	 * Getter for pending binary operator of the calculator
	 * 
	 * @return calculators current binary operator
	 */
	DoubleBinaryOperator getPendingBinaryOperation();

	/**
	 * Setter for calculators pending binary operator
	 * 
	 * @param op
	 *            new binary operator
	 */
	void setPendingBinaryOperation(DoubleBinaryOperator op);
}