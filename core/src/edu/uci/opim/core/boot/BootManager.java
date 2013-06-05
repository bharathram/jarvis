package edu.uci.opim.core.boot;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.apache.axis2.AxisFault;
import org.apache.axis2.engine.AxisServer;
import org.apache.log4j.Logger;

import edu.uci.opim.core.CoreManager;
import edu.uci.opim.core.parser.RuleParser;
import edu.uci.opim.core.rule.Rule;
import edu.uci.opim.core.web.CoreWebInterfaceImpl;
import edu.uci.opim.node.NodeClass;
import edu.uci.opim.node.NodeLocation;
import edu.uci.opim.node.Sensor;

/**
 * Perform the boot up tasks
 * 
 * @author bram
 * 
 */
public class BootManager {

	public static final Logger logger = Logger.getLogger(CoreManager.class);

	public void init() {

		// Read rule conf and setup data structures
		setupRuleSet();

		// Start web services
		AxisServer axisServer = new AxisServer();
		try {
			axisServer.deployService(CoreWebInterfaceImpl.class.getName());
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Unable To Boot");
		}

		// TODO: Start php server

	}

	private void setupRuleSet() {
		// Check for file
		File ruleConf = new File(CoreConfig.RULE_CONF_PATH);
		if (!ruleConf.canRead()) {
			logger.fatal("Unable to read configuration file " + ruleConf);
			System.exit(-1);
		}
		// Parse XML file
		RuleParser parser = new RuleParser(CoreConfig.RULE_CONF_PATH);
		List<Rule> ruleSet = parser.parseBlackList();

		// Populate index structures
		for (Rule rule : ruleSet) {
			// Map all sensors names to the rules that have them
			List<Sensor> dependentSensors = rule.getDependentSensors();
			for (Sensor sensor : dependentSensors) {
				CoreManager.getNodeManager().addRule(sensor, rule);
			}
			// Map all node classes to the the rules that have them
			List<NodeClass> dependentClasses = rule.getDependentClasses();
			for (NodeClass nodeClass : dependentClasses) {
				CoreManager.getClassManager().addRule(nodeClass, rule);
			}
			// Map all the locations to rules that have them
			Set<NodeLocation> dependentLocations = rule.getDependentLocations();
			for (NodeLocation nodeLocation : dependentLocations) {
				CoreManager.getLocManager().addRule(nodeLocation, rule);
			}
		}
		;
	}

	public void run() {

	}

	public void destroy() {

	}

}
