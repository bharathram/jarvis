package edu.uci.opim.core.rule;

import java.util.Iterator;
import java.util.List;

public class Condition {
	List<Predicate> predicates;

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
