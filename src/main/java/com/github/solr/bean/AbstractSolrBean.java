package com.github.solr.bean;

import org.apache.solr.client.solrj.beans.Field;

/**
 * @author Ilja Avelidi
 *
 */
public abstract class AbstractSolrBean {
	
	/**
	 * Returns the unique identifier of this Solr Bean.
	 * 
	 * @return the unique id
	 */
	public abstract String getId();
	
	
	/**
	 * @param id
	 */
	@Field("id")
	void setId(@SuppressWarnings("unused") String id) {
		
		// no-op
		
	}
}
