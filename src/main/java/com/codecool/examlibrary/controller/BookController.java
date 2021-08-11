package com.codecool.examlibrary.controller;

import com.codecool.examlibrary.handler.HttpResponseHandler;
import com.codecool.examlibrary.model.Author;
import com.codecool.examlibrary.model.Book;
import com.codecool.examlibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("book")
public class BookController {

    private final BookService bookService;
    @Autowired
    protected HttpResponseHandler responseHandler;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<?> listAll() {
        return responseHandler.getEntity(bookService.listAll(), OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        Book book = bookService.findById(id).orElse(null);
        if (book == null) {
            throw new RuntimeException();
        }
        return responseHandler.getEntity(book, OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Book book) {
        return responseHandler.getEntity(bookService.save(book), OK);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable("id") Long id) {
        bookService.deleteById(id);
    }

    @GetMapping("search")
    public ResponseEntity<?> listAllWithMinBookNumber(@RequestParam Integer year) {
        return responseHandler.getEntity(bookService.listAllByYear(year), OK);
    }
}