package edu.uci.opim.util;

import java.io.Serializable;

/**
 * Wrapper class for sting , as string is final.
 * 
 * @author bram
 * 
 */
public class StringWrapper implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5002457854940528390L;
	public String string;

	public StringWrapper(String className) {
		this.string = className;
	}

	@Override
	public String toString() {

		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public String getString() {
		return string;
	}

}
