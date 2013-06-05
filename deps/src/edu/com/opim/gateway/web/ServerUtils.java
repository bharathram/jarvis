package edu.com.opim.gateway.web;

import org.apache.axis2.engine.AxisServer;

public class ServerUtils {
	public static AxisServer server = new AxisServer();

	public static AxisServer getServer() {
		return server;
	}

}
