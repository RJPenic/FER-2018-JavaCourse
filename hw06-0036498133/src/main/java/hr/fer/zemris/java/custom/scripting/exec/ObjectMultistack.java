package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that allows storage of more values for a certain key using a stack-like
 * abstraction
 * 
 * @author Rafael Josip Penić
 *
 */
public class ObjectMultistack {

	/**
	 * Map used for storing ObjectMultistack pairs(key, stack)
	 */
	private Map<String, MultistackEntry> map;

	/**
	 * Class representing a single Multistack entry
	 * 
	 * @author Rafael Josip Penić
	 *
	 */
	private static class MultistackEntry {

		/**
		 * Value of the entry
		 */
		private ValueWrapper value;

		/**
		 * Reference to another entry
		 */
		private MultistackEntry next;

		/**
		 * Constructor for Multistack entries
		 * 
		 * @param value
		 *            value of the entry
		 * @param next
		 *            reference to the next entry
		 */
		public MultistackEntry(ValueWrapper value, MultistackEntry next) {
			super();
			this.value = value;
			this.next = next;
		}

	}

	/**
	 * Constructor for ObjectMultistack which allocates the map where the entries
	 * are stored
	 */
	public ObjectMultistack() {
		map = new HashMap<>();
	}

	/**
	 * Pushes given ValueWrapper on the stack with the given key
	 * 
	 * @param name
	 *            key of the stack
	 * @param valueWrapper
	 *            value that will be pushed on the stack
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		map.put(name, new MultistackEntry(valueWrapper, map.get(name)));
	}

	/**
	 * Returns ValueWrapper that is on the top of the stack with the given key and
	 * it removes it from the stack
	 * 
	 * @param name
	 *            key for the stack
	 * @return popped ValueWrapper
	 */
	public ValueWrapper pop(String name) {
		ValueWrapper result = peek(name);
		map.put(name, map.get(name).next);
		return result;
	}

	/**
	 * Returns ValueWrapper that is on the top of the stack with the given key, but
	 * it doesn't remove it
	 * 
	 * @param name
	 *            key for the stack
	 * @return peeked ValueWrapper
	 */
	public ValueWrapper peek(String name) {
		if (map.get(name) == null)
			throw new EmptyStackException();
		return map.get(name).value;
	}

	/**
	 * Method that tells if the stack for certain key is empty or not
	 * 
	 * @param name
	 *            stacks key
	 * @return true if the stack for the given key is empty, false otherwise
	 */
	public boolean isEmpty(String name) {
		return map.get(name) == null;
	}
}
