package com.github.solr.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.Http2SolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ilja Avelidi
 *
 */
@Configuration
public class SolrClientConfiguration {
	
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
	
}
