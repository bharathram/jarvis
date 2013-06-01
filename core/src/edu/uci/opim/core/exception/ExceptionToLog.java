package edu.uci.opim.core.exception;

import java.text.DateFormat;
import java.util.Date;

public class ExceptionToLog {

	public final String message;
	public final Object obj;
	public final Date time;
	public final Priority pr;

	public ExceptionToLog(String message, Object obj, Priority priority) {
		this.message = message;
		this.obj = obj;
		pr = priority;
		time = new Date();
	}

	@Override
	public String toString() {
		return DateFormat.getDateTimeInstance().format(time) + ":" + message
				+ "[" + obj.toString() + "]";
	}

}
