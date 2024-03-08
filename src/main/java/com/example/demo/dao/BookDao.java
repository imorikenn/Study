package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;

@Repository
public class BookDao implements BaseDao<Book> {

	@Autowired
	BookRepository repository;

	@Override
	public List<Book> findAll() {
		return repository.findAll();
	}

	@Override
	public Book findById(Integer id) throws DataNotFoundException {
		return repository.findById(id).orElseThrow(() -> new DataNotFoundException());
	}

	@Override
	public void save(Book book) {
		repository.save(book);
	}

	@Override
	public void delete(Integer id) {
		try {
			Book b = this.findById(id);
			repository.delete(b);
		} catch (DataNotFoundException e) {
			System.out.println("do nothing");
		}
	}

	public List<Book> findByAuthorId(Integer authorId) {
		return repository.findByAuthorId(authorId);
	}

	public List<Book> findByCategoryId(Integer categoryId) {
		return repository.findByCategoryId(categoryId);
	}
}
