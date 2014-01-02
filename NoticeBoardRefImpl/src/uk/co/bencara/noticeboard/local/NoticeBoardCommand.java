/**
 * 
 */
package uk.co.bencara.noticeboard.local;

/**
 * A simple immutable POJO holding the data related to a notice board command.
 * @author Les Eckersley
 *
 */
public class NoticeBoardCommand {
	
	private final String firstUserName;
	private final String secondUserName;
	private final String command;
	private final String messageText;
	
	/**
	 * @param originatingUserName
	 * @param targetUserName
	 * @param command
	 * @param messageText
	 */
	public NoticeBoardCommand(String originatingUserName,
			String targetUserName, String command, String messageText) {
		super();
		this.firstUserName = originatingUserName;
		this.secondUserName = targetUserName;
		this.command = command;
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
	public String getCommand() {
		return command;
	}

	/**
	 * @return the messageText
	 */
	public String getMessageText() {
		return messageText;
	}
	
	

}
