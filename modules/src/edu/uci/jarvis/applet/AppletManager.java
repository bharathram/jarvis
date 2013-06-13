package edu.uci.jarvis.applet;

import javax.swing.JFrame;

public class AppletManager {
	private static JFrame frame;

	public static JFrame getFrame() {
		if (frame == null) {
			synchronized (AppletManager.class) {
				if (frame == null) {
					frame = new JFrame();
				}
			}
		}
		return frame;
	}

}
