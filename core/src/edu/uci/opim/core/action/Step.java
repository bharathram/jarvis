package edu.uci.opim.core.action;

import edu.uci.opim.core.CoreManager;
import edu.uci.opim.core.exception.UnableToExecuteStepException;
import edu.uci.opim.node.NodeClass;
import edu.uci.opim.node.NodeLocation;
import edu.uci.opim.node.NodeState;

public class Step {
	private NodeLocation location;
	private NodeClass sensorClass;
	private String host;
	private NodeState state;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		if (!"".equals(host)) {
			CoreManager.getNodeManager().createActuator(host);
			this.host = host;
		}
	}

	public void setLocation(String location) {
		this.location = CoreManager.getLocManager().createLocation(location);
	}

	public NodeLocation getLocation() {
		return location;
	}

	public NodeClass getSensorClass() {
		return sensorClass;
	}

	public void setSensorClass(String sensorClass) {
		if (!"".equals(sensorClass)) {
			CoreManager.getClassManager().createClass(sensorClass);
			this.sensorClass = new NodeClass(sensorClass);
		}
	}

	public NodeState getState() {
		return state;
	}

	public void setState(String state) {
		this.state = new NodeState(host, state);
	}

	public void execute() throws UnableToExecuteStepException {
		// TODO Auto-generated method stub

	}

	@Override
	public String toString() {
		return "<do location=" + location + " host= " + host + " class="
				+ sensorClass + ">" + state + "</do>";
	}

}
