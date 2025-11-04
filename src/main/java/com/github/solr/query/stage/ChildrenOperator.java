package com.github.solr.query.stage;

/**
 * Represents a Solr child operator used in conjunction with Parent or Group queries.
 * <p>
 * This class allows chaining further child field conditions or ending the child block and returning to the parent builder.
 * <p>
 * Equivalent Solr usage:
 * 
 * <pre>
 * {!parent which=type_s:parent v='...'}child_field:value
 * </pre>
 *
 * @param <T>
 *            the current builder type (e.g., ParentChildren)
 * @param <C>
 *            the parent builder type to return after ending the children block
 * @author Ilja Avelidi
 */
public class ChildrenOperator<T, C> extends Operator<T> {
	
	/**  */
	private final C childrenParent;
	
	/**
	 * Creates a new ChildrenOperator.
	 *
	 * @param parent
	 *            the current builder (e.g., ParentChildren)
	 * @param childrenParent
	 *            the parent builder to return after ending the children block
	 * @param buffer
	 *            the shared StringBuilder holding the query
	 */
	public ChildrenOperator(T parent, C childrenParent, StringBuilder buffer) {
		super(parent, buffer);
		this.childrenParent = childrenParent;
	}
	
	
	/**
	 * Ends the current child operator and returns control to the parent builder.
	 *
	 * @return the parent builder
	 */
	public C endChildren() {
		return this.childrenParent;
	}
}
