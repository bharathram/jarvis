package edu.uci.opim.core.rule;

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
		// TODO Auto-generated method stub
		return false;
	}

}
