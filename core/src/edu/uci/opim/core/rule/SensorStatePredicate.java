package edu.uci.opim.core.rule;

import edu.uci.opim.core.ClassManager;
import edu.uci.opim.core.NodeManager;
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

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		if (!"".equals(host)) {
			this.host = host;
			NodeManager.getInstance().createSensor(host);
		}
	}

	public void setLocation(String location) {
		this.location = new NodeLocation(location);
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
			ClassManager.getInstance().createClass(sensorClass);
		}
	}

	@Override
	public boolean evaluate() {
		// TODO Auto-generated method stub
		return false;
	}

}
