package edu.uci.opim.core;

import edu.uci.opim.node.NodeState;
import edu.uci.opim.node.Sensor;

/**
 * Object to ecapsulate the state change due to a stimulus received from the
 * sensor.
 * 
 * @author bram
 * 
 */
public class StateChangedEvent {
	public final Sensor sensor;
	public final NodeState oldState, newState;

	public StateChangedEvent(Sensor sensor, NodeState oldState,
			NodeState newState) {
		this.sensor = sensor;
		this.oldState = oldState;
		this.newState = newState;
	}
}
