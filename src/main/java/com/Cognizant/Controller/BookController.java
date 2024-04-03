package com.Cognizant.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Cognizant.Service.BookService;
import com.Cognizant.model.Book;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/books")
@Api(tags = "Book API")
public class BookController {
	
	@Autowired
	private  BookService bookService;      // final we will not change value

//	@Autowired
//	public BookController(BookService bookService) {
//		this.bookService = bookService;
//	}

	@PostMapping
	@ApiOperation("Create a new book")
	public ResponseEntity<Book> createBook(@RequestBody Book book) {
		Book createdBook = bookService.createBook(book);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
	}

	@GetMapping("/name")
	@ApiOperation("Get books by name")
	public ResponseEntity<List<Book>> getBooksByName(@RequestParam("BookName") String name) {
		List<Book> books = bookService.getBooksByName(name);
		return ResponseEntity.ok(books);
	}

	@GetMapping("/author")
	@ApiOperation("Get books by author")
	public ResponseEntity<List<Book>> getBooksByAuthor(@RequestParam("AuthorName") String author) {
		List<Book> books = bookService.getBooksByAuthor(author);
		return ResponseEntity.ok(books);
	}

	@GetMapping("/publisher")
	@ApiOperation("Get books by publisher")
	public ResponseEntity<List<Book>> getBooksByPublisher(@RequestParam("PublisherName") String publisher) {
		List<Book> books = bookService.getBooksByPublisher(publisher);
		return ResponseEntity.ok(books);
	}

	@GetMapping("/discount")
	@ApiOperation("Get books by discount")
	public ResponseEntity<List<Book>> getBooksByDiscount(@RequestParam("MaxDiscount") int discount) {
		List<Book> books = bookService.getBooksByDiscount(discount);
		return ResponseEntity.ok(books);
	}

	@PutMapping("/{id}")
	@ApiOperation("Update a book by ID")
	public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
		Book updatedBook = bookService.updateBook(id, book);
		return ResponseEntity.ok(updatedBook);
	}

	@DeleteMapping("/{id}")
	@ApiOperation("Delete a book by ID")
	public ResponseEntity<String> deleteBook(@PathVariable Long id) {
		bookService.deleteBook(id);
		return ResponseEntity.ok("Book deleted successfully");
	}

	@GetMapping("/sort/price/asc")
	@ApiOperation("Get books sorted by price in ascending order")
	public ResponseEntity<List<Book>> getBooksSortedByPriceAsc() {
		List<Book> books = bookService.getBooksSortedByPriceAsc();
		return ResponseEntity.ok(books);
	}

	@GetMapping("/sort/price/desc")
	@ApiOperation("Get books sorted by price in descending order")
	public ResponseEntity<List<Book>> getBooksSortedByPriceDesc() {
		List<Book> books = bookService.getBooksSortedByPriceDesc();
		return ResponseEntity.ok(books);
	}

	@GetMapping("/orders/{status}")
	@ApiOperation("Get books by order status sorted by order date in ascending order")
	public ResponseEntity<List<Book>> getBooksByOrderStatusSortedByOrderDateAsc(@PathVariable String status) {
		List<Book> books = bookService.getBooksByOrderStatusSortedByOrderDateAsc(status);
		return ResponseEntity.ok(books);
	}

	@PostMapping("/order/{bookId}")
	@ApiOperation("Order a book by ID")
	public ResponseEntity<Book> orderBook(@PathVariable("bookId") Long bookId) {
		Book orderedBook = bookService.orderBook(bookId);
		return ResponseEntity.status(HttpStatus.CREATED).body(orderedBook);
	}
}