package com.github.solr.query.stage;

/**
 * Base class for logical operators like AND/OR.
 *
 * @param <T>
 *            the type of the parent builder
 * @author Ilja Avelidi
 */
public class Operator<T> {
	
	/**  */
	protected final T parent;
	
	/**  */
	protected final StringBuilder buffer;
	
	/**
	 * @param parent
	 * @param buffer
	 */
	public Operator(T parent, StringBuilder buffer) {
		this.parent = parent;
		this.buffer = buffer;
	}
	
	
	/**
	 * Appends AND operator in Solr query.
	 *
	 * @return parent builder
	 */
	public T and() {
		this.buffer.append(" AND ");
		return this.parent;
	}
	
	
	/**
	 * Appends OR operator in Solr query.
	 *
	 * @return parent builder
	 */
	public T or() {
		this.buffer.append(" OR ");
		return this.parent;
	}
}
