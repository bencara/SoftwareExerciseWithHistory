/**
 * 
 */
package uk.co.bencara.noticeboard.local;

import java.util.HashMap;
import java.util.Map;

import uk.co.bencara.noticeboard.model.User;


/**
 * A class to manage the retrieval and creation of users.
 * Typically this would be a singleton within a particular instance of the application
 * 
 * @author Les Eckersley
 * 
 */
public class UserManager {
	
	/**
	 * A map of the known users keyed by the unique user name
	 */
	private Map<String, User> usersByUserName = new HashMap<String, User>();
	

	/**
	 * @param userName the unique name of the user to be retrieved.
	 * @param create
	 *            an indicator to determine if a new user should be created if
	 *            one does not already exist
	 * @return the user associated with the user name. If the create parameter
	 *         is set to true this will return a new user with the passed user
	 *         name if one does not already exist
	 */
	public User retrieveUser(String userName, boolean createUser) {

		User requestedUser = usersByUserName.get(userName);
		if (requestedUser == null && createUser) {
			
			// TODO deal with an invalid user name being passed
			requestedUser = new User(userName);
			usersByUserName.put(userName, requestedUser);
		}
		
		return requestedUser;
		
	}

}
