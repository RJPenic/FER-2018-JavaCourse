package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Provider that stores entity manager factory which can later be used for lazy
 * connections
 * 
 * @author Rafael Josip Penić
 *
 */
public class JPAEMFProvider {

	/**
	 * Manager factory which can create an entity manager
	 */
	public static EntityManagerFactory emf;

	/**
	 * Getter for entity manager factory
	 * 
	 * @return factory
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}

	/**
	 * Setter for entity manager factory
	 * 
	 * @param emf
	 *            new factory
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}