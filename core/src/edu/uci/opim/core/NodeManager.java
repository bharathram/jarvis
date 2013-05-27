package edu.uci.opim.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import edu.uci.opim.core.rule.Rule;
import edu.uci.opim.node.Actuator;
import edu.uci.opim.node.SANode;
import edu.uci.opim.node.Sensor;

public class NodeManager {
	public static final Logger logger = Logger.getLogger(NodeManager.class);
	private static NodeManager instance;
	private Map<Sensor, List<Rule>> ruleGrid = new HashMap<Sensor, List<Rule>>();

	private Map<String, SANode> map;

	private NodeManager() {
		map = new HashMap<String, SANode>();
	}

	public static NodeManager getInstance() {
		if (instance == null) {
			synchronized (NodeManager.class) {
				if (instance == null) {
					instance = new NodeManager();
				}
			}
		}
		return instance;
	}

	public Sensor createSensor(String name) {
		SANode sensor = getNode(name);
		if (sensor == null) {
			logger.info("Creating sensor " + name);
			sensor = new Sensor();
			sensor.setName(name);
			map.put(name, sensor);
		}
		return (Sensor) sensor;
	}

	public Actuator createActuator(String name) {
		SANode actuator = getNode(name);
		if (actuator == null) {
			logger.info("Creating Actuator " + name);
			actuator = new Actuator();
			actuator.setName(name);
			map.put(name, actuator);
		}
		return (Actuator) actuator;
	}

	public Sensor getSensor(String name) {
		SANode saNode = map.get(name);
		if (saNode instanceof Sensor) {
			return (Sensor) saNode;
		}
		return null;
	}

	public Actuator getActuator(String name) {
		SANode saNode = map.get(name);
		if (saNode instanceof Sensor) {
			return (Actuator) saNode;
		}
		return null;
	}

	public SANode getNode(String name) {
		return map.get(name);
	}

	public void addRule(Sensor sensor, Rule rule) {
		List<Rule> ruleList = ruleGrid.get(sensor.getName());
		if (ruleList == null) {
			ruleList = new ArrayList<Rule>();
			ruleGrid.put(sensor, ruleList);
		}
		ruleList.add(rule);
	}
}
