package com.github.solr.query.stage;

/**
 * Builder for the which= clause inside a {!parent} Solr query.
 * <p>
 * Allows specifying additional field conditions for the parent filter and then returning to the parent builder.
 *
 * Example Solr syntax:
 * 
 * <pre>
 * {!parent which=(type_s:parentType)}childField:value
 * </pre>
 *
 * @param <T>
 *            the current builder type (e.g., ParentWhich)
 * @param <W>
 *            the parent builder type to return after ending the which block
 * @author Ilja Avelidi
 */
public class WhichOperator<T, W> extends Operator<T> {
	
	/**  */
	private final W whichParent;
	
	/**
	 * Creates a new WhichOperator.
	 *
	 * @param parent
	 *            the current builder (ParentWhich)
	 * @param whichParent
	 *            the parent builder to return after ending which block
	 * @param buffer
	 *            the shared query buffer
	 */
	public WhichOperator(T parent, W whichParent, StringBuilder buffer) {
		super(parent, buffer);
		this.whichParent = whichParent;
	}
	
	
	/**
	 * Ends the which= clause and returns to the parent builder.
	 *
	 * @return the parent builder
	 */
	public W endWhich() {
		this.buffer.append(")");
		return this.whichParent;
	}
}
