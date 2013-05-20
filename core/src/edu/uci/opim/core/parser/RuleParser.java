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

import edu.uci.opim.core.rule.Rule;

public class RuleParser {

	String fileName;

	public RuleParser(String filePath) {
		// TODO Auto-generated constructor stub
		this.fileName = filePath;
	}

	public List<Rule> parse() {
		List<Rule> nodes = new ArrayList<Rule>();
		return nodes;
	}

	public static void main(String argv[]) {

		try {

			File fXmlFile = new File(
					"C:\\Users\\Nisha\\Dropbox\\MiddlewareProject\\schema\\rule-conf.xml");
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

			NodeList nList = doc.getElementsByTagName("blacklist");
			NodeList nList1 = doc.getElementsByTagName("rule");
			NodeList nList2 = doc.getElementsByTagName("condition");

			System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					System.out.println("Sensor id : "
							+ eElement.getAttribute("priority"));
					System.out.println("Name : "
							+ eElement.getElementsByTagName("name").item(0)
									.getTextContent());
					System.out.println("Class Name : "
							+ eElement.getElementsByTagName("class").item(0)
									.getTextContent());
					System.out.println("Location : "
							+ eElement.getElementsByTagName("location").item(0)
									.getTextContent());
					NodeList nList3 = doc.getElementsByTagName("state");
					for (int temp1 = 0; temp1 < nList.getLength(); temp1++) {
						Node nNode1 = nList.item(temp1);
						if (nNode.getNodeType() == Node.ELEMENT_NODE) {
							System.out.println("Staff id : "
									+ eElement.getAttribute("id"));

						}

					}

					System.out.println("ws-path : "
							+ eElement.getAttribute("ws-path"));

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
