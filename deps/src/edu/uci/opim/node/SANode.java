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
	private List<NodeClass> classes = new ArrayList<NodeClass>();
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

	public NodeClass[] getClasses() {
		NodeClass[] ret = new NodeClass[classes.size()];
		classes.toArray(ret);
		return ret;
	}

	public void addClass(NodeClass newClass) {
		classes.add(newClass);

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
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SANode) {
			return ((SANode) obj).name.equals(name);
		}
		return false;
	}
}
