package edu.uci.opim.core.web;

import java.net.InetAddress;

import edu.uci.opim.core.CoreManager;
import edu.uci.opim.node.Actuator;
import edu.uci.opim.node.NodeState;
import edu.uci.opim.node.Sensor;

public class CoreWebInterfaceImpl implements CoreWebInterface {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.uci.opim.core.web.CoreWebInterface#registerGateway(java.net.InetAddress
	 * , java.lang.String)
	 */
	public String registerGateway(InetAddress ip, String key) {
		return CoreManager.getGatewayManager().registerGateway(ip);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.uci.opim.core.web.CoreWebInterface#registerSensor(java.lang.String,
	 * edu.uci.opim.node.Sensor)
	 */
	public void registerSensor(String gatewayId, Sensor sensor,
			NodeState initialState) {
		CoreManager.getGatewayManager().registerNode(sensor, gatewayId,
				initialState);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.uci.opim.core.web.CoreWebInterface#registerActuator(java.lang.String,
	 * edu.uci.opim.node.Actuator)
	 */
	public void registerActuator(String gatewayId, Actuator actuator) {
		CoreManager.getGatewayManager().registerNode(actuator, gatewayId, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uci.opim.core.web.CoreWebInterface#heartbeat(java.lang.String)
	 */
	public void heartbeat(String gatewayId) {
		CoreManager.getGatewayManager().checkIn(gatewayId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uci.opim.core.web.CoreWebInterface#stimulus(java.lang.String,
	 * java.lang.String, edu.uci.opim.node.NodeState)
	 */
	public void stimulus(String gatewayId, String sensorName, NodeState newState) {
		CoreManager.getNodeManager().handleStimulus(gatewayId, sensorName,
				newState);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uci.opim.core.web.CoreWebInterface#mail(java.lang.String,
	 * java.lang.String)
	 */
	public void mail(String gatewayId, String mesage) {
		// TODO Auto-generated method stub

	}

}
