package com.pluralsight.bookstore.endpoint;

import com.pluralsight.bookstore.model.Book;
import com.pluralsight.bookstore.model.Language;
import com.pluralsight.bookstore.repository.BookRepository;
import com.pluralsight.bookstore.util.IsbnGenerator;
import com.pluralsight.bookstore.util.NumberGenerator;
import com.pluralsight.bookstore.util.TextUtil;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.Date;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.*;
import static org.junit.Assert.*;


/*
 * Currently not working. Injected BookRepository in BookEndpoint is null. Not sure why
 */
@RunWith(Arquillian.class)
@RunAsClient
public class BookEndpointTest {

	@Deployment(testable=false)
    public static Archive<?> createDeploymentPackage() {

        return ShrinkWrap.create(WebArchive.class)
            .addClass(BookRepository.class)
            .addClass(Book.class)
            .addClass(Language.class)
            .addClass(TextUtil.class)
            .addClass(NumberGenerator.class)
            .addClass(IsbnGenerator.class)
            .addClass(BookEndpoint.class)
            .addClass(JAXRSConfiguration.class)
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
            .addAsManifestResource("META-INF/test-persistence.xml", "persistence.xml");
//            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
//            .addAsResource("META-INF/test-persistence.xml", "persistence.xml");
    }
	
	@Test
	@InSequence(2)
	public void shouldCountBooksInitial(@ArquillianResteasyResource("api/books") WebTarget webTarget) throws Exception {
		System.out.println(webTarget);
		Response response = webTarget.path("count").request().get();
		assertEquals(NOT_FOUND.getStatusCode(), response.getStatus());
	}
	
	@Test
	@InSequence(3)
	public void shouldFindAllBooksInitial(@ArquillianResteasyResource("api/books") WebTarget webTarget) throws Exception {
		Response response = webTarget.request(APPLICATION_JSON).get();
		assertEquals(NOT_FOUND.getStatusCode(), response.getStatus());
	}
	
	@Test
	@InSequence(4)
	public void shouldCreateBook(@ArquillianResteasyResource("api/books") WebTarget webTarget) throws Exception {
    	Book book = new Book("a title", "a description", 12F, "isbn", new Date(), 123, "http://blahblah", Language.ENGLISH);
    	Response response = webTarget.request(APPLICATION_JSON).post(Entity.entity(book, APPLICATION_JSON));
    	assertEquals(CREATED.getStatusCode(), response.getStatus());
	}
	
}
