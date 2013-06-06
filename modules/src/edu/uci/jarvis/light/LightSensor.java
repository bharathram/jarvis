package edu.uci.jarvis.light;

import edu.uci.jarvis.applet.SensorSimMod;

public class LightSensor extends SensorSimMod {

	/**
	 * @param args
	 */

	public LightSensor() {
		// TODO Auto-generated constructor stub
		super("Earthquake Sensor", new String[] { "Light on", "Light off" });
	}

	public static void main(String[] args) {
		LightSensor sen = new LightSensor();

	}

}
