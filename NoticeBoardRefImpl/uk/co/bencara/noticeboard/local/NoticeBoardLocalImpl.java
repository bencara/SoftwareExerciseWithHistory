/**
 * 
 */
package uk.co.bencara.noticeboard.local;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import uk.co.bencara.noticeboard.NoticeBoard;
import uk.co.bencara.noticeboard.model.PostedMessage;
import uk.co.bencara.noticeboard.model.User;

/**
 * A class to coordinate the processing of the Notice board requests.
 * 
 * © Bencara Systems Ltd
 * 
 * @author Les Eckersley
 * 
 */
public class NoticeBoardLocalImpl implements NoticeBoard {

	private CommandInterpreter interpreter = new CommandInterpreter();

	private UserManager userManager = new UserManager();

	private ResponseFormatter respFormatter = new ResponseFormatter();

	private Map<String, SortedSet<PostedMessage>> sortedMessagesForAuthorByAuthorName = new HashMap<String, SortedSet<PostedMessage>>();

	/**
	 * An implementation of the declared processRequest NoticeBoard method.
	 * 
	 * @see co.uk.bencara.noticeboard.NoticeBoard#processRequest(java.lang.String)
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

		// Set up the response holder
		List<String> response = null;

		// Call the appropriate method based on the command type of the request
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
			// TODO behaviour when the command is not an uninterpretable type
			break;
		}

		return response;

	}

	/**
	 * A method to perform the functionality associated with the wall command
	 * which displays and post time ordered list of formatted messages for the
	 * source user and any users that they have requested to follow.
	 * 
	 * @param sourceUserName
	 *            the source of the follow request
	 * @return the a post time ordered list of the formatted messages posted by
	 *         the source user and the users that they follow, if the request
	 *         could not be processed, error lines to be displayed to the user
	 *         will be returned (TBC)
	 */
	private List<String> getMessageWall(String sourceUserName,
			long requestReceiptTime) {
		// Get the user for the request source user creating one if necessary
		// TODO defend against illegal argument if the user name is invalid
		User sourceUser = userManager.retrieveUser(sourceUserName, true);

		// Create the set to hold and order the messages
		Set<PostedMessage> naturalOrderedSetOfWallMessages = new TreeSet<PostedMessage>();


		// Create a list of all the users whose messages should be in the wall
		// this includes the source user
		Set<User> allAuthorsForWall = new HashSet<User>(sourceUser.getFollowedAuthors());
		allAuthorsForWall.add(sourceUser);
		
		// Add all the messages for the followed authors
		for (User followedAuthor : allAuthorsForWall) {
			Set<PostedMessage> followedAuthorsMessages = sortedMessagesForAuthorByAuthorName
					.get(followedAuthor.getName());
			if (followedAuthorsMessages != null && followedAuthorsMessages.size() > 0 ) {
				naturalOrderedSetOfWallMessages
				.addAll(followedAuthorsMessages);
			}
			
		}
		
		// Convert the messages into response strings
		List<String> responseLines = respFormatter.formatMessagesForResponse(
				new ArrayList<PostedMessage>(naturalOrderedSetOfWallMessages),requestReceiptTime);
				
		// Return the response strings
		return responseLines;
	}

	/**
	 * A method to perform the functionality associated with setting a user to
	 * follow another user.
	 * 
	 * @param sourceUserName
	 *            the source of the follow request
	 * @param targetUserName
	 *            the user who the source user wants to follow
	 * @return error lines to be displayed to the user cannot follow the
	 *         specified user (TBC)
	 */
	private List<String> followUser(String sourceUserName, String targetUserName) {
		// Get the user for the request source user creating one if necessary
		// TODO defend against illegal argument if the user name is invalid
		User sourceUser = userManager.retrieveUser(sourceUserName, true);

		User targetUser = userManager.retrieveUser(targetUserName, false);

		// Check that the target user exists
		if (targetUser == null) {
			// TODO behaviour when the target user is not known to the system
		}

		sourceUser.addFollowedAuthor(targetUser);

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
	 *         be completed (TBC)
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
			sortedMessagesForAuthorByAuthorName.put(authorUser.getName(),
					messagesForAuthorByPostTime);
		}

		// Create a new message base on the arguements
		// TODO deal with illegal argument if the text is trivial
		int nextMessageNumber = messagesForAuthorByPostTime.size() + 1;
		PostedMessage newMessage = new PostedMessage(authorUser, message,
				requestReceiptTime, nextMessageNumber);

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
	 *         messages have been posted by the specified user. If the request
	 *         could not be processed, error lines to be displayed to the user
	 *         will be returned (TBC)
	 */
	private List<String> readMessages(String author, long requestReceiptTime) {
		// TODO deal with the author string not being valid
		User authorUser = userManager.retrieveUser(author, false);

		// Return null if the author is not known
		if (authorUser == null) {
			return null;
		}
		SortedSet<PostedMessage> sortedMessagesForAuthor = sortedMessagesForAuthorByAuthorName
				.get(authorUser.getName());

		// Return null if there are no messages for the author
		if (sortedMessagesForAuthor == null) {
			return null;
		}

		// Return the formatted response lines
		return respFormatter.formatMessagesForResponse(
				new ArrayList<PostedMessage>(sortedMessagesForAuthor),
				requestReceiptTime);
	}

}
