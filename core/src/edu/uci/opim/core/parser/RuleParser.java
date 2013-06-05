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

import edu.uci.opim.core.action.Action;
import edu.uci.opim.core.action.Step;
import edu.uci.opim.core.rule.Condition;
import edu.uci.opim.core.rule.Predicate;
import edu.uci.opim.core.rule.Predicate.Operands;
import edu.uci.opim.core.rule.Rule;
import edu.uci.opim.core.rule.SensorStatePredicate;
import edu.uci.opim.core.rule.TimePredicate;

public class RuleParser {

	String fileName;

	public RuleParser(String filePath) {
		this.fileName = filePath;
	}

	public List<Rule> parseBlackList() {
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

			Node blackListNode = doc.getElementsByTagName("blacklist").item(0);

			NodeList ruleNodeList = ((Element) blackListNode)
					.getElementsByTagName("rule");

			for (int i = 0; i < ruleNodeList.getLength(); i++) {
				System.out.println("----------------------------");
				System.out.println("nList:" + ruleNodeList.getLength());

				Node ruleNode = ruleNodeList.item(i);

				Rule rule = createRule(ruleNode);
				nodes.add(rule);

				System.out.println("\nCurrent Element :"
						+ ruleNode.getNodeName());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nodes;
	}

	private Rule createRule(Node ruleNode) {
		Rule rule = null;
		if (ruleNode.getNodeType() == Node.ELEMENT_NODE) {
			rule = new Rule();
			Element ruleElement = (Element) ruleNode;
			String priority = ruleElement.getAttribute("priority");
			rule.setPriority(Integer.parseInt(priority));
			System.out.println("Priorty: " + priority);

			String name = ruleElement.getElementsByTagName("name").item(0)
					.getTextContent();
			rule.setName(name);
			System.out.println("Name: " + name);

			Node conditionElement = ruleElement.getElementsByTagName(
					"condition").item(0);
			Condition condition = createCondition(conditionElement);
			rule.setCondition(condition);

			Node actionElement = ruleElement.getElementsByTagName("action")
					.item(0);

			Action action = createAction(actionElement);
			rule.setAction(action);
		}
		return rule;

		// for (int j = 0; j < nList1.getLength(); j++) {
		// System.out.println("nList1:" + nList1.getLength());
		// Node nNode1 = nList1.item(j);
		// if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
		// Element eElement1 = (Element) nNode1;
		//
		// System.out.println("Priority : "
		// + eElement1.getAttribute("priority"));
		// System.out.println("Name : "
		// + eElement1.getElementsByTagName("name").item(0)
		// .getTextContent());
		// for (int k = 0; k < nList2.getLength(); k++) {
		// System.out.println("nList2:" + nList2.getLength());
		// Node nNode2 = nList2.item(k);
		// if (nNode2.getNodeType() == Node.ELEMENT_NODE) {
		// System.out.println("\nCurrent Element2 :"
		// + nNode2.getNodeName());
		// for (int l = 0; l < nList3.getLength(); l++) {
		//
		// Node nNode3 = nList3.item(l);
		// if (nNode3.getNodeType() == Node.ELEMENT_NODE) {
		// Element eElement3 = (Element) nNode3;
		// System.out.println("\nCurrent Element3 :"
		// + nNode3.getNodeName());
		// System.out.println("host : "
		// + eElement3.getAttribute("host"));
		// System.out.println("oper : "
		// + eElement3.getAttribute("oper"));
		// System.out.println("class : "
		// + eElement3.getAttribute("class"));
		// System.out.println("oper : "
		// + eElement3.getAttribute("oper"));
		// System.out.println("class : "
		// + eElement3.getAttribute("class"));
		// System.out.println("location : "
		// + eElement3
		// .getAttribute("location"));
		// System.out.println("Name : "
		// + eElement3.getTextContent());
		// }
		// }
		//
		// }
		//
		// }
		// // System.out.println("action : "
		// // + eElement1.getElementsByTagName("action")
		// // .item(0).getTextContent());
		// for (int m = 0; m < nList1.getLength(); m++) {
		// Node nNode4 = nList4.item(m);
		// if (nNode4.getNodeType() == Node.ELEMENT_NODE) {
		// Element eElement4 = (Element) nNode4;
		// for (int n = 0; n < nList5.getLength(); n++) {
		//
		// Node nNode5 = nList5.item(n);
		// if (nNode5.getNodeType() == Node.ELEMENT_NODE) {
		// Element eElement5 = (Element) nNode5;
		// System.out.println("\nCurrent Element3 :"
		// + nNode5.getNodeName());
		// System.out.println("host : "
		// + eElement5.getAttribute("host"));
		// System.out.println("class : "
		// + eElement5.getAttribute("class"));
		// System.out.println("host : "
		// + eElement5.getAttribute("host"));
		// System.out.println("host : "
		// + eElement5.getAttribute("host"));
		// System.out.println("location : "
		// + eElement5
		// .getAttribute("location"));
		// System.out.println("Name : "
		// + eElement5.getTextContent());
		// }
		// }
		// }
		// }
		// }
		// }
		// }
	}

	private Action createAction(Node actionNode) {
		Action action = null;

		if (actionNode.getNodeType() == Node.ELEMENT_NODE) {
			action = new Action();
			Element aElement = (Element) actionNode;
			NodeList doElements = aElement.getElementsByTagName("do");
			for (int i = 0; i < doElements.getLength(); i++) {
				Node item = doElements.item(i);
				if (actionNode.getNodeType() == Node.ELEMENT_NODE) {

					Element doElement = (Element) item;

					Step step = new Step();
					step.setHost(doElement.getAttribute("host"));
					step.setLocation(doElement.getAttribute("location"));
					step.setSensorClass(doElement.getAttribute("class"));
					step.setState(doElement.getTextContent());
					System.out.println("Step :" + step);
					action.add(step);
				}
			}

		}
		return action;
	}

	private Condition createCondition(Node conditionNode) {
		Condition condition = null;
		if (conditionNode.getNodeType() == Node.ELEMENT_NODE) {
			condition = new Condition();
			Node next = conditionNode.getFirstChild();
			Predicate.Operands oper = null;
			while (next != null) {
				if (next.getNodeType() != Node.TEXT_NODE) {
					System.out.println("RuleParser.createRule()");
					String nodeName = next.getNodeName();
					System.out.println(nodeName);
					if (isConditionalOperator(nodeName)) {
						oper = Predicate.getOperand(nodeName);
					} else {
						Predicate pred = createPredicate(next, oper);
						oper = null;
						condition.addPredicate(pred);
					}
				}
				next = next.getNextSibling();
			}
		}
		return condition;
	}

	private Predicate createPredicate(Node next, Operands oper) {
		Predicate predicate = null;
		if (next.getNodeType() == Node.ELEMENT_NODE) {
			Element element = ((Element) next);
			if ("state".equals(next.getNodeName())) {
				SensorStatePredicate sensorStatePredicate = new SensorStatePredicate();
				sensorStatePredicate.setHost(element.getAttribute("host"));
				sensorStatePredicate.setOper(element.getAttribute("oper"));
				sensorStatePredicate.setSensorClass(element
						.getAttribute("class"));
				sensorStatePredicate.setLocation(element
						.getAttribute("location"));
				sensorStatePredicate.setNodeState(element.getTextContent());
				predicate = sensorStatePredicate;
			} else if ("time".equals(next.getNodeName())) {
				TimePredicate timepredicate = new TimePredicate();
				timepredicate.setStart(element.getAttribute("gt"));
				timepredicate.setEnd(element.getAttribute("lte"));
				predicate = timepredicate;
			}
			if (oper != null && predicate != null) {
				predicate.setChainedCondition(oper);
			}
		}
		return predicate;

	}

	private boolean isConditionalOperator(String nodeName) {
		if (Predicate.getOperand(nodeName) == null) {
			return false;
		} else
			return true;
	}

	public static void main(String argv[]) {
		RuleParser par = new RuleParser("rule-conf1.xml");
		par.parseBlackList();

	}
}
