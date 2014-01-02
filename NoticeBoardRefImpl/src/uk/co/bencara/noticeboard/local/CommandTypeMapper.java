/**
 * 
 */
package uk.co.bencara.noticeboard.local;

import org.apache.commons.lang3.StringUtils;

/**
 * A class to maps text command names to the supported NoticeBoardCommandTypes
 * @author Les Eckersley
 *
 */
public class CommandTypeMapper {
	
	public static final String postCommandString = "->";

	public static final String followCommandString = "follows";

	public static final String wallCommandString = "wall";
	
	public static final String readCommandString = "";

	/**
	 * A method to retrieve a NoticeBoardCoommandType corresponding to a based command string.
	 * Null will be returned if the command string is not supported
	 * @param commandString
	 * @return the appropriate NoticeBoardCommandType for the passed commandString or null if the string is not recognised
	 */
	static NoticeBoardCommandType getMappedCommandType(String commandString) {
		String trimmedCommandString = StringUtils.trimToEmpty(commandString);
		
		if (postCommandString.equals(trimmedCommandString)) {
			return NoticeBoardCommandType.POST;
		}
		if (followCommandString.equals(trimmedCommandString)) {
			return NoticeBoardCommandType.FOLLOW;
		}
		if (wallCommandString.equals(trimmedCommandString)) {
			return NoticeBoardCommandType.WALL;
		}
		if (readCommandString.equals(trimmedCommandString)) {
			return NoticeBoardCommandType.READ;
		}
		
		return null;
		
		
	}

}
