package edu.uci.jarvis.seismic;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import edu.uci.jarvis.applet.SensorSimMod;

@PluginImplementation
public class EarthquakeSensor extends SensorSimMod {

	/**
	 * @param args
	 */

	public EarthquakeSensor() {
		super("Earthquake Sensor", new String[] { "Earthquake high",
				"Earthquake Low" });
		EarthQuakeModel.getInstance().addSensor(this, true);
	}
}
