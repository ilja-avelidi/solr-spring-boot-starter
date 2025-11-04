package com.github.solr.query.stage;

/**
 * Builder for the v= clause inside a {!parent} Solr query.
 * <p>
 * Allows specifying the value/query for the parent block and then returning to the parent builder.
 *
 * Example Solr syntax:
 * 
 * <pre>
 * {!parent which=type_s:parentType v='childField:value'}
 * </pre>
 *
 * @param <T>
 *            the current builder type (e.g., ParentV)
 * @param <V>
 *            the parent builder type to return after closing the v block
 * @author Ilja Avelidi
 */
public class VOperator<T, V> extends Operator<T> {
	
	/**  */
	private final V vParent;
	
	/**
	 * Creates a new VOperator.
	 *
	 * @param parent
	 *            the current builder (ParentV)
	 * @param vParent
	 *            the parent builder to return after ending v block
	 * @param buffer
	 *            the shared query buffer
	 */
	public VOperator(T parent, V vParent, StringBuilder buffer) {
		super(parent, buffer);
		this.vParent = vParent;
	}
	
	
	/**
	 * Ends the v= clause and returns to the parent builder.
	 *
	 * @return the parent builder
	 */
	public V endV() {
		this.buffer.append("')");
		return this.vParent;
	}
}
