package hr.fer.zemris.java.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.cryptoUtil.CryptoUtil;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Class representing a simple form used for registering users
 * 
 * @author Rafael Josip Penić
 *
 */
public class RegistrationForm {

	/**
	 * Users first name
	 */
	private String firstName;

	/**
	 * Users last name
	 */
	private String lastName;

	/**
	 * Users email
	 */
	private String email;

	/**
	 * Users nickname
	 */
	private String nick;

	/**
	 * Users password
	 */
	private String password;

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

		if (this.firstName.isEmpty()) {
			errors.put("firstName", "First name is mandatory field!");
		}

		if (this.lastName.isEmpty()) {
			errors.put("lastName", "Last name is mandatory field!");
		}

		if (this.nick.isEmpty()) {
			errors.put("nick", "Nickname is mandatory field!");
		}
		
		if(!DAOProvider.getDAO().getBlogUserWithNick(this.nick).isEmpty()) {
			errors.put("nick", "Entered nickname is already taken!");
		};

		if (this.password.isEmpty()) {
			errors.put("password", "Password is mandatory field!");
		}

		int l = email.length();
		int p = email.indexOf('@');
		if (l < 3 || p == -1 || p == 0 || p == l - 1) {
			errors.put("email", "EMail is not formatted correctly!.");
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
		this.firstName = prepare(req.getParameter("firstName"));
		this.lastName = prepare(req.getParameter("lastName"));
		this.email = prepare(req.getParameter("email"));
		this.nick = prepare(req.getParameter("nick"));
		this.password = req.getParameter("password");
	}

	/**
	 * Prepares given string for further processing
	 * 
	 * @param s
	 *            string to be prepared
	 * @return transformed string
	 */
	private String prepare(String s) {
		if (s == null)
			return "";
		return s.trim();
	}

	/**
	 * Fills in info from the form to the given user object
	 * 
	 * @param user
	 *            blog user that will be filled with the info from forms
	 */
	public void fillInUser(BlogUser user) {
		user.setFirstName(this.firstName);
		user.setLastName(this.lastName);
		user.setEmail(this.email);
		user.setNick(this.nick);

		user.setPasswordHash(CryptoUtil.hashPassword(this.password));
	}

	/**
	 * Getter for first name
	 * 
	 * @return first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter for first name
	 * 
	 * @param firstName
	 *            new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter for last name
	 * 
	 * @return last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter for last name
	 * 
	 * @param lastName
	 *            new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter for email
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter for email
	 * 
	 * @param email
	 *            new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter for nick
	 * 
	 * @return nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Setter for nick
	 * 
	 * @param nick
	 *            new nick
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Getter for password
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter for password
	 * 
	 * @param password
	 *            new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
