package edu.com.opim.gateway.web;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import edu.uci.opim.node.NodeState;

@WebService(endpointInterface = "edu.com.opim.gateway.web.GatewayWebInterface", serviceName = "GatewayService")
public class GatewayWebInterfaceImpl implements GatewayWebInterface {
	Logger logger = Logger.getLogger(GatewayWebInterface.class);

	@Override
	public Boolean actionOnNode(String node, NodeState newState,
			Object parameter) {
		// TODO Auto-generated method stub
		logger.debug("GatewayWebInterfaceImpl.actionOnNode() Node:" + node
				+ " New state " + newState + "Object Paramert" + parameter);
		return GatewayController.getInstance().action(node, newState);
	}
}
