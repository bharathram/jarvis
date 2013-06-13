package edu.uci.opim.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import edu.uci.opim.core.exception.ExceptionToLog;
import edu.uci.opim.core.exception.Priority;
import edu.uci.opim.core.rule.Condition;
import edu.uci.opim.core.rule.Rule;
import edu.uci.opim.node.NodeLocation;
import edu.uci.opim.node.SANode;

/**
 * 
 * Manages everything to do node locations.
 * 
 * @author bram
 * 
 */
public class LocationManager {
	public static final Logger logger = Logger.getLogger(LocationManager.class);

	/**
	 * All known Locations
	 */
	private Map<String, NodeLocation> map = new HashMap<String, NodeLocation>();
	/**
	 * Maps locations to the rules associated with it.
	 */
	private Map<NodeLocation, List<Rule>> ruleGrid = new HashMap<NodeLocation, List<Rule>>();

	private Map<NodeLocation, List<Condition>> conditionGrid = new HashMap<NodeLocation, List<Condition>>();

	/**
	 * Maps the location to the nodes available at that location.
	 */
	private Map<NodeLocation, List<SANode>> locationGrid = new HashMap<NodeLocation, List<SANode>>();

	LocationManager() {
		// Do nothing
	}

	/**
	 * A new location is known
	 * 
	 * @param name
	 * @return
	 */
	public NodeLocation createLocation(String name) {
		NodeLocation loc = map.get(name);
		if (loc == null) {
			logger.info("Creating node location " + name);
			loc = new NodeLocation(name);
			map.put(name, loc);
		}
		return loc;
	}

	/**
	 * Adds a new rule to the location
	 * 
	 * @param location
	 * @param rule
	 */
	public void addRule(NodeLocation location, Rule rule) {
		List<Rule> ruleList = ruleGrid.get(location.string);
		if (ruleList == null) {
			ruleList = new ArrayList<Rule>();
			ruleGrid.put(location, ruleList);
		}
		ruleList.add(rule);
	}

	public void addWhiteListRule(NodeLocation location, Condition condition) {
		List<Condition> conditionList = conditionGrid.get(location.string);
		if (conditionList == null) {
			conditionList = new ArrayList<Condition>();
			conditionGrid.put(location, conditionList);
		}
		conditionList.add(condition);
	}

	/**
	 * Registers a new node with its location
	 * 
	 * @param node
	 */
	public void registerNode(SANode node) {
		if (node != null && node.getLocation() != null) {
			String name = node.getLocation().string;
			NodeLocation nodeLocation = map.get(name);
			if (nodeLocation == null) {
				CoreManager
						.getLogManager()
						.logEvent(
								new ExceptionToLog(
										"Node Location does not have any rule/Action associated with it",
										name, Priority.WARN));
				nodeLocation = createLocation(name);
			}
			List<SANode> list = locationGrid.get(nodeLocation);
			if (list == null) {
				list = new ArrayList<SANode>();
				locationGrid.put(nodeLocation, list);
			}
			list.add(node);
		} else {
			CoreManager.getLogManager().logEvent(
					new ExceptionToLog(
							"Node does not have a location associated with it",
							node, Priority.INFO));
		}
	}

	public void deRegisterNode(SANode node) {
		// TODO: Remove the node and its associated location

	}

	public void deRegisterRule(Rule rule) {
		// TODO: Disassociate the rule and the location
	}

	/**
	 * Gets the list of rules associated with this node location.
	 * 
	 * @param loc
	 * @return
	 */
	List<Rule> getRuleList(NodeLocation loc) {
		List<Rule> list = ruleGrid.get(loc);
		if (list == null) {
			return Collections.EMPTY_LIST;
		}
		return list;
	}

	List<Condition> getWhiteRuleList(NodeLocation loc) {
		List<Condition> list = conditionGrid.get(loc);
		if (list == null) {
			return Collections.EMPTY_LIST;
		}
		return list;
	}
}