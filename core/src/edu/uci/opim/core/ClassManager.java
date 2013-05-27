package edu.uci.opim.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

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
	public static final Logger logger = Logger.getLogger(ClassManager.class);
	private static ClassManager instance;

	private Map<String, NodeClass> map;
	private Map<NodeClass, List<SANode>> classGrid = new HashMap<NodeClass, List<SANode>>();
	private Map<NodeClass, List<Rule>> ruleGrid = new HashMap<NodeClass, List<Rule>>();

	private ClassManager() {
		map = new HashMap<String, NodeClass>();
		classGrid = new HashMap<NodeClass, List<SANode>>();
	}

	public static ClassManager getInstance() {
		if (instance == null) {
			synchronized (ClassManager.class) {
				if (instance == null) {
					instance = new ClassManager();
				}
			}
		}
		return instance;
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

	public void registerNode(String nClass, SANode node) {
		NodeClass nodeClass = getNodeClass(nClass);
		List<SANode> list = classGrid.get(nodeClass);
		if (list == null) {
			list = new ArrayList<SANode>();
		}
		list.add(node);
	}

	public void deRegisterNode(String node) {

	}

	public void addRule(NodeClass nClass, Rule rule) {
		List<Rule> ruleList = ruleGrid.get(nClass.string);
		if (ruleList == null) {
			ruleList = new ArrayList<Rule>();
			ruleGrid.put(nClass, ruleList);
		}
		ruleList.add(rule);
	}
}
