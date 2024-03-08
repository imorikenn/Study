package com.example.demo.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Book;
public interface BookRepository extends JpaRepository<Book, Integer> {
	List<Book> findByAuthorId(Integer authorId);
	List<Book> findByCategoryId(Integer categoryId);
}
