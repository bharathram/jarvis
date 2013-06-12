package edu.uci.opim.core.web;

import javax.jws.WebService;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.engine.AxisServer;

import edu.uci.jarvis.email.Email;
import edu.uci.opim.core.CoreManager;
import edu.uci.opim.node.Actuator;
import edu.uci.opim.node.NodeState;
import edu.uci.opim.node.Sensor;

@WebService(endpointInterface = "edu.uci.opim.core.web.CoreWebInterface", serviceName = "CosmossService")
public class CoreWebInterfaceImpl implements CoreWebInterface {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.uci.opim.core.web.CoreWebInterface#registerGateway(java.net.InetAddress
	 * , java.lang.String)
	 */
	@Override
	public String registerGateway(String key) {
		MessageContext inMessageContext = MessageContext
				.getCurrentMessageContext();
		String ip = (String) inMessageContext.getProperty("REMOTE_ADDR");

		System.out.println("CoreWebInterfaceImpl.registerGateway() InetAddress"
				+ ip + "Key:" + key);
		return CoreManager.getGatewayManager().registerGateway(key, ip);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.uci.opim.core.web.CoreWebInterface#registerSensor(java.lang.String,
	 * edu.uci.opim.node.Sensor)
	 */
	@Override
	public void registerSensor(String gatewayId, Sensor sensor,
			NodeState initialState) {
		System.out.println("CoreWebInterfaceImpl.registerSensor() gateway :"
				+ gatewayId + " sensor " + sensor + " node state "
				+ initialState);
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
	@Override
	public void registerActuator(String gatewayId, Actuator actuator) {
		System.out.println("CoreWebInterfaceImpl.registerActuator()gatewaty "
				+ gatewayId + " Actuator " + actuator);

		CoreManager.getGatewayManager().registerNode(actuator, gatewayId, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uci.opim.core.web.CoreWebInterface#heartbeat(java.lang.String)
	 */
	@Override
	public void heartbeat(String gatewayId) {
		System.out.println("CoreWebInterfaceImpl.heartbeat() gateway"
				+ gatewayId);

		CoreManager.getGatewayManager().checkIn(gatewayId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uci.opim.core.web.CoreWebInterface#stimulus(java.lang.String,
	 * java.lang.String, edu.uci.opim.node.NodeState)
	 */
	@Override
	public void stimulus(String gatewayId, String sensorName, NodeState newState) {

		System.out.println("CoreWebInterfaceImpl.stimulus() gateway "
				+ gatewayId + " Sensotr name " + sensorName + " news state "
				+ newState);

		CoreManager.getNodeManager().handleStimulus(gatewayId, sensorName,
				newState);
		System.out.println("CoreWebInterfaceImpl.stimulus()------END");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uci.opim.core.web.CoreWebInterface#mail(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void mail(String gatewayId, String mesage) {
		System.out.println("CoreWebInterfaceImpl.mail() gateway " + gatewayId
				+ " message " + mesage);
		// TODO Auto-generated method stub
		Email e = new Email();
		try {
			e.sendEmail(mesage, null);
		} catch (AddressException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MessagingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public static void main(String[] args) throws AxisFault {
		AxisServer axisServer = new AxisServer();
		axisServer.deployService(CoreWebInterfaceImpl.class.getName());
	}

}
