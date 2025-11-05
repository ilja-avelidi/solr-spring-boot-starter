# Solr Spring Boot Starter

A lightweight **Spring Boot Starter** for seamless integration of **Apache Solr 9.9.0** into Spring Boot applications.  
It provides asynchronous indexing via events, automatic Solr connection verification on startup, and a minimal configuration approach.

---

## Requirements

- **Java 17+**
- **Apache Solr:** `9.9.0`
- **Spring Boot:** tested with `3.5.7`
- **Single Solr Core support only:** the starter currently supports **exactly one Solr core**.

---

## Installation

Add the starter to your Spring Boot application's `pom.xml`:

```xml
<dependency>
    <groupId>com.github.avelidi</groupId>
    <artifactId>solr-spring-boot-starter</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

This starter internally uses Jetty **10.0.22** as the HTTP client for SolrJ.
Since Spring Boot 3 defines Jetty **12.x in its parent POM**, you must **explicitly include Jetty 10.0.22** to ensure compatibility with SolrJ 9.9.0:

```xml
<dependency>
    <groupId>org.eclipse.jetty</groupId>
    <artifactId>jetty-http</artifactId>
    <version>10.0.22</version>
</dependency>

<dependency>
    <groupId>org.eclipse.jetty</groupId>
    <artifactId>jetty-util</artifactId>
    <version>10.0.22</version>
</dependency>

<dependency>
    <groupId>org.eclipse.jetty</groupId>
    <artifactId>jetty-client</artifactId>
    <version>10.0.22</version>
</dependency>

<dependency>
    <groupId>org.eclipse.jetty</groupId>
    <artifactId>jetty-io</artifactId>
    <version>10.0.22</version>
</dependency>
```

---

## Solr Configuration

The starter expects a single Solr core to be available and reachable.

In your application.properties, define the Solr core URL like this:

```
solr.core.url=http://localhost:8983/solr/test
```
⚠️ The URL must end with the core name (e.g., /solr/test).


---

## Example Configuration Files

A working example of `schema.xml` and `solrconfig.xml` is provided in the [demo project](https://github.com/avelidi/solr-demo-config).  

These example files define a minimal Solr schema compatible with the starter.

---

## Features
- Auto-configures a `SolrClient` bean using the provided `solr.core.url`
- Verifies Solr connectivity at startup and publishes a `SolrReadyEvent`
- Provides asynchronous indexing and deletion through event-based operations
- Uses a `SolrAsyncIndexer` to handle indexing in the background
- Supports Spring Boot’s `@EnableScheduling` and `@Async` features out of the box

---

## How It Works
### 1. Automatic Configuration

The SolrAutoConfiguration and SolrStarterConfiguration classes register:
- A SolrClient (based on Http2SolrClient)
- A SolrConnectionVerifier that checks the Solr connection at startup
- A SolrAsyncIndexer for asynchronous document operations
- A SolrBeanRepository abstraction for saving and removing beans

### 2. Asynchronous Indexing

You can trigger indexing or deletion of a Solr bean by publishing an event.

Example:

```java
@Autowired
private ApplicationEventPublisher publisher;

public void indexProduct(ProductSolrBean product) {
    publisher.publishEvent(new SolrBeanIndexEvent(product));
}

public void removeProduct(ProductSolrBean product) {
    publisher.publishEvent(new SolrBeanRemoveEvent(product));
}
```

The `SolrAsyncIndexer` listens to these events and performs the actual Solr operations asynchronously:
- `SolrBeanIndexEvent` → triggers indexing (addBean)
- `SolrBeanRemoveEvent` → triggers deletion (deleteById)
- `SolrBeanIndexedEvent` and `SolrBeanRemovedEvent` are fired after completion

---

## Example Bean

Each bean must extend `AbstractSolrBean` and define its `id` field:

```java
public class SolrUserBean extends AbstractSolrBean {
	
	@Field("entity_id_l")
	private Long entityId;
	
	@Field("name_s")
	private String name;
	
	@Field("birth_date_dt")
	private Date birthDate;
	
	@Field(child = true)
	private List<SolrAccountBean> accounts = new ArrayList<>();
	
	public SolrUserBean() {
		
		// bean instantiation
		
	}
	
	@Override
	public String getId() {
		
		return String.valueOf(this.entityId);
		
	}
	
	...getters and setters
```

---

## Notes
- The starter is designed for core-based Solr setups, not SolrCloud.
- Only one Solr core may be configured at a time.
- The starter automatically commits after each operation.
- Connection failures at startup will be logged but won’t stop the application. 

---

## License

Distributed under the **[Apache License 2.0](LICENSE)**.
