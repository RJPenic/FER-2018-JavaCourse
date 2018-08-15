package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;

/**
 * Provider for DAO which provides only one method that gets DAO
 * 
 * @author Rafael Josip Penić
 *
 */
public class DAOProvider {

	/**
	 * DAO object used for manipulating with database
	 */
	private static DAO dao = new JPADAOImpl();

	/**
	 * Getter for DAO
	 * 
	 * @return DAO
	 */
	public static DAO getDAO() {
		return dao;
	}

}