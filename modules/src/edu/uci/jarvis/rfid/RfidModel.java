package edu.uci.jarvis.rfid;

import edu.uci.jarvis.applet.AbstractNodeModel;

public class RfidModel extends AbstractNodeModel {

	private static RfidModel instance = null;

	public static RfidModel getInstance() {
		if (instance == null) {
			synchronized (RfidModel.class) {
				if (instance == null) {
					instance = new RfidModel();
				}
			}
		}
		return instance;
	}

}
