package uk.co.bencara.noticeboard;

import java.util.List;

/**
 * Interface describing the behaviour of a notice board
 * @author Les Eckersley
 *
 */
public interface NoticeBoard {
	
	
	/**
	 * A method to make a request of the notice board.
	 * 
	 * @param commandString
	 * @return a list of string messages appropriate to the input request.  May be null or empty.
	 */
	public List<String> processRequest(String commandString);

}
