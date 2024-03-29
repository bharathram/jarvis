package edu.com.opim.gateway.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.MessagingException;

import org.apache.axis2.AxisFault;
import org.apache.commons.codec.binary.Base64;

import edu.com.opim.client.parser.SensorParser;
import edu.com.opim.core.stub.DynamicCoreWebClient;
import edu.uci.jarvis.email.Email;
import edu.uci.jarvis.mod.AbstractSensorModule;
import edu.uci.jarvis.mod.ActuatorModule;
import edu.uci.jarvis.mod.GatewayInterface;
import edu.uci.jarvis.mod.SensorModule;
import edu.uci.opim.client.boot.GatewayConfig;
import edu.uci.opim.core.exception.XMLParseException;
import edu.uci.opim.node.Actuator;
import edu.uci.opim.node.NodeState;
import edu.uci.opim.node.SANode;
import edu.uci.opim.node.Sensor;
import edu.uci.opim.util.LoaderUtils;

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
	public GatewayConfig config;
	/**
	 * Keeps track connectivity status
	 */
	private boolean isConnected = false;
	/**
	 * List of all the nodes connected to this gateway
	 */
	private List<SANode> nodeList;

	/**
	 * Mapping of all the classes to the
	 */
	private Map<String, Actuator> ActuatorMap = new HashMap<String, Actuator>();
	private Map<String, ActuatorModule> actModuleMap = new HashMap<String, ActuatorModule>();
	private Map<AbstractSensorModule, Sensor> SensorMap = new HashMap<AbstractSensorModule, Sensor>();
	private Map<String, SensorModule> sensorModuleMap = new HashMap<String, SensorModule>();

	private GatewayController() {
		Properties prop = new Properties();
		try {
			InputStream in = new FileInputStream("./conf/gateway.properties");
			prop.load(in);
			config = new GatewayConfig(prop);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
		// Read the sensor config files

		// TODO: Read the netconf file for controller configuration parameters.
		// TODO: Register the nodes with the server.

	}

	public boolean action(String node, Object state) {

		if (actModuleMap.containsKey(node)) {
			ActuatorModule am = actModuleMap.get(node);
			Object res = am.update(this, state.toString());
			Email e = new Email();
			try {
				if (res instanceof String) {
					e.sendEmail("Message From " + node + "\n" + res, null);
				} else if (res instanceof File) {
					System.out.println("GatewayController.action()");
					e.sendEmail("Message from " + node,
							new String[] { ((File) res).getAbsolutePath() });
				}
			} catch (MessagingException e1) {
				e1.printStackTrace();
			}
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
					AbstractSensorModule abs = (AbstractSensorModule) LoaderUtils
							.getSensorModule(temp.getConfPath());
					abs.addObserver(this);
					SensorMap.put(abs, temp);
					sensorModuleMap.put(temp.getName(), abs);
				} else {

					Actuator temp = (Actuator) nodeList.get(y);

					// ActuatorModule act = temp.getMode();
					String name = temp.getName();
					ActuatorMap.put(name, temp);
					ActuatorModule actuatorModule = LoaderUtils
							.getActuatorModule(temp.getConfPath());
					actModuleMap.put(name, actuatorModule);
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
		System.out.println("Update received from sensor " + SenMod + "info "
				+ obj1);
		// TODO Auto-generated method stub
		if (SensorMap.containsKey(SenMod)) {
			try {
				Sensor sen = SensorMap.get(SenMod);
				if (obj1 != null) {
					coreStub.stimulus(gatewayId, sen.getName(), new NodeState(
							obj1.toString()));
				} else {
					coreStub.stimulus(gatewayId, sen.getName(), new NodeState(
							""));

				}
			} catch (AxisFault e) {
				e.printStackTrace();
				System.out
						.println("[ERROR] Unable to forward stimulus to server");
			}
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
			synchronized (this) {
				try {
					if (isConnected) {
						coreStub.heartbeat(gatewayId);
					} else {
						System.out
								.println("[INFO]Attempting to restore connection ");
						register();
						registerNodes();

					}

				} catch (AxisFault e) {
					isConnected = false;
					System.out.println("[ERROR] Server not reachable :");

				} catch (MalformedURLException e) {
					isConnected = false;
					System.out.println("[ERROR] Malformed URL  :"
							+ e.getMessage());
				}
			}

		}
	}

	public void register() throws AxisFault, MalformedURLException {
		MessageDigest md;
		String hashKey = "";
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] key = md.digest(config.key.getBytes());
			hashKey = new String(Base64.encodeBase64(key));
			URL wUrl = new URL(config.WSDL);
			coreStub = new DynamicCoreWebClient(wUrl);
			gatewayId = coreStub.registerGateway(hashKey);
			startHeartBeat();
			isConnected = true;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public void registerNodes() {
		for (SANode node : nodeList) {
			try {
				if (node instanceof Sensor) {
					coreStub.registerSensor(gatewayId, (Sensor) node,
							new NodeState(""));
				} else {
					coreStub.registerActuator(gatewayId, (Actuator) node);
				}
			} catch (AxisFault e) {
				e.printStackTrace();
			}

		}
	}
}