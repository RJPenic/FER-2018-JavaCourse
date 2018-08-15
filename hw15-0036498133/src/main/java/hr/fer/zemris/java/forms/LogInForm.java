package hr.fer.zemris.java.forms;

import javax.servlet.http.HttpServletRequest;

/**
 * Class representing a simple submitting comment form
 * 
 * @author Rafael Josip Penić
 *
 */
public class LogInForm {

	/**
	 * User nickname
	 */
	private String nick;

	/**
	 * User password
	 */
	private String password;

	/**
	 * String containing error description(if there is one)
	 */
	private String error;

	/**
	 * return true if there is an error in the form
	 * 
	 * @return true in case error string is not null and false otherwise
	 */
	public boolean hasError() {
		return error != null;
	}

	/**
	 * Getter for error descriptor
	 * 
	 * @return error descriptor
	 */
	public String getError() {
		return error;
	}

	/**
	 * Fills form with info gotten from HTTP request
	 * 
	 * @param req
	 *            request from which the parameters will be extracted and stored in
	 *            form
	 */
	public void fillFromHttpRequest(HttpServletRequest req) {
		this.nick = req.getParameter("nick");
	}

	/**
	 * Getter for nickname
	 * 
	 * @return nickname
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Setter for nickname
	 * 
	 * @param nick
	 *            new nickname
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
	 * @param error
	 *            new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Setter for error descriptor
	 * 
	 * @param error
	 *            new error descriptor
	 */
	public void setError(String error) {
		this.error = error;
	}
}
