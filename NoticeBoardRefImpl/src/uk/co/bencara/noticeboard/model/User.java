/**
 * 
 */
package uk.co.bencara.noticeboard.model;

/**
 * The POJO representing a user of the notice board
 * 
 * @author Les Eckersley
 *
 */
public class User {
	
	/**
	 * The unique name of the user.
	 */
	private final String name;

	/**
	 * Standard constructor requiring the immutable name to be provided
	 * @param userName
	 */
	public User(String name) {
		super();
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	

}
