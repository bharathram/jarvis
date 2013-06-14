package edu.uci.jarvis.light;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import edu.uci.jarvis.applet.SensorSimMod;

@PluginImplementation
public class LightSensor extends SensorSimMod {

	/**
	 * @param args
	 */

	@Override
	public void init() {
		super.init("Light Sensor", new String[] { "Light on", "Light off" });
		LightModel.getInstance().addSensor(this, true);

	}
}
