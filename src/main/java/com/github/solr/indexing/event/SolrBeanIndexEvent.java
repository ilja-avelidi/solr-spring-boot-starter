package com.github.solr.indexing.event;

import com.github.solr.bean.AbstractSolrBean;

/**
 * @author Ilja Avelidi
 *
 */
public class SolrBeanIndexEvent extends AbstractSolrBeanEvent {
	
	/**  */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param solrBean
	 */
	public SolrBeanIndexEvent(AbstractSolrBean solrBean) {
		
		super(solrBean);
		
	}
}
