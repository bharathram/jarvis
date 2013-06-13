package edu.uci.opim.core;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.commons.codec.binary.Base64;

import edu.uci.opim.client.stub.DynamicGatewayClient;
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
	public String registerGateway(String pass, String address) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] secret = md.digest(CoreManager.getInstance().config.key
					.getBytes());
			String hashKey = new String(Base64.encodeBase64(secret));
			if (hashKey.equals(pass)) {
				String key = new String(md.digest(address.getBytes()));
				if (gatewayList.containsKey(key)) {
					CoreManager.getLogManager().logEvent(
							new ExceptionToLog("Gateway Already exists: ",
									address, Priority.ERROR));
				} else {
					GatewayNode gatewayNode = new GatewayNode(address);
					key = gatewayNode.getGateKey();
					synchronized (gatewayList) {
						gatewayList.put(key, gatewayNode);
					}
				}
				return key;
			}
			CoreManager.getLogManager().logEvent(
					new ExceptionToLog("Gateway Authentication faile", "Key:"
							+ pass + " IP: " + address, Priority.ERROR));
		} catch (Exception e) {
			e.printStackTrace();
			CoreManager.getLogManager().logEvent(
					new ExceptionToLog(
							"Eception occured while registering gateway", e,
							Priority.ERROR));
		}
		return "";
	}

	public void checkIn(String gateway) {

		if (checkGateway(gateway)) {
			gatewayList.get(gateway).checkIn();
		} else {
			CoreManager.getLogManager().logEvent(
					new ExceptionToLog("Check in from unknown gateway: ",
							gateway, Priority.ERROR));
		}
	}

	public void registerNode(SANode node, String gateway, NodeState initalState) {
		if (checkGateway(gateway)) {
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
		if (gatewayList.containsKey(gatewayId)) {
			MessageContext inMessageContext = MessageContext
					.getCurrentMessageContext();
			String ip = (String) inMessageContext.getProperty("REMOTE_ADDR");
			if (ip.equals(gatewayList.get(gatewayId).ip)) {
				return true;
			}
		}
		return false;
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

	private void execute(SANode node, Step step)
			throws UnableToExecuteStepException {
		System.out.println("GatewayManager.execute()");
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

		try {
			DynamicGatewayClient clientStub = new DynamicGatewayClient(new URL(
					"http://" + gatewayNode.ip
							+ ":6060/axis2/services/GatewayService?wsdl"));

			clientStub.actionOnNode(node.getName(), step.getState(), "");

		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
