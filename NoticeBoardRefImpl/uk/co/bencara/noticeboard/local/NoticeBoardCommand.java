/**
 * 
 */
package uk.co.bencara.noticeboard.local;

/**
 * A simple immutable POJO holding the data related to a notice board command.
 * 
 * © Bencara Systems Ltd
 * 
 * @author Les Eckersley
 *
 */
public class NoticeBoardCommand {
	
	private final String firstUserName;
	private final String secondUserName;
	private final NoticeBoardCommandType commandType;
	private final String messageText;
	
	/**
	 * Construct the command.
	 * @param originatingUserName
	 * @param targetUserName
	 * @param command
	 * @param messageText
	 */
	public NoticeBoardCommand(String originatingUserName,
			String targetUserName, NoticeBoardCommandType commandType, String messageText) {
		super();
		this.firstUserName = originatingUserName;
		this.secondUserName = targetUserName;
		this.commandType = commandType;
		this.messageText = messageText;
	}

	/**
	 * @return the firstUserName
	 */
	public String getFirstUserName() {
		return firstUserName;
	}

	/**
	 * @return the secondUserName
	 */
	public String getSecondUserName() {
		return secondUserName;
	}

	/**
	 * @return the command
	 */
	public NoticeBoardCommandType getCommandType() {
		return commandType;
	}

	/**
	 * @return the messageText
	 */
	public String getMessageText() {
		return messageText;
	}
	
	

}
