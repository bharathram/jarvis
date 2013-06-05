package edu.uci.opim.core.web;

import java.net.InetAddress;

/**
 * Class models the gateway node at the controller site.
 * 
 * @author bram
 * 
 */
public class GatewayNode {
	public final String ip;
	public final String gateKey;
	private long checkin;

	public GatewayNode(String address) {
		ip = address;
		this.gateKey = ip.toString();
		checkin = System.currentTimeMillis();
	}

	public String getGateKey() {
		return gateKey;
	}

	/**
	 * @return last time at which heartbeat was received.
	 */
	public long getCheckin() {
		return checkin;
	}

	/**
	 * Invoke when a heartbeat is received
	 */
	public void checkIn() {
		checkin = System.currentTimeMillis();
	}

	@Override
	public int hashCode() {

		return gateKey.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GatewayNode) {
			return gateKey.equals(((GatewayNode) obj).gateKey);
		}
		if (obj instanceof InetAddress) {
			return obj.equals(this.ip);
		}
		return false;
	}
}
