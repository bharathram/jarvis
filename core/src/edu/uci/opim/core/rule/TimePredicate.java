package edu.uci.opim.core.rule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
		String[] split1 = SimpleDateFormat.getTimeInstance(DateFormat.SHORT)
				.toString().split(":");
		int currTime = Integer.parseInt(split1[0]) * 60
				+ Integer.parseInt(split1[1]);

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
