package com.github.solr.query;

import java.util.List;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.springframework.util.StringUtils;

/**
 * @author Ilja Avelidi
 *
 */
public final class SolrQ {
	
	/**  */
	private final StringBuilder buffer = new StringBuilder();
	
	/**
	 * @author Ilja Avelidi
	 *
	 * @param <T>
	 */
	public static class Query<T> {
		
		/**  */
		private StringBuilder buffer;
		
		/**
		 * @param parent
		 * @param buffer
		 */
		public Query(StringBuilder buffer) {
			this.buffer = buffer;
		}
		
		
		/**
		 * @param name
		 * @param val
		 * @return builder operator
		 */
		public BuilderOperator<Query<T>> field(String name, String val) {
			
			this.buffer.append(name + ":" + ClientUtils.escapeQueryChars(val));
			
			return new BuilderOperator<>(this, this.buffer);
			
		}
		
		
		/**
		 * @return group
		 */
		public Group<BuilderOperator<Query<T>>> openGroup() {
			
			BuilderOperator<Query<T>> b = new BuilderOperator<>(this, this.buffer);
			this.buffer.append("(");
			
			return new Group<>(b, this.buffer);
		}
		
		
		/**
		 * @return parent
		 */
		public Parent<BuilderOperator<Query<T>>> parent() {
			
			BuilderOperator<Query<T>> b = new BuilderOperator<>(this, this.buffer);
			this.buffer.append("{!parent");
			
			return new Parent<>(b, this.buffer);
		}
		
	}
	
	/**
	 * @param name
	 * @param val
	 * @return builder operator
	 */
	public BuilderOperator<Query<SolrQ>> field(String name, String val) {
		
		Query<SolrQ> q = new Query<>(this.buffer);
		this.buffer.append(name + ":" + ClientUtils.escapeQueryChars(val));
		
		return new BuilderOperator<>(q, this.buffer);
		
	}
	
	
	/**
	 * @return group
	 */
	public Group<Query<SolrQ>> openGroup() {
		
		Query<SolrQ> q = new Query<>(this.buffer);
		this.buffer.append("(");
		
		return new Group<>(q, this.buffer);
	}
	
	
	/**
	 * @return parent
	 */
	public Parent<SolrQ> parent() {
		
		this.buffer.append("{!parent");
		
		return new Parent<>(this, this.buffer);
	}
	
	/**
	 * @author Ilja Avelidi
	 *
	 * @param <T>
	 */
	public static class Parent<T> {
		
		/**  */
		private T parent;
		
		/**  */
		private StringBuilder buffer;
		
		/**
		 * @param parent
		 * @param buffer
		 */
		public Parent(T parent, StringBuilder buffer) {
			this.parent = parent;
			this.buffer = buffer;
		}
		
		
		/**
		 * @return parent which
		 */
		public ParentWhich<Parent<T>> which() {
			this.buffer.append(" which=(");
			return new ParentWhich<>(this, this.buffer);
		}
		
		
		/**
		 * @return parent v
		 */
		public ParentV<Parent<T>> v() {
			this.buffer.append(" v='");
			return new ParentV<>(this, this.buffer);
		}
		
		
		/**
		 * @return parent children
		 */
		public ParentChildren<Parent<T>> children() {
			return new ParentChildren<>(this, this.buffer);
		}
		
		
		/**
		 * @return parent
		 */
		public T endParent() {
			return this.parent;
		}
	}
	
	/**
	 * @author Ilja Avelidi
	 *
	 * @param <T>
	 */
	public static class ParentWhich<T> {
		
		/**  */
		private T parent;
		
		/**  */
		private StringBuilder buffer;
		
		/**
		 * @param parent
		 * @param buffer
		 */
		public ParentWhich(T parent, StringBuilder buffer) {
			this.parent = parent;
			this.buffer = buffer;
		}
		
		
		/**
		 * @param name
		 * @param val
		 * @return which operator
		 */
		public WhichOperator<ParentWhich<T>, T> field(String name, String val) {
			this.buffer.append(name + ":" + ClientUtils.escapeQueryChars(val).replace("\\", "\\\\"));
			return new WhichOperator<>(this, this.parent, this.buffer);
		}
		
	}
	
