package com.codecool.examlibrary.service;

import com.codecool.examlibrary.model.Author;
import com.codecool.examlibrary.model.Book;
import com.codecool.examlibrary.repository.AuthorRepository;
import com.codecool.examlibrary.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorService(AuthorRepository authorRepository,
                         BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public List<Author> listAll() {
        return authorRepository.findAll();
    }

    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    public Author save(Author author) {
        authorRepository.save(author);
        return author;
    }

    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }

    public List<Book> listAllBooksByAuthorId(Long id) {
        return bookRepository.findAll().stream()
                .filter(book -> book.getAuthor().getId().equals(id))
                .collect(Collectors.toList());
    }

    public List<Author> listAllWithMinBookNumber(Integer c) {
        List<Author> authorsMoreThanCBooks = new ArrayList<>();
        for (Author author : authorRepository.findAll()) {
            long count = bookRepository.findAll().stream()
                    .filter(book -> book.getAuthor().equals(author))
                    .count();
            if (count >= c) {
                authorsMoreThanCBooks.add(author);
            }
        }
        return authorsMoreThanCBooks;
    }
}