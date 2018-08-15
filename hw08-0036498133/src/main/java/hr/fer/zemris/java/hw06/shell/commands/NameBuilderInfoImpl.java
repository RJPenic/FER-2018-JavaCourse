package hr.fer.zemris.java.hw06.shell.commands;

import java.util.regex.Matcher;

/**
 * Class representing an implementation of the NameBuilderInfo interface
 * 
 * @author Rafael Josip Penić
 *
 */
public class NameBuilderInfoImpl implements NameBuilderInfo {
	/**
	 * Matcher used to check if the given name matches the determined regex
	 */
	private Matcher m;

	/**
	 * String builder used when building a new name
	 */
	private StringBuilder sb;

	/**
	 * Constructor for the NameBuilderInfoImpl objects
	 * 
	 * @param m
	 *            Matcher of the object that will be constructed
	 */
	public NameBuilderInfoImpl(Matcher m) {
		this.m = m;
		sb = new StringBuilder();
	}

	@Override
	public StringBuilder getStringBuilder() {
		return sb;
	}

	@Override
	public String getGroup(int index) {
		return m.group(index);
	}

}
