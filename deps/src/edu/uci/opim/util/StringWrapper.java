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

	@Override
	public boolean equals(Object obj) {
		String o;
		if (obj instanceof StringWrapper) {
			o = ((StringWrapper) obj).string;
		} else if (obj instanceof String) {
			o = (String) obj;
		} else {
			return false;
		}

		return string.equals(o);

	}

	@Override
	public int hashCode() {
		return string.hashCode();

	}

}
