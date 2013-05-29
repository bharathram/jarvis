package edu.uci.opim.core;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import edu.uci.opim.core.exception.ExceptionToLog;
import edu.uci.opim.core.exception.Priority;
import edu.uci.opim.core.web.GatewayNode;
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
	public synchronized String registerGateway(InetAddress address) {
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
}
