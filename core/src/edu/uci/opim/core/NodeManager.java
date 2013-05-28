package edu.uci.opim.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import edu.uci.opim.core.exception.ExceptionToLog;
import edu.uci.opim.core.exception.Priority;
import edu.uci.opim.core.rule.Rule;
import edu.uci.opim.core.web.GatewayNode;
import edu.uci.opim.node.Actuator;
import edu.uci.opim.node.SANode;
import edu.uci.opim.node.Sensor;

public class NodeManager {
	private static final Logger logger = Logger.getLogger(CoreManager.class);
	/**
	 * Maps the sensor to the rules that it is associated with
	 */
	private Map<Sensor, List<Rule>> ruleGrid = new HashMap<Sensor, List<Rule>>();

	/**
	 * Map of all known nodes.(Nodes that have atleast one rule associated with
	 * them.
	 */
	private Map<String, SANode> knownNodeMap;

	/**
	 * List of nodes that have checked in with the core.
	 */
	private Map<SANode, GatewayNode> aliveNodes;

	NodeManager() {
		knownNodeMap = new HashMap<String, SANode>();
	}

	public Sensor createSensor(String name) {
		SANode sensor = getNode(name);
		if (sensor == null) {
			logger.info("Creating sensor " + name);
			sensor = new Sensor();
			sensor.setName(name);
			knownNodeMap.put(name, sensor);
		}
		return (Sensor) sensor;
	}

	public Actuator createActuator(String name) {
		SANode actuator = getNode(name);
		if (actuator == null) {
			logger.info("Creating Actuator " + name);
			actuator = new Actuator();
			actuator.setName(name);
			knownNodeMap.put(name, actuator);
		}
		return (Actuator) actuator;
	}

	public Sensor getSensor(String name) {
		SANode saNode = knownNodeMap.get(name);
		if (saNode instanceof Sensor) {
			return (Sensor) saNode;
		}
		return null;
	}

	public Actuator getActuator(String name) {
		SANode saNode = knownNodeMap.get(name);
		if (saNode instanceof Sensor) {
			return (Actuator) saNode;
		}
		return null;
	}

	public SANode getNode(String name) {
		return knownNodeMap.get(name);
	}

	public void addRule(Sensor sensor, Rule rule) {
		List<Rule> ruleList = ruleGrid.get(sensor.getName());
		if (ruleList == null) {
			ruleList = new ArrayList<Rule>();
			ruleGrid.put(sensor, ruleList);
		}
		ruleList.add(rule);
	}

	public void registerNode(SANode node, GatewayNode gateway) {
		if (!knownNodeMap.containsKey(node.getName())) {
			if (node instanceof Sensor) {
				CoreManager.getLogManager().logEvent(
						new ExceptionToLog("Sensor has no rule ", node
								.getName(), Priority.WARN));
			} else {
				CoreManager.getLogManager().logEvent(
						new ExceptionToLog("Actuator has no action ", node
								.getName(), Priority.WARN));

			}
		} else {
			aliveNodes.put(node, gateway);
		}
	}
}
