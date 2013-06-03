package edu.com.opim.core.stub;

import java.net.URL;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.rpc.client.RPCServiceClient;

import edu.uci.opim.core.web.CoreWebInterface;
import edu.uci.opim.node.Actuator;
import edu.uci.opim.node.NodeState;
import edu.uci.opim.node.Sensor;

public class DynamicCoreWebClient implements CoreWebInterface {
	private static final String NAMESPACE = "http://web.core.opim.uci.edu";
	private static final String SERVICE_NAME = "CosmossService";
	private static final String SERVICE_ENDPOINT = "CosmossServiceHttpSoap12Endpoint";
	private final RPCServiceClient dynamicClient;

	public DynamicCoreWebClient(URL wsdlUrl) throws AxisFault {
		dynamicClient = new RPCServiceClient(null, wsdlUrl, new QName(
				NAMESPACE, SERVICE_NAME), SERVICE_ENDPOINT);
	}

	@Override
	public String registerGateway(String ip, String key) throws AxisFault {
		Object[] result = dynamicClient.invokeBlocking(new QName(NAMESPACE,
				"registerGateway"), new Object[] { ip, key },
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
		dynamicClient.invokeBlocking(new QName(NAMESPACE, "registerSensor"),
				new Object[] { gatewayId, sensor, initialState },
				new Class[] {});

	}

	@Override
	public void registerActuator(String gatewayId, Actuator actuator)
			throws AxisFault {
		dynamicClient.invokeBlocking(new QName(NAMESPACE, "registerActuator"),
				new Object[] { gatewayId, actuator }, new Class[] {
						String.class, Actuator.class });
	}

	@Override
	public void heartbeat(String gatewayId) throws AxisFault {
		dynamicClient.invokeBlocking(new QName(NAMESPACE, "heartbeat"),
				new Object[] { gatewayId }, new Class[] { String.class, });
	}

	@Override
	public void stimulus(String gatewayId, String sensorName, NodeState newState)
			throws AxisFault {
		dynamicClient.invokeBlocking(new QName(NAMESPACE, "stimulus"),
				new Object[] { gatewayId, sensorName, newState }, new Class[] {
						String.class, String.class, NodeState.class });
	}

	@Override
	public void mail(String gatewayId, String mesage) throws AxisFault {
		dynamicClient.invokeBlocking(new QName(NAMESPACE, "mail"),
				new Object[] { gatewayId, mesage }, new Class[] { String.class,
						String.class });
	}

}
