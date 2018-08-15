package hr.fer.zemris.java.tecaj_13.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({ @NamedQuery(name = "BlogUser.query1", query = "select b from BlogUser as b"),
		@NamedQuery(name = "BlogUser.query2", query = "select b from BlogUser as b where b.nick=:nick and b.passwordHash=:passwordHash"),
		@NamedQuery(name = "BlogUser.query3", query = "select b from BlogUser as b where b.nick=:nick") })
@Entity
@Table(name = "blog_users")
/**
 * Class representing basic blog user containing basic information about him/her
 * 
 * @author Rafael Josip Penić
 *
 */
public class BlogUser {

	/**
	 * User ID
	 */
	private Long id;

	/**
	 * User first name
	 */
	private String firstName;

	/**
	 * User last name
	 */
	private String lastName;

	/**
	 * User nickname
	 */
	private String nick;

	/**
	 * User email
	 */
	private String email;

	/**
	 * Hash of users password
	 */
	private String passwordHash;

	@Id
	@GeneratedValue
	/**
	 * Getter for user ID
	 * 
	 * @return user ID
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Setter for users ID
	 * 
	 * @param id
	 *            new user ID
	 */
	public void setId(Long id) {
		this.id = id;
	}

	@Column(length = 50, nullable = false)
	/**
	 * Getter for user first name
	 * 
	 * @return user first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter for users first name
	 * 
	 * @param firstName
	 *            new user first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(length = 50, nullable = false)
	/**
	 * Getter for user last name
	 * 
	 * @return user last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter for users last name
	 * 
	 * @param lastName
	 *            new user last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(length = 50, nullable = false, unique = true)
	/**
	 * Getter for user nickname
	 * 
	 * @return user nickname
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Setter for users nickname
	 * 
	 * @param nickname
	 *            new user nickname
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	@Column(length = 70, nullable = false)
	/**
	 * Getter for user email
	 * 
	 * @return user email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter for users email
	 * 
	 * @param email
	 *            new user email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	@Column(length = 40, nullable = false)
	/**
	 * Getter for user password hash
	 * 
	 * @return user password hash
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Setter for users passwordHash
	 * 
	 * @param passwordHash
	 *            new user passwordHash
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogUser other = (BlogUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
