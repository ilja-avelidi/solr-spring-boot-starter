package com.github.solr.health;

import org.apache.solr.client.solrj.SolrClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationEventPublisher;
import com.github.solr.event.SolrReadyEvent;

/**
 * Verifies the connectivity to a Solr core on application startup.
 * <p>
 * Implements {@link ApplicationRunner} to perform the check after the Spring Boot application context is initialized. If the connection is successful, it publishes a {@link SolrReadyEvent} to signal that Solr is ready for use.
 * <p>
 * In case of a failed connection, the error is logged. This component ensures that other parts of the application can safely interact with Solr only after it is available.
 * 
 * @author Ilja Avelidi
 *
 */
public class SolrConnectionVerifier implements ApplicationRunner {
	
	/**  */
	private final SolrClient solrClient;
	
	/**  */
	private final Logger log = LoggerFactory.getLogger(SolrConnectionVerifier.class);
	
	/**  */
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	/**  */
	@Value("${solr.core.url}")
	private String solrCoreUrl;
	
	/**
	 * @param solrClient
	 */
	public SolrConnectionVerifier(SolrClient solrClient) {
		
		this.solrClient = solrClient;
		
	}
	
	
	/**
	 * @see org.springframework.boot.ApplicationRunner#run(org.springframework.boot.ApplicationArguments)
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		try {
			
			this.log.info("Starting Solr connection check...");
			
			this.solrClient.ping();
			
			this.log.info("Successfully connected to Solr");
			
			this.eventPublisher.publishEvent(new SolrReadyEvent(this.solrCoreUrl));
			
		} catch (Exception e) {
			
			this.log.error("Failed to connect to Solr: {}", e.getMessage(), e);
			
		}
		
	}
	
}
