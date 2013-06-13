package edu.uci.jarvis.applet;

import java.util.TimerTask;

class NotificationTask extends TimerTask {
	private SensorSimMod sensorMod;

	public NotificationTask(SensorSimMod mod) {
		this.sensorMod = mod;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		if (sensorMod != null) {
			sensorMod.timeTrigger();
		}
	}
}