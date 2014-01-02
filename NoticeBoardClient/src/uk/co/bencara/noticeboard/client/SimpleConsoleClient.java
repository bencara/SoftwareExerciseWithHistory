/**
 * 
 */
package uk.co.bencara.noticeboard.client;

import java.io.Console;
import java.util.List;

import uk.co.bencara.noticeboard.NoticeBoard;
import uk.co.bencara.noticeboard.local.NoticeBoardLocalImpl;

/**
 * A simple client for reading and writing Notice Board requests and responses
 * to the console using the default local notice board implementation.
 * 
 * This implementation allows the user to exit application by entering exit
 * 
 * 
 * @author Les Eckersley
 * 
 */
public class SimpleConsoleClient {
	
	private static final String EXIT_COMMAND = "exit";

	/**
	 * The notice board that should be used to process any requests
	 */
	private NoticeBoard noticeBoard;

	/**
	 * Start a client that uses the console for input and output and an instance
	 * of the default local notice board as its notice board
	 * 
	 * @param args
	 *            , not used
	 */
	public static void main(String[] args) {
		
		//Set up the notice board client
		SimpleConsoleClient noticeBoardClient = new SimpleConsoleClient();
		noticeBoardClient.setNoticeBoard(new NoticeBoardLocalImpl());

		// Begin accepting a processing input
		noticeBoardClient.begin();

	}

	/**
	 * Set the notice board implementation to be used
	 * @param noticeBoard
	 */
	private void setNoticeBoard(NoticeBoard noticeBoard) {
		this.noticeBoard = noticeBoard;
	}

	public void begin() {

		// Retrieve the console
		Console console = System.console();
		
		if (console != null) {
			// TODO implement error condition where environment does not support
			// a system console
		}

		while (true) {
			// Read a line command line from the console
			String noticeBoardInput = console.readLine("\n>");
			
			if (EXIT_COMMAND.equalsIgnoreCase(noticeBoardInput)) {
				break;
			}

			// Send the command to the notice board and record the response
			List<String> response = noticeBoard
					.processRequest(noticeBoardInput);

			// If the response is not null output for the use to view.
			if (response != null) {
				for (String responseLine : response) {
					console.format("\n%1$s", responseLine);
				}
			}
		}
	}

}
