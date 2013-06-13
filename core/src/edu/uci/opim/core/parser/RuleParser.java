package edu.uci.opim.core.parser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
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

	public List<Condition> parseWhiteList(String str) {
		List<Condition> nodes = new ArrayList<Condition>();

		try {

			InputStream is = new ByteArrayInputStream(str.getBytes());

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(is);

			// optional, but recommended
			// read this -
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			System.out.println("Root element :"
					+ doc.getDocumentElement().getNodeName());

			NodeList conditionNodeList = doc.getElementsByTagName("condition");
			// NodeList conditionNodeList = ((Element) ruleNodeList)
			// .getElementsByTagName("condition");

			for (int i = 0; i < conditionNodeList.getLength(); i++) {
				System.out.println("----------------------------");
				System.out.println("nList:" + conditionNodeList.getLength());

				Node conditionNode = conditionNodeList.item(i);

				Condition createCondition = createCondition(conditionNode);
				nodes.add(createCondition);

				System.out.println("\nCurrent Element :"
						+ conditionNode.getNodeName());

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
					System.out.println("RuleParser.createCondition()");
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
		RuleParser par = new RuleParser("");
		par.parseBlackList();
		par.parseWhiteList("<condition> <state host=\"RF01\" oper=\"EQ\">StudentPresent</state> <and/> <time gt=\"22:00\" lte=\"06:00\"/> </condition>");

	}
}