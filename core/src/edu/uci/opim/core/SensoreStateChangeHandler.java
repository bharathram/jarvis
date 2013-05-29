package edu.uci.opim.core;

import java.util.Observable;
import java.util.Observer;

public class SensoreStateChangeHandler implements Observer {

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof StateChangedEvent) {

		}

	}
}
