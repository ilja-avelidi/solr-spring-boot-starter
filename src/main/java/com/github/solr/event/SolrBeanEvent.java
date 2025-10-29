package com.github.solr.event;

import org.springframework.context.ApplicationEvent;
import com.github.solr.bean.AbstractSolrBean;

/**
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
