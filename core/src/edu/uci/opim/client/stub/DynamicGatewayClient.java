package edu.uci.opim.client.stub;

import java.net.URL;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.async.AxisCallback;
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
		AxisCallback callback = null;
		System.out.println("DynamicGatewayClient.actionOnNode() " + node
				+ newState + parameter);
		dynamicClient.invokeNonBlocking(new QName(NAMESPACE, "actionOnNode"),
				new Object[] { node, newState, parameter }, callback);

		return true;
	}
}
