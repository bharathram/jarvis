package edu.uci.opim.core.exception;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UnableToExecuteActionException extends Exception implements
		Iterable<UnableToExecuteStepException> {
	private List<UnableToExecuteStepException> list = new ArrayList<UnableToExecuteStepException>();

	public void add(UnableToExecuteStepException exception) {
		list.add(exception);
	}

	@Override
	public Iterator<UnableToExecuteStepException> iterator() {
		return list.iterator();
	}

}