	/**
	 * @author Ilja Avelidi
	 *
	 * @param <T>
	 */
	public static class ParentV<T> {
		
		/**  */
		private T parent;
		
		/**  */
		private StringBuilder buffer;
		
		/**
		 * @param parent
		 * @param buffer
		 */
		public ParentV(T parent, StringBuilder buffer) {
			this.parent = parent;
			this.buffer = buffer;
		}
		
		
		/**
		 * @param name
		 * @param val
		 * @return v operator
		 */
		public VOperator<ParentV<T>, T> field(String name, String val) {
			this.buffer.append(name + ":" + ClientUtils.escapeQueryChars(val).replace("\\", "\\\\"));
			return new VOperator<>(this, this.parent, this.buffer);
		}
	}
	
	/**
	 * @author Ilja Avelidi
	 *
	 * @param <T>
	 */
	public static class ParentChildren<T> {
		
		/**  */
		private T parent;
		
		/**  */
		private StringBuilder buffer;
		
		/**
		 * @param parent
		 * @param buffer
		 */
		public ParentChildren(T parent, StringBuilder buffer) {
			this.parent = parent;
			this.buffer = buffer;
		}
		
		
		/**
		 * @param name
		 * @param val
		 * @return children operator
		 */
		public ChildrenOperator<ParentChildren<T>, T> field(String name, String val) {
			this.buffer.append(name + ":" + ClientUtils.escapeQueryChars(val).replace("\\", "\\\\"));
			return new ChildrenOperator<>(this, this.parent, this.buffer);
		}
		
		
		/**
		 * @return terms
		 */
		public Terms<ParentChildren<T>> terms() {
			this.buffer.append("{!terms");
			return new Terms<>(this, this.buffer);
			
		}
		
		
		/**
		 * @return parent
		 */
		public T endChildren() {
			return this.parent;
		}
	}
	
	/**
	 * @author Ilja Avelidi
	 *
	 * @param <T>
	 */
	public static class Group<T> {
		
		/**  */
		private T parent;
		
		/**  */
		private StringBuilder buffer;
		
		/**
		 * @param parent
		 * @param buffer
		 */
		public Group(T parent, StringBuilder buffer) {
			this.parent = parent;
			this.buffer = buffer;
		}
		
		
		/**
		 * @param name
		 * @param val
		 * @return group operator
		 */
		public GroupOperator<Group<T>, T> field(String name, String val) {
			
			this.buffer.append(name + ":" + ClientUtils.escapeQueryChars(val));
			return new GroupOperator<>(this, this.parent, this.buffer);
			
		}
		
	}
	
	/**
	 * @author Ilja Avelidi
	 *
	 * @param <T>
	 */
	public static class Terms<T> {
		
		/**  */
		private T parent;
		
		/**  */
		private StringBuilder buffer;
		
		/**
		 * @param parent
		 * @param buffer
		 */
		public Terms(T parent, StringBuilder buffer) {
			this.parent = parent;
			this.buffer = buffer;
		}
		
		
		/**
		 * @return f
		 */
		public F<Terms<T>> f() {
			this.buffer.append(" f=");
			return new F<>(this, this.buffer);
		}
		
		
		/**
		 * @param val
		 * @return terms
		 */
		public Terms<T> value(String val) {
			this.buffer.append(val);
			return this;
		}
		
		
		/**
		 * @param vals
		 * @return terms
		 */
		public Terms<T> values(List<String> vals) {
			this.buffer.append(StringUtils.collectionToDelimitedString(vals, ","));
			return this;
		}
		
		
		/**
		 * @return parent
		 */
		public T endTerms() {
			return this.parent;
		}
	}
	
	/**
	 * @author Ilja Avelidi
	 *
	 * @param <T>
	 */
	public static class F<T> {
		
		/**  */
		private T parent;
		
		/**  */
		private StringBuilder buffer;
		
