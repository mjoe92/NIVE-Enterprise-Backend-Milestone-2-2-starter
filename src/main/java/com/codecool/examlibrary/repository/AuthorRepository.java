package com.codecool.examlibrary.repository;

import com.codecool.examlibrary.model.Author;
import com.codecool.examlibrary.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}