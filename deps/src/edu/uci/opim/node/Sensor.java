package edu.uci.opim.node;

import edu.uci.jarvis.mod.AbstractSensorModule;

public class Sensor extends SANode {
	private AbstractSensorModule mode;

	public void setMode(AbstractSensorModule sen) {
		this.mode = sen;
	}

	public AbstractSensorModule getMode() {
		return mode;
	}

}