		/**
		 * @param parent
		 * @param buffer
		 */
		public F(T parent, StringBuilder buffer) {
			this.parent = parent;
			this.buffer = buffer;
		}
		
		
		/**
		 * @return parent
		 */
		public T endF() {
			this.buffer.append("}");
			return this.parent;
		}
		
		
		/**
		 * @param name
		 * @return f
		 */
		public F<T> field(String name) {
			this.buffer.append(name);
			return new F<>(this.parent, this.buffer);
		}
		
	}
	
	/**
	 * @author Ilja Avelidi
	 *
	 * @param <T>
	 */
	public static class Operator<T> {
		
		/**  */
		private T parent;
		
		/**  */
		private StringBuilder buffer;
		
		/**
		 * @param parent
		 * @param buffer
		 */
		public Operator(T parent, StringBuilder buffer) {
			this.parent = parent;
			this.buffer = buffer;
		}
		
		
		/**
		 * @return parent
		 */
		public T and() {
			this.buffer.append(" AND ");
			return this.parent;
		}
		
		
		/**
		 * @return parent
		 */
		public T or() {
			this.buffer.append(" OR ");
			return this.parent;
		}
		
	}
	
	/**
	 * @author Ilja Avelidi
	 *
	 * @param <T>
	 * @param <G>
	 */
	public static class GroupOperator<T, G> extends Operator<T> {
		
		/**  */
		private G groupParent;
		
		/**
		 * @param parent
		 * @param groupParent
		 * @param buffer
		 */
		public GroupOperator(T parent, G groupParent, StringBuilder buffer) {
			super(parent, buffer);
			this.groupParent = groupParent;
		}
		
		
		/**
		 * @return group parent
		 */
		public G closeGroup() {
			super.buffer.append(")");
			return this.groupParent;
		}
		
	}
	
	/**
	 * @author Ilja Avelidi
	 *
	 * @param <T>
	 * @param <W>
	 */
	public static class WhichOperator<T, W> extends Operator<T> {
		
		/**  */
		private W whichParent;
		
		/**
		 * @param parent
		 * @param whichParent
		 * @param buffer
		 */
		public WhichOperator(T parent, W whichParent, StringBuilder buffer) {
			super(parent, buffer);
			this.whichParent = whichParent;
		}
		
		
		/**
		 * @return which parent
		 */
		public W endWhich() {
			super.buffer.append(")");
			return this.whichParent;
		}
	}
	
	/**
	 * @author Ilja Avelidi
	 *
	 * @param <T>
	 * @param <V>
	 */
	public static class VOperator<T, V> extends Operator<T> {
		
		/**  */
		private V vParent;
		
		/**
		 * @param parent
		 * @param vParent
		 * @param buffer
		 */
		public VOperator(T parent, V vParent, StringBuilder buffer) {
			super(parent, buffer);
			this.vParent = vParent;
		}
		
		
		/**
		 * @return v parent
		 */
		public V endV() {
			super.buffer.append("')}");
			return this.vParent;
		}
	}
	
	/**
	 * @author Ilja Avelidi
	 *
	 * @param <T>
	 * @param <C>
	 */
	public static class ChildrenOperator<T, C> extends Operator<T> {
		
		/**  */
		private C childrenParent;
		
		/**
		 * @param parent
		 * @param childrenParent
		 * @param buffer
		 */
		public ChildrenOperator(T parent, C childrenParent, StringBuilder buffer) {
			super(parent, buffer);
			this.childrenParent = childrenParent;
		}
		
		
		/**
		 * @return children parent
		 */
		public C endChildren() {
			return this.childrenParent;
		}
	}
	
	/**
	 * @author Ilja Avelidi
	 *
	 * @param <T>
	 */
	public static class BuilderOperator<T> extends Operator<T> {
		
		/**
		 * @param parent
		 * @param buffer
		 */
		public BuilderOperator(T parent, StringBuilder buffer) {
			super(parent, buffer);
		}
		
		
		/**
		 * @return Builds and returns the complete Solr query string.
		 */
		public String build() {
			return super.buffer.toString();
		}
	}
}
