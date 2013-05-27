package edu.uci.opim.core.exception;

import edu.uci.opim.core.action.Step;

public class UnableToExecuteStepException extends RuntimeException {
	public final Step step;

	public UnableToExecuteStepException(Step step) {
		this.step = step;

	}

}
