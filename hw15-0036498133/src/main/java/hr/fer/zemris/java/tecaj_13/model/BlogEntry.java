package hr.fer.zemris.java.tecaj_13.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQueries({
		@NamedQuery(name = "BlogEntry.upit1", query = "select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when"),
		@NamedQuery(name = "BlogEntry.upit2", query = "select b from BlogEntry as b where b.creator.id=:id") })
@Entity
@Table(name = "blog_entries")
@Cacheable(true)
/**
 * Class representing a simple blog entry
 * 
 * @author Rafael Josip Penić
 *
 */
public class BlogEntry {

	/**
	 * Entry ID
	 */
	private Long id;

	/**
	 * Entry comments
	 */
	private List<BlogComment> comments;

	/**
	 * Timestamp when the entry was created
	 */
	private Date createdAt;

	/**
	 * Timestamp when the entry was last modified
	 */
	private Date lastModifiedAt;

	/**
	 * Entry title
	 */
	private String title;

	/**
	 * Entry text
	 */
	private String text;

	/**
	 * User that created this entry
	 */
	private BlogUser creator;

	@Id
	@GeneratedValue
	/**
	 * Getter for ID
	 * 
	 * @return ID
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Setter for ID
	 * 
	 * @param id
	 *            new entry ID
	 */
	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(nullable = false)
	/**
	 * Getter for entry creator
	 * 
	 * @return entry creator
	 */
	public BlogUser getCreator() {
		return creator;
	}

	/**
	 * Setter for entry creator
	 * 
	 * @param creator
	 *            new entry creator
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
	}

	@OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("postedOn")
	/**
	 * Getter for comments list
	 * 
	 * @return comments list
	 */
	public List<BlogComment> getComments() {
		return comments;
	}

	/**
	 * Setter for comments list
	 * 
	 * @param comments
	 *            new comments list
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	/**
	 * Getter for createdAt timestamp
	 * 
	 * @return timestamp when the entry was created
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Setter for createdAt property
	 * 
	 * @param createdAt
	 *            new value of createdAt
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	/**
	 * Getter for lastModifiedAt
	 * 
	 * @return lastModifiedAt
	 */
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Setter for lastModifiedAt
	 * 
	 * @param lastModifiedAt
	 *            new lastModifiedAt
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	@Column(length = 200, nullable = false)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(length = 4096, nullable = false)
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}