package edu.uci.opim.client.boot;

public interface GatewayConfig {
	public static final String SENSOR_CONF_XML = "./conf/sensor-conf.xml";
	public static String HOST_NAME = "192.168.2.1";
	public static final int HOST_PORT = 6060;
	public static final String WSDL = "http://" + HOST_NAME + ":" + HOST_PORT
			+ "/axis2/services/CosmossService" + "?wsdl";

	public static final int PULSE_MIN = 1;
	public static final String KEY = "3d:ba:82:4a:0d:a5:43:9b:b4:f4:54:6e:ec:ba:f8:4c";

}
