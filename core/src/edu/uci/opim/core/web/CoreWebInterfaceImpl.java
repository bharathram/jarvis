package edu.uci.opim.core.web;

import java.util.List;
import java.util.Set;

import javax.jws.WebService;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.engine.AxisServer;

import edu.uci.jarvis.email.Email;
import edu.uci.opim.core.CoreManager;
import edu.uci.opim.core.parser.RuleParser;
import edu.uci.opim.core.rule.Condition;
import edu.uci.opim.node.Actuator;
import edu.uci.opim.node.NodeClass;
import edu.uci.opim.node.NodeLocation;
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
	}

	@Override
	public void exception(String key, String xmlrule) throws AxisFault {

		List<Condition> whiteRuleSet = null;
		// Parse XML file
		try {
			RuleParser parser = new RuleParser("");
			whiteRuleSet = parser.parseWhiteList(xmlrule);

		} catch (Exception e) {
			System.out.println("Error Parsing white rule");
			System.exit(-2);
		}
		// Populate index structures
		for (Condition condition : whiteRuleSet) {
			// Map all sensors names to the rules that have them
			List<Sensor> dependentSensors = condition.getHostList();
			for (Sensor sensor : dependentSensors) {
				CoreManager.getNodeManager()
						.addWhiteListRule(sensor, condition);
			}
			// Map all node classes to the the rules that have them
			List<NodeClass> dependentClasses = condition.getClassList();
			for (NodeClass nodeClass : dependentClasses) {
				CoreManager.getClassManager().addWhiteListRule(nodeClass,
						condition);
			}
			// Map all the locations to rules that have them
			Set<NodeLocation> dependentLocations = condition.getLocList();
			for (NodeLocation nodeLocation : dependentLocations) {
				CoreManager.getLocManager().addWhiteListRule(nodeLocation,
						condition);
			}
		}
		;

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