package edu.uci.opim.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;

import edu.uci.jarvis.email.Email;
import edu.uci.opim.core.exception.ExceptionToLog;
import edu.uci.opim.core.exception.Priority;
import edu.uci.opim.core.rule.Condition;
import edu.uci.opim.core.rule.Rule;
import edu.uci.opim.core.web.GatewayNode;
import edu.uci.opim.node.Actuator;
import edu.uci.opim.node.NodeLocation;
import edu.uci.opim.node.NodeState;
import edu.uci.opim.node.SANode;
import edu.uci.opim.node.Sensor;

/**
 * Manages everything to do with the nodes.
 * 
 * @author bram
 * 
 */
public class NodeManager extends Observable {
	private static final Logger logger = Logger.getLogger(CoreManager.class);
	/**
	 * Maps the sensor to the rules that it is associated with
	 */
	private Map<Sensor, List<Rule>> ruleGrid = new HashMap<Sensor, List<Rule>>();

	private Map<Sensor, List<Condition>> conditionGrid = new HashMap<Sensor, List<Condition>>();

	/**
	 * Map of all known nodes.(Nodes that have atleast one rule associated with
	 * them.
	 */
	private Map<String, SANode> knownNodeMap = new HashMap<String, SANode>();

	/**
	 * List of nodes that have checked in with the core.
	 */
	private Map<SANode, GatewayNode> aliveNodes = new HashMap<SANode, GatewayNode>();

	/**
	 * State of the system as reported by the the sensors.
	 */
	private Map<Sensor, NodeState> sysState = new HashMap<Sensor, NodeState>();

	NodeManager() {

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
		} else {
			if (!(actuator instanceof Actuator)) {
				throw new RuntimeException("Expected actuator node , found "
						+ actuator.toString());
			}
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
		if (saNode instanceof Actuator) {
			return (Actuator) saNode;
		}
		return null;
	}

	public SANode getNode(String name) {
		return knownNodeMap.get(name);
	}

	public GatewayNode getGatewayNode(SANode node) {
		return aliveNodes.get(node);

	}

	public void addRule(Sensor sensor, Rule rule) {
		List<Rule> ruleList = ruleGrid.get(sensor);
		if (ruleList == null) {
			ruleList = new ArrayList<Rule>();
			ruleGrid.put(sensor, ruleList);
		}
		ruleList.add(rule);
	}

	public void addWhiteListRule(Sensor sensor, Condition condition) {
		List<Condition> conditionList = conditionGrid.get(sensor);
		if (conditionList == null) {
			conditionList = new ArrayList<Condition>();
			conditionGrid.put(sensor, conditionList);
		}
		conditionList.add(condition);
	}

	public void registerNode(SANode node, GatewayNode gateway, NodeState state) {
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
			if (node instanceof Sensor) {
				createSensor(node.getName());
			} else {
				createActuator(node.getName());
			}
			SANode saNode = knownNodeMap.get(node.getName());
			saNode.setLocation(new NodeLocation(node.getLocation().string));
			CoreManager.getLocManager().registerNode(node);
			synchronized (this) {
				aliveNodes.put(node, gateway);
				if (node instanceof Sensor) {
					sysState.put((Sensor) node, state);
				}
			}

		}
	}

	public void deRegisterNode(List<GatewayNode> deadList) {
		if (deadList == null || deadList.size() == 0) {
			return;
		}
		Iterator<Entry<SANode, GatewayNode>> it = aliveNodes.entrySet()
				.iterator();
		while (it.hasNext()) {
			Entry<SANode, GatewayNode> pairs = it.next();
			if (deadList.contains(pairs.getValue())) {
				System.out.println("Sensor de-registered " + pairs.getKey());
				it.remove();
			}
		}
	}

	public void handleStimulus(String gatewayId, final String sensorName,
			final NodeState newState) {
		if (!CoreManager.getGatewayManager().checkGateway(gatewayId)) {
			CoreManager.getLogManager().logEvent(
					new ExceptionToLog(
							"Stimulus triggered from an unkon gateway",
							"Gateway" + gatewayId + "Sensor name" + sensorName
									+ "new state" + newState, Priority.ERROR));
			return;
		}

		SANode s = knownNodeMap.get(sensorName);
		if (!aliveNodes.containsKey(s)) {
			CoreManager
					.getLogManager()
					.logEvent(
							new ExceptionToLog(
									"Stimulus triggered from an Unregistered sensor form a known gateway",
									"Gateway" + gatewayId + "Sensor name"
											+ sensorName + "new state"
											+ newState, Priority.WARN));
			return;

		}
		final SANode saNode = knownNodeMap.get(sensorName);
		if (!(saNode instanceof Sensor)) {
			CoreManager
					.getLogManager()
					.logEvent(
							new ExceptionToLog(
									"Stimulus triggered from an uknown and unregistred sensor form a known gateway",
									"Gateway" + gatewayId + "Sensor name"
											+ sensorName + "new state"
											+ newState, Priority.WARN));
			return;
		}
		CoreManager.getLogManager().logEvent(
				new ExceptionToLog("Received Stimulus ", gatewayId
						+ " Sensor name" + sensorName + "new state" + newState,
						Priority.INFO));
		// Incase of deadlocks see here......
		final Map<Sensor, NodeState> entrySet;
		final NodeState oldState = sysState.get(saNode);
		if (!oldState.equals(newState)) {
			synchronized (saNode) {
				sysState.put((Sensor) saNode, newState);
				setChanged();
				entrySet = new HashMap<>(sysState);
			}
			// To reduce the call back time process event in a different thread
			new Thread(new Runnable() {

				@Override
				public void run() {
					notifyObservers(new StateChangedEvent((Sensor) saNode,
							oldState, newState, entrySet));

				}
			}).start();
		}
	}

	/**
	 * Gets a list of rules associated with this sensor;
	 * 
	 * @param sensor
	 * @return
	 */
	List<Rule> getRuleList(Sensor sensor) {
		List<Rule> list = ruleGrid.get(sensor);
		if (list == null) {
			return Collections.emptyList();
		}
		return list;
	}

	List<Condition> getWhiteRuleList(Sensor sensor) {
		List<Condition> list = conditionGrid.get(sensor);
		if (list == null) {
			return Collections.emptyList();
		}
		return list;
	}

	public void constructEmail(Rule r) {
		StringBuilder msg = new StringBuilder();
		msg.append("Time: " + System.currentTimeMillis() + "\n Rule:"
				+ r.getName() + "\n");
		for (Map.Entry<Sensor, NodeState> e : sysState.entrySet()) {
			String sname = e.getKey().getName();
			String state = e.getValue().string;
			SANode san = knownNodeMap.get(sname);
			GatewayNode gateway = aliveNodes.get(san);
			msg.append(gateway.getGateKey() + ":" + sname + "->" + state);

		}
		Email e = new Email();
		try {
			e.sendEmail(msg.toString(), null);
		} catch (AddressException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MessagingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
