package com.codecool.examlibrary.repository;

import com.codecool.examlibrary.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}