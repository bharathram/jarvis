package edu.uci.opim.node;

public class NodeClass {
	public final String name;

	public NodeClass(String className) {
		this.name = className;
	}

	@Override
	public String toString() {

		return name;
	}
}
