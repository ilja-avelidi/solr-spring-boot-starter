package com.github.solr.query;

import com.github.solr.query.localparam.parent.Parent;
import com.github.solr.query.stage.GroupOperator;

/**
 * Represents a Solr query group using parentheses, equivalent to (<expr> AND|OR <expr>).
 * <p>
 * This class supports nested groups and allows chaining of fields and logical operators. It also supports creating a parent block inside the group with {@link #parent()}.
 *
 * Example Solr syntax:
 * 
 * <pre>
 * (field1:value1 AND field2:value2)
 * </pre>
 *
 * @param <T>
 *            the type of the parent builder to return after closing this group
 * @author Ilja Avelidi
 */
public class Group<T> {
	
	/**  */
	private final T parent;
	
	/**  */
	private final StringBuilder buffer;
	
	/**
	 * Creates a new Group builder.
	 *
	 * @param parent
	 *            the parent builder
	 * @param buffer
	 *            the shared query buffer
	 */
	public Group(T parent, StringBuilder buffer) {
		this.parent = parent;
		this.buffer = buffer;
		this.buffer.append("(");
	}
	
	
	/**
	 * Adds a field expression inside this group. Equivalent to field:value in Solr.
	 *
	 * @param name
	 *            field name
	 * @param val
	 *            field value
	 * @return a GroupOperator for chaining logical operators (AND, OR)
	 */
	public GroupOperator<Group<T>, T> field(String name, String val) {
		SolrQ.appendField(this.buffer, name, val);
		return new GroupOperator<>(this, this.parent, this.buffer);
	}
	
	
	/**
	 * Opens a nested group inside this group.
	 *
	 * @return a new nested Group
	 */
	public Group<Group<T>> openGroup() {
		return new Group<>(this, this.buffer);
	}
	
	
	/**
	 * Closes the current group and returns to the parent builder.
	 *
	 * @return the parent builder
	 */
	public T closeGroup() {
		this.buffer.append(")");
		return this.parent;
	}
	
	
	/**
	 * Opens a Solr parent block inside this group.
	 *
	 * @return a Parent builder representing {!parent ...} inside this group
	 */
	public Parent<GroupOperator<Group<T>, T>> parent() {
		GroupOperator<Group<T>, T> groupOp = new GroupOperator<>(this, this.parent, this.buffer);
		return new Parent<>(groupOp, this.buffer);
	}
}
