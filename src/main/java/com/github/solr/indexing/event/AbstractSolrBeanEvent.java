package com.github.solr.indexing.event;

import org.springframework.context.ApplicationEvent;
import com.github.solr.bean.AbstractSolrBean;

/**
 * @author Ilja Avelidi
 *
 */
public abstract class AbstractSolrBeanEvent extends ApplicationEvent {
	
	/**  */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param solrBean
	 */
	public AbstractSolrBeanEvent(AbstractSolrBean solrBean) {
		
		super(solrBean);
		
	}
	
}
