package edu.uci.jarvis.seismic;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import edu.uci.jarvis.mod.ActuatorModule;
import edu.uci.jarvis.mod.GatewayInterface;

@PluginImplementation
public class EarthQuakeConfig implements ActuatorModule {

	@Override
	public Object update(GatewayInterface gateway, Object arg) {
		if (arg instanceof String) {
			String[] split = ((String) arg).split("_");
			if (split.length > 1) {
				EarthQuakeModel.getInstance().setFrequency(
						Integer.parseInt(split[1]) * 60 * 1000);
			}
		}
		return null;
	}
}
