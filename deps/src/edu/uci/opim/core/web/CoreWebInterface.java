package edu.uci.opim.core.web;

import javax.jws.WebService;

import org.apache.axis2.AxisFault;

import edu.uci.opim.node.Actuator;
import edu.uci.opim.node.NodeState;
import edu.uci.opim.node.Sensor;

@WebService
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
	 * @throws AxisFault
	 */
	public String registerGateway(String key) throws AxisFault;

	/**
	 * @param gatewayId
	 * @param sensorName
	 * @param sensor
	 * @throws AxisFault
	 */
	public void registerSensor(String gatewayId, Sensor sensor,
			NodeState initialState) throws AxisFault;

	/**
	 * @param gatewayId
	 * @param actuatorNode
	 * @param actuator
	 * @throws AxisFault
	 */
	public void registerActuator(String gatewayId, Actuator actuator)
			throws AxisFault;

	/**
	 * Gateway invokes this once in a while to let the controller know that it
	 * is alive
	 * 
	 * @param gatewayId
	 * @throws AxisFault
	 */
	public void heartbeat(String gatewayId) throws AxisFault;

	/**
	 * Invoked by the gateway when an event occurs
	 * 
	 * @param gatewayId
	 *            the gateway at which the event occurred
	 * @param sensorName
	 *            the name of the sensor at which the event occurred
	 * @param newState
	 *            the new state of the sensor.
	 * @throws AxisFault
	 */
	public void stimulus(String gatewayId, String sensorName, NodeState newState)
			throws AxisFault;

	/**
	 * The email service the controller has
	 * 
	 * @param gatewayId
	 * @param mesage
	 * @throws
	 */
	public void mail(String gatewayId, String mesage) throws AxisFault;

}
