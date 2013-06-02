package edu.uci.opim.node;

import edu.uci.opim.util.StringWrapper;



public class NodeState extends StringWrapper{

	//public final String id;
	//public final String name;

	/**
	 * The node state the node is initialized
	 */
	public static final NodeState INITIAL_STATE = new NodeState(
			"Unitialized");

	public NodeState(String name) {
	//	this.id = ID;
		super(name);
		
	}

/*	@Override
	public String toString() {
		return  name;
	}
*/	

}
