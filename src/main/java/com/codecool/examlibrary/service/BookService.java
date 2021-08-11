package com.codecool.examlibrary.service;

import com.codecool.examlibrary.model.Book;
import com.codecool.examlibrary.repository.AuthorRepository;
import com.codecool.examlibrary.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService extends BaseService<Book> {

    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository,
                       AuthorRepository authorRepository) {
        super(bookRepository);
        this.authorRepository = authorRepository;
    }

    public List<Book> listAllByYear(Integer year) {
        return repository.findAll().stream()
                .filter(book -> book.getYear() == year)
                .collect(Collectors.toList());
    }
}