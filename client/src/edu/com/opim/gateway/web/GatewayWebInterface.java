package edu.com.opim.gateway.web;

import edu.uci.opim.node.NodeState;

/**
 * Public web interface fore the gateway.
 * 
 * @author bram
 * 
 */
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
	 */
	public Boolean actionOnNode(String node, NodeState newState,
			Object parameter);

	/**
	 * Perform an action on a given class of nodes on this gateway
	 * 
	 * @param nClass
	 * @param newState
	 * @param parameter
	 * @return number of nodes on which the action was successful
	 */
	public Integer actionOnClass(String nClass, NodeState newState,
			Object parameter);

	/**
	 * Query a sensor state on a given sensor node
	 * 
	 * @param node
	 * @return
	 */
	public NodeState getState(String node);

}
