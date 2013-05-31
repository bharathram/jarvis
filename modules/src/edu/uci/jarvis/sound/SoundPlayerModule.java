package edu.uci.jarvis.sound;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import edu.uci.jarvis.mod.ActuatorModule;
import edu.uci.jarvis.mod.GatewayInterface;

@PluginImplementation
public class SoundPlayerModule implements ActuatorModule {
	public static final String BeepEmergency = "BeepEmergency";
	public static final String IntruderAlert = "IntruderAlert";
	public static final String EndPlay = "EndPlay";

	public static SoundPlayer emergency;
	public static SoundPlayer intruder;

	@Override
	public Object update(GatewayInterface gateway, Object arg) {
		if (arg instanceof String) {
			if (BeepEmergency.equals(arg)) {
				getEmergency().play();
			} else if (IntruderAlert.equals(arg)) {
				getIntruder().play();
			} else if (EndPlay.equals(arg)) {
				getEmergency().close();
				getIntruder().close();
			}
		}
		return null;
	}

	public static SoundPlayer getEmergency() {
		if (emergency == null) {
			emergency = new SoundPlayer(SoundPlayerModule.class.getResource(
					"sound1.mp3").getFile());
		}
		return emergency;

	}

	public static SoundPlayer getIntruder() {
		if (intruder == null) {
			intruder = new SoundPlayer(SoundPlayerModule.class.getResource(
					"sound2.mp3").getFile());
		}
		return intruder;

	}

}
