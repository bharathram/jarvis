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
	private LoaderUtils() {
		// Should not be invoked this is a Utility class
	}

	private static PluginManager pm = PluginManagerFactory
			.createPluginManager();

	public static ActuatorModule getActuatorModule(String jarPath) {
		pm.addPluginsFrom(new File(jarPath).toURI());
		return pm.getPlugin(ActuatorModule.class);
	}

	public static SensorModule getSensorModule(String jarPath) {
		File file = new File(jarPath);
		System.out.println("Attempting to load file at " + file.getPath());
		pm.addPluginsFrom(file.toURI());
		SensorModule plugin = pm.getPlugin(SensorModule.class);
		if (plugin == null) {
			throw new RuntimeException("No plugin found at " + jarPath);
		}
		return plugin;
	}
}
