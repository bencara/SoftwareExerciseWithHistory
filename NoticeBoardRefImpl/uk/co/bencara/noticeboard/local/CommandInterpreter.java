/**
 * 
 */
package uk.co.bencara.noticeboard.local;

/**
 * A class to provide functionality to parser a notice board command input
 * string and populate a command object
 * 
 * © Bencara Systems Ltd
 * 
 * @author Les Eckersley
 * 
 */
public class CommandInterpreter {

	/**
	 * A method to convert a command string into a NoticeBoardCommand. Null will
	 * be returned if the message cannot be interpreted.
	 * 
	 * @param commandString
	 * @return a correctly populated command or null if the string could not be
	 *         interprted
	 */
	public NoticeBoardCommand interpretCommandString(String commandString) {
		if (commandString == null) {
			// TODO behaviour when a null string is passed
		}

		// Tokenise based on whitespace
		String[] commandParts = commandString.split(" ");
		String firstUserName = null;
		String secondUserName = null;
		NoticeBoardCommandType commandType = null;
		StringBuilder messageBuilder = new StringBuilder();
		int commandArgumentsFound = 0;
		boolean success = true;

		// Special case for the read command which has no command string
		if (commandParts.length == 1) {
			commandType = NoticeBoardCommandType.READ;
		}

		// Name a loop so that we can stop processing the message if it is
		// invalid
		ParsingLoop: for (String candidateCommandPart : commandParts) {
			// Since we are splitting using whitespace no need to trim
			// but have to allow for whitespaces in the message
			if (candidateCommandPart == null
					|| ("".equals(candidateCommandPart) && !NoticeBoardCommandType.POST
							.equals(commandType))) {
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
				commandType = CommandTypeMapper
						.getMappedCommandType(candidateCommandPart);
				if (commandType == null) {
					// We do not understand the command type so the request is a
					// failure
					success = false;
					break ParsingLoop;
				}
				commandArgumentsFound = 2;
				break;

			case 2:
				if (NoticeBoardCommandType.FOLLOW.equals(commandType)) {
					// If it is a follow command then the third significant
					// token must be the user name of the person to be followed
					secondUserName = candidateCommandPart;
				} else if (NoticeBoardCommandType.POST.equals(commandType)) {
					// This is the first part of the message to be posted
					// so start building the message
					messageBuilder.append(candidateCommandPart);
				} else {
					// We are not expecting more info for the supported messages
					// so we cannot interpret the request
					success = false;
					break ParsingLoop;
				}
				commandArgumentsFound = 3;
				break;

			default:

				// add another part of the message remembering to replace any
				// whitespace that has been interpreted as a delimitter
				messageBuilder.append(" ");
				messageBuilder.append(candidateCommandPart);
				break;
			}
		}

		if (success) {
			String messageText = null;
			if (messageBuilder.length() > 0) {
				messageText = messageBuilder.toString();
			}

			// We can interpret the message so return the appropriate command
			return new NoticeBoardCommand(firstUserName, secondUserName,
					commandType, messageText);
		}

		// We can't interpret the message so return null.
		return null;
	}

}
