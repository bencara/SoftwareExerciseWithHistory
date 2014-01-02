/**
 * 
 */
package uk.co.bencara.noticeboard.local;

import java.util.ArrayList;
import java.util.List;

import uk.co.bencara.noticeboard.model.PostedMessage;


/**
 * A utility class to provide formatting for the response to be returned to the
 * client.
 * 
 * © Bencara Systems Ltd
 * 
 * @author Les Eckersley
 * 
 */
public class ResponseFormatter {

	private static final int millisInSecond = 1000;
	private static final int millisInMinute = 1000 * 60;
	private static final int millisInHour = 1000 * 60 * 60;
	private static final int millisInDay = 1000 * 60 * 60 * 24;

	/**
	 * 
	 */
	public ResponseFormatter() {
	}

	/**
	 * A method to convert and list of posted messages into an list of response
	 * text lines The order of the list will be maintained. If a null list is
	 * passed a null list will be returned. If an empty list is passed an empty
	 * list will be returned
	 * 
	 * For each passed message a response line in the format "UserName" -
	 * "MessageText" ("ElapsedTime") will be returned.
	 * 
	 * The elapsed time will be the number of the largest of days, hours,
	 * minutes or second for with at least one period has elapsed
	 * 
	 * @param messages
	 *            the list of messages to be converted to response text lines,
	 *            may be null or empty
	 * @param currentTime
	 *            the time use to determine the age of the passed messages
	 * @return a list populated with response text lines corresponding to the
	 *         passed messages.
	 */
	public List<String> formatMessagesForResponse(List<PostedMessage> messages,
			long currentTime) {
		// Return null if no messages are passed.
		if (messages == null) {
			return null;
		}
		ArrayList<String> responseLines = new ArrayList<String>();
		for (PostedMessage postedMessage : messages) {
			long timeSincePosting = currentTime - postedMessage.getPostTime();
			assert timeSincePosting >= 0: "Cannot have a negative elapsed time";
			String elapsedTimeString = convertElapsedTime(timeSincePosting);
			String responseLine = String.format("%1$s - %2$s (%3$s)",
					postedMessage.getAuthor().getName(),
					postedMessage.getText(), elapsedTimeString);
			responseLines.add(responseLine);
		}

		return responseLines;

	}

	private String convertElapsedTime(long elapsedMilliseconds) {

		String pluralString = "";
		String periodString = null;
		long elapsedPeriods = 0;
		if ((elapsedPeriods = elapsedMilliseconds / millisInDay) > 0) {
			periodString = "day";
		} else if ((elapsedPeriods = elapsedMilliseconds / millisInHour) > 0) {
			periodString = "hour";
		} else if ((elapsedPeriods = elapsedMilliseconds / millisInMinute) > 0) {
			periodString = "minute";
		} else {
			elapsedPeriods = elapsedMilliseconds / millisInSecond;
			periodString = "second";
			// Potentially since seconds are the smallest unit we could have 0 seconds
			if (elapsedPeriods == 0) {
				pluralString = "s";
			}
		}

		if (elapsedPeriods > 1 ) {
			pluralString = "s";
		}

		return String.format("%1$d %2$s%3$s ago", elapsedPeriods, periodString,
				pluralString);

	}

}
