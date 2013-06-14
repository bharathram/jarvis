package edu.uci.opim.node;

import edu.uci.opim.util.StringWrapper;

public class NodeLocation extends StringWrapper {

	public static final NodeLocation EMPTY = new NodeLocation("");
	private static final long serialVersionUID = 1L;

	public NodeLocation(String className) {
		super(className);
	}

	public NodeLocation() {
		super("");
	}
}
