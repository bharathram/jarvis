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

import edu.uci.opim.core.node.SANode;

public class SensorParser {
	String fileName;

	public SensorParser(String filePath) {
		this.fileName = filePath;
	}

	public List<SANode> parse() {
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
			NodeList stateList = doc.getElementsByTagName("state");

			System.out.println("----------------------------");

			for (int i = 0; i < sensorList.getLength(); i++) {

				Node nNode = sensorList.item(i);

				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					System.out.println("Sensor id : "
							+ eElement.getAttribute("id"));
					System.out.println("Name : "
							+ eElement.getElementsByTagName("name").item(0)
									.getTextContent());
					System.out.println("Class Name : "
							+ eElement.getElementsByTagName("class").item(0)
									.getTextContent());
					System.out.println("Location : "
							+ eElement.getElementsByTagName("location").item(0)
									.getTextContent());
					for (int temp1 = 0; temp1 < stateList.getLength(); temp1++) {
						Node nNode1 = stateList.item(temp1);
						if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
							System.out.println("State id : "
									+ ((Element) nNode1).getAttribute("id"));
							System.out.println("state value:"
									+ ((Element) nNode1).getTextContent());
						}

						System.out.println("ws-path : "
								+ eElement.getElementsByTagName("ws-path")
										.item(0).getTextContent());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String argv[]) {
		SensorParser par = new SensorParser(
				"C:\\Users\\Nisha\\Dropbox\\MiddlewareProject\\schema\\sensor-conf.xml");
		par.parse();

	}

}