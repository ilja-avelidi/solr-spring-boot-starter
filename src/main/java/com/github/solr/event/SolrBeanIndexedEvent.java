package com.github.solr.event;

import com.github.solr.bean.AbstractSolrBean;

/**
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
