package edu.uci.opim.core.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.uci.opim.core.exception.XMLParseException;
import edu.uci.opim.node.Actuator;
import edu.uci.opim.node.NodeClass;
import edu.uci.opim.node.NodeLocation;
import edu.uci.opim.node.NodeState;
import edu.uci.opim.node.SANode;
import edu.uci.opim.node.Sensor;

public class SensorParser {
	String fileName;

	public SensorParser(String filePath) {
		this.fileName = filePath;
	}

	public List<SANode> parse() throws XMLParseException {
		List<SANode> nodes = new ArrayList<SANode>();
		try {
			File fXmlFile = new File(fileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			// optional, but recommended
			// read this -
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			System.out.println("Root element :"
					+ doc.getDocumentElement().getNodeName());

			NodeList sensorList = doc.getElementsByTagName("sensor");
			NodeList actuatorList = doc.getElementsByTagName("actuator");
			System.out.println("----------------------------");

			for (int i = 0; i < sensorList.getLength(); i++) {

				Node nNode = sensorList.item(i);

				SANode sensor = createSAN(nNode, true);
				nodes.add(sensor);
			}
			for (int i = 0; i < actuatorList.getLength(); i++) {

				Node nNode = actuatorList.item(i);

				SANode actuator = createSAN(nNode, false);
				nodes.add(actuator);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new XMLParseException();
		}
		return null;
	}

	private SANode createSAN(Node nNode, boolean isSensor) {

		System.out.println("\nCurrent Element :" + nNode.getNodeName());
		SANode node;
		if (isSensor) {
			node = new Sensor();
		} else {
			node = new Actuator();
		}

		if (nNode.getNodeType() == Node.ELEMENT_NODE) {

			Element eElement = (Element) nNode;

			int ID = Integer.parseInt(eElement.getAttribute("id"));
			node.setId(ID);
			System.out.println("Sensor id : " + eElement.getAttribute("id"));
			String Name = eElement.getElementsByTagName("name").item(0)
					.getTextContent();
			node.setName(Name);
			System.out.println("Name : "
					+ eElement.getElementsByTagName("name").item(0)
							.getTextContent());
			NodeList classList = eElement.getElementsByTagName("class");
			for (int temp = 0; temp < classList.getLength(); temp++) {
				System.out.println("Class Name : "
						+ eElement.getElementsByTagName("class").item(temp)
								.getTextContent());
				String class2 = eElement.getElementsByTagName("class")
						.item(temp).getTextContent();
				NodeClass class1 = new NodeClass(class2);
				node.addClass(class1);
				System.out.println("Class Name : "
						+ eElement.getElementsByTagName("class").item(temp)
								.getTextContent());
			}
			NodeLocation location = new NodeLocation(eElement
					.getElementsByTagName("location").item(0).getTextContent());
			node.setLocation(location);
			System.out.println("Location : "
					+ eElement.getElementsByTagName("location").item(0)
							.getTextContent());
			NodeList stateList = eElement.getElementsByTagName("state");
			for (int temp1 = 0; temp1 < stateList.getLength(); temp1++) {
				Node nNode1 = stateList.item(temp1);

				if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
					String id = ((Element) nNode1).getAttribute("id");
					String stateName = ((Element) nNode1).getTextContent();
					System.out.println("State id : "
							+ ((Element) nNode1).getAttribute("id"));
					System.out.println("state value:"
							+ ((Element) nNode1).getTextContent());
					NodeState state = new NodeState(id, stateName);
					node.addState(state);
				}

			}
			String confPath = eElement.getElementsByTagName("ws-path").item(0)
					.getTextContent();
			node.setConfPath(confPath);
			System.out.println("ws-path : "
					+ eElement.getElementsByTagName("ws-path").item(0)
							.getTextContent());
		}
		return node;
	}

	public static void main(String argv[]) {
		SensorParser par = new SensorParser("sensor-conf.xml");
		try {
			par.parse();
		} catch (XMLParseException e) {

			e.printStackTrace();
		}

	}
}