package com.github.solr.health;

import org.apache.solr.client.solrj.SolrClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * @author Ilja Avelidi
 *
 */
public class SolrConnectionVerifier implements ApplicationRunner {
	
	/**  */
	private final SolrClient solrClient;
	
	/**  */
	private final Logger log = LoggerFactory.getLogger(SolrConnectionVerifier.class);
	
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
			
		} catch (Exception e) {
			
			this.log.error("Failed to connect to Solr: {}", e.getMessage(), e);
			
		}
		
	}
	
}
