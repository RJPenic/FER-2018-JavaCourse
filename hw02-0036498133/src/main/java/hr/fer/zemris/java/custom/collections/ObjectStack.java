package hr.fer.zemris.java.custom.collections;

/**
 * This class defines stack and basic stack methods like push, pop and etc.
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public class ObjectStack {

	/**
	 * Size of a stack that is considered empty.
	 */
	public static final int EMPTY_STACK_SIZE = 0;

	/**
	 * Collection in which elements of the stack will be stored.
	 */
	private ArrayIndexedCollection coll;

	/**
	 * Default constructor without any arguments which only allocates collection in
	 * which stack elements will be stored.
	 */
	public ObjectStack() {
		coll = new ArrayIndexedCollection();
	}

	/**
	 * Tells if there are any elements on the stack.
	 * 
	 * @return true if the stack is empty and false otherwise
	 */
	public boolean isEmpty() {
		return coll.isEmpty();
	}

	/**
	 * Method that calculates size of the stack(number of objects on the stack).
	 * 
	 * @return number of objects in the stack
	 */
	public int size() {
		return coll.size();
	}

	/**
	 * Adds given object on the stack.
	 * 
	 * @param value
	 *            Object that will be added on the stack.
	 */
	public void push(Object value) {
		coll.add(value);
	}

	/**
	 * Removes lastly added object from the stack and returns its value.
	 * 
	 * @return last added object on stack.
	 */
	public Object pop() {
		if (coll.size() == EMPTY_STACK_SIZE) {
			throw new EmptyStackException("It is not possible to remove elements from empty stack.");
		}

		Object tempObj = peek();
		coll.remove(coll.size() - 1);
		return tempObj;
	}

	/**
	 * Peeks at the top of the stack and returns the value of the "seen" object.
	 * 
	 * @return value of the object that was added to the stack last
	 */
	public Object peek() {
		return coll.get(coll.size() - 1);
	}

	/**
	 * Removes all the objects from stack.
	 */
	public void clear() {
		coll.clear();
	}
}
