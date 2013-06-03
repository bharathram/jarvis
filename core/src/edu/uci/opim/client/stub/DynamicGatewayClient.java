package edu.uci.opim.client.stub;

import java.net.URL;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.rpc.client.RPCServiceClient;

import edu.com.opim.gateway.web.GatewayWebInterface;
import edu.uci.opim.node.NodeState;

public class DynamicGatewayClient implements GatewayWebInterface {
	private static final String NAMESPACE = "http://web.gateway.opim.com.edu";
	private static final String SERVICE_NAME = "GatewayService";
	private static final String SERVICE_ENDPOINT = "GatewayServiceHttpSoap12Endpoint";
	private final RPCServiceClient dynamicClient;

	public DynamicGatewayClient(URL wsdlUrl) throws AxisFault {
		dynamicClient = new RPCServiceClient(null, wsdlUrl, new QName(
				NAMESPACE, SERVICE_NAME), SERVICE_ENDPOINT);
	}

	@Override
	public Boolean actionOnNode(String node, NodeState newState,
			Object parameter) throws AxisFault {
		Object[] result = dynamicClient.invokeBlocking(new QName(NAMESPACE,
				"actionOnNode"), new Object[] { node, newState, parameter },
				new Class[] { String.class, NodeState.class, Object.class });
		if (result != null && result.length > 1 && result[0] instanceof Boolean) {
			return (Boolean) result[0];

		}
		throw new AxisFault(
				"Unexpected response from webservice for Boolean actionOnNode(String node, NodeState newState, 	Object parameter");
	}

	@Override
	public Integer actionOnClass(String nClass, NodeState newState,
			Object parameter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeState getState(String node) {
		// TODO Auto-generated method stub
		return null;
	}

}
