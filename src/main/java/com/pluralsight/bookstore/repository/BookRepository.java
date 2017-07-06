package com.pluralsight.bookstore.repository;

import java.util.List;


import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

import com.pluralsight.bookstore.model.Book;
import com.pluralsight.bookstore.util.NumberGenerator;
import com.pluralsight.bookstore.util.TextUtil;

@Transactional(SUPPORTS) //Used for ReadOnly transactions
public class BookRepository {
	
	@Inject
	private TextUtil textUtil;

	@Inject
	private NumberGenerator generator;
	
	@PersistenceContext(unitName = "bookStorePU")
	private EntityManager em;
	
	public Book find(@NotNull Long id) {
		return em.find(Book.class, id);
	}
	
	@Transactional(REQUIRED) //Used for WriteOnly transactions
	public Book create(@NotNull Book book) {
		book.setTitle(textUtil.sanitize(book.getTitle()));
		book.setIsbn(generator.generateNumber());
		em.persist(book);
		return book;
	}
	
	@Transactional(REQUIRED)
	public void delete(@NotNull Long id) {
		em.remove(em.getReference(Book.class, id));	
	}
	
	public List<Book> findAll() {
		return em.createQuery("select b from Book b order by b.title desc", Book.class)
				.getResultList();
	}
	
	public Long countAll() {
		return em.createQuery("select count(b) from Book b", Long.class)
				.getSingleResult();
	}
	
	public List<Book> findByTitle(String title) {
		return em.createQuery("select b from Book b where b.title like ?0", Book.class)
				.setParameter(0, "%"+title+"%")
				.getResultList();
	}
}
