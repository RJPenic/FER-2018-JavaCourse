package hr.fer.zemris.java.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Class representing a simple form used when creating or updating a post
 * 
 * @author Rafael Josip Penić
 *
 */
public class EditPostForm {

	/**
	 * Title of the post to be created
	 */
	private String title;

	/**
	 * Text of the post to be created
	 */
	private String text;

	/**
	 * Posts ID(used when updating)
	 */
	private Long id;

	/**
	 * Map used as storage for errors and their descriptors
	 */
	private Map<String, String> errors = new HashMap<>();

	/**
	 * Getter for error linked with given property name
	 * 
	 * @param propertyName
	 *            name of the property with which the error is connected
	 * @return string containing info about the error
	 */
	public String getError(String propertyName) {
		return errors.get(propertyName);
	}

	/**
	 * Determines if there are any errors or not
	 * 
	 * @return true if there are errors in error map and false otherwise
	 */
	public boolean errorsExist() {
		return !errors.isEmpty();
	}

	/**
	 * Tests if there is an error linked with some property with the given name
	 * 
	 * @param propertyName
	 *            name of the property to which the error is linked to
	 * @return true if there is an error for the given property and false otherwise
	 */
	public boolean hasErrorForProperty(String propertyName) {
		return errors.containsKey(propertyName);
	}

	/**
	 * Fills form with info gotten from HTTP request
	 * 
	 * @param req
	 *            request from which the parameters will be extracted and stored in
	 *            form
	 */
	public void fillFromHttpRequest(HttpServletRequest req) {
		this.title = req.getParameter("title");
		this.text = req.getParameter("text");
	}

	/**
	 * Tests if current properties are valid and if they are not, fills errors map
	 * accordingly
	 */
	public void validateProperties() {
		errors.clear();

		if (this.text.isEmpty()) {
			errors.put("text", "Text is mandatory field!");
		}

		if (this.title.isEmpty()) {
			errors.put("title", "Title is mandatory field!");
		}
	}

	/**
	 * Getter for forms title
	 * 
	 * @return forms title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter for forms title
	 * 
	 * @param title
	 *            new form title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter for forms text
	 * 
	 * @return forms text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Setter for forms text
	 * 
	 * @param text
	 *            new form text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Getter for forms post ID
	 * 
	 * @return forms post ID
	 */
	public Long getID() {
		return id;
	}

	/**
	 * Setter for forms post ID
	 * 
	 * @param id
	 *            new form post ID
	 */
	public void setID(Long id) {
		this.id = id;
	}

}
