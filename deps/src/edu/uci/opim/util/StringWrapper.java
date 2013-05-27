package edu.uci.opim.util;

/**
 * Wrapper class for sting , as string is final.
 * 
 * @author bram
 * 
 */
public class StringWrapper {
	public final String string;

	public StringWrapper(String className) {
		this.string = className;
	}

	@Override
	public String toString() {

		return string;
	}

}
