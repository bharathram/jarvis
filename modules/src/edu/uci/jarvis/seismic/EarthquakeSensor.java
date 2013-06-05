package edu.uci.jarvis.seismic;

import edu.uci.jarvis.applet.SensorSimMod;

public class EarthquakeSensor extends SensorSimMod {

	/**
	 * @param args
	 */

	public EarthquakeSensor() {
		// TODO Auto-generated constructor stub
		super("Earthquake Sensor", new String[] { "Earthquake high",
				"Earthquake Low" });
	}

	public static void main(String[] args) {
		EarthquakeSensor sen = new EarthquakeSensor();

	}

}
