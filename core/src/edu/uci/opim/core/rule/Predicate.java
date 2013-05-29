package edu.uci.opim.core.rule;

import edu.uci.opim.core.StateChangedEvent;

public abstract class Predicate {
	public static enum Operands {
		AND, OR;
	};

	public static Predicate.Operands getOperand(String oper) {
		if ("and".equals(oper))
			return Operands.AND;
		if ("or".equals(oper))
			return Operands.OR;
		return null;
	}

	private Operands chainedCondition;

	public abstract boolean evaluate(StateChangedEvent evt);

	public void setChainedCondition(Operands chainedCondition) {
		this.chainedCondition = chainedCondition;
	}

	public static boolean evaluate(boolean start, Predicate predicate,
			StateChangedEvent evt) {
		boolean result;
		switch (predicate.chainedCondition) {
		case AND:
			result = start && predicate.evaluate(evt);
			break;
		case OR:
			result = start || predicate.evaluate(evt);
		default:
			result = predicate.evaluate(evt);
			break;
		}
		return result;
	}

}
