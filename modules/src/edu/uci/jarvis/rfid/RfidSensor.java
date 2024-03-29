package edu.uci.jarvis.rfid;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import edu.uci.jarvis.applet.SensorSimMod;

@PluginImplementation
public class RfidSensor extends SensorSimMod {

	@Override
	public void init() {
		super.init("Rfid Sensor", new String[] { "Student In", "Student Out",
				"Professor In", "Professor Out" });
		RfidModel.getInstance().addSensor(this, false);

	}
}
