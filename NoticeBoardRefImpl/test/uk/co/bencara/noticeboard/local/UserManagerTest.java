/**
 * 
 */
package uk.co.bencara.noticeboard.local;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import uk.co.bencara.noticeboard.local.UserManager;
import uk.co.bencara.noticeboard.model.User;


/**
 * A class to independently test the UserManager class.
 * 
 * @author Les Eckersley
 * 
 */
public class UserManagerTest {

	private UserManager managerUnderTest;
	private User retrievedUser;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		managerUnderTest = new UserManager();
		retrievedUser = null;
	}

	/**
	 * Test the retrieveUser Method where the user doesn't already exist and
	 * should not be created
	 */
	@Test
	public void testRetrieveUserUserDoesntExistDontCreate() {
		retrievedUser = managerUnderTest.retrieveUser("Unknown", false);
		assertNull(
				"Unknown user should not be retrieved if create have not been specified",
				retrievedUser);
	}

	/**
	 * Test the retrieveUser Method where the user doesn't already exist and
	 * should be created
	 */
	@Test
	public void testRetrieveUserUserDoesntExistCreate() {
		retrievedUser = managerUnderTest.retrieveUser("Unknown", true);
		assertNotNull("Unknown user should be created if specified",
				retrievedUser);
		assertEquals("Created user should be populated with correct name",
				"Unknown", retrievedUser.getName());
	}

	/**
	 * Test the retrieveUser Method where the user does already exist
	 */
	@Test
	public void testRetrieveUserUserDoesExist() {

		// Set up the user manager with a number of known users
		managerUnderTest.retrieveUser("known1", true);
		User known2User = managerUnderTest.retrieveUser("known2", true);
		managerUnderTest.retrieveUser("known3", true);
		managerUnderTest.retrieveUser("known4", true);

		// Check that the correct existing user is retrieved
		retrievedUser = managerUnderTest.retrieveUser("known2", true);
		assertNotNull("Known user should be retrieved specified", retrievedUser);
		assertTrue(
				"Retireved user should be existing user and not a new user with the same name",
				known2User == retrievedUser);

	}

}
