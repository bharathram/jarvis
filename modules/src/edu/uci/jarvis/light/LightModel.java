package edu.uci.jarvis.light;

import edu.uci.jarvis.applet.AbstractNodeModel;

public class LightModel extends AbstractNodeModel {

	private static LightModel instance = null;

	public static LightModel getInstance() {
		if (instance == null) {
			synchronized (LightModel.class) {
				if (instance == null) {
					instance = new LightModel();
				}
			}
		}
		return instance;
	}

}