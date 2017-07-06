package com.pluralsight.bookstore.endpoint;

import java.net.URI;
import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.Min;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.pluralsight.bookstore.model.Book;
import com.pluralsight.bookstore.repository.BookRepository;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/books")
public class BookEndpoint {

	@Inject
	private BookRepository bookRepository;
	
	@GET
	@Path("/{param : \\d+}")
	@Produces(APPLICATION_JSON)
	public Response findBook(@PathParam("param") @Min(1) Long id) {
		Book book = bookRepository.find(id);
		return (book == null) ? Response.status(Response.Status.NOT_FOUND).build() : Response.ok(book).build();
	}

	@POST
	@Consumes(APPLICATION_JSON)
	public Response createBook(Book book, @Context UriInfo uriInfo) {
		book = bookRepository.create(book);
		if (book == null) return Response.noContent().build();
		URI createdURI = uriInfo.getBaseUriBuilder().path(book.getId().toString()).build();
		return Response.created(createdURI).build();
	}

	@DELETE
	@Path("/{id : \\d+}")
	public Response deleteBook(@PathParam("id") @Min(1) Long id) {
		bookRepository.delete(id);
		return Response.noContent().build();
	}

	@GET
	@Produces(APPLICATION_JSON)
	public Response findAllBooks() {
		List<Book> books = bookRepository.findAll();
		return (books.size() == 0) ? Response.status(Response.Status.NOT_FOUND).build() : Response.ok(books).build();
	}

	@GET
	@Path("/count")
	public Response countAllBooks() {
		Long count = bookRepository.countAll();
		return (count == 0) ? Response.status(Response.Status.NOT_FOUND).build() : Response.ok(count).build();
	}
	
	@GET
	@Path("/search") // /search?title={title}
	@Produces(APPLICATION_JSON)
	public Response findBookByTitle(@QueryParam("title") String title) {
		System.out.println(title);
		List<Book> books = bookRepository.findByTitle(title);
		return (books.size() == 0) ? Response.status(Response.Status.NOT_FOUND).build() : Response.ok(books).build();
	}
	
	
	
}
