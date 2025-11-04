package com.github.solr.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * Spring Boot auto-configuration for Solr integration.
 * <p>
 * This class enables automatic configuration of Solr by importing {@link SolrStarterConfiguration}. The class itself does not define any additional beans or behavior.
 * <p>
 * Usage: Simply include this starter in your project, and Spring Boot will automatically configure Solr according to {@link SolrStarterConfiguration}.
 * <p>
 * No further action is required to enable the auto-configuration. This class is intended for internal starter usage.
 * 
 * @author Ilja Avelidi
 */
@AutoConfiguration
@Import(SolrStarterConfiguration.class)
public class SolrAutoConfiguration {
	
	// no-op
	
}
