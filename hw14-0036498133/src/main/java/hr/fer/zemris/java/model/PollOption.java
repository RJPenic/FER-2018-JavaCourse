package hr.fer.zemris.java.model;

/**
 * Class representing a simple poll option for which user can vote for
 * 
 * @author Rafael Josip Penić
 *
 */
public class PollOption {

	/**
	 * Poll option ID
	 */
	private long id;

	/**
	 * Poll option title
	 */
	private String optionTitle;

	/**
	 * Poll option example link
	 */
	private String optionLink;

	/**
	 * Poll ID
	 */
	private long pollID;

	/**
	 * Poll option votes count
	 */
	private Long votesCount;

	/**
	 * Getter for poll option ID
	 * 
	 * @return poll option ID
	 */
	public long getId() {
		return id;
	}

	/**
	 * Setter for poll option ID
	 * 
	 * @param id
	 *            poll option ID
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Getter for poll option title
	 * 
	 * @return poll option title
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * Setter for poll option title
	 * 
	 * @param optionTitle
	 *            poll option title
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	/**
	 * Getter for poll option example link
	 * 
	 * @return poll option link
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * Setter for poll option example link
	 * 
	 * @param optionLink
	 *            poll option example link
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}

	/**
	 * Getter for parent poll ID
	 * 
	 * @return poll ID
	 */
	public long getPollID() {
		return pollID;
	}

	/**
	 * Setter for parent poll ID
	 * 
	 * @param pollID
	 *            poll ID
	 */
	public void setPollID(long pollID) {
		this.pollID = pollID;
	}

	/**
	 * Getter for votes count
	 * 
	 * @return votes count
	 */
	public Long getVotesCount() {
		return votesCount;
	}

	/**
	 * Setter for poll option vote count
	 * 
	 * @param votesCount
	 *            number of votes
	 */
	public void setVotesCount(Long votesCount) {
		this.votesCount = votesCount;
	}

}
