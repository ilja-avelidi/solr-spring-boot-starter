package com.github.solr.bean;

import org.apache.solr.client.solrj.beans.Field;

/**
 * Base class for all Solr beans, providing common functionality.
 * <p>
 * This abstract class defines the unique identifier for the Solr document. All subclasses must implement the {@link #getId()} method to return the unique ID of the document in the Solr index.
 * <p>
 * The {@link #setId(String)} method is annotated with {@link Field} for Solr mapping purposes but does not perform any operation.
 * 
 * @author Ilja Avelidi
 */
public abstract class AbstractSolrBean {
	
	/**
	 * Returns the unique identifier of this Solr document.
	 * <p>
	 * Subclasses must implement this method to provide the document ID that will be stored and indexed in Solr.
	 *
	 * @return the unique id of this document
	 */
	public abstract String getId();
	
	
	/**
	 * Setter for the unique identifier. This method is used by SolrJ during bean mapping and is annotated with {@link Field}. It does not perform any operation by default.
	 *
	 * @param id
	 *            the unique identifier to set (ignored)
	 */
	@Field("id")
	void setId(@SuppressWarnings("unused") String id) {
		// no-op
	}
}
