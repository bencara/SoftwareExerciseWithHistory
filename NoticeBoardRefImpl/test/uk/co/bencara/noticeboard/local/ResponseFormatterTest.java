/**
 * 
 */
package uk.co.bencara.noticeboard.local;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import uk.co.bencara.noticeboard.model.PostedMessage;
import uk.co.bencara.noticeboard.model.User;

/**
 * A class to test the the ResponseFormatter class functionality
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
	 * <ul>Each message has been correctly interpreted
	 * <ul>The interpretation of each message is in the response.
	 * <ul>The response lines are in the order of the passed messages.
	 */
	@Test
	public void testFormatMessagesForResponseValidInput() {
		inputMessages = new ArrayList<PostedMessage>();
		inputMessages.add(new PostedMessage(new User("User1"), "First message", baseTime - 4000));
		inputMessages.add(new PostedMessage(new User("User2"), "Second message", baseTime - 3000));
		inputMessages.add(new PostedMessage(new User("User3"), "Third message", baseTime - 2000));
		
		generatedResponseLines = formatterUnderTest.formatMessagesForResponse(inputMessages, baseTime);
		
		String expectedResponse1 = "User1 - First message (4 seconds ago)";
		String expectedResponse2 = "User2 - Second message (3 seconds ago)";
		String expectedResponse3 = "User3 - Third message (4 seconds ago)";
		

	}

}
