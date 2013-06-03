package edu.com.opim.gateway.web;

import javax.jws.WebService;

import edu.uci.opim.node.NodeState;

@WebService(endpointInterface = "edu.com.opim.gateway.web.GatewayWebInterface", serviceName = "GatewayService")
public class GatewayWebInterfaceImpl implements GatewayWebInterface {

	public Boolean actionOnNode(String node, NodeState newState,
			Object parameter) {

		// TODO Auto-generated method stub
		return null;
	}

	public Integer actionOnClass(String nClass, NodeState newState,
			Object parameter) {
		// TODO Auto-generated method stub
		return null;
	}

	public NodeState getState(String node) {
		// TODO Auto-generated method stub
		return null;
	}

}
