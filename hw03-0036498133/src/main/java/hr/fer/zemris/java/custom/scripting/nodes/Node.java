package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Base class for all graph nodes.
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public class Node {

	/**
	 * Collection where nodes children will be stored
	 */
	ArrayIndexedCollection collection;

	/**
	 * Constructor for Node that allocates nodes collection
	 */
	public Node() {
		collection = new ArrayIndexedCollection();
	}

	/**
	 * Method that adds node to a children collection of the node
	 * 
	 * @param child
	 *            node that will be added into the children collection
	 */
	public void addChildNode(Node child) {
		if (collection == null) {
			collection = new ArrayIndexedCollection();
		}

		collection.add(child);
	}

	/**
	 * Method that returns number of nodes children
	 * 
	 * @return number of the nodes children
	 */
	public int numberOfChildren() {
		return collection.size();
	}

	/**
	 * Method that gets a child on the given index from children collection
	 * 
	 * @param index
	 *            index of a child about to be returned
	 * @return child that is stored in the children collection on the given index
	 */
	public Node getChild(int index) {
		return (Node) collection.get(index);
	}
}
