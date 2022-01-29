package com.nastyabagdasarova.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.nastyabagdasarova.Interface.IParseTimeStamp;

public class ParseTimeStampComponent implements IParseTimeStamp {

	@Override
	public Date parseTimestamp(String timestamp) {
    	final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    try {
	    	return new Timestamp(DATE_TIME_FORMAT.parse(timestamp).getTime());
	    } catch (ParseException e) {
	        throw new IllegalArgumentException(e);
	    }
	}

	@Override
	public Date parseDatestamp(String timestamp) {
    	final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	    try {
	    	return new Timestamp(DATE_TIME_FORMAT.parse(timestamp).getTime());
	    } catch (ParseException e) {
	        throw new IllegalArgumentException(e);
	    }
	}

}
