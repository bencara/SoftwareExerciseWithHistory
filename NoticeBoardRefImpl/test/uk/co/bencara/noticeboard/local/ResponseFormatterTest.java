/**
 * 
 */
package uk.co.bencara.noticeboard.local;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import uk.co.bencara.noticeboard.local.ResponseFormatter;
import uk.co.bencara.noticeboard.model.PostedMessage;
import uk.co.bencara.noticeboard.model.User;


/**
 * A class to test the the ResponseFormatter class functionality
 * 
 * © Bencara Systems Ltd
 * 
 * @author Les Eckersley
 * 
 */
public class ResponseFormatterTest {

	private ResponseFormatter formatterUnderTest = new ResponseFormatter();
	private List<PostedMessage> inputMessages;
	private List<String> generatedResponseLines;
	private long baseTime = System.currentTimeMillis();

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		inputMessages = null;
		generatedResponseLines = null;
	}

	/**
	 * Test the FormatMessagesForResponse method when the inputs are valid.
	 * <ul>
	 * Specifically test that:
	 * <ul>
	 * Each message has been correctly interpreted
	 * <ul>
	 * The interpretation of each message is in the response.
	 * <ul>
	 * The response lines are in the order of the passed messages.
	 */
	@Test
	public void testFormatMessagesOrderAndGeneralFormatting() {
		inputMessages = new ArrayList<PostedMessage>();
		inputMessages.add(new PostedMessage(new User("User1"), "First message",
				baseTime - 4000, 1));
		inputMessages.add(new PostedMessage(new User("User2"),
				"Second message", baseTime - 3000, 1));
		inputMessages.add(new PostedMessage(new User("User3"), "Third message",
				baseTime - 2000, 1));

		generatedResponseLines = formatterUnderTest.formatMessagesForResponse(
				inputMessages, baseTime);

		String expectedResponse1 = "User1 - First message (4 seconds ago)";
		String expectedResponse2 = "User2 - Second message (3 seconds ago)";
		String expectedResponse3 = "User3 - Third message (2 seconds ago)";

		// Check that a line is generated per message
		assertEquals(3, generatedResponseLines.size());

		// Check that the messages are as expected and in the same order as
		// passed in.
		assertEquals(expectedResponse1, generatedResponseLines.get(0));
		assertEquals(expectedResponse2, generatedResponseLines.get(1));
		assertEquals(expectedResponse3, generatedResponseLines.get(2));
	}

	/**
	 * Test the FormatMessagesForResponse method in relation to the generation
	 * of the elapsed time for less than 1 second
	 */
	@Test
	public void testFormatMessagesElapsedTimeFormattingZeroSeconds() {
		inputMessages = new ArrayList<PostedMessage>();

		// Check condition where the elapsed time is zero
		inputMessages.add(new PostedMessage(new User("User1"), "First message",
				baseTime, 1));
		generatedResponseLines = formatterUnderTest.formatMessagesForResponse(
				inputMessages, baseTime);

		// Check that the zero elapsed time is interpreted correctly
		String expectedResponse = "User1 - First message (0 seconds ago)";
		assertEquals(expectedResponse, generatedResponseLines.get(0));

		// Check upper bondary condition
		inputMessages = new ArrayList<PostedMessage>();
		inputMessages.add(new PostedMessage(new User("User1"), "First message",
				baseTime - 999, 1));
		generatedResponseLines = formatterUnderTest.formatMessagesForResponse(
				inputMessages, baseTime);

		// Check that the 999 millis elapsed time is interpreted correctly
		expectedResponse = "User1 - First message (0 seconds ago)";
		assertEquals(expectedResponse, generatedResponseLines.get(0));

	}

	/**
	 * Test the FormatMessagesForResponse method in relation to the generation
	 * of the elapsed time 1 <= elapsedTime < 2
	 */
	@Test
	public void testFormatMessagesElapsedTimeFormattingOneSecond() {
		inputMessages = new ArrayList<PostedMessage>();

		// Check condition where the elapsed time is zero
		inputMessages.add(new PostedMessage(new User("User1"), "First message",
				baseTime - 1000, 1));
		generatedResponseLines = formatterUnderTest.formatMessagesForResponse(
				inputMessages, baseTime);

		// Check that the 1000 millis elapsed time is interpreted correctly
		String expectedResponse = "User1 - First message (1 second ago)";
		assertEquals(expectedResponse, generatedResponseLines.get(0));

		// Check upper boundary condition 1999 millis
		inputMessages = new ArrayList<PostedMessage>();
		inputMessages.add(new PostedMessage(new User("User1"), "First message",
				baseTime - 1999, 1));
		generatedResponseLines = formatterUnderTest.formatMessagesForResponse(
				inputMessages, baseTime);

		// Check that the 1999 millis elapsed time is interpreted correctly
		expectedResponse = "User1 - First message (1 second ago)";
		assertEquals(expectedResponse, generatedResponseLines.get(0));
	}

	/**
	 * Test the FormatMessagesForResponse method in relation to the generation
	 * of the elapsed time over the second/minute boundary
	 */
	@Test
	public void testFormatMessagesElapsedTimeFormattingSecondToMinuteBoundary() {
		inputMessages = new ArrayList<PostedMessage>();

		// Check condition where the elapsed time is 59.999 seconds
		inputMessages.add(new PostedMessage(new User("User1"), "First message",
				baseTime - 59999, 1));
		generatedResponseLines = formatterUnderTest.formatMessagesForResponse(
				inputMessages, baseTime);

		// Check that the 59999 millis elapsed time is interpreted correctly
		String expectedResponse = "User1 - First message (59 seconds ago)";
		assertEquals(expectedResponse, generatedResponseLines.get(0));

		// Check 1 minute boundary condition
		inputMessages = new ArrayList<PostedMessage>();
		inputMessages.add(new PostedMessage(new User("User1"), "First message",
				baseTime - 60000, 1));
		generatedResponseLines = formatterUnderTest.formatMessagesForResponse(
				inputMessages, baseTime);

		// Check that the 60000 millis elapsed time is interpreted correctly
		expectedResponse = "User1 - First message (1 minute ago)";
		assertEquals(expectedResponse, generatedResponseLines.get(0));
	}

	/**
	 * Test the FormatMessagesForResponse method in relation to the generation
	 * of the elapsed time over the minute/hour boundary
	 */
	@Test
	public void testFormatMessagesElapsedTimeFormattingMinuteToHourBoundary() {
		inputMessages = new ArrayList<PostedMessage>();

		// Check condition where the elapsed time is 59 minutes 59.999 seconds
		// seconds
		inputMessages.add(new PostedMessage(new User("User1"), "First message",
				baseTime - 3599999, 1));
		generatedResponseLines = formatterUnderTest.formatMessagesForResponse(
				inputMessages, baseTime);

		// Check that the 3599999 millis elapsed time is interpreted correctly
		String expectedResponse = "User1 - First message (59 minutes ago)";
		assertEquals(expectedResponse, generatedResponseLines.get(0));

		// Check minute boundary condition
		inputMessages = new ArrayList<PostedMessage>();
		inputMessages.add(new PostedMessage(new User("User1"), "First message",
				baseTime - 3600000, 1));
		generatedResponseLines = formatterUnderTest.formatMessagesForResponse(
				inputMessages, baseTime);

		// Check that the 3600000 millis elapsed time is interpreted correctly
		expectedResponse = "User1 - First message (1 hour ago)";
		assertEquals(expectedResponse, generatedResponseLines.get(0));
	}

	/**
	 * Test the FormatMessagesForResponse method in relation to the generation
	 * of the elapsed time over the hour/day boundary
	 */
	@Test
	public void testFormatMessagesElapsedTimeFormattingHourToDayBoundary() {
		inputMessages = new ArrayList<PostedMessage>();

		// Check condition where the elapsed time is 23 hours 59 minutes 59.999
		// seconds seconds
		inputMessages.add(new PostedMessage(new User("User1"), "First message",
				baseTime - 86399999, 1));
		generatedResponseLines = formatterUnderTest.formatMessagesForResponse(
				inputMessages, baseTime);

		// Check that the 36000 millis elapsed time is interpreted correctly
		String expectedResponse = "User1 - First message (23 hours ago)";
		assertEquals(expectedResponse, generatedResponseLines.get(0));

		// Check minute boundary condition
		inputMessages = new ArrayList<PostedMessage>();
		inputMessages.add(new PostedMessage(new User("User1"), "First message",
				baseTime - 86400000, 1));
		generatedResponseLines = formatterUnderTest.formatMessagesForResponse(
				inputMessages, baseTime);

		// Check that the 60000 millis elapsed time is interpreted correctly
		expectedResponse = "User1 - First message (1 day ago)";
		assertEquals(expectedResponse, generatedResponseLines.get(0));
	}

	/**
	 * Test the FormatMessagesForResponse method in relation to the generation
	 * of the elapsed time for multiple days
	 */
	@Test
	public void testFormatMessagesElapsedTimeFormattingDays() {
		inputMessages = new ArrayList<PostedMessage>();

		// Check condition where the elapsed time is 3 days and change
		inputMessages.add(new PostedMessage(new User("User1"), "First message",
				baseTime - 259201000, 1));
		generatedResponseLines = formatterUnderTest.formatMessagesForResponse(
				inputMessages, baseTime);

		// Check that the 3 days and change is interpreted correctly
		String expectedResponse = "User1 - First message (3 days ago)";
		assertEquals(expectedResponse, generatedResponseLines.get(0));

	}

}
