package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Interface that defines visitors that do certain action when they visit
 * certain node
 * 
 * @author Rafael Josip Penić
 *
 */
public interface INodeVisitor {

	/**
	 * Method that will be done when visiting text node
	 * 
	 * @param node
	 *            node that is visited
	 */
	public void visitTextNode(TextNode node);

	/**
	 * Method that will be done when visiting for-loop node
	 * 
	 * @param node
	 *            node that is visited
	 */
	public void visitForLoopNode(ForLoopNode node);

	/**
	 * Method that will be done when visiting echo node
	 * 
	 * @param node
	 *            node that is visited
	 */
	public void visitEchoNode(EchoNode node);

	/**
	 * Method that will be done when visiting document node
	 * 
	 * @param node
	 *            node that is visited
	 */
	public void visitDocumentNode(DocumentNode node);
}
