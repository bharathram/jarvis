package edu.uci.opim.core;

import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import edu.uci.opim.core.rule.Rule;
import edu.uci.opim.node.NodeClass;

public class SensorStateChangeHandler implements Observer {

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof StateChangedEvent) {
			Set<Rule> ruleSet = new HashSet<Rule>();
			// List of rules to process for a node
			StateChangedEvent evt = (StateChangedEvent) arg;
			List<Rule> ruleList = CoreManager.getNodeManager().getRuleList(
					evt.sensor);
			ruleSet.addAll(ruleList);
			// List of rules to process for a class
			NodeClass[] classes = evt.sensor.getClasses();
			for (NodeClass nodeClass : classes) {
				ruleList = CoreManager.getClassManager().getRuleList(nodeClass);
				ruleSet.addAll(ruleList);
			}

			// List of rules to process for the sensor location
			ruleList = CoreManager.getLocManager().getRuleList(
					evt.sensor.getLocation());
			ruleSet.addAll(ruleList);

			// Process the rules
			for (Rule rule : ruleSet) {
				if (rule.checkCondition((StateChangedEvent) arg)) {
					rule.executeAction();
				}
			}
		}
	}
}
