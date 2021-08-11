package com.codecool.examlibrary.service;

import com.codecool.examlibrary.model.Book;
import com.codecool.examlibrary.repository.AuthorRepository;
import com.codecool.examlibrary.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository,
                       AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public List<Book> listAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public Book save(Book book) {
        bookRepository.save(book);
        return book;
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> listAllByYear(Integer year) {
        return bookRepository.findAll().stream()
                .filter(book -> book.getYear() == year)
                .collect(Collectors.toList());
    }
}