/**
 * 
 */
package uk.co.bencara.noticeboard.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import uk.co.bencara.noticeboard.model.PostedMessage;
import uk.co.bencara.noticeboard.model.User;

/**
 * A class to test the User POJO class.
 * 
 * © Bencara Systems Ltd
 * 
 * @author Les Eckersley
 * 
 */
public class PostedMessageTest {
	private PostedMessage messageUnderTest;

	private PostedMessage messageForComparison;

	private User user1 = new User("User1");
	private User user2 = new User("User2");

	private String firstOrderMessage = "a";
	private String secondOrderMessage = "b";

	private long baseInsertTime = System.currentTimeMillis();
	private long laterInsertTime = baseInsertTime + 1;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		messageUnderTest = null;
		messageForComparison = null;
	}

	/**
	 * Test the constructor where a valid name is passed. This also tests the
	 * get methods for the attributes
	 */
	@Test
	public void testConstructorValidAttributes() {
		// Check that a valid user name is accepted and the attributes populated

		messageUnderTest = new PostedMessage(user1, "Hello World",
				baseInsertTime, 1);
		assertEquals(
				"The author should have been populated with the passed author and the author should be returned by the get method",
				user1, messageUnderTest.getAuthor());
		assertEquals(
				"The message should have been populated with the passed message and the message should be returned by the get method",
				"Hello World", messageUnderTest.getText());

		assertEquals(
				"The posting time should have been populated with the passed posting time and the posting time should be returned by the get method",
				baseInsertTime, messageUnderTest.getPostTime());
		
		assertEquals(
				"The posting order should have been populated with the passed posting order and the posting order should be returned by the get method",
				1, messageUnderTest.getPostOrder());
	}

	/**
	 * Test the constructor where an invalid author is passed.
	 */
	@Test
	public void testConstructorInvalidAuthor() {

		try {
			messageUnderTest = new PostedMessage(null, "Hello World",
					baseInsertTime, 1);
			fail("Null User should have been rejected");
		} catch (IllegalArgumentException iae) {
			assertTrue(
					"Error message should be meaningful",
					iae.getMessage().indexOf(
							"Cannot pass null author or empty or null message") > -1);
		}

	}

	/**
	 * Test the constructor where an invalid message is passed.
	 */
	@Test
	public void testConstructorInvalidEmptyMessage() {
		// Check empty message rejected
		try {
			messageUnderTest = new PostedMessage(user1, "  	", baseInsertTime, 1);
			fail("Empty message should have been rejected");
		} catch (IllegalArgumentException iae) {
			assertTrue(
					"Error message should be meaningful",
					iae.getMessage().indexOf(
							"Cannot pass null author or empty or null message") > -1);
		}

	}

	/**
	 * Test the constructor where an invalid message is passed.
	 */
	@Test
	public void testConstructorInvalidNullMessage() {
		// Check empty message rejected
		try {
			messageUnderTest = new PostedMessage(user1, null, baseInsertTime, 1);
			fail("Null message should have been rejected");
		} catch (IllegalArgumentException iae) {
			assertTrue(
					"Error message should be meaningful",
					iae.getMessage().indexOf(
							"Cannot pass null author or empty or null message") > -1);
		}

	}

	/**
	 * Test the getAuthor method.
	 */
	@Test
	public void testGetAuthor() {
		// This is a placeholder the get method is tested in the valid
		// constructor test
	}

	/**
	 * Test the getText method.
	 */
	@Test
	public void testGetText() {
		// This is a placeholder the get method is tested in the valid
		// constructor test
	}

	/**
	 * Test the getPostTime method.
	 */
	@Test
	public void testGetPostTime() {
		// This is a placeholder the get method is tested in the valid
		// constructor test
	}

	/**
	 * Test the equals method.
	 */
	@Test
	public void testEquals() {
		// Check that all attributes the same returns equal
		messageUnderTest = new PostedMessage(user1, secondOrderMessage,
				laterInsertTime, 1);
		messageForComparison = new PostedMessage(user1, secondOrderMessage,
				laterInsertTime, 1);
		assertTrue(
				"Messages with identical attributes should be considered equal",
				messageUnderTest.equals(messageForComparison));
		assertTrue(
				"Messages with identical attributes should be considered equal",
				messageForComparison.equals(messageUnderTest));

		// Check that different users is deemed not equal
		messageUnderTest = new PostedMessage(user1, secondOrderMessage,
				laterInsertTime, 1);
		messageForComparison = new PostedMessage(user2, secondOrderMessage,
				laterInsertTime, 1);
		assertFalse("Messages with different user should not be equal",
				messageUnderTest.equals(messageForComparison));
		assertFalse("Messages with different user should not be equal",
				messageForComparison.equals(messageUnderTest));

		// Check that different posted time is deemed not equal
		messageUnderTest = new PostedMessage(user1, secondOrderMessage,
				laterInsertTime, 1);
		messageForComparison = new PostedMessage(user1, secondOrderMessage,
				baseInsertTime, 1);
		assertFalse("Messages with different post time should not be equal",
				messageUnderTest.equals(messageForComparison));
		assertFalse("Messages with different post time should not be equal",
				messageForComparison.equals(messageUnderTest));

		// Check that different text is deemed not equal
		messageUnderTest = new PostedMessage(user1, secondOrderMessage,
				laterInsertTime, 1);
		messageForComparison = new PostedMessage(user1, firstOrderMessage,
				laterInsertTime, 1);
		assertFalse("Messages with different text should not be equal",
				messageUnderTest.equals(messageForComparison));
		assertFalse("Messages with different text should not be equal",
				messageForComparison.equals(messageUnderTest));
		
		// Check that different post order is deemed not equal
		messageUnderTest = new PostedMessage(user1, firstOrderMessage,
				laterInsertTime, 1);
		messageForComparison = new PostedMessage(user1, firstOrderMessage,
				laterInsertTime, 2);
		assertFalse("Messages with different post order should not be equal",
				messageUnderTest.equals(messageForComparison));
		assertFalse("Messages with different post order should not be equal",
				messageForComparison.equals(messageUnderTest));

		// Check that null for comparison returns false
		assertFalse("Null object for comparision should return false",
				messageUnderTest.equals(null));

		// Check that a none User object returns false
		assertFalse("Comparison with none User object should return false",
				messageUnderTest.equals(new String("User1")));

	}

	/**
	 * Test the hashcode method.
	 */
	@Test
	public void testHashcode() {

		// Check that all attributes the same (i.e. that will be equal) returns
		// equal hash code
		messageUnderTest = new PostedMessage(user1, secondOrderMessage,
				laterInsertTime, 1);
		messageForComparison = new PostedMessage(user1, secondOrderMessage,
				laterInsertTime, 1);
		assertTrue(
				"Messages with identical attributes should be return the same hashcode",
				messageUnderTest.hashCode() == messageForComparison.hashCode());

	}

	/**
	 * Test the compareTo method
	 */
	@Test
	public void testCompareTo() {

		// Check that the author, message text and post order ordering are
		// overridden by the post time
		messageUnderTest = new PostedMessage(user2, secondOrderMessage,
				baseInsertTime, 2);
		messageForComparison = new PostedMessage(user1, firstOrderMessage,
				laterInsertTime, 1);
		assertTrue("Reverse post time should be dominant in natural ordering",
				messageUnderTest.compareTo(messageForComparison) > 0);
		assertTrue("Reverse post time should be dominant in natural ordering",
				messageForComparison.compareTo(messageUnderTest) < 0);

		// Check that with same post time author is dominant
		messageUnderTest = new PostedMessage(user1, secondOrderMessage,
				baseInsertTime, 2);
		messageForComparison = new PostedMessage(user2, firstOrderMessage,
				baseInsertTime, 1);
		assertTrue("Author should be dominant where post time is equal",
				messageUnderTest.compareTo(messageForComparison) < 0);
		assertTrue("Author should be dominant where post time is equal",
				messageForComparison.compareTo(messageUnderTest) > 0);

		// Check that with same post time and author the post order determines ordering
		messageUnderTest = new PostedMessage(user2, secondOrderMessage,
				baseInsertTime, 1);
		messageForComparison = new PostedMessage(user2, firstOrderMessage,
				baseInsertTime, 2);
		assertTrue("Reverse post order should be dominant where post time and author are equal",
				messageUnderTest.compareTo(messageForComparison) > 0);
		assertTrue("Reverse post order should be dominant where post time and author are equal",
				messageForComparison.compareTo(messageUnderTest) < 0);

		// Check that with same post time, author and post order the message determines ordering
		messageUnderTest = new PostedMessage(user1, firstOrderMessage,
				baseInsertTime, 1);
		messageForComparison = new PostedMessage(user1, secondOrderMessage,
				baseInsertTime, 1);
		assertTrue(
				"Message text should be user to determine order where post time, author and post order are equal",
				messageUnderTest.compareTo(messageForComparison) < 0);
		assertTrue(
				"Message text should be user to determine order where post time, author and post order are equal",
				messageForComparison.compareTo(messageUnderTest) > 0);


	}

}
