package edu.uci.opim.client.boot;

import java.util.Properties;

import edu.uci.opim.core.web.Config;

public class GatewayConfig extends Config {

	public static final String SENSOR_CONF_XML = "./conf/sensor-conf.xml";
	public static final int PULSE_MIN = 1;
	// public static String HOST_NAME = "192.168.2.1";
	public String HOST_NAME;
	public final int HOST_PORT;
	public final String WSDL;

	public GatewayConfig(Properties p) {
		// TODO Auto-generated constructor stub
		super(p);
		HOST_NAME = p.getProperty("HOST_NAME");
		HOST_PORT = Integer.parseInt(p.getProperty("HOST_PORT", "6060"));
		WSDL = "http://" + HOST_NAME + ":" + HOST_PORT
				+ "/axis2/services/CosmossService" + "?wsdl";
	}

}
