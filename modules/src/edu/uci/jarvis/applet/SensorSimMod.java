package edu.uci.jarvis.applet;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import edu.uci.jarvis.mod.AbstractSensorModule;
import edu.uci.opim.node.NodeState;

public class SensorSimMod extends AbstractSensorModule {
	final Applet ap;

	String SensorName = "";
	String[] SensorStates;

	private Thread gui;

	public SensorSimMod(String name, String[] states) {
		// TODO Auto-generated constructor stub

		this.SensorName = name;
		SensorStates = states;
		ap = new Applets();

		// gui = new Thread(new Runnable() {

		// @Override
		// public void run() {
		final JFrame frame = new JFrame();
		frame.getContentPane().add(ap);
		// TODO Auto-generated method stub
		ap.init();
		frame.setVisible(true);
	}

	// });
	// gui.start();
	// }

	class Applets extends Applet implements ActionListener {
		@Override
		public void init() {
			System.out.println("SensorSimMod.Applets.init()");
			Button state;
			Label label1 = new Label("Sensor Name:");

			label1.setVisible(true);
			Label label2 = new Label(SensorName);
			Label label3 = new Label("States:");
			add(label1);
			add(label2);
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
				SensorSimMod.this.notify(new NodeState(state.getLabel()));
			}
		}

		TextField textField1, textField2;
	}
}