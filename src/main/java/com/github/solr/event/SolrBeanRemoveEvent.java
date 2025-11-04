package com.github.solr.event;

import com.github.solr.bean.AbstractSolrBean;

/**
 * Event used to request the removal of a Solr bean from the index.
 * <p>
 * This event is intended exclusively for the {@link com.github.solr.indexing.SolrAsyncIndexer}, which listens to it and performs the actual removal of the given {@link AbstractSolrBean} from the Solr index.
 * <p>
 * It is not meant for any other purpose and should not be misused.
 * 
 * Example usage:
 * 
 * <pre>
 * {@literal @}Autowired
 * private ApplicationEventPublisher publisher;
 * 
 * // request asynchronous removal of a Solr bean from the index
 * publisher.publishEvent(new SolrBeanRemoveEvent(mySolrBean));
 * </pre>
 * 
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
