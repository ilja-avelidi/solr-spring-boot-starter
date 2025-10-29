package com.github.solr.indexing;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import com.github.solr.bean.AbstractSolrBean;
import com.github.solr.event.SolrBeanEvent;
import com.github.solr.event.SolrBeanIndexEvent;
import com.github.solr.event.SolrBeanIndexedEvent;
import com.github.solr.event.SolrBeanRemoveEvent;
import com.github.solr.event.SolrBeanRemovedEvent;
import com.github.solr.repository.SolrBeanRepository;

/**
 * @author Ilja Avelidi
 *
 */
public class SolrAsyncIndexer {
	
	/**  */
	private final BlockingQueue<SolrBeanEvent> queue = new LinkedBlockingQueue<>(10_000);
	
	/**  */
	private final Logger log = LoggerFactory.getLogger(SolrAsyncIndexer.class);
	
	/**  */
	@Autowired
	private SolrBeanRepository solrBeanRepository;
	
	/**  */
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	/**
	 * @param event
	 */
	@Async
	@EventListener
	public void processEvent(SolrBeanEvent event) {
		
		try {
			
			this.queue.put(event);
			
		} catch (InterruptedException e) {
			
			this.log.warn("Failed to process event: {}", e.getMessage());
			
		}
		
	}
	
	
	/**
	 * @throws Exception
	 */
	@Scheduled(fixedRateString = "${solr.indexer.rate.ms:200}")
	public void processQueue() throws Exception {
		
		while (!this.queue.isEmpty()) {
			
			SolrBeanEvent event = this.queue.take();
			AbstractSolrBean solrBean = (AbstractSolrBean)event.getSource();
			
			String id = solrBean.getId();
			
			this.log.trace(String.format("Processing event: [BEAN-ID: %s, EVENT: %s]", id, event.getClass().getSimpleName()));
			
			try {
				
				if (event instanceof SolrBeanIndexEvent) {
					
					this.solrBeanRepository.save(solrBean);
					
					this.eventPublisher.publishEvent(new SolrBeanIndexedEvent(solrBean));
					
				}
				else if (event instanceof SolrBeanRemoveEvent) {
					
					this.solrBeanRepository.remove(id);
					
					this.eventPublisher.publishEvent(new SolrBeanRemovedEvent(solrBean));
					
				}
				
			} catch (Exception e) {
				
				this.log.warn("Error while processing queue event: {}", e.getMessage());
				
			}
			
		}
		
	}
	
}
