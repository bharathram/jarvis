package edu.uci.opim.node;

import edu.uci.opim.util.StringWrapper;

public class NodeClass extends StringWrapper {
	public static final NodeClass EMPTY = new NodeClass("");

	private static final long serialVersionUID = 1L;

	public NodeClass(String className) {
		super(className);
	}

	public NodeClass() {
		super("");
	}
}
