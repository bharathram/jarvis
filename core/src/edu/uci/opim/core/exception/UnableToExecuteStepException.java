package edu.uci.opim.core.exception;

import edu.uci.opim.core.action.Step;

public class UnableToExecuteStepException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8751317748154621462L;
	public final Step step;

	public UnableToExecuteStepException(Step step) {
		this.step = step;
	}

	public UnableToExecuteStepException(Step step, String msg) {
		super(msg);
		this.step = step;

	}
}
