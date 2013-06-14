package edu.uci.jarvis.makey;

import java.applet.Applet;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import edu.uci.jarvis.mod.AbstractSensorModule;
import edu.uci.opim.node.NodeState;

@PluginImplementation
public class TouchSensor extends AbstractSensorModule {

	JFrame frame = new JFrame();
	Applet ap;

	public TouchSensor() {
	}

	@Override
	public void init() {
		final JFrame frame = new JFrame();
		ap = new UiApplet();
		ap.init();
		frame.getContentPane().add(ap);

		frame.setSize(ap.getSize());

		frame.setVisible(true);
	}

	class UiApplet extends Applet implements KeyListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void init() {
			setBounds(0, 0, 350, 250);
			setBackground(Color.gray);
			setFocusable(true);
			System.out.println("SensorSimMod.Applets.init()");

			JLabel state = new JLabel("Makey Makey");
			this.addKeyListener(this);
			add(state);
			this.requestFocus();
		}

		@Override
		public void keyPressed(KeyEvent arg0) {
			TouchSensor.this.notify(new NodeState("Pressed"));
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			TouchSensor.this.notify(new NodeState("Released"));
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// Do nothing
		}
	}
}
