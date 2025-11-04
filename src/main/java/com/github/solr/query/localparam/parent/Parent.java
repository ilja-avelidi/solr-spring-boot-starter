package com.github.solr.query.localparam.parent;

import com.github.solr.query.stage.VOperator;

/**
 * Represents a Solr LocalParams expression using the {@code {!parent}} query parser.
 * <p>
 * The {@code {!parent}} parser allows joining parent and child documents inside Solr to query across nested document structures.
 * <p>
 * Example Solr equivalent:
 * 
 * <pre>
 * {!parent which=type_s:parent v='child_field:value'}
 * </pre>
 *
 * <p>
 * This class acts as a fluent builder for constructing such expressions. It supports defining the <b>which</b> clause (the filter identifying parent docs), the <b>v</b> clause (the query applied to children), and optional follow-up children
 * conditions.
 *
 * @param <T>
 *            the parent builder type to return to after closing the parent clause
 * @author Ilja Avelidi
 * @see <a href="https://solr.apache.org/guide/solr/latest/query-guide/block-join-query-parser.html"> Solr Documentation: Block Join Queries</a>
 */
public class Parent<T> {
	
	/** Reference to the parent builder */
	private final T parent;
	
	/** Shared query buffer */
	private final StringBuilder buffer;
	
	/** Buffer for the 'which' clause */
	private final StringBuilder which = new StringBuilder();
	
	/** Buffer for the 'v' clause */
	private final StringBuilder v = new StringBuilder();
	
	/** Buffer for appended child conditions after parent query */
	private final StringBuilder children = new StringBuilder();
	
	/**
	 * Creates a new {@code Parent} builder.
	 *
	 * @param parent
	 *            the parent builder
	 * @param buffer
	 *            the shared query buffer
	 */
	public Parent(T parent, StringBuilder buffer) {
		this.parent = parent;
		this.buffer = buffer;
	}
	
	
	/**
	 * Starts defining the {@code which} clause of the parent query.
	 * <p>
	 * Equivalent Solr syntax:
	 * 
	 * <pre>
	 * {!parent which=(...)
	 * </pre>
	 *
	 * @return a {@link ParentWhich} builder for the "which" clause
	 */
	public ParentWhich<Parent<T>> which() {
		this.which.append(" which=(");
		return new ParentWhich<>(this, this.which);
	}
	
	
	/**
	 * Starts defining the {@code v} clause of the parent query.
	 * <p>
	 * Equivalent Solr syntax:
	 * 
	 * <pre>
	 * {!parent ... v='...'
	 * </pre>
	 *
	 * @return a {@link ParentV} builder for the "v" clause
	 */
	public ParentV<Parent<T>> v() {
		this.v.append(" v=('");
		return new ParentV<>(this, this.v);
	}
	
	
	/**
	 * Starts defining additional children conditions that will be appended after the closing parent block.
	 *
	 * @return a {@link ParentChildren} builder
	 */
	public ParentChildren<Parent<T>> children() {
		return new ParentChildren<>(this, this.children);
	}
	
	
	/**
	 * Completes the parent expression and returns control to the parent builder.
	 * <p>
	 * Example final structure:
	 * 
	 * <pre>
	 * {!parent which=(type_s:parent) v=('child_field:value')}child_field2:val2
	 * </pre>
	 *
	 * @return the parent builder instance
	 */
	public T endParent() {
		this.buffer.append(this.which);
		if (!this.v.isEmpty())
			this.buffer.append(this.v);
		this.buffer.append("}");
		this.buffer.append(this.children);
		return this.parent;
	}
}


/**
 * Builder for the {@code v} clause inside a {@code {!parent}} query.
 * <p>
 * The {@code v} clause defines the query that applies to the child documents.
 * <p>
 * Example Solr syntax:
 * 
 * <pre>
 * {!parent which=(type_s:parent)v='child_field:value'}
 * </pre>
 *
 * @param <T>
 *            the parent builder type
 */
class ParentV<T> {
	
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
	public ParentV(T parent, StringBuilder buffer) {
		this.parent = parent;
		this.buffer = buffer;
	}
	
	
	/**
	 * Adds a field condition to the {@code v} clause.
	 *
	 * @param name
	 *            field name
	 * @param val
	 *            field value
	 * @return a {@link VOperator} builder for further logic or closure
	 */
	public VOperator<ParentV<T>, T> field(String name, String val) {
		this.buffer.append(name).append(":").append(val);
		return new VOperator<>(this, this.parent, this.buffer);
	}
}
