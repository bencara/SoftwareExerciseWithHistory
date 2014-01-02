/**
 * 
 */
package uk.co.bencara.noticeboard.local;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import uk.co.bencara.noticeboard.local.NoticeBoardLocalImpl;

/**
 * A class to test the NoticeBoardLocalImpl class for the expected business
 * scenarios.
 * 
 * © Bencara Systems Ltd
 * 
 * @author Les Eckersley
 * 
 */
public class NoticeBoardLocalImplTest {

	public static final String JOHN_MESSAGE_1 = "John -> Hello World";
	public static final String JOHN_MESSAGE_2 = "John -> My next message";
	public static final String JOHN_MESSAGE_3 = "John -> A further message";
	public static final String JOHN_MESSAGE_4 = "John -> Last Message";

	public static final String EXPECTED_JOHN_RESPONSE_1_START = "John - Hello World (";
	public static final String EXPECTED_JOHN_RESPONSE_2_START = "John - My next message (";
	public static final String EXPECTED_JOHN_RESPONSE_3_START = "John - A further message (";
	public static final String EXPECTED_JOHN_RESPONSE_4_START = "John - Last Message (";

	public static final String JACK_MESSAGE_1 = "Jack -> Message 1";
	public static final String JACK_MESSAGE_2 = "Jack -> Message 2";
	public static final String JACK_MESSAGE_3 = "Jack -> Message 3";
	public static final String JACK_MESSAGE_4 = "Jack -> Message 4";

	public static final String EXPECTED_JACK_RESPONSE_1_START = "Jack - Message 1 (";
	public static final String EXPECTED_JACK_RESPONSE_2_START = "Jack - Message 2 (";
	public static final String EXPECTED_JACK_RESPONSE_3_START = "Jack - Message 3 (";
	public static final String EXPECTED_JACK_RESPONSE_4_START = "Jack - Message 4 (";

	public static final String MEL_MESSAGE_1 = "Mel -> Message 1";
	public static final String MEL_MESSAGE_2 = "Mel -> Message 1";
	public static final String MEL_MESSAGE_3 = "Mel -> Message 1";
	public static final String MEL_MESSAGE_4 = "Mel -> Message 1";

