package com.Cognizant.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.Cognizant.Repository.BookRepository;
import com.Cognizant.model.Book;

class BookServiceTest {

	@Mock
	private BookRepository bookRepository;

	private BookService bookService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		bookService = new BookService(bookRepository);
	}

	@Test
	void testCreateBook() {
		Book book = new Book();
		when(bookRepository.save(book)).thenReturn(book);

		Book createdBook = bookService.createBook(book);

		assertEquals(book, createdBook);
		verify(bookRepository, times(1)).save(book);//verify to check expected behaviour
	}

	@Test
	void testGetBooksByName() {
		String name = "Book Name";
		List<Book> expectedBooks = new ArrayList<>();
		when(bookRepository.findByName(name)).thenReturn(expectedBooks);

		List<Book> actualBooks = bookService.getBooksByName(name);

		assertEquals(expectedBooks, actualBooks);
		verify(bookRepository, times(1)).findByName(name);
	}

	@Test
	void testGetBooksByAuthor() {
		String author = "Book Author";
		List<Book> expectedBooks = new ArrayList<>();
		when(bookRepository.findByAuthor(author)).thenReturn(expectedBooks);

		List<Book> actualBooks = bookService.getBooksByAuthor(author);

		assertEquals(expectedBooks, actualBooks);
		verify(bookRepository, times(1)).findByAuthor(author);
	}
	
	
	@Test
	public void testGetBooksByPublisher_ValidPublisher_NoMatchingBooks() {
		String publisher = "Cognizant  Publications";
	List<Book> expectedBooks = new ArrayList<>();
	Mockito.when(bookRepository.findByPublisher(publisher)).thenReturn(expectedBooks);

	List<Book> actualBooks = bookService.getBooksByPublisher(publisher);

	Assert.assertEquals(expectedBooks, actualBooks);
	}
	
	@Test
	public void testDeleteBook_ValidId() {
	Long bookId = 1L;

	bookService.deleteBook(bookId);

	Mockito.verify(bookRepository, Mockito.times(1)).deleteById(bookId);
	}
	
	
}
