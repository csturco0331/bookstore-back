package com.pluralsight.bookstore.repository;

import com.pluralsight.bookstore.model.Book;
import com.pluralsight.bookstore.model.Language;

import org.apache.commons.lang.StringUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import static org.junit.Assert.*;


@RunWith(Arquillian.class)
public class BookRepositoryTest {
	
	@Inject
	private BookRepository bookRepository;
	
	static Long bookId;
	
    @Deployment
    public static JavaArchive createDeploymentPackage() {

        return ShrinkWrap.create(JavaArchive.class)
            .addClass(BookRepository.class)
            .addClass(Book.class)
            .addClass(Language.class)
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
            .addAsManifestResource("META-INF/test-persistence.xml", "persistence.xml");
    }
    
    @Test
    @InSequence(1)
    public void shouldBeDeployed() {
    	assertNotNull(bookRepository);
    }
    
    @Test
    @InSequence(2)
    public void shouldGetNoBook() {
    	assertEquals(Long.valueOf(0), bookRepository.countAll());
    	assertEquals(0, bookRepository.findAll().size());
    }
    
    @Test
    @InSequence(3)
    public void shouldCreateABook() {
        // Creates a book
    	Book book = bookRepository.create(new Book("a title", "a description", 12F, "isbn", new Date(), 123, "http://blahblah", Language.ENGLISH));
        // Checks the created book
        assertNotNull(book);
        assertNotNull(book.getId());
        bookId = book.getId();
    }

    @Test
    @InSequence(4)
    public void shouldFindTheCreatedBook() {
        // Finds the book
        Book bookFound = bookRepository.find(bookId);
        //Checks the found book
        assertNotNull(bookFound.getId());
        assertEquals("a title", bookFound.getTitle());
    }

    @Test
    @InSequence(5)
    public void shouldGetOneBook() {
        // Count all
        assertEquals(Long.valueOf(1), bookRepository.countAll());
        // Find all
        assertEquals(1, bookRepository.findAll().size());
    }

    @Test
    @InSequence(6)
    public void shouldDeleteTheCreatedBook() {
        // Deletes the book
        bookRepository.delete(bookId);
        // Checks the deleted book
        Book bookDeleted = bookRepository.find(bookId);
        assertNull(bookDeleted);
    }

    @Test
    @InSequence(7)
    public void shouldGetNoMoreBook() {
        // Count all
        assertEquals(Long.valueOf(0), bookRepository.countAll());
        // Find all
        assertEquals(0, bookRepository.findAll().size());
    }
    
    @Test(expected = Exception.class)
    @InSequence(8)
    public void shouldNotCreateInvalidBookNullTitle() {
    	bookRepository.create(new Book(null, "a description", 12F, "isbn", new Date(), 123, "http://blahblah", Language.ENGLISH));
    }
    
    @Test(expected = Exception.class)
    @InSequence(9)
    public void shouldNotCreateInvalidBookEmptyTitle() {
    	bookRepository.create(new Book("", "a description", 12F, "isbn", new Date(), 123, "http://blahblah", Language.ENGLISH));
    }
    
    @Test(expected = Exception.class)
    @InSequence(10)
    public void shouldNotCreateInvalidBookMaxTitle() {
    	String title = String.join("", Collections.nCopies(201, "*"));
    	bookRepository.create(new Book(title, "a description", 12F, "isbn", new Date(), 123, "http://blahblah", Language.ENGLISH));
    }
    
    @Test(expected = Exception.class)
    @InSequence(11)
    public void shouldNotCreateInvalidBookMaxDescription() {
    	String description = String.join("", Collections.nCopies(10001, "*"));
    	bookRepository.create(new Book("a title", description, 12F, "isbn", new Date(), 123, "http://blahblah", Language.ENGLISH));
    }
    
    @Test(expected = Exception.class)
    @InSequence(12)
    public void shouldNotCreateInvalidBookMinCost() {
    	bookRepository.create(new Book("a title", "a description", .8F, "isbn", new Date(), 123, "http://blahblah", Language.ENGLISH));
    }
    
    @Test(expected = Exception.class)
    @InSequence(13)
    public void shouldNotCreateInvalidBookNullIsbn() {
    	bookRepository.create(new Book("a title", "a description", 12F, null, new Date(), 123, "http://blahblah", Language.ENGLISH));
    }
    
    @Test(expected = Exception.class)
    @InSequence(14)
    public void shouldNotCreateInvalidBookEmptyIsbn() {
    	bookRepository.create(new Book("a title", "a description", 12F, "", new Date(), 123, "http://blahblah", Language.ENGLISH));
    }
    
    @Test(expected = Exception.class)
    @InSequence(15)
    public void shouldNotCreateInvalidBookMaxIsbn() {
    	String isbn = String.join("", Collections.nCopies(51, "*"));
    	bookRepository.create(new Book("a title", "a description", 12F, isbn, new Date(), 123, "http://blahblah", Language.ENGLISH));
    }
    
    @Test(expected = Exception.class)
    @InSequence(16)
    public void shouldNotCreateInvalidBookFutureDate() {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(new Date());
    	calendar.add(Calendar.DATE, 1);
    	Date date = calendar.getTime();
    	bookRepository.create(new Book("a title", "a description", 12F, "isbn", date, 123, "http://blahblah", Language.ENGLISH));
    }
    
    @Test(expected = Exception.class)
    @InSequence(17)
    public void shouldNotFindInvalidId() {
    	bookRepository.find(null);
    }
    
    @Test(expected = Exception.class)
    @InSequence(18)
    public void shouldNotCreateNullBook() {
    	bookRepository.create(null);
    }
    
    @Test(expected = Exception.class)
    @InSequence(19)
    public void shouldNotDeleteInvalidId() {
    	bookRepository.delete(null);
    }
}
