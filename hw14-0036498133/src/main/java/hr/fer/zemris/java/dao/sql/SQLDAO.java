package hr.fer.zemris.java.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.dao.DAO;
import hr.fer.zemris.java.dao.DAOException;
import hr.fer.zemris.java.model.Poll;
import hr.fer.zemris.java.model.PollOption;

/**
 * Ovo je implementacija podsustava DAO uporabom tehnologije SQL. Ova
 * konkretna implementacija očekuje da joj veza stoji na raspolaganju
 * preko {@link SQLConnectionProvider} razreda, što znači da bi netko
 * prije no što izvođenje dođe do ove točke to trebao tamo postaviti.
 * U web-aplikacijama tipično rješenje je konfigurirati jedan filter 
 * koji će presresti pozive servleta i prije toga ovdje ubaciti jednu
 * vezu iz connection-poola, a po zavrsetku obrade je maknuti.
 *  
 * @author marcupic
 */
public class SQLDAO implements DAO {

	@Override
	public List<Poll> getPollList() throws DAOException {
		List<Poll> polls = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("SELECT id, title, message FROM Polls");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						Poll poll = new Poll();
						poll.setId(rs.getLong(1));
						poll.setTitle(rs.getString(2));
						poll.setMessage(rs.getString(3));
						polls.add(poll);
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(SQLException ex) {
			throw new DAOException("Error while getting polls list.", ex);
		}
		
		return polls;
	}

	@Override
	public PollOption getPollOptionWithId(long id) throws DAOException {
		PollOption pollOption = null;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("SELECT id, optionTitle, optionLink, pollID, votesCount FROM PollOptions WHERE id=?");
			pst.setLong(1, Long.valueOf(id));
			try {
				ResultSet rs = pst.executeQuery();
				try {
					if(rs!=null && rs.next()) {
						pollOption = new PollOption();
						pollOption.setId(rs.getLong(1));
						pollOption.setOptionTitle(rs.getString(2));
						pollOption.setOptionLink(rs.getString(3));
						pollOption.setPollID(rs.getLong(4));
						pollOption.setVotesCount(rs.getLong(5));
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(SQLException ex) {
			throw new DAOException("Error while getting poll option with id " + id, ex);
		}
		
		return pollOption;
	}

	@Override
	public void incrementPollOptionVotes(long id) throws DAOException {
		PollOption pollOption = getPollOptionWithId(id);
		long votes = pollOption.getVotesCount();
		
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("UPDATE PollOptions SET votesCount=? WHERE id=?");
			pst.setLong(1, votes+1);
			pst.setLong(2, id);
			
			try {
				pst.executeUpdate();
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
			
		} catch (SQLException ex) {
			throw new DAOException("Error while incrementing votes", ex);

		}
	}

	@Override
	public List<PollOption> getPollOptionsFromPoll(long pollID) throws DAOException {
		List<PollOption> pollOptions = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("SELECT id, optionTitle, optionLink, pollID, votesCount FROM PollOptions WHERE pollId=?");
			pst.setLong(1, pollID);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						PollOption pollOption = new PollOption();
						pollOption.setId(rs.getLong(1));
						pollOption.setOptionTitle(rs.getString(2));
						pollOption.setOptionLink(rs.getString(3));
						pollOption.setPollID(rs.getLong(4));
						pollOption.setVotesCount(rs.getLong(5));
						pollOptions.add(pollOption);
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(SQLException ex) {
			throw new DAOException("Error while getting polls options list.", ex);
		}
		
		return pollOptions;
	}

	@Override
	public Poll getPollWithId(long id) throws DAOException {
		Poll poll = null;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("SELECT id, title, message FROM Polls WHERE id=?");
			pst.setLong(1, Long.valueOf(id));
			try {
				ResultSet rs = pst.executeQuery();
				try {
					if(rs!=null && rs.next()) {
						poll = new Poll();
						poll.setId(rs.getLong(1));
						poll.setTitle(rs.getString(2));
						poll.setMessage(rs.getString(3));
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(SQLException ex) {
			throw new DAOException("Error while getting poll with id " + id, ex);
		}
		
		return poll;
	}

}