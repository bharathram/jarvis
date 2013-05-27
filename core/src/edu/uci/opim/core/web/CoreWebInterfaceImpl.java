package edu.uci.opim.core.web;

import java.net.InetAddress;

import edu.uci.opim.node.Actuator;
import edu.uci.opim.node.NodeState;
import edu.uci.opim.node.Sensor;

public class CoreWebInterfaceImpl implements CoreWebInterface {

	public String registerGateway(InetAddress ip, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	public void registerSensor(String gatewayId, String sensorName,
			Sensor sensor) {
		// TODO Auto-generated method stub
		
	}

	public void registerActuator(String gatewayId, String actuatorNode,
			Actuator actuator) {
		// TODO Auto-generated method stub
		
	}

	public void heartbeat(String gatewayId) {
		// TODO Auto-generated method stub
		
	}

	public void stimulus(String gatewayId, String sensorName, NodeState newState) {
		// TODO Auto-generated method stub
		
	}

	public void mail(String gatewayId, String mesage) {
		// TODO Auto-generated method stub
		
	}

	
}
