package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Interface that defines basic methods which are needed to work with the
 * database
 * 
 * @author Rafael Josip Penić
 *
 */
public interface DAO {

	/**
	 * Dohvaća entry sa zadanim <code>id</code>-em. Ako takav entry ne postoji,
	 * vraća <code>null</code>.
	 * 
	 * @param id
	 *            ključ zapisa
	 * @return entry ili <code>null</code> ako entry ne postoji
	 * @throws DAOException
	 *             ako dođe do pogreške pri dohvatu podataka
	 */
	BlogEntry getBlogEntry(Long id) throws DAOException;

	/**
	 * Inserts given user in database
	 * 
	 * @param user
	 *            user to be added into database
	 * @throws DAOException
	 *             in case of an error while inserting the user
	 */
	void insertNewUser(BlogUser user) throws DAOException;

	/**
	 * Gets all registered blog users in database
	 * 
	 * @return list containing all blog users
	 * @throws DAOException
	 *             in case of an error while getting users
	 */
	List<BlogUser> getBlogUsers() throws DAOException;

	/**
	 * Gets blog user with the given nick and password
	 * 
	 * @param nick
	 *            users nickname
	 * @param password
	 *            users password
	 * @return list containing one or none users
	 * @throws DAOException
	 *             in case of an error while working with database
	 */
	List<BlogUser> getBlogUser(String nick, String password) throws DAOException;

	/**
	 * Gets blog user with the given nick
	 * 
	 * @param nick
	 *            users nickname
	 * @return list containing one or none users
	 * @throws DAOException
	 *             in case of an error while working with database
	 */
	List<BlogUser> getBlogUserWithNick(String nick) throws DAOException;

	/**
	 * Gets all entries/posts posted by the given user
	 * 
	 * @param user
	 *            user which entries will be selected
	 * @return list containing all entries posted by the given user
	 * @throws DAOException
	 *             in case of an error while working with database
	 */
	List<BlogEntry> getBlogEntriesForUser(BlogUser user) throws DAOException;

	/**
	 * Inserts new comment into database
	 * 
	 * @param comment
	 *            comment to be inserted
	 * @throws DAOException
	 *             in case of an error while inserting
	 */
	void insertNewComment(BlogComment comment) throws DAOException;

	/**
	 * Updates give entry
	 * 
	 * @param entry
	 *            entry to be updated
	 * @throws DAOException
	 *             in case of an error while updating entry
	 */
	void updateEntry(BlogEntry entry) throws DAOException;
}