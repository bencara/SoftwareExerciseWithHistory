/**
 * 
 */
package uk.co.bencara.noticeboard.local;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * A class to test the NoticeBoardCommand class
 * 
 * © Bencara Systems Ltd
 * 
 * @author Les Eckersley
 * 
 */
public class NoticeBoardCommandTest {

	private NoticeBoardCommand commandUnderTest;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		commandUnderTest = null;
	}

	/**
	 * Test method for
	 * {@link uk.co.bencara.noticeboard.local.NoticeBoardCommand#NoticeBoardCommand(java.lang.String, java.lang.String, uk.co.bencara.noticeboard.local.NoticeBoardCommandType, java.lang.String)}
	 * .
	 */
	@Test
	public void testNoticeBoardCommand() {
		commandUnderTest = new NoticeBoardCommand("User1", "User2",
				NoticeBoardCommandType.FOLLOW, "Hello World");
		assertEquals("The first user has either not been set or retrieved", "User1",  commandUnderTest.getFirstUserName());
		assertEquals("The second user has either not been set or retrieved", "User2",  commandUnderTest.getSecondUserName());
		assertEquals("The command type has either not been set or retrieved", NoticeBoardCommandType.FOLLOW,  commandUnderTest.getCommandType());
		assertEquals("The message has either not been set or retrieved", "Hello World",  "Hello World");
	}

	/** 
	 * Test method for
	 * {@link uk.co.bencara.noticeboard.local.NoticeBoardCommand#getFirstUserName()}
	 * .
	 */
	@Test
	public void testGetFirstUserName() {
		// Placeholder, the get method is tested in the constructor test
	}

	/**
	 * Test method for
	 * {@link uk.co.bencara.noticeboard.local.NoticeBoardCommand#getSecondUserName()}
	 * .
	 */
	@Test
	public void testGetSecondUserName() {
		// Placeholder, the get method is tested in the constructor test
	}

	/**
	 * Test method for
	 * {@link uk.co.bencara.noticeboard.local.NoticeBoardCommand#getCommandType()}
	 * .
	 */
	@Test
	public void testGetCommandType() {
		// Placeholder, the get method is tested in the constructor test
	}

	/**
	 * Test method for
	 * {@link uk.co.bencara.noticeboard.local.NoticeBoardCommand#getMessageText()}
	 * .
	 */
	@Test
	public void testGetMessageText() {
		// Placeholder, the get method is tested in the constructor test
	}

}
