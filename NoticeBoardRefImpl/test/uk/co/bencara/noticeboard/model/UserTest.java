/**
 * 
 */
package uk.co.bencara.noticeboard.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

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
		// Check that a valid user name is accepted and the name attribute populated
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

		// Check that hashcode of Users with the same name (i.e. will be considered equal) are equal
		userForComparison = new User("User1");
		assertTrue("Users with the same name have same hashcode",
				userUnderTest.hashCode() == userForComparison.hashCode());


	}

}
