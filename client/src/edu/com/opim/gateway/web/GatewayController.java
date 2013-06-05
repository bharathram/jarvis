package edu.com.opim.gateway.web;

import java.util.List;
import java.util.Map;
import java.util.Observable;

import edu.com.opim.client.parser.SensorParser;
import edu.uci.jarvis.mod.AbstractSensorModule;
import edu.uci.jarvis.mod.ActuatorModule;
import edu.uci.jarvis.mod.GatewayInterface;
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
	private static final String SENSOR_CONF_XML = "sensor-conf.xml";

	private static GatewayController instance = null;

	/**
	 * List of all the nodes connected to this gateway
	 */
	private List<SANode> nodeList;

	/**
	 * Mapping of all the classes to the
	 */
	private Map<String, Actuator> ActuatorMap;
	private Map<AbstractSensorModule, Sensor> SensorMap;

	private GatewayController() {

		// Read the sensor config files
		String file = SENSOR_CONF_XML;
		SensorParser sp = new SensorParser(file);
		try {
			nodeList = sp.parse();

			// TODO: Load all the services mentioned in the config file.
			for (int y = 0; y < nodeList.size(); y++) {
				if (nodeList.get(y).getModule().equals("sensor")) {
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
							+ SENSOR_CONF_XML);
			System.exit(-1);

		}
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

	@Override
	public void update(Observable SenMod, Object obj1) {
		// TODO Auto-generated method stub
		if (SensorMap.containsKey(SenMod)) {
			Sensor sen = SensorMap.get(SenMod);
			sen.getName();
			obj1.toString();
		}

	}

}