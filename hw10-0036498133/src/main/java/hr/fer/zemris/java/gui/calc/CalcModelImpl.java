package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

/**
 * Implementation of the CalcModel interface
 * 
 * @author Rafael Josip Penić
 *
 */
public class CalcModelImpl implements CalcModel {

	/**
	 * Current value of the calculator
	 */
	private String value;

	/**
	 * Calculators current active operand(used when performing binary operation)
	 */
	private double activeOperand;

	/**
	 * Boolean flag which determines if the active operand is set or not
	 */
	private boolean activeOperandFlag = false;

	/**
	 * Pending binary operator of the calculator
	 */
	private DoubleBinaryOperator pendingBinaryOperator;

	/**
	 * List that stores calculators listeners
	 */
	private List<CalcValueListener> listeners = new ArrayList<>();

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		Objects.requireNonNull(l, "Given listener reference is null.");
		listeners.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		Objects.requireNonNull(l, "Given listener reference is null.");
		listeners.remove(l);
	}

	@Override
	public double getValue() {
		if (value == null)
			return 0.0;

		return Double.parseDouble(value);
	}

	@Override
	public void setValue(double value) {
		if (Double.isNaN(value) || Double.isInfinite(value))
			throw new IllegalArgumentException("Given double is infinity or NaN(Not a number).");

		this.value = Double.toString(value);
		notifyListeners();
	}

	@Override
	public void clear() {
		value = null;
		notifyListeners();
	}

	@Override
	public void clearAll() {
		clear();
		clearActiveOperand();
		pendingBinaryOperator = null;
		notifyListeners();
	}

	@Override
	public void swapSign() {
		if (value != null && !value.isEmpty()) {
			if (value.startsWith("-")) {
				value = value.substring(1);
			} else {
				value = "-" + value;
			}

			notifyListeners();
		}
	}

	@Override
	public void insertDecimalPoint() {
		if (value == null) {
			value = new String();
		}

		if (!value.contains(".")) {
			if (value.isEmpty()) {
				value += "0.";
			} else {
				value += ".";
			}

			notifyListeners();
		}
	}

	@Override
	public void insertDigit(int digit) {
		if (value == null) {
			value = new String();
		}

		if (!(Double.parseDouble(value + digit) > Double.MAX_VALUE)) {
			if (!(digit == 0 && value.equals("0"))) {
				value += digit;

				if (value.startsWith("0") && value.length() > 1 && !value.startsWith("0.")) {
					value = value.substring(1);
				}

				notifyListeners();
			}
		}
	}

	@Override
	public boolean isActiveOperandSet() {
		return activeOperandFlag;
	}

	@Override
	public double getActiveOperand() {
		if (!isActiveOperandSet())
			throw new IllegalStateException("Active operand is not set.");

		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		activeOperandFlag = true;
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = 0;
		activeOperandFlag = false;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingBinaryOperator;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		Objects.requireNonNull(op, "Given operator is null.");

		this.pendingBinaryOperator = op;
	}

	@Override
	public String toString() {
		if (value == null)
			return "0";

		if (value.startsWith("."))
			return Double.toString(Double.parseDouble(value));

		return value;
	}

	/**
	 * Method that notifies all listeners of the model that stored value changed
	 */
	public void notifyListeners() {
		for (CalcValueListener listener : listeners) {
			listener.valueChanged(this);
		}
	}
}
