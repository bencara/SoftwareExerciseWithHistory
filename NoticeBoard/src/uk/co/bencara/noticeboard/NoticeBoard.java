package uk.co.bencara.noticeboard;

import java.util.List;

/**
 * Interface describing the behaviour of a notice board
 * @author Les Eckersley
 *
 */
public interface NoticeBoard {
	
	public static final String postCommand = "->";
	
	public static final String followCommand = "follows";
	
	public static final String wallCommand = "wall";
	
	
	/**
	 * A method to make a request of the notice board.
	 * 
	 * @param commandString
	 * @return a list of string messages approriate to the input request.
	 */
	public List<String> makeRequest(String commandString);

}
