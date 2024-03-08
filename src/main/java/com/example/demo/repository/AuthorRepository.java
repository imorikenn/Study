package com.example.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Author;
public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
