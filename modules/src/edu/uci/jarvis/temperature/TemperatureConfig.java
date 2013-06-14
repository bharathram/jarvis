package edu.uci.jarvis.temperature;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import edu.uci.jarvis.mod.ActuatorModule;
import edu.uci.jarvis.mod.GatewayInterface;

@PluginImplementation
public class TemperatureConfig implements ActuatorModule {

	@Override
	public Object update(GatewayInterface gateway, Object arg) {
		if (arg instanceof String) {
			String[] split = ((String) arg).split("_");
			if (split.length > 1) {
				TemperatureModel.getInstance().setFrequency(
						Integer.parseInt(split[1]) * 60 * 1000);
			}
		}
		return null;
	}
}
