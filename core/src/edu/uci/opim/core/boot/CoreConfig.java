package edu.uci.opim.core.boot;

import java.util.Properties;

import edu.uci.opim.core.web.Config;

public class CoreConfig extends Config {
	public static String RULE_CONF_PATH = "./conf/rule-conf.xml";
	public static long HEART_BEAT = 50000;
	public final String DB_URL;
	public final String DB_USERNAME;
	public final String DB_PWD;

	public CoreConfig(Properties p) {
		// TODO Auto-generated constructor stub
		super(p);
		DB_URL = p.getProperty("DB_URL");
		DB_USERNAME = p.getProperty("DB_USERNAME");
		DB_PWD = p.getProperty("DB_PWD", "");
	}
}
