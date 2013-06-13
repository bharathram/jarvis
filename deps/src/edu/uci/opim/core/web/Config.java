package edu.uci.opim.core.web;

import java.util.Properties;

public class Config {

	public final String key;
	public final String email_host;
	public final String email_port;
	public final String email_sys;
	public final String email_admin;
	public final String pwd_sys;

	/**
	 * @param args
	 */
	public Config(Properties p) {
		// TODO Auto-generated constructor stub
		key = p.getProperty("KEY");
		email_host = p.getProperty("EMAIL_HOST");
		email_port = p.getProperty("EMAIL_PORT");
		email_sys = p.getProperty("EMAIL_SYS");
		email_admin = p.getProperty("EMAIL_ADMIN");
		pwd_sys = p.getProperty("PWD_SYS");

	}

}
