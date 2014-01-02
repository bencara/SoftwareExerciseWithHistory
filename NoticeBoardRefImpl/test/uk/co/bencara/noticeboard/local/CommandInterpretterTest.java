/**
 * 
 */
package uk.co.bencara.noticeboard.local;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Les Eckersley
 * 
 */
public class CommandInterpretterTest {

	private CommandInterpretter interpretter = new CommandInterpretter();

	private NoticeBoardCommand interprettedCommand = null;

	@Before
	public void initialise() {
		interprettedCommand = null;
	}

	/**
	 * Test method for
	 * {@link uk.co.bencara.noticeboard.local.CommandInterpretter#InterpretCommandString(java.lang.String)}
	 * .
	 */
	@Test
	public void testInterpretCommandString() {
		fail("Not yet implemented");
	}

	@Test
	public void testSunnyDayWallCommand() {
		interprettedCommand = interpretter
				.interpretCommandString("TestUser1 wall");
		assertEquals("Sunny Day Wall first user not interpretted correctly",
				"TestUser1", interprettedCommand.getFirstUserName());
	}
}
