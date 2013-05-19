package edu.uci.opim.core.rule;

import edu.uci.opim.core.action.Action;
import edu.uci.opim.core.exception.UnableToExecuteActionException;

public class Rule {
	private int priority;
	private String name;
	private Condition condition;
	private Action action;

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	// TODO:Add system state attribute
	public boolean checkCondition() {
		return condition.evaluate();
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public void executeAction() throws UnableToExecuteActionException {
		action.execute();
	}

}
