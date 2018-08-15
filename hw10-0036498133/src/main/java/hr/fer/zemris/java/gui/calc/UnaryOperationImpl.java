package hr.fer.zemris.java.gui.calc;

import java.util.Objects;
import java.util.function.DoubleUnaryOperator;

import javax.swing.JCheckBox;

/**
 * Implementation of the BinaryOperation interface
 * 
 * @author Rafael Josip Penić
 *
 */
public class UnaryOperationImpl implements UnaryOperation {

	/**
	 * Unary operation that will be performed if the invert flag is false
	 */
	private DoubleUnaryOperator notInvertedOp;

	/**
	 * Unary operation that will be performed if the invert flag is true
	 */
	private DoubleUnaryOperator invertedOp;

	/**
	 * Boolean flag which determines which unary operation will be
	 * performed(inverted operation or the not inverted one)
	 */
	private JCheckBox invFlag;

	/**
	 * Constructor for UnaryOperationImpl objects
	 * 
	 * @param notInvertedOp
	 *            unary operation that will be performed if the inversion flag is
	 *            not set
	 * @param invertedOp
	 *            unary operation that will be performed if the inversion flag is
	 *            set
	 * @param invFlag
	 *            determines if the operation should be inverted or not
	 */
	public UnaryOperationImpl(DoubleUnaryOperator notInvertedOp, DoubleUnaryOperator invertedOp, JCheckBox invFlag) {
		super();
		this.notInvertedOp = notInvertedOp;
		this.invertedOp = invertedOp;
		this.invFlag = invFlag;
	}

	@Override
	public void performAction(CalcModelImpl calculator) {
		Objects.requireNonNull(calculator, "Given CalcModel reference is null.");
		
		if (!invFlag.isSelected()) {
			calculator.setValue(notInvertedOp.applyAsDouble(calculator.getValue()));
		} else {
			calculator.setValue(invertedOp.applyAsDouble(calculator.getValue()));
		}
	}
}
