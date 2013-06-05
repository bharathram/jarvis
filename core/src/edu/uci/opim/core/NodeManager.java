package edu.uci.opim.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;

import edu.uci.jarvis.email.Email;
import edu.uci.opim.core.exception.ExceptionToLog;
import edu.uci.opim.core.exception.Priority;
import edu.uci.opim.core.rule.Rule;
import edu.uci.opim.core.web.GatewayNode;
import edu.uci.opim.node.Actuator;
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

	public GatewayNode getGatewayNode(SANode node) {
		return aliveNodes.get(node);

	}

	public void addRule(Sensor sensor, Rule rule) {
		List<Rule> ruleList = ruleGrid.get(sensor.getName());
		if (ruleList == null) {
			ruleList = new ArrayList<Rule>();
			ruleGrid.put(sensor, ruleList);
		}
		ruleList.add(rule);
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
			synchronized (this) {
				aliveNodes.put(node, gateway);
				if (node instanceof Sensor) {
					sysState.put((Sensor) node, state);
				}
			}

		}
	}

	public void handleStimulus(String gatewayId, String sensorName,
			NodeState newState) {
		if (!CoreManager.getGatewayManager().checkGateway(gatewayId)) {
			CoreManager.getLogManager().logEvent(
					new ExceptionToLog(
							"Stimulus triggered from an unkon gateway",
							"Gateway" + gatewayId + "Sensor name" + sensorName
									+ "new state" + newState, Priority.ERROR));
			return;
		}
		if (!aliveNodes.containsKey(sensorName)) {
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
		SANode saNode = knownNodeMap.get(sensorName);
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

		// Incase of deadlocks see here......
		Set<Entry<Sensor, NodeState>> entrySet;
		NodeState oldState;
		synchronized (this) {
			oldState = sysState.get(saNode);
			sysState.put((Sensor) saNode, newState);
			setChanged();
			entrySet = sysState.entrySet();
		}
		notifyObservers(new StateChangedEvent((Sensor) saNode, oldState,
				newState));
	}

	/**
	 * Gets a list of rules associated with this sensor;
	 * 
	 * @param sensor
	 * @return
	 */
	List<Rule> getRuleList(Sensor sensor) {
		return ruleGrid.get(sensor);
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
