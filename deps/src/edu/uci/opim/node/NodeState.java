package edu.uci.opim.node;

public class NodeState {

	public final String id;
	public final String name;

	/**
	 * The node state the node is initialized
	 */
	public static final NodeState INITIAL_STATE = new NodeState("",
			"Unitialized");

	public NodeState(String ID, String stateName) {
		this.id = ID;
		this.name = stateName;
	}

	@Override
	public String toString() {
		return id + "->" + name;
	}
}
