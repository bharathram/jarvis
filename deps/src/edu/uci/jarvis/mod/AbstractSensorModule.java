package edu.uci.jarvis.mod;

import java.util.Observable;

import edu.uci.opim.node.NodeState;

abstract public class AbstractSensorModule extends Observable implements
		SensorModule {

	/**
	 * Adds an observer to the set of observers for this object, provided that
	 * it is not the same as some observer already in the set. The order in
	 * which notifications will be delivered to multiple observers is not
	 * specified. See the class comment.
	 * 
	 * @param o
	 *            an observer to be added.
	 * @throws NullPointerException
	 *             if the parameter o is null.
	 */
	public void addObserver(GatewayInterface o) {
		System.out.println("Adding ipserver " + o);
		super.addObserver(o);
	}

	/**
	 * Deletes an observer from the set of observers of this object. Passing
	 * <CODE>null</CODE> to this method will have no effect.
	 * 
	 * @param o
	 *            the observer to be deleted.
	 */
	public void deleteObserver(GatewayInterface o) {
		deleteObserver(o);
	}

	protected void notify(final NodeState n) {
		System.out.println("SensorSimMod.notify()" + n);
		setChanged();
		new Thread(new Runnable() {

			@Override
			public void run() {
				notifyObservers(n);

			}
		}).start();
	}

}
