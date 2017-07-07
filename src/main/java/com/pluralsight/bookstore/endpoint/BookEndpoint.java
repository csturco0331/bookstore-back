package com.pluralsight.bookstore.endpoint;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

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

import io.swagger.annotations.*;

@SwaggerDefinition(
    info = @Info(
        title = "BookStore APIs",
        description = "BookStore APIs exposed from a Java EE back-end to an Angular front-end",
        version = "V1.0.0",
        contact = @Contact(
            name = "Cory Turco"
        )
    ),
    host = "localhost:8080",
    basePath = "/bookstore-back/api",
    schemes = {SwaggerDefinition.Scheme.HTTP, SwaggerDefinition.Scheme.HTTPS},
    tags = {
        @Tag(name = "Book", description = "Tag used to denote operations as private")
    }
)
@Path("/books")
@Api("Book")
public class BookEndpoint {

	@Inject
	private BookRepository bookRepository;
	
	@GET
	@Path("/{param : \\d+}")
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = "Returns a book given an identifier", response = Book.class)
	@ApiResponses({
		@ApiResponse(code = 200, message = "Book found"),
		@ApiResponse(code = 400, message = "Invalid input. Id cannot be lower than 1"),
		@ApiResponse(code = 404, message = "Book not found")
	})
	public Response findBook(@PathParam("param") @Min(1) Long id) {
		Book book = bookRepository.find(id);
		return (book == null) ? Response.status(Response.Status.NOT_FOUND).build() : Response.ok(book).build();
	}

	@POST
	@Consumes(APPLICATION_JSON)
	@ApiOperation("Creates a book given a JSon Book representation")
    @ApiResponses({
        @ApiResponse(code = 201, message = "The book is created"),
        @ApiResponse(code = 415, message = "Format is not JSon")
    })
	public Response createBook(Book book, @Context UriInfo uriInfo) {
		book = bookRepository.create(book);
		URI createdURI = uriInfo.getBaseUriBuilder().path(book.getId().toString()).build();
		return Response.created(createdURI).build();
	}

	@DELETE
	@Path("/{id : \\d+}")
	@ApiOperation("Deletes a book given an id")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Book has been deleted"),
        @ApiResponse(code = 400, message = "Invalid input. Id cannot be lower than 1"),
        @ApiResponse(code = 500, message = "Book not found")
    })
	public Response deleteBook(@PathParam("id") @Min(1) Long id) {
		bookRepository.delete(id);
		return Response.noContent().build();
	}

	@GET
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = "Returns all the books", response = Book.class, responseContainer = "List")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Books found"),
        @ApiResponse(code = 204, message = "No books found"),
    })
	public Response findAllBooks() {
		List<Book> books = bookRepository.findAll();
		return (books.size() == 0) ? Response.status(Response.Status.NOT_FOUND).build() : Response.ok(books).build();
	}

	@GET
	@Path("/count")
	@ApiOperation(value = "Returns the number of books", response = Long.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "Number of books found"),
        @ApiResponse(code = 204, message = "No books found"),
    })
	public Response countAllBooks() {
		Long count = bookRepository.countAll();
		return (count == 0) ? Response.status(Response.Status.NOT_FOUND).build() : Response.ok(count).build();
	}
	
	@GET
	@Path("/search") // /search?title={title}
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = "Returns all books with similar given title", response = Book.class, responseContainer = "List")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Book/s found"),
		@ApiResponse(code = 204, message = "No books found")
	})
	public Response findBookByTitle(@QueryParam("title") String title) {
		System.out.println(title);
		List<Book> books = bookRepository.findByTitle(title);
		return (books.size() == 0) ? Response.status(Response.Status.NOT_FOUND).build() : Response.ok(books).build();
	}
	
	
	
}
