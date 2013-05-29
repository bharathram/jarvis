package edu.uci.opim.core.web;

import java.net.InetAddress;

import edu.uci.opim.node.Actuator;
import edu.uci.opim.node.NodeState;
import edu.uci.opim.node.Sensor;

public interface CoreWebInterface {
	/**
	 * Registers a new gateway with the controller
	 * 
	 * @param ip
	 *            Address of the gateway
	 * @param key
	 *            Security key like ssh may be
	 * @return An identifier used for addressing this gate way for the rest of
	 *         the session.
	 */
	public String registerGateway(InetAddress ip, String key);

	/**
	 * @param gatewayId
	 * @param sensorName
	 * @param sensor
	 */
	public void registerSensor(String gatewayId, Sensor sensor,
			NodeState initialState);

	/**
	 * @param gatewayId
	 * @param actuatorNode
	 * @param actuator
	 */
	public void registerActuator(String gatewayId, Actuator actuator);

	/**
	 * Gateway invokes this once in a while to let the controller know that it
	 * is alive
	 * 
	 * @param gatewayId
	 */
	public void heartbeat(String gatewayId);

	/**
	 * Invoked by the gateway when an event occurs
	 * 
	 * @param gatewayId
	 *            the gateway at which the event occurred
	 * @param sensorName
	 *            the name of the sensor at which the event occurred
	 * @param newState
	 *            the new state of the sensor.
	 */
	public void stimulus(String gatewayId, String sensorName, NodeState newState);

	/**
	 * The email service the controller has
	 * 
	 * @param gatewayId
	 * @param mesage
	 */
	public void mail(String gatewayId, String mesage);

}
