package edu.uci.opim.core;

import java.util.Map.Entry;
import java.util.Set;

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
	public final Set<Entry<Sensor, NodeState>> sysState;

	public StateChangedEvent(Sensor sensor, NodeState oldState,
			NodeState newState, Set<Entry<Sensor, NodeState>> sysState) {
		this.sensor = sensor;
		this.oldState = oldState;
		this.newState = newState;
		this.sysState = sysState;
	}
}
