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

			NodeList nList = doc.getElementsByTagName("blacklist");
			NodeList nList1 = doc.getElementsByTagName("rule");
			NodeList nList2 = doc.getElementsByTagName("condition");
			NodeList nList3 = doc.getElementsByTagName("state");
			NodeList nList4 = doc.getElementsByTagName("action");
			NodeList nList5 = doc.getElementsByTagName("do");

			System.out.println("----------------------------");

			for (int i = 0; i < nList.getLength(); i++) {
				System.out.println("nList:" + nList.getLength());

				Node nNode = nList.item(i);

				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					for (int j = 0; j < nList1.getLength(); j++) {
						System.out.println("nList1:" + nList1.getLength());
						Node nNode1 = nList1.item(j);
						if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
							Element eElement1 = (Element) nNode1;

							System.out.println("Priority : "
									+ eElement1.getAttribute("priority"));
							System.out.println("Name : "
									+ eElement1.getElementsByTagName("name")
											.item(0).getTextContent());
							for (int k = 0; k < nList2.getLength(); k++) {
								System.out.println("nList2:"
										+ nList2.getLength());
								Node nNode2 = nList2.item(k);
								if (nNode2.getNodeType() == Node.ELEMENT_NODE) {
									System.out.println("\nCurrent Element2 :"
											+ nNode2.getNodeName());
									for (int l = 0; l < nList3.getLength(); l++) {

										Node nNode3 = nList3.item(l);
										if (nNode3.getNodeType() == Node.ELEMENT_NODE) {
											Element eElement3 = (Element) nNode3;
											System.out
													.println("\nCurrent Element3 :"
															+ nNode3.getNodeName());
											System.out
													.println("host : "
															+ eElement3
																	.getAttribute("host"));
											System.out
													.println("oper : "
															+ eElement3
																	.getAttribute("oper"));
											System.out
													.println("class : "
															+ eElement3
																	.getAttribute("class"));
											System.out
													.println("oper : "
															+ eElement3
																	.getAttribute("oper"));
											System.out
													.println("class : "
															+ eElement3
																	.getAttribute("class"));
											System.out
													.println("location : "
															+ eElement3
																	.getAttribute("location"));
											System.out.println("Name : "
													+ eElement3
															.getTextContent());
										}
									}

								}

							}
							// System.out.println("action : "
							// + eElement1.getElementsByTagName("action")
							// .item(0).getTextContent());
							for (int m = 0; m < nList1.getLength(); m++) {
								Node nNode4 = nList4.item(m);
								if (nNode4.getNodeType() == Node.ELEMENT_NODE) {
									Element eElement4 = (Element) nNode4;
									for (int n = 0; n < nList5.getLength(); n++) {

										Node nNode5 = nList5.item(n);
										if (nNode5.getNodeType() == Node.ELEMENT_NODE) {
											Element eElement5 = (Element) nNode5;
											System.out
													.println("\nCurrent Element3 :"
															+ nNode5.getNodeName());
											System.out
													.println("host : "
															+ eElement5
																	.getAttribute("host"));
											System.out
													.println("class : "
															+ eElement5
																	.getAttribute("class"));
											System.out
													.println("host : "
															+ eElement5
																	.getAttribute("host"));
											System.out
													.println("host : "
															+ eElement5
																	.getAttribute("host"));
											System.out
													.println("location : "
															+ eElement5
																	.getAttribute("location"));
											System.out.println("Name : "
													+ eElement5
															.getTextContent());
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String argv[]) {
		RuleParser par = new RuleParser(
				"C:\\Users\\Nisha\\Dropbox\\MiddlewareProject\\schema\\rule-conf1.xml");
		par.parse();

	}
}
