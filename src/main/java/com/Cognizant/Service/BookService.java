package com.Cognizant.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.Cognizant.Repository.BookRepository;
import com.Cognizant.model.Book;
import com.Cognizant.model.OrderStatus;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookService {
	private final BookRepository bookRepository;   // for CRUD operation 

	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public Book createBook(Book book) {
		return bookRepository.save(book);
	}

	public List<Book> getBooksByName(String name) {
		return bookRepository.findByName(name);
	}

	public List<Book> getBooksByAuthor(String author) {
		return bookRepository.findByAuthor(author);
	}

	public List<Book> getBooksByPublisher(String publisher) {
		return bookRepository.findByPublisher(publisher);
	}

	@Value("${book.discount.max}")
	private int maxDiscount;

	@Value("${book.discount.unavailable}")
	private int unavailableDiscount;

	@Value("#{'${book.discount.available}'.split(',')}")
	private List<Integer> availableDiscounts;
	
	public List<Book> getBooksByDiscount(int discount) {
		List<Book> books;

		if (discount > maxDiscount) {
		throw new RuntimeException("Max discount available is " + maxDiscount + "%. Please search with a valid discount.");
		} else if (discount == unavailableDiscount) {
		throw new RuntimeException("No books available with " + unavailableDiscount + "% discount. Please search with a valid discount.");
		} else {
		books = bookRepository.findByDiscount(discount);
		if (books.isEmpty()) {
		int nearestDiscount = findNearestDiscount(discount);
		books = bookRepository.findByDiscount(nearestDiscount);
		}
		}

		return books;
		}

		private int findNearestDiscount(int discount) {
		int nearestDiscount = 0;
		int diff = Integer.MAX_VALUE;

		for (int availableDiscount : availableDiscounts) {
		int currentDiff = Math.abs(discount - availableDiscount);
		if (currentDiff < diff) {
		diff = currentDiff;
		nearestDiscount = availableDiscount;
		}
		}

		return nearestDiscount;
		}
		
	
	
	public Book updateBook(Long id, Book book) {
		Book existingBook = bookRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

		if (book.getName() != null) {
			existingBook.setName(book.getName());
		}
		if (book.getAuthor() != null) {
			existingBook.setAuthor(book.getAuthor());
		}
		if (book.getPublisher() != null) {
			existingBook.setPublisher(book.getPublisher());
		}
		if (book.getEdition() != null) {
			existingBook.setEdition(book.getEdition());
		}
		if (book.getDiscount() > 0) {
			existingBook.setDiscount(book.getDiscount());
		}
		if (book.getStock() >= 0) {
			existingBook.setStock(book.getStock());
		}
		if (book.getPrice() > 0) {
			existingBook.setPrice(book.getPrice());
		}
		if (book.getOrderStatus() != null) {
			existingBook.setOrderStatus(book.getOrderStatus());
		}
		if (book.getOrderDate() != null) {
			existingBook.setOrderDate(book.getOrderDate());
		}

		return bookRepository.save(existingBook);
	}

	public void deleteBook(Long id) {
		bookRepository.deleteById(id);
	}

	public List<Book> getBooksSortedByPriceAsc() {
		return bookRepository.findAllByOrderByPriceAsc();
	}

	public List<Book> getBooksSortedByPriceDesc() {
		return bookRepository.findAllByOrderByPriceDesc();
	}

	public List<Book> getBooksByOrderStatusSortedByOrderDateAsc(String status) {
		return bookRepository.findByOrderStatusOrderByOrderDateAsc(status);
	}

	public Book orderBook(Long bookId) {
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));
		if (book.getOrderStatus() == null) {
			book.setOrderStatus(OrderStatus.ORDERED.name());
			book.setOrderDate(LocalDate.now());
			return bookRepository.save(book);
		} else {
			throw new RuntimeException("Book with id " + bookId + " is already ordered.");
		}
	}
	
	
}