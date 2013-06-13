package edu.uci.jarvis.test;

import java.util.Timer;
import java.util.TimerTask;

public class FrequencyMod {
	long frequency;
	Timer timer;
	private static FrequencyMod instance = null;

	public FrequencyMod() {
		// TODO Auto-generated constructor stub
		System.out.println("FrequencyMod.FrequencyMod()");
	}

	public static FrequencyMod getInstance() {
		if (instance == null) {
			synchronized (FrequencyMod.class) {
				if (instance == null) {
					instance = new FrequencyMod();
				}
			}
		}
		return instance;
	}

	public void setFrequency(long frequency) {
		System.out.println("FrequencyMod.setFrequency()" + frequency);
		this.frequency = frequency;
		timer.cancel();
		timer = new Timer();
		timer.schedule(new NotificationTask(), frequency, frequency);
	}

	public long getFrequency() {
		System.out.println("FrequencyMod.getFrequency()" + frequency);
		return frequency;
	}

	static class NotificationTask extends TimerTask {

		@Override
		public void run() {
			// TODO Auto-generated method stub

		}

	}

}
