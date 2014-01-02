/**
 * 
 */
package uk.co.bencara.noticeboard.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * The POJO representing a user of the notice board with an immutable business
 * key of name.
 * It is assumed that Users will be managed so that only one user with a particular name can exist in a system.
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
	 * Standard constructor requiring the immutable name to be provided
	 * 
	 * @param userName, must be a none trivial string with at least 1 character that is not whitespace
	 */
	public User(String name) {
		super();
		if (StringUtils.trimToNull(name) == null) {
			throw new IllegalArgumentException("The name of the user must be a none trivial string");
		}
		
		// TODO the user name actually needs constraining to not contain whitespace
		
		this.name = name;
	}

	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
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

	/*
	 * (non-Javadoc)
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

}
