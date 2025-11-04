package com.github.solr.query.stage;

/**
 * Operator class specifically for Group to allow closing the group.
 *
 * @param <T>
 *            current builder type
 * @param <G>
 *            parent builder type after closing the group
 * @author Ilja Avelidi
 */
public class GroupOperator<T, G> extends Operator<T> {
	
	/**  */
	private final G groupParent;
	
	/**
	 * @param parent
	 * @param groupParent
	 * @param buffer
	 */
	public GroupOperator(T parent, G groupParent, StringBuilder buffer) {
		super(parent, buffer);
		this.groupParent = groupParent;
	}
	
	
	/**
	 * Closes the current group and returns the outer parent builder.
	 *
	 * @return the outer parent builder
	 */
	public G closeGroup() {
		this.buffer.append(")");
		return this.groupParent;
	}
	
}
