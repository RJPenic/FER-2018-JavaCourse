package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Class that represents current context and stores turtle states.
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public class Context {

	/**
	 * Stack that is used as a turtle state storage.
	 */
	ObjectStack stack;

	/**
	 * Constructor for context. It allocates a new stack.
	 */
	public Context() {
		stack = new ObjectStack();
	}

	/**
	 * Method that returns turtle state from the top of the stack without removing
	 * it.
	 * 
	 * @return current turtle state
	 * @throws EmptyStackException
	 *             if peeking empty stack.
	 */
	public TurtleState getCurrentState() throws EmptyStackException {
		return (TurtleState) (stack.peek());
	}

	/**
	 * Method that pushes given turtle state on the top of the stack.
	 * 
	 * @param state
	 *            turtle state that will be pushed on the stack.
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}

	/**
	 * Removes one turtle state from the top of the stack.
	 * 
	 * @throws EmptyStackException
	 *             if popping empty stack
	 */
	public void popState() throws EmptyStackException {
		stack.pop();
	}
}
