package com.github.solr.event;

import com.github.solr.bean.AbstractSolrBean;

/**
 * Event that is published after a Solr bean has been removed from the index.
 * <p>
 * This event is intended to notify listeners that the given {@link AbstractSolrBean} has been successfully removed from the Solr index.
 * <p>
 * It is not meant for triggering removal or any other purpose.
 * 
 * Example usage:
 * 
 * <pre>
 * {@literal @}EventListener
 * public void handleRemoval(SolrBeanRemovedEvent event) {
 *     AbstractSolrBean removedBean = event.getSource();
 *     // perform post-removal actions, e.g., cache cleanup or logging
 * }
 * </pre>
 * 
 * @author Ilja Avelidi
 *
 */
public class SolrBeanRemovedEvent extends SolrBeanEvent {
	
	/**  */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param solrBean
	 */
	public SolrBeanRemovedEvent(AbstractSolrBean solrBean) {
		
		super(solrBean);
		
	}
	
}
