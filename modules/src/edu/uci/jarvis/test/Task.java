package edu.uci.jarvis.test;

import java.applet.Applet;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

import edu.uci.jarvis.mod.AbstractSensorModule;
import edu.uci.opim.node.NodeState;

public class Task extends AbstractSensorModule {

	JFrame frame = new JFrame();
	Applet ap;

	public Task() {
		final JFrame frame = new JFrame();
		frame.getContentPane().add(ap);
		ap = new task();
		ap.init();
		frame.setSize(ap.getSize());
		// TODO Auto-generated method stub

		frame.setVisible(true);
	}

	class task extends Applet implements KeyListener {
		@Override
		public void init() {
			setBounds(0, 0, 350, 250);
			setBackground(Color.gray);
			System.out.println("SensorSimMod.Applets.init()");

			JLabel state = new JLabel();
			state.addKeyListener(this);
			add(state);
		}

		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			// Task.this.notify(new NodeState("KeyPressed"));
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			Task.this.notify(new NodeState("KeyPressed"));
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

}
