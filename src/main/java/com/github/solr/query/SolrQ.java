package com.github.solr.query;

import com.github.solr.query.localparam.parent.Parent;
import com.github.solr.query.stage.BuilderOperator;

/**
 * Root entry point for building Solr query strings using a fluent API.
 * <p>
 * This class provides convenience methods for constructing complex queries with nested groups, local parameters (e.g. {!parent}, {!terms}), and logical operators. Each subcomponent is represented by a dedicated class for type-safe chaining.
 * </p>
 * 
 * @author Ilja Avelidi
 */
public final class SolrQ {
	
	/** Buffer used to accumulate the query string. */
	private final StringBuilder buffer = new StringBuilder();
	
	/**
	 * Appends a field expression to the query buffer. Equivalent to adding a Solr field expression (e.g., <code>name:value</code>).
	 *
	 * @param buffer
	 *            The active query buffer
	 * @param name
	 *            The field name
	 * @param val
	 *            The field value
	 */
	static void appendField(StringBuilder buffer, String name, String val) {
		buffer.append(name).append(":").append(val);
	}
	
	
	/**
	 * Adds a field expression (e.g., <code>title:hello</code>).
	 *
	 * @param name
	 *            The Solr field name
	 * @param val
	 *            The field value
	 * @return A {@link BuilderOperator} for chaining logical operators or finishing the query
	 */
	public BuilderOperator<Query<SolrQ>> field(String name, String val) {
		Query<SolrQ> q = new Query<>(this.buffer);
		appendField(this.buffer, name, val);
		return new BuilderOperator<>(q, this.buffer);
	}
	
	
	/**
	 * Opens a group using parentheses — equivalent to <code>( ... )</code> in Solr syntax. Used for logical grouping of multiple query expressions.
	 *
	 * @return a new {@link Group} instance for nested field and operator chaining
	 */
	public Group<BuilderOperator<SolrQ>> openGroup() {
		this.buffer.append("(");
		BuilderOperator<SolrQ> parent = new BuilderOperator<>(this, this.buffer);
		return new Group<>(parent, this.buffer);
	}
	
	
	/**
	 * Opens a Solr local parameter block with the <code>{!parent}</code> syntax. This is used to query parent documents based on child/parent relationships.
	 *
	 * @return a {@link Parent} instance to define which, v, and child query parts
	 */
	public Parent<BuilderOperator<SolrQ>> parent() {
		this.buffer.append("{!parent");
		BuilderOperator<SolrQ> parent = new BuilderOperator<>(this, this.buffer);
		return new Parent<>(parent, this.buffer);
	}
	
	/**
	 * Represents a nested Solr query context. Each {@link Query} object maintains a reference to the same shared buffer.
	 *
	 * @param <T>
	 *            The type of the parent query builder context
	 */
	public static class Query<T> {
		
		/** The shared query buffer. */
		private final StringBuilder buffer;
		
		/**
		 * Creates a new query section within the main query buffer.
		 *
		 * @param buffer
		 *            Shared query buffer
		 */
		public Query(StringBuilder buffer) {
			this.buffer = buffer;
		}
		
		
		/**
		 * Appends a field condition to this query. Equivalent to <code>field:value</code> in Solr syntax.
		 *
		 * @param name
		 *            The field name
		 * @param val
		 *            The field value
		 * @return A {@link BuilderOperator} for chaining
		 */
		public BuilderOperator<Query<T>> field(String name, String val) {
			appendField(this.buffer, name, val);
			return new BuilderOperator<>(this, this.buffer);
		}
		
		
		/**
		 * Opens a new logical group <code>(...)</code> within the current query.
		 *
		 * @return a {@link Group} context for nested expressions
		 */
		public Group<BuilderOperator<Query<T>>> openGroup() {
			this.buffer.append("(");
			BuilderOperator<Query<T>> parent = new BuilderOperator<>(this, this.buffer);
			return new Group<>(parent, this.buffer);
		}
		
		
		/**
		 * Opens a new <code>{!parent}</code> block from within this query context. Used to build queries that select parent documents based on child matches.
		 *
		 * @return a {@link Parent} instance for defining parent-local parameters
		 */
		public Parent<BuilderOperator<Query<T>>> parent() {
			this.buffer.append("{!parent");
			BuilderOperator<Query<T>> parent = new BuilderOperator<>(this, this.buffer);
			return new Parent<>(parent, this.buffer);
		}
	}
}
