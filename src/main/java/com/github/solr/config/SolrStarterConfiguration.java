package com.github.solr.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.Http2SolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.github.solr.health.SolrConnectionVerifier;
import com.github.solr.indexing.SolrAsyncIndexer;
import com.github.solr.repository.SolrBeanRepository;
import com.github.solr.repository.SolrBeanRepositoryImpl;

/**
 * @author Ilja Avelidi
 *
 */
@Configuration
@EnableScheduling
public class SolrStarterConfiguration {
	
	/**  */
	@Value("${solr.core.url}")
	private String solrCoreUrl;
	
	/**
	 * @return solr client
	 */
	@Bean
	public SolrClient solrClient() {
		
		return new Http2SolrClient.Builder(this.solrCoreUrl)
				.build();
	}
	
	
	/**
	 * @param solrClient
	 * @return solr connection verifier
	 */
	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public SolrConnectionVerifier solrConnectionVerifier(SolrClient solrClient) {
		
		return new SolrConnectionVerifier(solrClient);
		
	}
	
	
	/**
	 * @return solr async indexer
	 */
	@Bean
	public SolrAsyncIndexer solrAsyncIndexer() {
		
		return new SolrAsyncIndexer();
		
	}
	
	
	/**
	 * @return solr bean repository
	 */
	@Bean
	public SolrBeanRepository solrBeanRepository() {
		
		return new SolrBeanRepositoryImpl();
		
	}
	
}
