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

	public static ActuatorModule getActuatorModule(String jarPath) {
		PluginManager pm = PluginManagerFactory.createPluginManager();
		pm.addPluginsFrom(new File(jarPath).toURI());
		ActuatorModule plugin = pm.getPlugin(ActuatorModule.class);
		System.out.println("Plugin class " + plugin);

		return plugin;
	}

	public static SensorModule getSensorModule(String jarPath) {
		PluginManager pm = PluginManagerFactory.createPluginManager();
		File file = new File(jarPath);
		System.out.println("Attempting to load file at " + file.getPath());
		pm.addPluginsFrom(file.toURI());
		SensorModule plugin = pm.getPlugin(SensorModule.class);
		if (plugin == null) {
			throw new RuntimeException("No plugin found at " + jarPath);
		}
		plugin.init();
		return plugin;
	}
}
