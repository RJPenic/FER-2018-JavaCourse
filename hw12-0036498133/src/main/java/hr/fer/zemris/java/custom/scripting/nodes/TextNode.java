package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * A node representing a piece of textual data.
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public class TextNode extends Node {

	/**
	 * String of the text stored in the node.
	 */
	private String text;

	/**
	 * Constructor of ForLoop node.
	 * 
	 * @param text
	 *            string of the text on which the text of the node willbe
	 *            initialized
	 */
	public TextNode(String text) {
		this.text = text;
	}

	/**
	 * Getter for text stored in the Text Node
	 * 
	 * @return string representing text in the node
	 */
	public String getText() {
		return text;
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}
}
