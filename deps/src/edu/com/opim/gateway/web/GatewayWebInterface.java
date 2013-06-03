package edu.com.opim.gateway.web;

import javax.jws.WebService;

import org.apache.axis2.AxisFault;

import edu.uci.opim.node.NodeState;

/**
 * Public web interface fore the gateway.
 * 
 * @author bram
 * 
 */
@WebService
public interface GatewayWebInterface {

	/**
	 * Perform an action on a given actuator node/ or perform some sensor
	 * configuration.
	 * 
	 * @param node
	 * @param newState
	 * @param parameter
	 *            optional parameter
	 * @return if the action was successful
	 * @throws AxisFault
	 */

	public Boolean actionOnNode(String node, NodeState newState,
			Object parameter) throws AxisFault;

}
