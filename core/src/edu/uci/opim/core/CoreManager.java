package edu.uci.opim.core;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import edu.uci.opim.core.boot.CoreConfig;

/**
 * Central controller.
 * 
 * @author bram
 * 
 */
public class CoreManager {

	private static CoreManager instance;

	private LocationManager locManager;
	private ClassManager classManager;
	private NodeManager nodeManager;
	private GatewayManager gatewayManager;
	private LogManager eventLogger;
	public CoreConfig config;

	private CoreManager() {
		// Initialize all the managers
		Properties prop = new Properties();
		try {
			InputStream in = new FileInputStream("./conf/core.properties");
			prop.load(in);
			config = new CoreConfig(prop);

			locManager = new LocationManager();
			classManager = new ClassManager();
			nodeManager = new NodeManager();
			gatewayManager = new GatewayManager();
			eventLogger = new LogManager();
			// Registerinf statechange listener
			nodeManager.addObserver(new SensorStateChangeHandler());
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

	}

	public static CoreManager getInstance() {
		if (instance == null) {
			synchronized (CoreManager.class) {
				if (instance == null) {
					instance = new CoreManager();
				}
			}
		}
		return instance;
	}

	public static LocationManager getLocManager() {
		return getInstance().locManager;
	}

	public static NodeManager getNodeManager() {
		return getInstance().nodeManager;
	}

	public static ClassManager getClassManager() {
		return getInstance().classManager;
	}

	public static GatewayManager getGatewayManager() {
		return getInstance().gatewayManager;
	}

	public static LogManager getLogManager() {
		return getInstance().eventLogger;
	}

}
