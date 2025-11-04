package com.github.solr.event;

import org.springframework.context.ApplicationEvent;

/**
 * Event that is published when the Solr client is fully initialized and ready to use.
 * <p>
 * This event carries the Solr core URL as its source and can be used to notify other components that they can safely interact with Solr.
 * <p>
 * It is intended for internal use to signal Solr readiness and should not be misused for unrelated purposes.
 * 
 * Example usage:
 * 
 * <pre>
 * {@literal @}EventListener
 * public void handleSolrReady(SolrReadyEvent event) {
 *     String solrCoreUrl = (String) event.getSource();
 *     // perform actions that require Solr to be ready
 * }
 * </pre>
 * 
 * @author Ilja Avelidi
 *
 */
public class SolrReadyEvent extends ApplicationEvent {
	
	/**  */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param solrUrl
	 */
	public SolrReadyEvent(String solrUrl) {
		
		super(solrUrl);
		
	}
	
}
