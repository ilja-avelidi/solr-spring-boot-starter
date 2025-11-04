package com.github.solr.event;

import com.github.solr.bean.AbstractSolrBean;

/**
 * Event used to trigger the asynchronous indexing of a Solr bean.
 * <p>
 * This event is intended exclusively for the {@link com.github.solr.indexing.SolrAsyncIndexer}, which listens to it and performs the actual indexing of the given {@link AbstractSolrBean}.
 * <p>
 * It is not meant for any other purpose and should not be misused.
 * 
 * Example usage:
 * 
 * <pre>
 * {@literal @}Autowired
 * private ApplicationEventPublisher publisher;
 * 
 * // trigger asynchronous indexing of a Solr bean
 * publisher.publishEvent(new SolrBeanIndexEvent(mySolrBean));
 * </pre>
 * 
 * @author Ilja Avelidi
 *
 */
public class SolrBeanIndexEvent extends SolrBeanEvent {
	
	/**  */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param solrBean
	 */
	public SolrBeanIndexEvent(AbstractSolrBean solrBean) {
		
		super(solrBean);
		
	}
}
