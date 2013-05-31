package edu.uci.jarvis.cam;

import java.io.File;
import java.io.IOException;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import edu.uci.jarvis.mod.ActuatorModule;
import edu.uci.jarvis.mod.GatewayInterface;

@PluginImplementation
public class WebCamModule implements ActuatorModule {
	public static String state = "TakePic";
	public static WebCam instance = new WebCam();

	@Override
	public Object update(GatewayInterface gateway, Object arg) {
		if (arg instanceof String) {
			if (state.equals(arg)) {

				String capture;
				try {
					capture = instance.capture();
					File file = new File(capture);
					return file;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
