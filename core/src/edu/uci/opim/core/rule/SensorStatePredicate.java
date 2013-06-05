package edu.uci.opim.core.rule;

import edu.uci.opim.core.CoreManager;
import edu.uci.opim.core.StateChangedEvent;
import edu.uci.opim.node.NodeClass;
import edu.uci.opim.node.NodeLocation;

/**
 * 
 * @author bram
 * 
 */
public class SensorStatePredicate extends Predicate {
	NodeLocation location;
	NodeClass sensorClass;
	String host;
	String nodeState;
	String oper;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		if (!"".equals(host)) {
			this.host = host;
			CoreManager.getNodeManager().createSensor(host);
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
			this.sensorClass = new NodeClass(sensorClass);
			CoreManager.getClassManager().createClass(sensorClass);
		}
	}

	public String getNodeState() {
		return nodeState;
	}

	public void setNodeState(String nodeState) {
		this.nodeState = nodeState;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public String getOper() {
		return oper;
	}

	@Override
	public boolean evaluate(StateChangedEvent evt) {
		if (oper.equals("EQ")) {
			if (nodeState.equals(evt.newState)) {
				return true;
			} else {

				return false;
			}
		} else {
			if (nodeState.equals(evt.newState)) {
				return false;
			} else {
				return true;
			}

		}
	}

}
