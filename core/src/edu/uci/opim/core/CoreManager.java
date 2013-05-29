package edu.uci.opim.core;

import edu.uci.opim.core.boot.BootManager;

/**
 * Central controller.
 * 
 * @author bram
 * 
 */
public class CoreManager {

	private static CoreManager instance;

	private BootManager bootManager;
	private LocationManager locManager;
	private ClassManager classManager;
	private NodeManager nodeManager;
	private GatewayManager gatewayManager;
	private LogManager eventLogger;

	private CoreManager() {
		// Initialize all the managers
		locManager = new LocationManager();
		classManager = new ClassManager();
		nodeManager = new NodeManager();
		bootManager = new BootManager();
		gatewayManager = new GatewayManager();
		// Registerinf statechange listener
		nodeManager.addObserver(new SensoreStateChangeHandler());

		// Perform the bootup tasks

		bootManager.init();
		bootManager.run();
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
