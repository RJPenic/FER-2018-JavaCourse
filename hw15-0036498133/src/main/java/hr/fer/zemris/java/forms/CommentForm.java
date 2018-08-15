package hr.fer.zemris.java.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Class representing a simple submitting comment form
 * 
 * @author Rafael Josip Penić
 *
 */
public class CommentForm {

	/**
	 * Forms email
	 */
	private String email;

	/**
	 * Message entered in form
	 */
	private String message;

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
	 * Tests if current properties are valid and if they are not, fills errors map
	 * accordingly
	 */
	public void validateProperties() {
		errors.clear();

		if (email != null) {
			int l = email.length();
			int p = email.indexOf('@');
			if (l < 3 || p == -1 || p == 0 || p == l - 1) {
				errors.put("email", "EMail is not formatted correctly!");
			}
		}

		if (this.message.isEmpty()) {
			errors.put("message", "Message is mandatory field if you want to submit a comment!");
		}
	}

	/**
	 * Fills form with info gotten from HTTP request
	 * 
	 * @param req
	 *            request from which the parameters will be extracted and stored in
	 *            form
	 */
	public void fillFromHttpRequest(HttpServletRequest req) {
		this.email = req.getParameter("email");
		this.message = req.getParameter("message");
	}

	/**
	 * Getter for forms email
	 * 
	 * @return forms email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter for forms email
	 * 
	 * @param email
	 *            new form email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter for form message
	 * 
	 * @return form message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter for form message
	 * 
	 * @param message
	 *            new form message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
