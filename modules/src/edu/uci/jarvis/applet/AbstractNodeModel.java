package edu.uci.jarvis.applet;

import java.util.Timer;

public class AbstractNodeModel {
	private static long DEFAULT = 5 * 60 * 1000;

	long frequency;
	Timer timer;
	SensorSimMod sensorMod;

	public void setFrequency(long frequency) {
		if (sensorMod != null) {
			sensorMod.updateLabel("" + (frequency / 60 / 1000) + " mins");
			System.out.println("FrequencyMod.setFrequency()" + frequency);
			this.frequency = frequency;
			if (timer != null) {
				timer.cancel();
			}
			timer = new Timer();
			timer.schedule(new NotificationTask(sensorMod), frequency,
					frequency);
		}
	}

	public long getFrequency() {
		System.out.println("FrequencyMod.getFrequency()" + frequency);
		return frequency;
	}

	public synchronized void addSensor(SensorSimMod mod, boolean tostart) {
		this.sensorMod = mod;
		if (tostart) {
			setFrequency(DEFAULT);
		}

	}

}
