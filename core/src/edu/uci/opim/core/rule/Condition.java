package edu.uci.opim.core.rule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Condition {
	List<Predicate> predicates;

	public Condition() {
		// TODO Auto-generated constructor stub
		predicates = new ArrayList<Predicate>();
	}

	public boolean evaluate() {
		boolean cond = false;
		Iterator<Predicate> iterator = predicates.iterator();
		// TODO:Handle braces
		while (iterator.hasNext()) {
			Predicate predicate = (Predicate) iterator.next();
			cond = Predicate.evaluate(cond, predicate);
		}
		return cond;
	}

	public void addPredicate(Predicate predicate) {
		predicates.add(predicate);
	}

}
