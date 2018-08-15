package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * A node representing a command which generates some textual output
 * dynamically.
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 */
public class EchoNode extends Node {

	/**
	 * Elements stored in the node
	 */
	private Element[] elements;

	/**
	 * Constructor for EchoNode class
	 * 
	 * @param elements
	 *            array that will be representing elements of the newly allocated
	 *            node
	 */
	public EchoNode(Element[] elements) {
		super();
		this.elements = elements;
	}

	/**
	 * Getter for the array where the elements are stored
	 * 
	 * @return array of nodes elements
	 */
	public Element[] getElements() {
		return elements;
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}
}
