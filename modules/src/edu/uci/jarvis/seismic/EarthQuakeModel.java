package edu.uci.jarvis.seismic;

import edu.uci.jarvis.applet.AbstractNodeModel;

public class EarthQuakeModel extends AbstractNodeModel {

	private static EarthQuakeModel instance = null;

	public static EarthQuakeModel getInstance() {
		if (instance == null) {
			synchronized (EarthQuakeModel.class) {
				if (instance == null) {
					instance = new EarthQuakeModel();
				}
			}
		}
		return instance;
	}

}
