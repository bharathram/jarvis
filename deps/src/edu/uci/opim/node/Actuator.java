package edu.uci.opim.node;

import edu.uci.jarvis.mod.ActuatorModule;

public class Actuator extends SANode {

	private ActuatorModule mode;

	public void setMode(ActuatorModule sen) {
		this.mode = sen;
	}

	public ActuatorModule getMode() {
		return mode;
	}

}
