package edu.uci.opim.core;

import edu.uci.opim.core.boot.BootManager;

public class CoreManager {

	private static CoreManager instance;

	BootManager bootManager;
	LocationManager locManager;
	ClassManager classManager;
	NodeManager nodeManager;

	private CoreManager() {
		bootManager = new BootManager();

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

}
