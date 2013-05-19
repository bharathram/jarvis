package edu.uci.opim.core.rule;

public abstract class Predicate {
	public static enum Operands {
		AND, OR;
	};

	private Operands chainedCondition;

	public abstract boolean evaluate();

	public void setChainedCondition(Operands chainedCondition) {
		this.chainedCondition = chainedCondition;
	}

	public static boolean evaluate(boolean start, Predicate predicate) {
		boolean result;
		switch (predicate.chainedCondition) {
		case AND:
			result = start && predicate.evaluate();
			break;
		case OR:
			result = start || predicate.evaluate();
		default:
			result = predicate.evaluate();
			break;
		}
		return result;
	}

}
