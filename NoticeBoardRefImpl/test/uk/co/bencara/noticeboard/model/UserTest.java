/**
 * 
 */
package uk.co.bencara.noticeboard.model;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import uk.co.bencara.noticeboard.model.User;

/**
 * A class to test the User POJO class.
 * 
 * @author Les Eckersley
 * 
 */
public class UserTest {
	private User userUnderTest;

	private User userForComparison;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		userUnderTest = null;
		userForComparison = null;
	}

	/**
	 * Test the constructor where a valid name is passed. This also tests the
	 * get method for the name.
	 */
	@Test
	public void testConstructorValidName() {
		// Check that a valid user name is accepted and the name attribute
		// populated
		userUnderTest = new User("ValidName");
		assertEquals(
				"The new user should have been populated with the pass name and the name should be returned by the get method",
				"ValidName", userUnderTest.getName());
	}

	/**
	 * Test the constructor where an invalid name is passed.
	 */
	@Test
	public void testConstructorInvalidName() {

		try {
			userUnderTest = new User("  	");
			fail("Trivial User name should have been rejected");
		} catch (IllegalArgumentException iae) {
			assertTrue(
					"Error message should be meaningful",
					iae.getMessage()
							.indexOf(
									"The name of the user must be a none trivial string") > -1);
		}

	}

	/**
	 * Test the getName method.
	 */
	@Test
	public void testAddFollowedAuthor() {
		userUnderTest = new User("ValidName");
		User userFollowed1 = new User("Followed1");
		User userFollowed2 = new User("Followed2");

		// Check that a null set null for a new user
		assertNotNull(
				"Null is not a valid return for the getFollowedAuthors method",
				userUnderTest.getFollowedAuthors());

		// Check that passing null is ignored
		try {
			userUnderTest.addFollowedAuthor(null);
		} catch (Exception e) {
			fail("Adding a null user should have been ignored");
		}
		assertEquals(
				"Null should not have been added to the list of authors followed",
				0, userUnderTest.getFollowedAuthors().size());

		// Check that users can be added and are returned
		userUnderTest.addFollowedAuthor(userFollowed1);
		userUnderTest.addFollowedAuthor(userFollowed2);
		Set<User> returnedAuthors = userUnderTest.getFollowedAuthors();

		assertEquals(
				"Both added users should be returned in the followed authors set",
				2, returnedAuthors.size());
		assertTrue(
				"Added userFollowed1 should be returned in the followed authors set",
				returnedAuthors.contains(userFollowed1));
		assertTrue(
				"Added userFollowed2 should be returned in the followed authors set",
				returnedAuthors.contains(userFollowed2));
		
		// Check that the returned set cannot be modified
		boolean failed = false;
		try {
			returnedAuthors.add(userUnderTest);
		} catch (Exception e) {
			failed = true;
		}
		assertTrue("The returned set should not be modifiable", failed);
		


	}

	/**
	 * Test the getName method.
	 */
	@Test
	public void testGetName() {
		// This is a placeholder the get method is tested in the valid
		// constructor test
	}

	/**
	 * Test the equals method.
	 */
	@Test
	public void testEquals() {
		userUnderTest = new User("User1");

		// Check that comparison with a User with the same name returns true
		userForComparison = new User("User1");
		assertTrue("Users with the same name are considered equal",
				userUnderTest.equals(userForComparison));

		// Check that comparison with a User with the same name returns true
		userForComparison = new User("User2");
		assertFalse("Users with the different name are considered not equal",
				userUnderTest.equals(userForComparison));

		// Check that null for comparison returns false
		assertFalse("Null object for comparision should return false",
				userUnderTest.equals(null));

		// Check that a none User object returns false
		assertFalse("Comparison with none User object should return false",
				userUnderTest.equals(new String("User1")));
		
	}

	/**
	 * Test the hashcode method.
	 */
	@Test
	public void testHashcode() {

		userUnderTest = new User("User1");

		// Check that hashcode of Users with the same name (i.e. will be
		// considered equal) are equal
		userForComparison = new User("User1");
		assertTrue("Users with the same name have same hashcode",
				userUnderTest.hashCode() == userForComparison.hashCode());

	}
	
	/**
	 * Test the hashcode method.
	 */
	@Test
	public void testBlah() {
		char copywrite = 169;

		System.out.println(copywrite);

	}
	
	

}
