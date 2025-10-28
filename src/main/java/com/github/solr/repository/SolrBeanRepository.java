package com.github.solr.repository;

import java.io.IOException;
import org.apache.solr.client.solrj.SolrServerException;
import com.github.solr.bean.AbstractSolrBean;

/**
 * @author Ilja Avelidi
 *
 */
public interface SolrBeanRepository {
	
	/**
	 * @param solrBean
	 * @throws SolrServerException
	 * @throws IOException
	 */
	void save(AbstractSolrBean solrBean) throws IOException, SolrServerException;
	
	
	/**
	 * @param id
	 *            the unique identifier
	 * @throws SolrServerException
	 * @throws IOException
	 */
	void remove(String id) throws SolrServerException, IOException;
	
}
