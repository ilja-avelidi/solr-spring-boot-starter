package com.github.solr.query.localparam.term;

/**
 * Builder for the f= clause inside a {!terms} query.
 * <p>
 * Allows specifying the field for the terms query and then returning to the Terms builder once done.
 *
 * Example Solr syntax:
 * 
 * <pre>
 * {!terms f=field_name}value1,value2
 * </pre>
 *
 * @param <T>
 *            the parent builder type (usually {@link Terms})
 * @author Ilja Avelidi
 */
public class F<T> {
	
	/**  */
	private final T parent;
	
	/**  */
	private final StringBuilder buffer;
	
	/**
	 * Creates a new F builder.
	 *
	 * @param parent
	 *            the parent builder (Terms)
	 * @param buffer
	 *            the shared query buffer
	 */
	public F(T parent, StringBuilder buffer) {
		this.parent = parent;
		this.buffer = buffer;
	}
	
	
	/**
	 * Sets the field name for the f= clause.
	 *
	 * @param name
	 *            the Solr field name
	 * @return the current F builder for chaining
	 */
	public F<T> field(String name) {
		this.buffer.append(name);
		return this;
	}
	
	
	/**
	 * Ends the f= clause and returns to the parent Terms builder.
	 *
	 * @return the parent Terms builder
	 */
	public T endF() {
		this.buffer.append("}");
		return this.parent;
	}
}
