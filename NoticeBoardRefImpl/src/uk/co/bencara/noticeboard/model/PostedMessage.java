/**
 * 
 */
package uk.co.bencara.noticeboard.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * An immutable POJO representing a message posted on a notice board.
 * 
 * 
 * @author Les Eckersley
 * 
 */
public class PostedMessage implements Comparable<PostedMessage> {

	private final User author;
	private final String text;
	private final long postTime;
	private final int postOrder;

	/**
	 * Public constructor requiring all the mandatory attributes.
	 * 
	 * @param author
	 *            the author of the message may not be null
	 * @param text
	 *            the message text may not be null or an empty string
	 * @param postTime
	 *            the time that the post request was received
	 * @param postOrder
	 *            an integer used to determine the ordering of messages for an
	 *            author when multiple messages are received in the same millisecond
	 */
	public PostedMessage(User author, String text, long postTime, int postOrder) {
		super();
		// Check arguments
		if (author == null || StringUtils.trimToNull(text) == null) {
			throw new IllegalArgumentException(
					"Cannot pass null author or empty or null message");
		}

		this.author = author;
		this.text = text;
		this.postTime = postTime;
		this.postOrder = postOrder;
	}

	/**
	 * @return the author
	 */
	public User getAuthor() {
		return author;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @return the postTime
	 */
	public long getPostTime() {
		return postTime;
	}
	
	/**
	 * @return the postTime
	 */
	public long getPostOrder() {
		return postOrder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof PostedMessage)) {
			return false;
		}
		if (o == this) {
			return true;
		}
		PostedMessage toBeCompared = (PostedMessage) o;
		return new EqualsBuilder().append(author, toBeCompared.author)
				.append(postTime, toBeCompared.postTime)
				.append(text, toBeCompared.text).append(postOrder, toBeCompared.postOrder).isEquals();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(author).append(postTime)
				.append(text).append(postOrder).toHashCode();
	}

	/**
	 * Natural comparison based on comparing (in order) posted time (desc), author name, posted order (desc)
	 * and then message text.
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(PostedMessage messageToCompare) {
		long comparisonValue = messageToCompare.postTime - this.postTime;
		
		if (comparisonValue == 0) {
			comparisonValue = this.author.getName().compareTo(
					messageToCompare.author.getName());
		}
		if (comparisonValue == 0) {
			comparisonValue = messageToCompare.postOrder - this.postOrder;
		}
		
		if (comparisonValue == 0) {
			comparisonValue = this.text.compareTo(messageToCompare.text);
		}

		// If the value is positive return 1
		if (comparisonValue > 0) {
			return 1;
		}

		// If the value is negative return -1
		if (comparisonValue < 0) {
			return -1;
		}

		// The object must compare to be equal
		return 0;
	}

}
