package com.github.solr.event;

import org.springframework.context.ApplicationEvent;
import com.github.solr.bean.AbstractSolrBean;

/**
 * Base class for all Solr-related Spring events.
 * <p>
 * This event wraps a {@link AbstractSolrBean} and can be extended to represent specific Solr actions, such as indexing, updating, or deleting a document.
 * <p>
 * Subclasses can provide more context or additional metadata for event listeners.
 * 
 * @author Ilja Avelidi
 *
 */
public abstract class SolrBeanEvent extends ApplicationEvent {
	
	/**  */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param solrBean
	 */
	public SolrBeanEvent(AbstractSolrBean solrBean) {
		
		super(solrBean);
		
	}
	
}
