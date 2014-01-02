/**
 * 
 */
package uk.co.bencara.noticeboard.local;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import uk.co.bencara.noticeboard.NoticeBoard;
import uk.co.bencara.noticeboard.model.PostedMessage;
import uk.co.bencara.noticeboard.model.User;

/**
 * @author Les Eckersley
 * 
 */
public class NoticeBoardLocalImpl implements NoticeBoard {

	private CommandInterpreter interpreter = new CommandInterpreter();

	private UserManager userManager = new UserManager();
	
	private ResponseFormatter respFormatter = new ResponseFormatter();

	private Map<String, SortedSet<PostedMessage>> sortedMessagesForAuthorByAuthorName = new HashMap<String, SortedSet<PostedMessage>>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.co.bencara.noticeboard.NoticeBoard#makeRequest(java.lang.String)
	 */
	@Override
	public List<String> processRequest(String commandString) {
		Long requestRecievedTime = System.currentTimeMillis();
		NoticeBoardCommand command = interpreter
				.interpretCommandString(commandString);

		// Check if the command could be interpreted
		if (command == null) {
			// TODO behaviour when command cannot be interpreted
			return null;
		}

		List<String> response = null;

		switch (command.getCommandType()) {
		case READ:
			response = readMessages(command.getFirstUserName(),
					requestRecievedTime);
			break;

		case FOLLOW:
			response = followUser(command.getFirstUserName(),
					command.getSecondUserName());
			break;

		case WALL:
			response = getMessageWall(command.getFirstUserName(),
					requestRecievedTime);
			break;

		case POST:
			response = postMessage(command.getFirstUserName(),
					command.getMessageText(), requestRecievedTime);
			break;

		default:
			// TODO the command is not an uninterpretable type
			break;
		}

		return response;

	}

	private List<String> getMessageWall(String firstUserName,
			long requestReceiptTime) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<String> followUser(String firstUserName, String secondUserName) {
		// TODO
		return null;
	}

	/**
	 * A method to perform the functionality associated with posting a message
	 * to the notice board.
	 * 
	 * @param author
	 *            may not be null
	 * @param message
	 *            may not be trivial
	 * @param requestReceiptTime
	 * @return error lines to be displayed to the user if the request could not
	 *         be completed
	 */
	private List<String> postMessage(String author, String message,
			long requestReceiptTime) {
		// Get the user for the author creating one if necessary
		// TODO defend against illegal argument if the user name is invalid
		User authorUser = userManager.retrieveUser(author, true);

		// Retrieve the authors existing messages
		SortedSet<PostedMessage> messagesForAuthorByPostTime = sortedMessagesForAuthorByAuthorName
				.get(authorUser.getName());
		if (messagesForAuthorByPostTime == null) {
			// This is the first message posted by the user so initialise their
			// message board
			messagesForAuthorByPostTime = new TreeSet<PostedMessage>();
		}

		// Create a new message base on the arguements
		// TODO deal with illegal argument if the text is trivial
		PostedMessage newMessage = new PostedMessage(authorUser, message,
				requestReceiptTime);

		messagesForAuthorByPostTime.add(newMessage);

		// Potentially return an error message if the message text ort user name
		// is invalid
		return null;

	}

	/**
	 * A method to perform the functionality associated with reading all the
	 * messages posted by a user to the notice board.
	 * 
	 * @param author
	 *            the author of the required messages
	 * @param requestReceiptTime
	 *            the time that the request to read the messages was recieved
	 * @return the messages associated with the passed author name formatted for
	 *         inclusion in the response for display to the user. Null if no
	 *         messages have been posted by the specified user
	 */
	private List<String> readMessages(String author, long requestReceiptTime) {
		// TODO deal with the author string not being valid
		User authorUser = userManager.retrieveUser(author, false);
		
		// Return null if the author is not known
		if (authorUser == null) {
			return null;
		}
		SortedSet<PostedMessage> sortedMessagesForAuthor = sortedMessagesForAuthorByAuthorName.get(authorUser.getName());
		
		// Return null if there are no messages for the author
		if (sortedMessagesForAuthor == null) {
			return null;
		}
		
		// Return the formatted response lines
		return respFormatter.formatMessagesForResponse(new ArrayList<PostedMessage>(sortedMessagesForAuthor), requestReceiptTime);
	}


}
