package hr.fer.zemris.java.tecaj_13.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "blog_comments")
/**
 * Class representing blog comments and it provides info about comments origin
 * and its content
 * 
 * @author Rafael Josip Penić
 *
 */
public class BlogComment {

	/**
	 * Comment id
	 */
	private Long id;

	/**
	 * Entry on which this comment is left
	 */
	private BlogEntry blogEntry;

	/**
	 * EMail of the user who left his comment
	 */
	private String usersEMail;

	/**
	 * Comment message
	 */
	private String message;

	/**
	 * Date on which the comment is posted
	 */
	private Date postedOn;

	@Id
	@GeneratedValue
	/**
	 * Getter for comment ID
	 * 
	 * @return comment ID
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Setter for comment ID
	 * 
	 * @param id
	 *            new comments ID
	 */
	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(nullable = false)
	/**
	 * Getter for comment blog entry
	 * 
	 * @return comments blog entry
	 */
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}

	/**
	 * Setter for comments blog entry
	 * 
	 * @param blogEntry
	 *            new comments blog entry
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	@Column(length = 100, nullable = false)
	/**
	 * Getter for comment author email
	 * 
	 * @return comment authors email
	 */
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Setter for email
	 * 
	 * @param usersEMail
	 *            new users email
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	@Column(length = 4096, nullable = false)
	/**
	 * Getter for comment message
	 * 
	 * @return comments message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter for comment message
	 * 
	 * @param message
	 *            new comment message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	/**
	 * Getter for comments post time
	 * 
	 * @return comments post time
	 */
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Setter for post time of the comment
	 * 
	 * @param postedOn
	 *            new post time
	 * 
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
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
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}