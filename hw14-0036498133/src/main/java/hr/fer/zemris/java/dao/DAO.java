package hr.fer.zemris.java.dao;

import java.util.List;

import hr.fer.zemris.java.model.Poll;
import hr.fer.zemris.java.model.PollOption;

/**
 * Sučelje prema podsustavu za perzistenciju podataka.
 * 
 * @author marcupic
 *
 */
public interface DAO {
	/**
	 * Extracts list of polls from the database
	 * 
	 * @return list containing currently available polls
	 * @throws DAOException
	 *             in case of an error while working with database
	 */
	public List<Poll> getPollList() throws DAOException;

	/**
	 * Extracts poll option with the given ID
	 * 
	 * @param id
	 *            poll option ID
	 * @return poll option with the given ID
	 * @throws DAOException
	 *             in case of an error while working with database
	 */
	public PollOption getPollOptionWithId(long id) throws DAOException;

	/**
	 * Increments number of votes in the poll option with the given ID
	 * 
	 * @param id
	 *            poll option which votes will be incremented
	 * @throws DAOException
	 *             in case of an error while working with database
	 */
	public void incrementPollOptionVotes(long id) throws DAOException;

	/**
	 * Constructs a list containing poll options of the poll with the given ID
	 * 
	 * @param pollID
	 *            poll ID
	 * @return list containing poll options of the poll with the given ID
	 * @throws DAOException
	 *             in case of an error while working with the database
	 */
	public List<PollOption> getPollOptionsFromPoll(long pollID) throws DAOException;

	/**
	 * Gets a poll with the given ID from the database
	 * 
	 * @param id
	 *            id of the poll that will be extracted
	 * @return extracted poll
	 * @throws DAOException
	 *             in case of an error while working with database
	 */
	public Poll getPollWithId(long id) throws DAOException;
}