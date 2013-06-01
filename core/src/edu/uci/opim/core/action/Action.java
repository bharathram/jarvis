package edu.uci.opim.core.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.uci.opim.core.exception.UnableToExecuteActionException;
import edu.uci.opim.core.exception.UnableToExecuteStepException;

public class Action {
	protected final List<Step> list = new ArrayList<Step>();

	public void add(Step step) {
		list.add(step);
	}

	public void execute() throws UnableToExecuteActionException {
		UnableToExecuteActionException actionException = null;
		Iterator<Step> iterator = list.iterator();
		while (iterator.hasNext()) {
			try {
				Step step = (Step) iterator.next();
				step.execute();
			} catch (UnableToExecuteStepException e) {
				if (actionException == null) {
					actionException = new UnableToExecuteActionException();
				}
				actionException.add(e);
			}
		}
		if (actionException != null) {
			throw actionException;
		}
	}
}
