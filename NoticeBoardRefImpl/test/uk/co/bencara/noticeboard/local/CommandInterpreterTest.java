/**
 * 
 */
package uk.co.bencara.noticeboard.local;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import uk.co.bencara.noticeboard.local.CommandInterpreter;
import uk.co.bencara.noticeboard.local.NoticeBoardCommand;
import uk.co.bencara.noticeboard.local.NoticeBoardCommandType;

/**
 * A class to test the functionality of the CommandInterpreter, currently this
 * only test the behaviuor with valid inputs.
 * 
 * © Bencara Systems Ltd
 * 
 * @author Les Eckersley
 * 
 */
public class CommandInterpreterTest {

	private CommandInterpreter interpreter = new CommandInterpreter();

	private NoticeBoardCommand interpretedCommand = null;

	@Before
	public void initialise() {
		interpretedCommand = null;
	}

	/**
	 * Test the operation of the interpreter with a valid "wall" command
	 */
	@Test
	public void testInterpretCommandStringSunnyDayWallCommand() {
		interpretedCommand = interpreter
				.interpretCommandString("TestUser1 wall");
		assertEquals("Sunny Day Wall first user not interpretted correctly",
				"TestUser1", interpretedCommand.getFirstUserName());
		assertEquals("Sunny Day Wall command not interpretted correctly",
				NoticeBoardCommandType.WALL,
				interpretedCommand.getCommandType());
		assertNull("Sunny Day Wall second user not interpretted correctly",
				interpretedCommand.getSecondUserName());
		assertNull("Sunny Day Wall message not interpretted correctly",
				interpretedCommand.getMessageText());
	}

	/**
	 * Test the operation of the interpreter with a valid "reading" command
	 */
	@Test
	public void testInterpretCommandStringSunnyDayReadingCommand() {
		interpretedCommand = interpreter.interpretCommandString("TestUser1");
		assertEquals("Sunny Day Reading first user not interpretted correctly",
				"TestUser1", interpretedCommand.getFirstUserName());
		assertEquals("Sunny Day Reading command not interpretted correctly",
				NoticeBoardCommandType.READ,
				interpretedCommand.getCommandType());
		assertNull("Sunny Day Reading second user not interpretted correctly",
				interpretedCommand.getSecondUserName());
		assertNull("Sunny Day Reading message not interpretted correctly",
				interpretedCommand.getMessageText());
	}

	/**
	 * Test the operation of the interpreter with a valid "reading" command
	 */
	@Test
	public void testInterpretCommandStringSunnyDayPostingCommand() {

		interpretedCommand = interpreter
				.interpretCommandString("TestUser1 ->    my	special    message");
		assertEquals("Sunny Day Posting first user not interpretted correctly",
				"TestUser1", interpretedCommand.getFirstUserName());
		assertEquals("Sunny Day Posting command not interpretted correctly",
				NoticeBoardCommandType.POST,
				interpretedCommand.getCommandType());
		assertNull("Sunny Day Posting second user not interpretted correctly",
				interpretedCommand.getSecondUserName());
		assertEquals("Sunny Day Posting message not interpretted correctly",
				"   my	special    message", interpretedCommand.getMessageText());
	}

	/**
	 * Test the operation of the interpreter with a valid "follow" command
	 */
	@Test
	public void testInterpretCommandStringSunnyDayFollowCommand() {

		interpretedCommand = interpreter
				.interpretCommandString("TestUser1 follows TestUser2");
		assertEquals(
				"Sunny Day Following first user not interpretted correctly",
				"TestUser1", interpretedCommand.getFirstUserName());
		assertEquals("Sunny Day Following command not interpretted correctly",
				NoticeBoardCommandType.FOLLOW,
				interpretedCommand.getCommandType());
		assertEquals(
				"Sunny Day Following second user not interpretted correctly",
				"TestUser2", interpretedCommand.getSecondUserName());
		assertNull("Sunny Day Following message not interpretted correctly",
				interpretedCommand.getMessageText());
	}

	// TODO negative testing
}
