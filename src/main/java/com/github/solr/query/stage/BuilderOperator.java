package com.github.solr.query.stage;

/**
 * BuilderOperator represents a stage in the SolrQ fluent API where the user can finish building the query and retrieve it as a string.
 *
 * Example usage:
 * 
 * <pre>
 * 
 * SolrQ q = new SolrQ();
 * 
 * String query = q.field("name", "value").and().field("type", "x").build();
 * </pre>
 *
 * @param <T>
 *            the parent builder type (e.g., SolrQ, Query, Group)
 * @author Ilja Avelidi
 */
public class BuilderOperator<T> extends Operator<T> {
	
	/**
	 * Creates a new BuilderOperator.
	 *
	 * @param parent
	 *            the parent builder to return after building
	 * @param buffer
	 *            the shared query buffer
	 */
	public BuilderOperator(T parent, StringBuilder buffer) {
		
		super(parent, buffer);
		
	}
	
	
	/**
	 * Returns the current query as a string.
	 *
	 * @return the Solr query string built so far
	 */
	public String build() {
		
		return this.buffer.toString();
		
	}
	
}
