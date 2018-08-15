package hr.fer.zemris.java.gui.calc;

import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

/**
 * Implementation of the BinaryOperation interface
 * 
 * @author Rafael Josip Penić
 *
 */
public class BinaryOperationImpl implements BinaryOperation {

	/**
	 * Binary operation that will be performed in performAction method
	 */
	private DoubleBinaryOperator op;

	/**
	 * Constructs a BinaryOperationImpl object with the given binary operator
	 * 
	 * @param op
	 *            operator defining what will binaryOperationImpl do(add, sub or
	 *            some other operation)
	 */
	public BinaryOperationImpl(DoubleBinaryOperator op) {
		this.op = op;
	}

	@Override
	public void performAction(CalcModelImpl calculator) {
		Objects.requireNonNull(calculator, "Given CalcModel reference is null.");
		
		if (calculator.isActiveOperandSet()) {
			calculator.setActiveOperand(calculator.getPendingBinaryOperation()
					.applyAsDouble(calculator.getActiveOperand(), calculator.getValue()));
		} else {
			calculator.setActiveOperand(calculator.getValue());
		}

		calculator.setPendingBinaryOperation((left, right) -> {
			return op.applyAsDouble(left, right);
		});

		calculator.clear();
	}

}
