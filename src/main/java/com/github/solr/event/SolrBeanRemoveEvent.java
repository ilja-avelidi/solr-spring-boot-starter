package com.github.solr.event;

import com.github.solr.bean.AbstractSolrBean;

/**
 * @author Ilja Avelidi
 *
 */
public class SolrBeanRemoveEvent extends SolrBeanEvent {
	
	/**  */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param solrBean
	 */
	public SolrBeanRemoveEvent(AbstractSolrBean solrBean) {
		
		super(solrBean);
		
	}
}
