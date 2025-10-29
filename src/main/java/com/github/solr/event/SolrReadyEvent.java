package com.github.solr.event;

import org.springframework.context.ApplicationEvent;

/**
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
