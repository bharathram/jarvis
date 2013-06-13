package edu.uci.opim.core;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import edu.uci.opim.core.exception.ExceptionToLog;
import edu.uci.opim.core.exception.Priority;
import edu.uci.opim.core.exception.UnableToExecuteActionException;
import edu.uci.opim.core.exception.UnableToExecuteStepException;
import edu.uci.opim.core.rule.Condition;
import edu.uci.opim.core.rule.Rule;
import edu.uci.opim.node.NodeClass;

public class SensorStateChangeHandler implements Observer {

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof StateChangedEvent) {
			StateChangedEvent evt = (StateChangedEvent) arg;
			Set<Condition> whiteruleSet = new HashSet<Condition>();
			List<Condition> whiteruleList = CoreManager.getNodeManager()
					.getWhiteRuleList(evt.sensor);
			whiteruleSet.addAll(whiteruleList);

			NodeClass[] whiteclasses = evt.sensor.getClasses();
			for (NodeClass nodeClass : whiteclasses) {
				whiteruleList = CoreManager.getClassManager().getWhiteRuleList(
						nodeClass);
				whiteruleSet.addAll(whiteruleList);
			}

			whiteruleList = CoreManager.getLocManager().getWhiteRuleList(
					evt.sensor.getLocation());
			whiteruleSet.addAll(whiteruleList);

			for (Condition whiterule : whiteruleSet) {
				if (whiterule.evaluate((StateChangedEvent) arg)) {
					return;
				}
			}
			Set<Rule> ruleSet = new HashSet<Rule>();
			// List of rules to process for a node

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
					try {
						rule.executeAction();
					} catch (UnableToExecuteActionException e) {
						Iterator<UnableToExecuteStepException> iterator = e
								.iterator();
						while (iterator.hasNext()) {
							UnableToExecuteStepException exception = iterator
									.next();
							CoreManager.getLogManager().logEvent(
									new ExceptionToLog(exception.getMessage()
											+ exception.step.toString(), rule,
											Priority.FATAL));
						}
					}
				}
			}
		}
	}
}
