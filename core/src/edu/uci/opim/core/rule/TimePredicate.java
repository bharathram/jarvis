package edu.uci.opim.core.rule;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.uci.opim.core.StateChangedEvent;

public class TimePredicate extends Predicate {

	long start, end;

	public long getStart() {
		return start;
	}

	public long getEnd() {
		return end;
	}

	public void setStart(String starttime) {
		String[] split = starttime.split(":");
		int hour = Integer.parseInt(split[0]);
		int min = Integer.parseInt(split[1]);
		start = hour * 60 + min;

	}

	public void setEnd(String endtime) {
		String[] split = endtime.split(":");
		int hour = Integer.parseInt(split[0]);
		int min = Integer.parseInt(split[1]);
		end = hour * 60 + min;
	}

	@Override
	public boolean evaluate(StateChangedEvent evt) {
		Date d = new Date();

		String hr = new SimpleDateFormat("HH").format(d);
		String min = new SimpleDateFormat("mm").format(d);
		int currTime = Integer.parseInt(hr) * 60 + Integer.parseInt(min);

		if (end < 12 * 60 || start > 12 * 60) {
			if (currTime < end || currTime > start) {
				return true;
			} else
				return false;
		} else {
			if (currTime < end && currTime > start) {
				return true;
			} else
				return false;
		}
	}
}
