package edu.uci.opim.core.rule;

import java.util.List;
import java.util.Set;

import edu.uci.opim.core.action.Action;
import edu.uci.opim.core.exception.UnableToExecuteActionException;
import edu.uci.opim.node.NodeClass;
import edu.uci.opim.node.NodeLocation;
import edu.uci.opim.node.Sensor;

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

	public List<Sensor> getDependentSensors() {
		return condition.getHostList();
	}

	public List<NodeClass> getDependentClasses() {
		return condition.getClassList();
	}

	public Set<NodeLocation> getDependentLocations() {
		return condition.getLocList();
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public void executeAction() throws UnableToExecuteActionException {
		action.execute();
	}

}
