package com.codecool.examlibrary.service;

import com.codecool.examlibrary.model.Book;
import com.codecool.examlibrary.repository.AuthorRepository;
import com.codecool.examlibrary.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService extends BaseService<Book> {

    public BookService(BookRepository bookRepository) {
        super(bookRepository);
    }

    public List<Book> listAllByYear(Integer year) {
        return repository.findAll().stream()
                .filter(book -> book.getYear() == year)
                .collect(Collectors.toList());
    }
}