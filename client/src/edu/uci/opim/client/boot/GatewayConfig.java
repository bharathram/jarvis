package edu.uci.opim.client.boot;

public interface GatewayConfig {
	public static final String SENSOR_CONF_XML = "./conf/sensor-conf.xml";
	public static final String HOST_NAME = "192.168.0.16";
	public static final int HOST_PORT = 6060;
	public static final String WSDL = "http://" + HOST_NAME + ":" + HOST_PORT
			+ "/axis2/services/CosmossService" + "?wsdl";

	public static final int PULSE_MIN = 1;

}
