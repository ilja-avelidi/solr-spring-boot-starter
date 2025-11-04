package com.github.solr.query.localparam.parent;

import com.github.solr.query.stage.WhichOperator;

/**
 * Builder for the {@code which} clause inside a {@code {!parent}} query.
 * <p>
 * The {@code which} clause defines the filter condition used to identify the parent documents in Solr.
 * <p>
 * Example Solr syntax:
 * 
 * <pre>
 * {!parent which=(type_s:parent)}
 * </pre>
 *
 * @param <T>
 *            the parent builder type
 * @author Ilja Avelidi
 */
public class ParentWhich<T> {
	
	/**  */
	private final T parent;
	
	/**  */
	private final StringBuilder buffer;
	
	/**
	 * @param parent
	 *            the parent builder
	 * @param buffer
	 *            the shared query buffer
	 */
	public ParentWhich(T parent, StringBuilder buffer) {
		this.parent = parent;
		this.buffer = buffer;
	}
	
	
	/**
	 * Adds a field expression to the {@code which} clause.
	 * <p>
	 * Equivalent to adding a Solr field condition (e.g. {@code name:value}).
	 *
	 * @param name
	 *            field name
	 * @param val
	 *            field value
	 * @return a {@link WhichOperator} builder for further logical operations
	 */
	public WhichOperator<ParentWhich<T>, T> field(String name, String val) {
		this.buffer.append(name).append(":").append(val);
		return new WhichOperator<>(this, this.parent, this.buffer);
	}
}
