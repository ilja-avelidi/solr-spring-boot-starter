package com.github.solr.query.localparam.term;

import java.util.List;
import org.springframework.util.StringUtils;

/**
 * Represents a Solr {!terms} local parameter query.
 * <p>
 * This builder allows constructing a {!terms} query with fields (f=...) and multiple values, producing syntax like:
 * 
 * <pre>
 * {!terms f=field_name}value1,value2,value3
 * </pre>
 *
 * @param <T>
 *            the parent builder type to return after ending the terms block
 * @author Ilja Avelidi
 */
public class Terms<T> {
	
	/**  */
	private final T parent;
	
	/**  */
	private final StringBuilder buffer;
	
	/**
	 * Creates a new Terms builder.
	 *
	 * @param parent
	 *            the parent builder (e.g., ParentChildren)
	 * @param buffer
	 *            the shared query buffer
	 */
	public Terms(T parent, StringBuilder buffer) {
		this.parent = parent;
		this.buffer = buffer;
	}
	
	
	/**
	 * Starts the f= part of the {!terms} query.
	 *
	 * @return a {@link F} builder for defining the field
	 */
	public F<Terms<T>> f() {
		this.buffer.append(" f=");
		return new F<>(this, this.buffer);
	}
	
	
	/**
	 * Adds a single value to the terms query.
	 *
	 * @param val
	 *            the value to add
	 * @return the current Terms builder for chaining
	 */
	public Terms<T> value(String val) {
		this.buffer.append(val);
		return this;
	}
	
	
	/**
	 * Adds multiple values to the terms query, comma-separated.
	 *
	 * @param vals
	 *            list of values to add
	 * @return the current Terms builder for chaining
	 */
	public Terms<T> values(List<String> vals) {
		this.buffer.append(StringUtils.collectionToDelimitedString(vals, ","));
		return this;
	}
	
	
	/**
	 * Ends the terms block and returns to the parent builder.
	 *
	 * @return the parent builder
	 */
	public T endTerms() {
		return this.parent;
	}
}
