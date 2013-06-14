package edu.uci.jarvis.seismic;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import edu.uci.jarvis.applet.SensorSimMod;

@PluginImplementation
public class EarthquakeSensor extends SensorSimMod {

	@Override
	public void init() {
		super.init("Earthquake Sensor", new String[] { "Normal",
				"Earthquake high", "Earthquake Low" });
		EarthQuakeModel.getInstance().addSensor(this, true);
	}
}
