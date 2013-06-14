package edu.uci.opim.node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * Abstract sensor actuator node
 * 
 * @author Nisha, Chethana
 * 
 */
public abstract class SANode {
	private String id;
	private String name;
	private NodeClass classes;
	private NodeLocation location;
	private String confPath;
	private List<NodeState> states = new ArrayList<NodeState>();

	/**
	 * Map to lookup human readable messages for sensor states.
	 */
	private Map<NodeState, String> message;
	private boolean isAlive = false;

	public void setLocation(NodeLocation location) {
		this.location = location;
	}

	public NodeLocation getLocation() {
		return location;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public NodeClass getClasses() {

		return classes;
	}

	public void setClasses(NodeClass classes) {
		this.classes = classes;
	}

	public NodeState[] getStates() {
		NodeState[] ret = new NodeState[states.size()];
		states.toArray(ret);
		return ret;
	}

	public void addState(NodeState newState) {
		states.add(newState);

	}

	public void setConfPath(String confPath) {
		this.confPath = confPath;
	}

	public String getConfPath() {
		return confPath;
	}

	public boolean isAlive() {
		return isAlive;

	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	@Override
	public String toString() {
		return "" + classes + "name:" + name + "location" + location + "]";
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SANode) {
			return ((SANode) obj).name.equals(name);
		}
		return false;
	}
}
