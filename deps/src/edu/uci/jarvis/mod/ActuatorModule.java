package edu.uci.jarvis.mod;

import net.xeoh.plugins.base.Plugin;

public interface ActuatorModule extends Plugin {

	public Object update(GatewayInterface gateway, Object arg);
}
