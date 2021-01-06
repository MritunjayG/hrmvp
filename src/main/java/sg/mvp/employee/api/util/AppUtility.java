package sg.mvp.employee.api.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AppUtility {

	private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private final SimpleDateFormat DATE_FORMAT_A = new SimpleDateFormat("dd-MMM-yy");

	private final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public java.sql.Date parseDate(String date) {
		try {
			if (date.length() == 10)
				return new Date(DATE_FORMAT.parse(date).getTime());
			else if (date.length() == 9)
				return new Date(DATE_FORMAT_A.parse(date).getTime());
			else
				return null;

		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

}
