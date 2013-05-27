package edu.uci.opim.core.exception;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UnableToExecuteActionException extends RuntimeException implements
		Iterable<RuntimeException> {
	private List<RuntimeException> list = new ArrayList<RuntimeException>();

	public void add(RuntimeException exception) {
		list.add(exception);
	}

	@Override
	public Iterator<RuntimeException> iterator() {
		return list.iterator();
	}

}
