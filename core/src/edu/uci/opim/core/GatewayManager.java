package edu.uci.opim.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uci.opim.core.action.Step;
import edu.uci.opim.core.exception.ExceptionToLog;
import edu.uci.opim.core.exception.Priority;
import edu.uci.opim.core.exception.UnableToExecuteStepException;
import edu.uci.opim.core.web.GatewayNode;
import edu.uci.opim.node.NodeClass;
import edu.uci.opim.node.NodeLocation;
import edu.uci.opim.node.NodeState;
import edu.uci.opim.node.SANode;

public class GatewayManager {
	/**
	 * List of Registered gateways
	 */
	private Map<String, GatewayNode> gatewayList = new HashMap<String, GatewayNode>();

	/**
	 * Register an new gateway
	 * 
	 * @param address
	 * @return
	 */
	public synchronized String registerGateway(String address) {
		String key = "";
		if (gatewayList.containsKey(key)) {
			CoreManager.getLogManager().logEvent(
					new ExceptionToLog("Gateway Already exists: ", address,
							Priority.ERROR));
		} else {
			GatewayNode gatewayNode = new GatewayNode(address);
			key = gatewayNode.getGateKey();
		}
		return key;
	}

	public void checkIn(String gateway) {
		GatewayNode gatewayNode = gatewayList.get(gateway);
		if (gatewayNode != null) {
			gatewayNode.checkIn();
		} else {
			CoreManager.getLogManager().logEvent(
					new ExceptionToLog("Check in from unknown gateway: ",
							gateway, Priority.ERROR));
		}
	}

	public synchronized void registerNode(SANode node, String gateway,
			NodeState initalState) {
		if (gatewayList.containsKey(gateway)) {
			// Register the node with the node manager
			CoreManager.getNodeManager().registerNode(node,
					gatewayList.get(gateway), initalState);
			// Find out the location of the node and register it with the
			// location manager
			CoreManager.getLocManager().registerNode(node);
			// Find out the node class and register it with the ClassManager
			CoreManager.getClassManager().registerNode(node);
		} else {
			CoreManager
					.getLogManager()
					.logEvent(
							new ExceptionToLog(
									"Attempt to register a node from an unknown gateway ",
									gateway, Priority.ERROR));
		}
	}

	public boolean checkGateway(String gatewayId) {
		return gatewayList.containsKey(gatewayId);
	}

	/**
	 * Response to the stimulus via the actuators
	 * 
	 * @param step
	 * @throws UnableToExecuteStepException
	 */
	public void response(Step step) throws UnableToExecuteStepException {
		List<SANode> actuators = new ArrayList<SANode>();
		// Get attributes of the step
		String host = step.getHost();
		NodeClass nClass = step.getSensorClass();
		NodeLocation location = step.getLocation();

		// Filter out nodes with whom we need to communicate
		if (host != null) {
			// If there has been a host specified
			actuators.add(CoreManager.getNodeManager().getActuator(host));
		} else if (nClass != null) {
			// If there is a class of nodes to whom we need to communicate
			List<SANode> nodeList = CoreManager.getClassManager().getNodeList(
					nClass);
			if (nodeList == null) {
				throw new UnableToExecuteStepException(step,
						"Cant find class list , this should not happen");
			}
			if (location == null) {
				// No location based filter
				actuators.addAll(nodeList);
			} else {
				// Apply location based filter
				for (SANode saNode : nodeList) {
					if (location.equals(saNode.getLocation())) {
						actuators.add(saNode);
					}
				}
			}
			// Apply the step to each of the identified nodes
			String msg = "";
			for (SANode saNode : actuators) {
				if (CoreManager.getNodeManager().getGatewayNode(saNode) != null) {
					execute(saNode, step);
				} else {
					msg += saNode.toString() + " , ";
				}
			}
			if (!"".equals(msg)) {
				throw new UnableToExecuteStepException(step,
						"Unable to connect to " + msg);
			}
		}
	}

	private void execute(SANode node, Step step)
			throws UnableToExecuteStepException {

		if (node == null) {
			throw new UnableToExecuteStepException(step,
					"Unknown node. This should not happen ");
		}

		GatewayNode gatewayNode = CoreManager.getNodeManager().getGatewayNode(
				node);
		if (gatewayNode == null) {
			throw new UnableToExecuteStepException(step,
					"Unable to connect to target node");
		}
		// TODO:
		// Invoke the gateway stub and call
		// actionOnNode(String node, NodeState newState,Object parameter);

	}
}
