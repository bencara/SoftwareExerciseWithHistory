/**
 * 
 */
package uk.co.bencara.noticeboard.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * The POJO representing a user of the notice board with an immutable business
 * key of name. It is assumed that Users will be managed so that only one user
 * with a particular name can exist in a system.
 * 
 * @author Les Eckersley
 * 
 */
public class User {

	/**
	 * The immutable unique name of the user.
	 */
	private final String name;

	/**
	 * The set of authors that this users follows
	 */
	private Set<User> followedAuthorsSet;

	/**
	 * Standard constructor requiring the immutable name to be provided
	 * 
	 * @param userName
	 *            , must be a none trivial string with at least 1 character that
	 *            is not whitespace
	 */
	public User(String name) {
		super();
		if (StringUtils.trimToNull(name) == null) {
			throw new IllegalArgumentException(
					"The name of the user must be a none trivial string");
		}

		// TODO the user name actually needs constraining to not contain
		// whitespace

		this.name = name;
	}

	public String getName() {
		return name;
	}

	/**
	 * A method to a user to the list of those whose postings are followed by
	 * this user.
	 * 
	 * @param author
	 *            the author to be added to those followed by this user, a null
	 *            value will be ignored
	 */
	public void addFollowedAuthor(User author) {
		if (author == null) {
			// TODO should this be allowed or should this be an illegal argument
			return;
		}
		// TODO ignore requests to follow self
		
		getFollowedAuthorsSet().add(author);
	}

	/**
	 * Get the set of authors currently followed by this user. An unmodifiable
	 * set is returned.
	 * 
	 * @return the Set of authors followed by this user, may not be null but may
	 *         be empty
	 */
	public Set<User> getFollowedAuthors() {
		return Collections.unmodifiableSet(getFollowedAuthorsSet());
	}

	/**
	 * Equals implementation based on the name business key
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof User)) {
			return false;
		}
		if (o == this) {
			return true;
		}
		User toBeCompared = (User) o;
		return new EqualsBuilder().append(name, toBeCompared.name).isEquals();

	}

	/**
	 * Hashcode implementation based on the name business key
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(name).toHashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}

	/**
	 * Lazy getter for the Set of authors that this user follows.
	 * 
	 * @return the existing set of followed atuhor or an empty set if no authors
	 *         are currently being followed
	 */
	private Set<User> getFollowedAuthorsSet() {
		if (followedAuthorsSet == null) {
			followedAuthorsSet = new HashSet<User>();
		}
		return followedAuthorsSet;
	}

}
