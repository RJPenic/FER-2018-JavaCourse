package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * This class demonstrates how ObjectStack class and its methods work. This will
 * be done by realizing reverse polish notation(postfix).
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 */
public class StackDemo {

	/*
	 * Constants
	 */
	public static final int INDEX_OF_EXPRESSION_ARGUMENT = 0;
	public static final int ZERO = 0;
	public static final int ONE = 1;
	
	/**
	 * Main method that realizes reverse polish notation using the string that
	 * method gets as command line argument.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		String[] tempStringArray = args[INDEX_OF_EXPRESSION_ARGUMENT].trim().split("\\s+");
		ObjectStack stack = new ObjectStack();

		for(int i = ZERO; i < tempStringArray.length; i++) {
			try {
				int number = Integer.parseInt(tempStringArray[i]);
				stack.push(number);
			} catch(NumberFormatException ex) {
				calculate(stack, tempStringArray[i]);
			}
		}

		if (stack.size() == ONE) {
			System.out.println("Expression evaluates to " + stack.pop() + ".");
		} else {
			System.out.println("Entered expression is invalid. Ending the program.");
		}
	}

	/**
	 * Static method that combines first 2 elements on stack via the given operator
	 * and return result on the stack.
	 * 
	 * @param stack
	 *            stack from which 2 integers will be taken
	 * @param operator
	 *            operator that will "combine" 2 taken integers
	 * @throws EmptyStackException
	 *             in case it is impossible to take 2 arguments from the stack
	 */
	private static void calculate(ObjectStack stack, String operator) {
		try {
			int arg2 = (int) stack.pop();
			int arg1 = (int) stack.pop();

			switch (operator) {
			case ("+"):
				stack.push(arg1 + arg2);
				break;
			case ("-"):
				stack.push(arg1 - arg2);
				break;
			case ("*"):
				stack.push(arg1 * arg2);
				break;
			case ("%"):
				stack.push(arg1 % arg2);
				break;
			case ("/"):
				if (arg2 != ZERO) {
					stack.push(arg1 / arg2);
					break;
				} else {
					System.out.println("Dividing by zero is not allowed.");
				}
			default:
				System.out.println("Entered expression is invalid. Ending the program.");
				System.exit(ONE);
			}
		} catch (EmptyStackException ex) {
			System.out.println("Insufficient number of arguments to do '" + operator + "' operation. Ending program.");
			System.exit(ONE);
		}
	}
}
