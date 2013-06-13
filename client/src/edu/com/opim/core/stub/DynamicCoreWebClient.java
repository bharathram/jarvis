package edu.com.opim.core.stub;

import java.net.URL;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.async.AxisCallback;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.rpc.client.RPCServiceClient;

import edu.uci.opim.core.web.CoreWebInterface;
import edu.uci.opim.node.Actuator;
import edu.uci.opim.node.NodeState;
import edu.uci.opim.node.Sensor;

//Workaround In this class 
// Using asynchronus RPC due to circumvent a bug in the axis2. JIRA
// issue:TUSCANY-1658 Dynamically generated WSDL does not generate
// wsdl:output if method has
// void return
// https://issues.apache.org/jira/browse/TUSCANY-1658

public class DynamicCoreWebClient implements CoreWebInterface {
	private static final String NAMESPACE = "http://web.core.opim.uci.edu";
	private static final String SERVICE_NAME = "CosmossService";
	private static final String SERVICE_ENDPOINT = "CosmossServiceHttpSoap12Endpoint";
	private final RPCServiceClient dynamicClient;

	// private boolean[] lock = new boolean[1];

	public DynamicCoreWebClient(URL wsdlUrl) throws AxisFault {
		dynamicClient = new RPCServiceClient(null, wsdlUrl, new QName(
				NAMESPACE, SERVICE_NAME), SERVICE_ENDPOINT);
	}

	@Override
	public String registerGateway(String key) throws AxisFault {
		Object[] result = dynamicClient.invokeBlocking(new QName(NAMESPACE,
				"registerGateway"), new Object[] { key },
				new Class[] { String.class });
		if (result != null && result.length == 1 && result[0] instanceof String) {
			return (String) result[0];
		}
		throw new AxisFault(
				"Unexpected response from webservice for String registerGateway(InetAddress ip, String key)");
	}

	@Override
	public void registerSensor(String gatewayId, Sensor sensor,
			NodeState initialState) throws AxisFault {
		// dynamicClient.invokeBlocking(new QName(NAMESPACE, "registerSensor"),
		// new Object[] { gatewayId, sensor, initialState });
		dynamicClient.invokeNonBlocking(new QName(NAMESPACE, "registerSensor"),
				new Object[] { gatewayId, sensor, initialState },
				new MyCallBack());
		// waitForCallBack();

	}

	@Override
	public void registerActuator(String gatewayId, Actuator actuator)
			throws AxisFault {
		dynamicClient.invokeNonBlocking(
				new QName(NAMESPACE, "registerActuator"), new Object[] {
						gatewayId, actuator }, new MyCallBack());
		// waitForCallBack();
	}

	@Override
	public void heartbeat(String gatewayId) throws AxisFault {
		dynamicClient.invokeNonBlocking(new QName(NAMESPACE, "heartbeat"),
				new Object[] { gatewayId }, new MyCallBack());
		// waitForCallBack();
	}

	@Override
	public void stimulus(String gatewayId, String sensorName, NodeState newState)
			throws AxisFault {
		dynamicClient.invokeNonBlocking(new QName(NAMESPACE, "stimulus"),
				new Object[] { gatewayId, sensorName, newState },
				new MyCallBack());
		// waitForCallBack();
	}

	@Override
	public void exception(String key, String xmlrule) throws AxisFault {
		dynamicClient.invokeNonBlocking(new QName(NAMESPACE, "exception"),
				new Object[] { key, xmlrule }, new MyCallBack());
	}

	@Override
	public void mail(String gatewayId, String mesage) throws AxisFault {
		dynamicClient.invokeNonBlocking(new QName(NAMESPACE, "mail"),
				new Object[] { gatewayId, mesage }, new MyCallBack());
		// waitForCallBack();
	}

	public static class MyCallBack implements AxisCallback {

		@Override
		public void onMessage(MessageContext arg0) {

		}

		@Override
		public void onFault(MessageContext arg0) {
			// Nothing to do

		}

		@Override
		public void onError(Exception arg0) {
			// Nothing to do
		}

		@Override
		public void onComplete() {
		}

	}
}