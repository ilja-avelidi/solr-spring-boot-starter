package com.github.solr.repository;

import java.io.IOException;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.github.solr.bean.AbstractSolrBean;

/**
 * Default implementation of {@link SolrBeanRepository} using a {@link SolrClient}.
 * <p>
 * Provides methods to save and remove Solr beans from the configured Solr core. Each operation immediately commits the changes to ensure that the Solr index reflects the updates.
 * 
 * @author Ilja Avelidi
 *
 */
@Repository
public class SolrBeanRepositoryImpl implements SolrBeanRepository {
	
	/**  */
	@Autowired
	private SolrClient solrClient;
	
	/**
	 * @see com.github.solr.repository.SolrBeanRepository#save(com.github.solr.bean.AbstractSolrBean)
	 */
	@Override
	public void save(AbstractSolrBean solrBean) throws IOException, SolrServerException {
		
		this.solrClient.addBean(solrBean);
		this.solrClient.commit();
		
	}
	
	
	/**
	 * @see com.github.solr.repository.SolrBeanRepository#remove(java.lang.String)
	 */
	@Override
	public void remove(String id) throws SolrServerException, IOException {
		
		this.solrClient.deleteById(id);
		this.solrClient.commit();
		
	}
}
