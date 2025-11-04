package com.github.solr.event;

import com.github.solr.bean.AbstractSolrBean;

/**
 * Event that is published when a Solr bean has been successfully indexed.
 * <p>
 * This event wraps the {@link AbstractSolrBean} that was indexed, allowing listeners to react to the indexing operation, for example, to trigger additional processing, logging, or caching.
 * 
 * @author Ilja Avelidi
 *
 */
public class SolrBeanIndexedEvent extends SolrBeanEvent {
	
	/**  */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param solrBean
	 */
	public SolrBeanIndexedEvent(AbstractSolrBean solrBean) {
		
		super(solrBean);
		
	}
	
}
