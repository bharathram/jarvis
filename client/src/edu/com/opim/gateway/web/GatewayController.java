package edu.com.opim.gateway.web;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.axis2.AxisFault;

import edu.com.opim.client.parser.SensorParser;
import edu.com.opim.core.stub.DynamicCoreWebClient;
import edu.uci.jarvis.mod.AbstractSensorModule;
import edu.uci.jarvis.mod.ActuatorModule;
import edu.uci.jarvis.mod.GatewayInterface;
import edu.uci.opim.client.boot.GatewayConfig;
import edu.uci.opim.core.exception.XMLParseException;
import edu.uci.opim.node.Actuator;
import edu.uci.opim.node.SANode;
import edu.uci.opim.node.Sensor;

/**
 * Singleton class for all the functionalities of the gateway.
 * 
 * @author bram
 * 
 */
public class GatewayController implements GatewayInterface {

	private static GatewayController instance = null;
	private Timer heartBeat = new Timer();
	private DynamicCoreWebClient coreStub;
	private String gatewayId;

	/**
	 * List of all the nodes connected to this gateway
	 */
	private List<SANode> nodeList;

	/**
	 * Mapping of all the classes to the
	 */
	private Map<String, Actuator> ActuatorMap = new HashMap<String, Actuator>();
	private Map<AbstractSensorModule, Sensor> SensorMap = new HashMap<AbstractSensorModule, Sensor>();

	private GatewayController() {

		// Read the sensor config files

		// TODO: Read the netconf file for controller configuration parameters.
		// TODO: Register the nodes with the server.

	}

	public boolean action(String node, Object state) {
		if (ActuatorMap.containsKey(node)) {
			Actuator ac = ActuatorMap.get(node);

			ActuatorModule am = ac.getMode();
			am.update(this, state);
		}
		return true;
	}

	public static GatewayController getInstance() {
		if (instance == null) {
			synchronized (GatewayController.class) {
				if (instance == null) {
					instance = new GatewayController();
				}
			}
		}
		return instance;
	}

	public void init() {
		// Init Sensor Configuration
		String file = GatewayConfig.SENSOR_CONF_XML;
		SensorParser sp = new SensorParser(file);
		try {
			nodeList = sp.parse();

			// TODO: Load all the services mentioned in the config file.
			for (int y = 0; y < nodeList.size(); y++) {
				if (nodeList.get(y) instanceof Sensor) {
					System.out.println("Loading plugin for " + nodeList.get(y));
					Sensor temp = (Sensor) nodeList.get(y);
					AbstractSensorModule abs = temp.getMode();
					abs.addObserver(this);
					SensorMap.put(abs, temp);
				} else {

					Actuator temp = (Actuator) nodeList.get(y);

					// ActuatorModule act = temp.getMode();
					String name = temp.getName();
					ActuatorMap.put(name, temp);
				}

			}
		} catch (XMLParseException e) {
			System.out
					.println("FATAL: Unable to read Sensor configuration file "
							+ GatewayConfig.SENSOR_CONF_XML);
			System.exit(-1);

		}
	}

	@Override
	public void update(Observable SenMod, Object obj1) {
		// TODO Auto-generated method stub
		if (SensorMap.containsKey(SenMod)) {
			Sensor sen = SensorMap.get(SenMod);
			sen.getName();
			obj1.toString();
		}

	}

	private void startHeartBeat() {
		heartBeat.scheduleAtFixedRate(new HBTimerTask(),
				GatewayConfig.PULSE_MIN * 60 * 1000,
				GatewayConfig.PULSE_MIN * 60 * 1000);

	}

	private class HBTimerTask extends TimerTask {

		@Override
		public void run() {
			try {
				coreStub.heartbeat(gatewayId);
			} catch (AxisFault e) {
				System.out.println("[ERROR] Server not reachable :");
			}

		}
	}

	public void register(String wsdl) throws AxisFault, MalformedURLException {
		URL wUrl = new URL(wsdl);
		coreStub = new DynamicCoreWebClient(wUrl);
		// TODO: Secure key exchange
		String key = "";

		gatewayId = coreStub.registerGateway(key);
		startHeartBeat();
	}
}