package com.github.solr.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * @author Ilja Avelidi
 *
 */
@AutoConfiguration
@Import(SolrStarterConfiguration.class)
public class SolrAutoConfiguration {
	
	// no-op
	
}
