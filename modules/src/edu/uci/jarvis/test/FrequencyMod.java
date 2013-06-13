package edu.uci.jarvis.test;

import java.util.Timer;

public class FrequencyMod {
	Integer frequency;
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

	public void setFrequency(Integer frequency) {
		System.out.println("FrequencyMod.setFrequency()" + frequency);
		this.frequency = frequency;
		timer.cancel();
		timer = new Timer();
		timer.schedule(new Task(), firstTime, frequency);
	}

	public Integer getFrequency() {
		System.out.println("FrequencyMod.getFrequency()" + frequency);
		return frequency;
	}

}
