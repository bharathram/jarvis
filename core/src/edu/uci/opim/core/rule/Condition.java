package edu.uci.opim.core.rule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.uci.opim.core.ClassManager;
import edu.uci.opim.core.NodeManager;
import edu.uci.opim.node.NodeClass;
import edu.uci.opim.node.Sensor;

public class Condition {
	List<Predicate> predicates;
	List<Sensor> hostList;
	List<NodeClass> classList;

	public Condition() {
		// TODO Auto-generated constructor stub
		predicates = new ArrayList<Predicate>();
		hostList = new ArrayList<Sensor>();
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

			Sensor sensor = NodeManager.getInstance().getSensor(
					((SensorStatePredicate) predicate).host);
			if (sensor != null && !hostList.contains(sensor)) {
				hostList.add(sensor);
			} else {
				NodeClass nodeClass = ClassManager.getInstance().getNodeClass(
						((SensorStatePredicate) predicate).sensorClass.string);
				if (nodeClass != null && !classList.contains(nodeClass)) {
					classList.add(nodeClass);
				}
				// TODO:Location manager
			}
		}
	}

	public List<Sensor> getHostList() {
		return hostList;
	}

	public List<NodeClass> getClassList() {
		return classList;
	}
}
