package edu.uci.opim.core.web;

import java.net.InetAddress;

public class GatewayNode {
	InetAddress ip;
	String name;

	public GatewayNode(InetAddress address, String name) {
		ip = address;
		this.name = name;
	}
}