	private NoticeBoardLocalImpl noticeBoardUnderTest;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		noticeBoardUnderTest = new NoticeBoardLocalImpl();
	}

	/**
	 * Test method for
	 * {@link uk.co.bencara.noticeboard.local.NoticeBoardLocalImpl#processRequest(java.lang.String)}
	 * .
	 * 
	 * This method test a simple case where a simgle message has been posted for
	 * a single user and this message is retrieved by a read request
	 */
	@Test
	public void testProcessRequestSimplePostAndRead() {

		noticeBoardUnderTest.processRequest("John -> Hello World");
		List<String> responseLines = noticeBoardUnderTest
				.processRequest("John");

		// Check that the single message has been posted and retrieved
		assertEquals("One message should have been posted and retrieved", 1,
				responseLines.size());

		// Check that the response is returned as expected (i.e. in the correct
		// format
		// If the two requests do not take less than a second we are in trouble
		String expectedResponse = "John - Hello World (0 seconds ago)";
		assertEquals(expectedResponse, responseLines.get(0));
	}

	/**
	 * Test method for
	 * {@link uk.co.bencara.noticeboard.local.NoticeBoardLocalImpl#processRequest(java.lang.String)}
	 * .
	 * 
	 * This method test a simple case where a multiple messages have been posted
	 * for multiple users in a very short time span. Specifically this tests
	 * that:
	 * <ul>
	 * All the posted messages for the read command user are returned.
	 * <ul>
	 * Only the posted messages for the read command user are returned
	 * <ul>
	 * The messages are returned in post time, author, post order
	 */
	@Test
	public void testProcessRequestMultiplePostAndRead() {
		populateMultipleMessagesMultipleUsersNoDelay();

		List<String> responseLines = noticeBoardUnderTest
				.processRequest("John");

		// Check that the 4 messages posted for "John" have been posted and
		// retrieved
		assertEquals(
				"The four messages for the read command user should have been posted and retrieved",
				4, responseLines.size());

		// That the messages are retrieved in the correct order and are all for
		// the correct user

		assertTrue(
				"The responses may be for the wrong user or not in the correct order",
				responseLines.get(0).startsWith(EXPECTED_JOHN_RESPONSE_4_START));
		assertTrue(
				"The responses may be for the wrong user or not in the correct order",
				responseLines.get(1).startsWith(EXPECTED_JOHN_RESPONSE_3_START));
		assertTrue(
				"The responses may be for the wrong user or not in the correct order",
				responseLines.get(2).startsWith(EXPECTED_JOHN_RESPONSE_2_START));
		assertTrue(
				"The responses may be for the wrong user or not in the correct order",
				responseLines.get(3).startsWith(EXPECTED_JOHN_RESPONSE_1_START));
	}

	/**
	 * Test method for
	 * {@link uk.co.bencara.noticeboard.local.NoticeBoardLocalImpl#processRequest(java.lang.String)}
	 * .
	 * 
	 * This method tests the processing of the "wall" command for a new user.
	 */
	@Test
	public void testProcessRequestWallNewUser() {

		// Check that the request is processed but no response lines are
		// returned.
		List<String> responseLines = noticeBoardUnderTest
				.processRequest("John wall");

		assertTrue(responseLines.size() == 0);

	}

	/**
	 * Test method for
	 * {@link uk.co.bencara.noticeboard.local.NoticeBoardLocalImpl#processRequest(java.lang.String)}
	 * .
	 * 
	 * This message the situation where a user to follows one other user and
	 * both have messages posted prior to a wall command:
	 * <ul>
	 * Messages for both the user and the followed user are returned.
	 * <ul>
	 * The all the combined messages for both users are ordered by post time
	 * then author
	 * <ul>
	 * Messages posted by other (none followed users) are not returned
	 */
	@Test
	public void testProcessRequestNotFollowingAndWall() {
		populateMultipleMessagesMultipleUsersWithDelays();

		// Check that Johns own messages are retrieved
		List<String> responseLines = noticeBoardUnderTest
				.processRequest("John wall");

		// Check that the 4 messages posted for "John" have been retrieved by
		// the wall command
		assertEquals(
				"The response should inlcude all the messages from John (source user) and Jack (followed user)",
				4, responseLines.size());

		// That the messages are retrieved in the correct order and are all for
		// the correct users
		assertTrue(
				"The responses may be for the wrong user or not in the correct order",
				responseLines.get(0).startsWith(EXPECTED_JOHN_RESPONSE_4_START));
		assertTrue(
				"The responses may be for the wrong user or not in the correct order",
				responseLines.get(1).startsWith(EXPECTED_JOHN_RESPONSE_3_START));
		assertTrue(
				"The responses may be for the wrong user or not in the correct order",
				responseLines.get(2).startsWith(EXPECTED_JOHN_RESPONSE_2_START));
		assertTrue(
				"The responses may be for the wrong user or not in the correct order",
				responseLines.get(3).startsWith(EXPECTED_JOHN_RESPONSE_1_START));
	}

	/**
	 * Test method for
	 * {@link uk.co.bencara.noticeboard.local.NoticeBoardLocalImpl#processRequest(java.lang.String)}
	 * .
	 * 
	 * This message the situation where a user to follows one other user and
	 * both have messages posted prior to a wall command:
	 * <ul>
	 * Messages for both the user and the followed user are returned.
	 * <ul>
	 * The all the combined messages for both users are ordered by post time
	 * then author
	 * <ul>
	 * Messages posted by other (none followed users) are not returned
	 */
	@Test
	public void testProcessRequestFollowUserNoMessagesAndWall() {
		// populate the notice board with messages posted by john only
		populateMultipleMessagesJohnOnlyWithDelays();
		
		noticeBoardUnderTest.processRequest("Jack follows John");
		
		// Follow Jack who has no posted messages
		noticeBoardUnderTest.processRequest("John follows Jack");

		// Check that Johns own messages are retrieved by the wall request
		// and that the absence of messages for Jack does not cause an issue
		List<String> responseLines = noticeBoardUnderTest
				.processRequest("John wall");

		// Check that the 4 messages posted for "John" have been posted and
		// retrieved
		assertEquals(
				"The response should inlcude all the messages from John (source user) and Jack (followed user)",
				4, responseLines.size());

		// That the messages are retrieved in the correct order and are all for
		// the correct users
		assertTrue(
				"The responses may be for the wrong user or not in the correct order",
				responseLines.get(0).startsWith(EXPECTED_JOHN_RESPONSE_4_START));
		assertTrue(
				"The responses may be for the wrong user or not in the correct order",
				responseLines.get(1).startsWith(EXPECTED_JOHN_RESPONSE_3_START));
		assertTrue(
				"The responses may be for the wrong user or not in the correct order",
				responseLines.get(2).startsWith(EXPECTED_JOHN_RESPONSE_2_START));
		assertTrue(
				"The responses may be for the wrong user or not in the correct order",
				responseLines.get(3).startsWith(EXPECTED_JOHN_RESPONSE_1_START));
	}

	/**
	 * Test method for
	 * {@link uk.co.bencara.noticeboard.local.NoticeBoardLocalImpl#processRequest(java.lang.String)}
	 * .
	 * 
	 * This message the situation where a user to follows one other user and
	 * both have messages posted prior to a wall command:
	 * <ul>
	 * Messages for both the user and the followed user are returned.
	 * <ul>
	 * The all the combined messages for both users are ordered by post time
	 * then author
	 * <ul>
	 * Messages posted by other (none followed users) are not returned
	 */
	@Test
	public void testProcessRequestFollowUserAndWall() {
		// populate the notice board with messages posted by John, Jack and Mel
		populateMultipleMessagesMultipleUsersWithDelays();

		noticeBoardUnderTest.processRequest("John follows Jack");

		List<String> responseLines = noticeBoardUnderTest
				.processRequest("John wall");

		// Check that the 4 messages posted for "John" have been posted and
		// retrieved
		assertEquals(
				"The response should inlcude all the messages from John (source user) and Jack (followed user)",
				8, responseLines.size());

		// That the messages are retrieved in the correct order and are all for
		// the correct users
		assertTrue(
				"The responses may be for the wrong user or not in the correct order",
				responseLines.get(0).startsWith(EXPECTED_JOHN_RESPONSE_4_START));
		assertTrue(
				"The responses may be for the wrong user or not in the correct order",
				responseLines.get(1).startsWith(EXPECTED_JACK_RESPONSE_4_START));
		assertTrue(
				"The responses may be for the wrong user or not in the correct order",
				responseLines.get(2).startsWith(EXPECTED_JOHN_RESPONSE_3_START));
		assertTrue(
				"The responses may be for the wrong user or not in the correct order",
				responseLines.get(3).startsWith(EXPECTED_JACK_RESPONSE_3_START));
		assertTrue(
				"The responses may be for the wrong user or not in the correct order",
				responseLines.get(4).startsWith(EXPECTED_JOHN_RESPONSE_2_START));
		assertTrue(
				"The responses may be for the wrong user or not in the correct order",
				responseLines.get(5).startsWith(EXPECTED_JACK_RESPONSE_2_START));
		assertTrue(
				"The responses may be for the wrong user or not in the correct order",
				responseLines.get(6).startsWith(EXPECTED_JOHN_RESPONSE_1_START));
		assertTrue(
				"The responses may be for the wrong user or not in the correct order",
				responseLines.get(7).startsWith(EXPECTED_JACK_RESPONSE_1_START));
		
	}

	/**
	 * Test method for
	 * {@link uk.co.bencara.noticeboard.local.NoticeBoardLocalImpl#processRequest(java.lang.String)}
	 * .
	 * 
	 * This method test a simple case where a multiple messages have been posted
	 * for multiple users with time between the message postings. Specifically
	 * this tests that:
	 * <ul>
	 * All the posted messages for the read command user are returned.
	 * <ul>
	 * Only the posted messages for the read command user are returned
	 * <ul>
	 * The messages are returned in post time, author, post order
	 */
	@Test
	public void testProcessRequestMultiplePostAndReadWithPostDelays() {
		// populate the notice board with messages posted by John, Jack and Mel
		populateMultipleMessagesMultipleUsersWithDelays();

		// Call a read request
		List<String> responseLines = noticeBoardUnderTest
				.processRequest("John");

		// Check that the 4 messages posted for "John" have been posted and
		// retrieved
		assertEquals(
				"The four messages for the read command user should have been posted and retrieved",
				4, responseLines.size());

		// That the messages are retrieved in the correct order and are all for
		// the correct user

		assertTrue(
				"The responses may be for the wrong user or not in the correct order",
				responseLines.get(0).startsWith(EXPECTED_JOHN_RESPONSE_4_START));
		assertTrue(
				"The responses may be for the wrong user or not in the correct order",
				responseLines.get(1).startsWith(EXPECTED_JOHN_RESPONSE_3_START));
		assertTrue(
				"The responses may be for the wrong user or not in the correct order",
				responseLines.get(2).startsWith(EXPECTED_JOHN_RESPONSE_2_START));
		assertTrue(
				"The responses may be for the wrong user or not in the correct order",
				responseLines.get(3).startsWith(EXPECTED_JOHN_RESPONSE_1_START));
	}

	/**
	 * A utility method to post the standard set of messages with no delay
	 * between each posting
	 */
	private void populateMultipleMessagesMultipleUsersNoDelay() {
		String[] standardPosts = getStandardMessagesInStandardPostOrder();
		for (String currentPost : standardPosts) {
			noticeBoardUnderTest.processRequest(currentPost);
		}

	}

	/**
	 * A utility method to post the standard set of messages with 0.5 second
	 * delays between each posting
	 */
	private void populateMultipleMessagesMultipleUsersWithDelays() {
		String[] standardPosts = getStandardMessagesInStandardPostOrder();
		for (String currentPost : standardPosts) {
			noticeBoardUnderTest.processRequest(currentPost);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				fail("Interuptions not expected in tests");
			}
		}

	}

	/**
	 * A utility method to post the standard set of messages with 0.5 second
	 * delays between each posting
	 */
	private void populateMultipleMessagesJohnOnlyWithDelays() {
		String[] standardPosts = getJohnOnlyMessagesInStandardPostOrder();
		for (String currentPost : standardPosts) {
			noticeBoardUnderTest.processRequest(currentPost);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				fail("Interuptions not expected in tests");
			}
		}

	}

	/**
	 * A utility method to return the set of test message for all users in the
	 * expected posting order.
	 * 
	 * @return the array of post request strings
	 */
	private String[] getStandardMessagesInStandardPostOrder() {
		return new String[] { JACK_MESSAGE_1, JOHN_MESSAGE_1, JACK_MESSAGE_2,
				JOHN_MESSAGE_2, JACK_MESSAGE_3, JOHN_MESSAGE_3, JACK_MESSAGE_4,
				JOHN_MESSAGE_4, MEL_MESSAGE_1, MEL_MESSAGE_2, MEL_MESSAGE_3,
				MEL_MESSAGE_4 };

	}

	/**
	 * A utility method to return the set of test messages for user John in the
	 * expected posting order.
	 * 
	 * @return the array of post request strings
	 */
	private String[] getJohnOnlyMessagesInStandardPostOrder() {
		return new String[] { JOHN_MESSAGE_1, JOHN_MESSAGE_2, JOHN_MESSAGE_3,
				JOHN_MESSAGE_4 };

	}
}
