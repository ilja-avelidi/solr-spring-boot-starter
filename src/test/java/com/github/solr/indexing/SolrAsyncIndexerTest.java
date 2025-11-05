package com.github.solr.indexing;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.lang.reflect.Field;
import java.util.concurrent.BlockingQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import com.github.solr.bean.AbstractSolrBean;
import com.github.solr.event.SolrBeanIndexEvent;
import com.github.solr.event.SolrBeanIndexedEvent;
import com.github.solr.event.SolrBeanRemoveEvent;
import com.github.solr.event.SolrBeanRemovedEvent;
import com.github.solr.repository.SolrBeanRepository;

/**
 * @author Ilja Avelidi
 *
 */
class SolrAsyncIndexerTest {
	
	/**  */
	@Mock
	private SolrBeanRepository solrBeanRepository;
	
	/**  */
	@Mock
	private ApplicationEventPublisher eventPublisher;
	
	/**  */
	@Mock
	private AbstractSolrBean solrBean;
	
	/**  */
	@InjectMocks
	private SolrAsyncIndexer indexer;
	
	/**
	 * 
	 */
	@SuppressWarnings("resource")
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		when(this.solrBean.getId()).thenReturn("test-id");
	}
	
	
	/**
	 * @throws Exception
	 */
	@Test
	void testProcessQueue_withIndexEvent_shouldSaveAndPublishIndexedEvent() throws Exception {
		SolrBeanIndexEvent event = new SolrBeanIndexEvent(this.solrBean);
		this.indexer.processEvent(event);
		
		this.indexer.processQueue();
		
		verify(this.solrBeanRepository, times(1)).save(this.solrBean);
		
		ArgumentCaptor<SolrBeanIndexedEvent> captor = ArgumentCaptor.forClass(SolrBeanIndexedEvent.class);
		verify(this.eventPublisher, times(1)).publishEvent(captor.capture());
		
		SolrBeanIndexedEvent publishedEvent = captor.getValue();
		assert publishedEvent.getSource() == this.solrBean;
	}
	
	
	/**
	 * @throws Exception
	 */
	@Test
	void testProcessQueue_withRemoveEvent_shouldDeleteAndPublishRemovedEvent() throws Exception {
		SolrBeanRemoveEvent event = new SolrBeanRemoveEvent(this.solrBean);
		this.indexer.processEvent(event);
		
		this.indexer.processQueue();
		
		verify(this.solrBeanRepository, times(1)).remove("test-id");
		
		ArgumentCaptor<SolrBeanRemovedEvent> captor = ArgumentCaptor.forClass(SolrBeanRemovedEvent.class);
		verify(this.eventPublisher, times(1)).publishEvent(captor.capture());
		
		SolrBeanRemovedEvent publishedEvent = captor.getValue();
		assert publishedEvent.getSource() == this.solrBean;
	}
	
	
	/**
	 * @throws Exception
	 */
	@Test
	void testProcessEvent_shouldAddEventToQueue() throws Exception {
		SolrBeanIndexEvent event = new SolrBeanIndexEvent(this.solrBean);
		
		this.indexer.processEvent(event);
		
		Field field = SolrAsyncIndexer.class.getDeclaredField("queue");
		field.setAccessible(true);
		
		BlockingQueue<?> queue = (BlockingQueue<?>)field.get(this.indexer);
		
		assert !queue.isEmpty();
	}
	
	
	/**
	 * @throws Exception
	 */
	@Test
	void testProcessQueue_withRepositoryException_shouldLogWarning() throws Exception {
		SolrBeanIndexEvent event = new SolrBeanIndexEvent(this.solrBean);
		this.indexer.processEvent(event);
		
		doThrow(new RuntimeException("Solr error")).when(this.solrBeanRepository).save(this.solrBean);
		
		this.indexer.processQueue();
		
		verify(this.solrBeanRepository, times(1)).save(this.solrBean);
	}
}
