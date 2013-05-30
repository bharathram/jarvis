package edu.uci.opim.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import edu.uci.opim.core.exception.ExceptionToLog;
import edu.uci.opim.core.exception.Priority;
import edu.uci.opim.core.rule.Rule;
import edu.uci.opim.node.NodeClass;
import edu.uci.opim.node.SANode;

/**
 * Keeps track of all the node classes
 * 
 * @author bram
 * 
 */
public class ClassManager {
	private static final Logger logger = Logger.getLogger(CoreManager.class);

	/**
	 * All known classes of nodes
	 */
	private Map<String, NodeClass> map = new HashMap<String, NodeClass>();;
	/**
	 * Mapping of the known classes to the sensors that belong to that class
	 */
	private Map<NodeClass, List<SANode>> classGrid = new HashMap<NodeClass, List<SANode>>();
	/**
	 * Mapping of the class to the rules that are associated with them
	 */
	private Map<NodeClass, List<Rule>> ruleGrid = new HashMap<NodeClass, List<Rule>>();

	ClassManager() {
	}

	public NodeClass createClass(String name) {
		NodeClass nClass = getNodeClass(name);
		if (nClass == null) {
			logger.info("Creating node class " + name);
			nClass = new NodeClass(name);
			map.put(name, nClass);
		}
		return nClass;
	}

	public NodeClass getNodeClass(String name) {
		return map.get(name);

	}

	private void registerNode(String nClass, SANode node) {
		NodeClass nodeClass = getNodeClass(nClass);
		if (nodeClass == null) {
			CoreManager
					.getLogManager()
					.logEvent(
							new ExceptionToLog(
									"Node Class does not have any rule assiciated with it",
									nClass, Priority.WARN));
			nodeClass = createClass(nClass);
		}

		List<SANode> list = classGrid.get(nodeClass);
		if (list == null) {
			list = new ArrayList<SANode>();
		}
		list.add(node);
	}

	public void registerNode(SANode node) {
		NodeClass[] classes = node.getClasses();
		for (NodeClass nodeClass : classes) {
			registerNode(nodeClass.string, node);
		}
	}

	public void addRule(NodeClass nClass, Rule rule) {
		List<Rule> ruleList = ruleGrid.get(nClass.string);
		if (ruleList == null) {
			ruleList = new ArrayList<Rule>();
			ruleGrid.put(nClass, ruleList);
		}
		ruleList.add(rule);
	}

	public void deRegisterNode(String node) {
		// TODO: Remove the node form the list of nodes form the class

	}

	public void removeRule(Rule rule) {
		// TODO: disassoicate the rule with the class;
	}

	/**
	 * Gets a list if rules associated with the node class
	 * 
	 * @param nClass
	 * @return
	 */
	List<Rule> getRuleList(NodeClass nClass) {
		return ruleGrid.get(nClass);

	}

}
