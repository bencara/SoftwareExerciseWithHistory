/**
 * 
 */
package uk.co.bencara.noticeboard.local;

import static uk.co.bencara.noticeboard.NoticeBoard.followCommand;
import static uk.co.bencara.noticeboard.NoticeBoard.postCommand;
import static uk.co.bencara.noticeboard.NoticeBoard.wallCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * A class to provide functionality to parser a notice board command input string and populate a command object
 * 
 * @author Les Eckersley
 * 
 */
public class CommandParser {

	private static final List<String> validCommands;

	static {
		validCommands = new ArrayList<String>();
		validCommands.add(followCommand);
		validCommands.add(postCommand);
		validCommands.add(wallCommand);
	}

	/**
	 * A method to convert a command string into a NoticeBoardCommand. Null will
	 * be returned if the message cannot be interpretted.
	 * 
	 * @param commandString
	 * @return a correctly populated command or null if the string could not be interpretted
	 */
	public NoticeBoardCommand InterpretCommandString(String commandString) {
		if (commandString == null) {
			// TODO behaviour when a null string is passed
		}

		// Tokenise based on whitespace
		String[] commandParts = commandString.split("/s");
		String firstUserName = null;
		String secondUserName = null;
		String command = null;
		StringBuilder messageBuilder = new StringBuilder();
		int commandArgumentsFound = 0;
		boolean success = true;
		
		// Name a loop so that we can stop processing the message if it is invalid
		ParsingLoop: for (String candidateCommandPart : commandParts) {
			// Since we are splitting using whitespace no need to trim
			// but have to allow for whitespaces in the message
			if (candidateCommandPart != null
					|| ("".equals(candidateCommandPart) && !postCommand
							.equals(command))) {
				continue;
			}
			switch (commandArgumentsFound) {
			case 0:
				// The first none null token must be a user name
				firstUserName = candidateCommandPart;
				commandArgumentsFound = 1;
				break;

			case 1:
				// The second none null token must be the command string
				if (!validCommands.contains(candidateCommandPart)) {
					success = false;
					break ParsingLoop;
				}
				command = candidateCommandPart;
				commandArgumentsFound = 2;
				break;

			case 2:
				if (followCommand.equals(command)) {
					// If it is a follow command then the third significant
					// token must be the user name of the person to be followed
					secondUserName = candidateCommandPart;
				} else if (postCommand.equals(command)) {
					// This is the first part of the message to be posted
					// so start building the message
					messageBuilder.append(candidateCommandPart);
				} else{
					success = false;
					break ParsingLoop;
				}
				commandArgumentsFound = 3;
				break;

			default:
				// add another part of the message remembering to replace any whaitspace that has been interpretted as a delimitter
				messageBuilder.append(" ");
				messageBuilder.append(candidateCommandPart);
				break;
			}
		}
		
		if (success) {
			// We can interpret the message so return the appropriate command
			return new NoticeBoardCommand(firstUserName, secondUserName, command, messageBuilder.toString());
		}
		
		// We can't interpret the message so return null.
		return null;
	}

}
