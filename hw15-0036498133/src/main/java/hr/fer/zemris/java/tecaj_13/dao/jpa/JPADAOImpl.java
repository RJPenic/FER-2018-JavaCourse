package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.List;

import hr.fer.zemris.java.cryptoUtil.CryptoUtil;
import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Implementation of DAO interface
 * 
 * @author Rafael Josip Penić
 *
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		return JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
	}

	@Override
	public void insertNewUser(BlogUser user) throws DAOException {
		JPAEMProvider.getEntityManager().persist(user);
	}

	@Override
	public void updateEntry(BlogEntry entry) throws DAOException {
		JPAEMProvider.getEntityManager().persist(entry);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BlogUser> getBlogUsers() throws DAOException {
		return (List<BlogUser>) JPAEMProvider.getEntityManager().createNamedQuery("BlogUser.query1").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BlogUser> getBlogUser(String nick, String password) throws DAOException {
		return (List<BlogUser>) JPAEMProvider.getEntityManager().createNamedQuery("BlogUser.query2")
				.setParameter("nick", nick).setParameter("passwordHash", CryptoUtil.hashPassword(password))
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BlogUser> getBlogUserWithNick(String nick) throws DAOException {
		return (List<BlogUser>) JPAEMProvider.getEntityManager().createNamedQuery("BlogUser.query3")
				.setParameter("nick", nick).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BlogEntry> getBlogEntriesForUser(BlogUser user) throws DAOException {
		return (List<BlogEntry>) JPAEMProvider.getEntityManager().createNamedQuery("BlogEntry.upit2")
				.setParameter("id", user.getId()).getResultList();
	}

	@Override
	public void insertNewComment(BlogComment comment) throws DAOException {
		JPAEMProvider.getEntityManager().persist(comment);
	}

}