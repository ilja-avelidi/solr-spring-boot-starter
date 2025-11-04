package com.github.solr.query.localparam.parent;

import com.github.solr.query.localparam.term.Terms;
import com.github.solr.query.stage.ChildrenOperator;

/**
 * Builder for adding conditions related to child documents after the {@code {!parent}} block has been closed.
 * <p>
 * This enables chaining additional constraints that apply to children in the resulting Solr query.
 *
 * @param <T>
 *            the parent builder type
 * @author Ilja Avelidi
 */
public class ParentChildren<T> {
	
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
	public ParentChildren(T parent, StringBuilder buffer) {
		this.parent = parent;
		this.buffer = buffer;
	}
	
	
	/**
	 * Adds a Solr field expression (e.g. {@code name:value}) for child conditions.
	 *
	 * @param name
	 *            field name
	 * @param val
	 *            field value
	 * @return a {@link ChildrenOperator} builder for logical chaining
	 */
	public ChildrenOperator<ParentChildren<T>, T> field(String name, String val) {
		this.buffer.append(name).append(":").append(val);
		return new ChildrenOperator<>(this, this.parent, this.buffer);
	}
	
	
	/**
	 * Starts a {@code {!terms}} LocalParam expression as a child condition.
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * {!terms f=child_id_s}123,456,789
	 * </pre>
	 *
	 * @return a {@link Terms} builder
	 */
	public Terms<ParentChildren<T>> terms() {
		this.buffer.append("{!terms");
		return new Terms<>(this, this.buffer);
	}
}
