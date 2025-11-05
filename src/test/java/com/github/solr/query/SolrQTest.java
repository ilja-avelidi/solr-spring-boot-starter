package com.github.solr.query;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.github.solr.query.stage.BuilderOperator;

/**
 * @author Ilja Avelidi
 *
 */
class SolrQTest {
	
	/**  */
	private SolrQ solrQ;
	
	/**
	 * 
	 */
	@BeforeEach
	void setUp() {
		this.solrQ = new SolrQ();
	}
	
	
	/**
	 * 
	 */
	@Test
	void testAppendField_AppendsCorrectly() {
		StringBuilder sb = new StringBuilder();
		SolrQ.appendField(sb, "title", "hello");
		assertEquals("title:hello", sb.toString());
	}
	
	
	/**
	 * 
	 */
	@Test
	void testField_AppendsFieldAndReturnsOperator() {
		BuilderOperator<SolrQ.Query<SolrQ>> op = this.solrQ.field("name", "John");
		assertNotNull(op);
		assertTrue(op.build().contains("name:John"));
	}
	
	
	/**
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 * 
	 */
	@Test
	void testOpenGroup_AppendsOpeningParenthesis() throws NoSuchFieldException, IllegalAccessException {
		this.solrQ.openGroup();
		StringBuilder buffer = getField("buffer", StringBuilder.class, this.solrQ);
		assertTrue(buffer.indexOf("(") > -1);
	}
	
	
	/**
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 * 
	 */
	@Test
	void testParent_AppendsParentLocalParam() throws NoSuchFieldException, IllegalAccessException {
		this.solrQ.parent();
		StringBuilder buffer = getField("buffer", StringBuilder.class, this.solrQ);
		assertTrue(buffer.indexOf("{!parent") > -1);
	}
	
	
	/**
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 * 
	 */
	@Test
	void testNestedQuery_FieldChaining() throws NoSuchFieldException, IllegalAccessException {
		SolrQ.Query<SolrQ> query = new SolrQ.Query<>(new StringBuilder());
		query.field("category", "books").and().field("author", "tolkien");
		StringBuilder buffer = getField("buffer", StringBuilder.class, query);
		assertTrue(buffer.indexOf("category:books") > -1);
		assertTrue(buffer.indexOf("author:tolkien") > -1);
	}
	
	
	/**
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * 
	 */
	@Test
	void testNestedGroupWithinQuery() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		SolrQ.Query<SolrQ> query = new SolrQ.Query<>(new StringBuilder());
		query.openGroup();
		StringBuilder buffer = getField("buffer", StringBuilder.class, query);
		assertTrue(buffer.indexOf("(") > -1);
	}
	
	
	/**
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 * 
	 */
	@Test
	void testParentWithinQuery() throws NoSuchFieldException, IllegalAccessException {
		SolrQ.Query<SolrQ> query = new SolrQ.Query<>(new StringBuilder());
		query.parent();
		StringBuilder buffer = getField("buffer", StringBuilder.class, query);
		assertTrue(buffer.indexOf("{!parent") > -1);
	}
	
	
	/**
	 * @param <T>
	 * @param fieldName
	 * @param fieldType
	 * @param instance
	 * @return field
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	private <T> T getField(String fieldName, Class<T> fieldType, Object instance) throws NoSuchFieldException, IllegalAccessException {
		Field field = instance.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		Object _field = field.get(instance);
		return fieldType.cast(_field);
	}
	
}
