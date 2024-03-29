package edu.uci.jarvis.temperature;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import edu.uci.jarvis.applet.SensorSimMod;

@PluginImplementation
public class TemperatureSensor extends SensorSimMod {

	/**
	 * @param args
	 */

	public void init() {
		super.init("Temperature Sensor", new String[] { "Temperature warm",
				"Temperature hot", "Temperature cold" });
		TemperatureModel.getInstance().addSensor(this, true);

	}
}