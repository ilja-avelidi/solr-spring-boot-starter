package com.github.solr.indexing.event;

import com.github.solr.bean.AbstractSolrBean;

/**
 * @author Ilja Avelidi
 *
 */
public class SolrBeanRemoveEvent extends AbstractSolrBeanEvent {
	
	/**  */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param solrBean
	 */
	public SolrBeanRemoveEvent(AbstractSolrBean solrBean) {
		
		super(solrBean);
		
	}
}
