package com.github.solr.event;

import com.github.solr.bean.AbstractSolrBean;

/**
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
