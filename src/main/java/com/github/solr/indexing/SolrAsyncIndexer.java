package com.github.solr.indexing;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.github.solr.bean.AbstractSolrBean;
import com.github.solr.indexing.event.AbstractSolrBeanEvent;
import com.github.solr.indexing.event.SolrBeanIndexEvent;
import com.github.solr.indexing.event.SolrBeanRemoveEvent;
import com.github.solr.repository.SolrBeanRepository;

/**
 * @author Ilja Avelidi
 *
 */
@Component
public class SolrAsyncIndexer {
	
	/**  */
	private final BlockingQueue<AbstractSolrBeanEvent> queue = new LinkedBlockingQueue<>(10_000);
	
	/**  */
	private final Logger log = LoggerFactory.getLogger(SolrAsyncIndexer.class);
	
	/**  */
	@Autowired
	private SolrBeanRepository solrBeanRepository;
	
	/**
	 * @param event
	 */
	@Async
	@EventListener
	public void processEvent(AbstractSolrBeanEvent event) {
		
		try {
			
			this.queue.put(event);
			
		} catch (InterruptedException e) {
			
			this.log.error("Failed to process event: {}", e.getMessage());
			
		}
		
	}
	
	
	/**
	 * @throws Exception
	 */
	@Scheduled(fixedRateString = "${solr.indexer.rate.ms:200}")
	public void processQueue() throws Exception {
		
		while (!this.queue.isEmpty()) {
			
			AbstractSolrBeanEvent event = this.queue.take();
			AbstractSolrBean solrBean = (AbstractSolrBean)event.getSource();
			
			String id = solrBean.getId();
			
			this.log.trace(String.format("Processing event: [BEAN-ID: %s, EVENT: %s]", id, event.getClass().getSimpleName()));
			
			try {
				
				switch (event) {
					
					case SolrBeanIndexEvent e -> this.solrBeanRepository.save(solrBean);
				
					case SolrBeanRemoveEvent e -> this.solrBeanRepository.remove(id);
				
					default -> throw new IllegalArgumentException("Unexpected type: " + event);
				
				}
				
			} catch (Exception e) {
				
				this.log.error("Failed to process event: {}", e.getMessage(), e);
				
			}
			
		}
		
	}
	
}
