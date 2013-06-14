package edu.uci.jarvis.applet;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import edu.uci.jarvis.mod.AbstractSensorModule;
import edu.uci.opim.node.NodeState;

abstract public class SensorSimMod extends AbstractSensorModule {
	Applets ap;

	String SensorName = "";
	String[] SensorStates;
	String prevState;

	public SensorSimMod() {
	}

	public void init(String name, String[] states) {
		// TODO Auto-generated constructor stub

		this.SensorName = name;
		SensorStates = states;
		prevState = states[0];
		ap = new Applets();

		// gui = new Thread(new Runnable() {

		// @Override
		// public void run() {
		final JFrame frame = new JFrame();
		frame.getContentPane().add(ap);
		ap.init();
		frame.setSize(ap.getSize());
		// TODO Auto-generated method stub

		frame.setVisible(true);
	}

	public void timeTrigger() {
		notify(new NodeState(prevState));
	}

	public void updateLabel(String label) {
		ap.monitor.setText("Monitoring :" + label);
		ap.monitor.repaint();
	}

	// });
	// gui.start();
	// }

	class Applets extends Applet implements ActionListener {

		@Override
		public void init() {
			setBounds(0, 0, 350, 250);
			setBackground(Color.gray);
			System.out.println("SensorSimMod.Applets.init()");
			Button state;
			Label label1 = new Label("Sensor Name: " + SensorName);

			label1.setVisible(true);
			monitor = new Label("Monitoring:");
			monitor.setVisible(true);

			Label label3 = new Label("States:");

			add(label1);
			add(monitor);
			add(label3);
			for (String name : SensorStates) {
				state = new Button(name);
				state.addActionListener(this);
				add(state);
			}
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() instanceof Button) {
				Button state = (Button) e.getSource();
				prevState = state.getLabel();
				SensorSimMod.this.notify(new NodeState(prevState));
			}
		}

		TextField textField1, textField2;
		Label monitor;

	}

}