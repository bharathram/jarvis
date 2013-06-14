package edu.uci.jarvis.test;

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
public class Task extends AbstractSensorModule {

	JFrame frame = new JFrame();
	Applet ap;

	public Task() {
		final JFrame frame = new JFrame();
		ap = new task();
		ap.init();
		frame.getContentPane().add(ap);

		frame.setSize(ap.getSize());
		// TODO Auto-generated method stub

		frame.setVisible(true);
	}

	class task extends Applet implements KeyListener {
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
			// TODO Auto-generated method stub
			Task.this.notify(new NodeState("KeyPressed"));
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			Task.this.notify(new NodeState("KeyReleased"));
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			// Task.this.notify(new NodeState("KeyPressed"));

		}
	}

	/**
	 * @param args
	 * @throws
	 */

	public static void main(String[] args) throws InterruptedException {
		Task t = new Task();
	}
}
