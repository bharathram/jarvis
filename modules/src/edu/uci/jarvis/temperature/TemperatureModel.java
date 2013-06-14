package edu.uci.jarvis.temperature;

import edu.uci.jarvis.applet.AbstractNodeModel;

public class TemperatureModel extends AbstractNodeModel {

	/**
	 * @param args
	 */

	private static TemperatureModel instance = null;

	public static TemperatureModel getInstance() {
		if (instance == null) {
			synchronized (TemperatureModel.class) {
				if (instance == null) {
					instance = new TemperatureModel();
				}
			}
		}
		return instance;
	}

}
