package edu.uci.opim.core.boot;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import edu.uci.opim.core.ClassManager;
import edu.uci.opim.core.NodeManager;
import edu.uci.opim.core.parser.RuleParser;
import edu.uci.opim.core.rule.Rule;
import edu.uci.opim.core.web.GatewayNode;
import edu.uci.opim.node.NodeClass;
import edu.uci.opim.node.Sensor;

public class BootManager {

	private static BootManager instance;

	public static final Logger logger = Logger.getLogger(BootManager.class);
	private List<GatewayNode> gatewayList = new ArrayList<GatewayNode>();

	/**
	 * List of sensors that have checked in with the core.
	 */
	private Map<Sensor, GatewayNode> aliveSensors;

	private BootManager() {
		init();
		run();

	}

	public void init() {
		// TODO:Initialize some of the managers

		// TODO: Initialize and open the db connection
		// TODO: Read rule conf and setup data structures
		setupRuleSet();

		// TODO: Start web services
		// TODO: Start php server

	}

	private void setupRuleSet() {
		File ruleConf = new File(CoreConfig.RULE_CONF_PATH);
		if (!ruleConf.canRead()) {
			logger.fatal("Unable to read configuration file " + ruleConf);
			System.exit(-1);
		}
		RuleParser parser = new RuleParser(CoreConfig.RULE_CONF_PATH);
		List<Rule> ruleSet = parser.parseBlackList();
		for (Rule rule : ruleSet) {
			List<Sensor> dependentSensors = rule.getDependentSensors();
			for (Sensor sensor : dependentSensors) {
				NodeManager.getInstance().addRule(sensor, rule);
			}

			List<NodeClass> dependentClasses = rule.getDependentClasses();
			for (NodeClass nodeClass : dependentClasses) {
				ClassManager.getInstance().addRule(nodeClass, rule);
			}
			// TODO: Location Manager
		}
		;
	}

	public void run() {

	}

	public void destroy() {

	}

	public static BootManager getInstance() {
		if (instance == null) {
			synchronized (BootManager.instance) {
				if (instance == null) {
					instance = new BootManager();
				}
			}
		}
		return instance;
	}

}
