package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Font;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.CompoundBorder;

import hr.fer.zemris.java.gui.layouts.CalcLayout;

/**
 * Program that realizes simple Calculator with GUI
 * 
 * @author Rafael Josip Penić
 *
 */
public class Calculator extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Calculator model in which the value and similar information are stored
	 */
	private CalcModelImpl calcModel = new CalcModelImpl();

	/**
	 * Stack on which the current value of the calculator can be pushed or popped
	 */
	private Stack<Double> stack = new Stack<>();

	/**
	 * Constructor for Calculator frame
	 */
	public Calculator() {
		setLocation(40, 40);
		setTitle("Calculator");
		setSize(490, 250);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
	}

	/**
	 * Method which initializes Graphical User Interface of the frame of the
	 * calculator
	 */
	private void initGUI() {
		setLayout(new CalcLayout(2));

		JLabel label = new JLabel();
		label.setBorder(new CompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1),
				BorderFactory.createEmptyBorder(0, 0, 0, 6)));
		label.setFont(new Font("Serif", Font.BOLD, 20));
		label.setBackground(Color.YELLOW);
		label.setOpaque(true);

		JButton equalsButton = new JButton("=");
		JButton clrButton = new JButton("clr");

		JButton recipButton = new JButton("1/x");
		JButton sinButton = new JButton("sin");

		JButton logButton = new JButton("log");
		JButton cosButton = new JButton("cos");

		JButton lnButton = new JButton("ln");
		JButton tanButton = new JButton("tan");

		JButton expButton = new JButton("x^n");
		JButton ctgButton = new JButton("ctg");

		JButton[] numberButton = new JButton[10];

		for (int i = 0; i < 10; i++) {
			numberButton[i] = new JButton(Integer.toString(i));
		}

		JButton negateButton = new JButton("+/-");
		JButton decimalPointButton = new JButton(".");
		JButton addButton = new JButton("+");
		JButton subButton = new JButton("-");
		JButton multiplyButton = new JButton("*");
		JButton divideButton = new JButton("/");

		JButton resetButton = new JButton("res");
		JButton pushButton = new JButton("push");
		JButton popButton = new JButton("pop");
		JCheckBox invertButton = new JCheckBox("Inv");

		// ------------------------------------------------

		add(label, "1,1");
		add(equalsButton, "1,6");
		add(clrButton, "1,7");

		add(recipButton, "2,1");
		add(sinButton, "2,2");
		add(numberButton[7], "2,3");
		add(numberButton[8], "2,4");
		add(numberButton[9], "2,5");
		add(divideButton, "2,6");
		add(resetButton, "2,7");

		add(logButton, "3,1");
		add(cosButton, "3,2");
		add(numberButton[4], "3,3");
		add(numberButton[5], "3,4");
		add(numberButton[6], "3,5");
		add(multiplyButton, "3,6");
		add(pushButton, "3,7");

		add(lnButton, "4,1");
		add(tanButton, "4,2");
		add(numberButton[1], "4,3");
		add(numberButton[2], "4,4");
		add(numberButton[3], "4,5");
		add(subButton, "4,6");
		add(popButton, "4,7");

		add(expButton, "5,1");
		add(ctgButton, "5,2");
		add(numberButton[0], "5,3");
		add(negateButton, "5,4");
		add(decimalPointButton, "5,5");
		add(addButton, "5,6");
		add(invertButton, "5,7");

		invertButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		invertButton.setBorderPainted(true);

		// ---------------------------------------------
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		calcModel.addCalcValueListener(l -> {
			label.setText(calcModel.toString());
		});

		for (int i = 0; i < 10; i++) {
			int temp = i;
			numberButton[i].addActionListener(e -> {
				calcModel.insertDigit(temp);
			});
		}

		equalsButton.addActionListener(e -> {
			try {
				calcModel.setValue(calcModel.getPendingBinaryOperation().applyAsDouble(calcModel.getActiveOperand(),
						calcModel.getValue()));
			} catch (IllegalArgumentException ex) {
				JOptionPane.showMessageDialog(null, "Error! Active operand will be cleared.");
			} catch (IllegalStateException ex) {
				JOptionPane.showMessageDialog(null, "Error! Active operand is not set.");
			}

			calcModel.clearActiveOperand();
		});

		clrButton.addActionListener(e -> {
			calcModel.clear();
			calcModel.notifyListeners();
		});

		recipButton.addActionListener(e -> {
			try {
				calcModel.setValue(1 / calcModel.getValue());
			} catch (IllegalArgumentException ex) {
				JOptionPane.showMessageDialog(null, "Cannot find reciprocal from zero.");
			}
		});

		sinButton.addActionListener(e -> {
			try {
				new UnaryOperationImpl(Math::sin, Math::asin, invertButton).performAction(calcModel);
			} catch (IllegalArgumentException ex) {
				JOptionPane.showMessageDialog(null, "Error! Argument of arcsin must be between -1 and 1!");
			}
		});

		logButton.addActionListener(e -> {
			try {
				new UnaryOperationImpl(Math::log10, (val) -> Math.pow(10, val), invertButton).performAction(calcModel);
			} catch (IllegalArgumentException ex) {
				JOptionPane.showMessageDialog(null, "Error! Logarithm argument must be positive.");
			}
		});

		cosButton.addActionListener(e -> {
			try {
				new UnaryOperationImpl(Math::cos, Math::acos, invertButton).performAction(calcModel);
			} catch (IllegalArgumentException ex) {
				JOptionPane.showMessageDialog(null, "Error! Argument of arccos must be between -1 and 1!");
			}
		});

		lnButton.addActionListener(e -> {
			try {
				new UnaryOperationImpl(Math::log, Math::exp, invertButton).performAction(calcModel);
			} catch (IllegalArgumentException ex) {
				JOptionPane.showMessageDialog(null, "Error! Logarithm argument must be positive.");
			}
		});

		tanButton.addActionListener(e -> {
			try {
				new UnaryOperationImpl(Math::tan, Math::atan, invertButton).performAction(calcModel);
			} catch (IllegalArgumentException ex) {
				JOptionPane.showMessageDialog(null, "Error while calculating tangens!");
			}
		});

		ctgButton.addActionListener(e -> {
			try {
				new UnaryOperationImpl(val -> 1 / Math.tan(val), val -> 1 / Math.atan(val), invertButton)
						.performAction(calcModel);
			} catch (IllegalArgumentException ex) {
				JOptionPane.showMessageDialog(null, "Error while calculating cotangens!");
			}
		});

		negateButton.addActionListener(e -> {
			calcModel.swapSign();
		});

		decimalPointButton.addActionListener(e -> {
			calcModel.insertDecimalPoint();
		});

		resetButton.addActionListener(e -> {
			calcModel.clearAll();
		});

		pushButton.addActionListener(e -> {
			stack.push(calcModel.getValue());
		});

		popButton.addActionListener(e -> {
			if (!stack.isEmpty()) {
				calcModel.setValue(stack.pop());
			} else {
				JOptionPane.showMessageDialog(null, "Stack is empty!");
			}
		});

		addButton.addActionListener(e -> {
			new BinaryOperationImpl((arg0, arg1) -> arg0 + arg1).performAction(calcModel);
			;
		});

		subButton.addActionListener(e -> {
			new BinaryOperationImpl((arg0, arg1) -> arg0 - arg1).performAction(calcModel);
		});

		multiplyButton.addActionListener(e -> {
			new BinaryOperationImpl((arg0, arg1) -> arg0 * arg1).performAction(calcModel);
		});

		divideButton.addActionListener(e -> {
			new BinaryOperationImpl((arg0, arg1) -> arg0 / arg1).performAction(calcModel);
		});

		expButton.addActionListener(e -> {
			if (!invertButton.isSelected()) {
				new BinaryOperationImpl((arg0, arg1) -> Math.pow(arg0, arg1)).performAction(calcModel);
			} else {
				new BinaryOperationImpl((arg0, arg1) -> Math.pow(arg0, 1 / arg1)).performAction(calcModel);
			}
		});
	}

	/**
	 * Main method
	 * 
	 * @param args
	 *            command line argument
	 */
	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			JFrame frame = new Calculator();
			frame.pack();
			frame.setVisible(true);
		});
	}
}
