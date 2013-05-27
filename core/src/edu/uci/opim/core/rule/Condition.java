package edu.uci.opim.core.rule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import edu.uci.opim.core.CoreManager;
import edu.uci.opim.node.NodeClass;
import edu.uci.opim.node.NodeLocation;
import edu.uci.opim.node.Sensor;

public class Condition {
	List<Predicate> predicates;
	List<Sensor> hostList;
	List<NodeClass> classList;
	Set<NodeLocation> locList;

	public Condition() {
		predicates = new ArrayList<Predicate>();
		hostList = new ArrayList<Sensor>();
		locList = new HashSet<NodeLocation>();
	}

	public boolean evaluate() {
		boolean cond = false;
		Iterator<Predicate> iterator = predicates.iterator();
		// TODO:Handle braces
		while (iterator.hasNext()) {
			Predicate predicate = (Predicate) iterator.next();
			cond = Predicate.evaluate(cond, predicate);
		}
		return cond;
	}

	public void addPredicate(Predicate predicate) {
		predicates.add(predicate);
		if (predicate instanceof SensorStatePredicate) {

			Sensor sensor = CoreManager.getNodeManager().getSensor(
					((SensorStatePredicate) predicate).host);
			if (sensor != null && !hostList.contains(sensor)) {
				hostList.add(sensor);
			} else {
				NodeClass nodeClass = CoreManager
						.getClassManager()
						.getNodeClass(
								((SensorStatePredicate) predicate).sensorClass.string);
				if (nodeClass != null && !classList.contains(nodeClass)) {
					classList.add(nodeClass);
				}
				locList.add(((SensorStatePredicate) predicate).location);
			}
		}
	}

	public List<Sensor> getHostList() {
		return hostList;
	}

	public List<NodeClass> getClassList() {
		return classList;
	}

	public Set<NodeLocation> getLocList() {
		return locList;
	}
}
