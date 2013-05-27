package edu.uci.opim.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import edu.uci.opim.core.rule.Rule;
import edu.uci.opim.node.NodeLocation;

public class LocationManager {
	public static final Logger logger = Logger.getLogger(LocationManager.class);

	private static LocationManager instance;
	private Map<NodeLocation, List<Rule>> ruleGrid = new HashMap<NodeLocation, List<Rule>>();

	private LocationManager() {
	}

	public static LocationManager getInstance() {
		if (instance == null) {
			synchronized (LocationManager.class) {
				if (instance == null) {
					instance = new LocationManager();
				}
			}
		}
		return instance;
	}

	public void addRule(NodeLocation location, Rule rule) {
		List<Rule> ruleList = ruleGrid.get(location.string);
		if (ruleList == null) {
			ruleList = new ArrayList<Rule>();
			ruleGrid.put(location, ruleList);
		}
		ruleList.add(rule);
	}
}
