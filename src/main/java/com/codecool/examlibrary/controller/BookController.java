package com.codecool.examlibrary.controller;

import com.codecool.examlibrary.model.Book;
import com.codecool.examlibrary.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("book")
public class BookController extends BaseController<Book, BookService> {

    public BookController(BookService bookService) {
        super(bookService);
    }

    @GetMapping("search")
    public ResponseEntity<?> listAllWithMinBookNumber(@RequestParam Integer year) {
        return responseHandler.getEntity(service.listAllByYear(year), OK);
    }
}