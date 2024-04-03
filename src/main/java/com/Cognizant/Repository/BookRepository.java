package com.Cognizant.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.Cognizant.model.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

	List<Book> findByName(String name);

    List<Book> findByAuthor(String author);

    List<Book> findByPublisher(String publisher);

    List<Book> findByDiscount(int discount);

    List<Book> findAllByOrderByPriceAsc();

    List<Book> findAllByOrderByPriceDesc();

    List<Book> findByOrderStatusOrderByOrderDateAsc(String status);
}