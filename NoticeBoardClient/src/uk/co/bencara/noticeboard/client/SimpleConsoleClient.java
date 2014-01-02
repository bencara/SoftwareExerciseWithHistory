/**
 * 
 */
package uk.co.bencara.noticeboard.client;

import java.io.Console;
import java.util.List;

import uk.co.bencara.noticeboard.NoticeBoard;

/**
 * A simple client for reading and writing Notice Board requests and responses
 * to the console using the default local notice board implementation.
 * 
 * 
 * @author Les Eckersley
 * 
 */
public class SimpleConsoleClient {

	private NoticeBoard noticeBoard;

	public static void main(String[] args) {
		SimpleConsoleClient noticeBoardClient = new SimpleConsoleClient();
		
		noticeBoardClient.begin();
		
		
		
	}

	public NoticeBoard getNoticeBoard() {
		return noticeBoard;
	}

	private void setNoticeBoard(NoticeBoard noticeBoard) {
		this.noticeBoard = noticeBoard;
	}
	
	public void begin(){
		
		Console console = System.console();
		if (console != null) {
			// TODO implement error condition where environment does not support a system console
		}

		while (true) {
			// Read a line command line from the console
			String noticeBoardInput = console.readLine("\n>");
			
			// Send the command to the notice board and record the response
			List<String> response = noticeBoard.makeRequest(noticeBoardInput);
			
			// If the response is not null output for the use to view.
			if (response != null) {
				for (String responseLine : response) {
					console.format("\n%1$s", responseLine);
				}
			}
		}
	}
	
	

}
