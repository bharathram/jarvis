package edu.uci.opim.client.boot;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;

import org.apache.axis2.AxisFault;
import org.apache.axis2.engine.AxisServer;

import edu.com.opim.gateway.web.GatewayController;
import edu.com.opim.gateway.web.GatewayWebInterfaceImpl;
import edu.com.opim.gateway.web.ServerUtils;

public class GatewayBootManager {
	public void init() {
		try {
			GatewayController.getInstance().init();
		} catch (Exception e) {
			e.printStackTrace();
			System.out
					.println("FATAL ERROR: Error occured while reading sensor configuration");
			System.exit(-1);
		}

		// Publish web service
		try {
			AxisServer axisServer = ServerUtils.getServer();
			axisServer.deployService(GatewayWebInterfaceImpl.class.getName());

			// Do service discovery
			while (!testConnection()) {
				System.out
						.println("Error : Unable to connect to the server retrying in 1 min");
				Thread.sleep(1000 * 60);
			}
			// Checkin with the core
			GatewayController.getInstance().register(GatewayConfig.WSDL);
		} catch (AxisFault e) {
			System.out.println("FATAL Error while starting web service ");
			System.exit(-2);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Wait for stuff to happen
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean testConnection() {
		Socket socket = null;
		boolean reachable = false;
		try {
			socket = new Socket(GatewayConfig.HOST_NAME,
					GatewayConfig.HOST_PORT);
			reachable = true;
		} catch (IOException e) {

		} finally {
			if (socket != null)
				try {
					socket.close();
				} catch (IOException e) {
				}
		}
		return reachable;

	}

	public static void main(String[] args) {
		GatewayBootManager manager = new GatewayBootManager();
		manager.init();
	}

}
