package edu.uci.opim.util;

import java.io.File;

import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import edu.uci.jarvis.mod.ActuatorModule;
import edu.uci.jarvis.mod.SensorModule;

/**
 * Utility methods to load jar files
 * 
 * @author bram
 * 
 */
public class LoaderUtils {
	private static PluginManager pm = PluginManagerFactory
			.createPluginManager();

	public static ActuatorModule getActuatorModule(String jarPath) {
		pm.addPluginsFrom(new File(jarPath).toURI());
		return pm.getPlugin(ActuatorModule.class);
	}

	public static SensorModule getSensorModule(String jarPath) {
		pm.addPluginsFrom(new File(jarPath).toURI());
		return pm.getPlugin(SensorModule.class);
	}
}
