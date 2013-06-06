package edu.uci.jarvis.temperature;

import edu.uci.jarvis.applet.SensorSimMod;

public class TemperatureSensor extends SensorSimMod {

	/**
	 * @param args
	 */

	public TemperatureSensor() {
		// TODO Auto-generated constructor stub
		super("Temperature Sensor", new String[] { "Temperature warm",
				"Temperature hot", "Temperature cold" });
	}

	public static void main(String[] args) {
		TemperatureSensor sen = new TemperatureSensor();

	}

}
