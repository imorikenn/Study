package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.dao.BookDao;
import com.example.demo.entity.Book;

@Service
public class BookService implements BaseService<Book> {
	@Autowired
	private BookDao bookDao;

	@Override
	public List<Book> findAll() {
		return bookDao.findAll();
	}

	@Override
	public Book findById(Integer id) throws DataNotFoundException {
		return bookDao.findById(id);
	}

	public List<Book> findByAuthorId(Integer authorId) throws DataNotFoundException {
		return bookDao.findByAuthorId(authorId);
	}

	public List<Book> findByCategoryId(Integer categoryId) throws DataNotFoundException {
		return bookDao.findByCategoryId(categoryId);
	}

	@Override
	public void save(Book book) {
		bookDao.save(book);
	}

	@Override
	public void delete(Integer id) {
		bookDao.delete(id);
	}
}
