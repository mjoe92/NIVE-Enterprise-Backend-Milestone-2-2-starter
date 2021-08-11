package com.codecool.examlibrary.controller;

import com.codecool.examlibrary.handler.HttpResponseHandler;
import com.codecool.examlibrary.model.Author;
import com.codecool.examlibrary.model.Book;
import com.codecool.examlibrary.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("author")
public class AuthorController {

    private final AuthorService authorService;
    @Autowired
    protected HttpResponseHandler responseHandler;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<?> listAll() {
        return responseHandler.getEntity(authorService.listAll(), OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        Author author = authorService.findById(id).orElse(null);
        if (author == null) {
            throw new RuntimeException();
        }
        return responseHandler.getEntity(author, OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Author author) {
        return responseHandler.getEntity(authorService.save(author), OK);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable("id") Long id) {
        authorService.deleteById(id);
    }

    @GetMapping("{id}/books")
    public ResponseEntity<?> listAllBooksByAuthorId(@PathVariable("id") Long id) {
        return responseHandler.getEntity(authorService.listAllBooksByAuthorId(id), OK);
    }

    @GetMapping("search")
    public ResponseEntity<?> listAllWithMinBookNumber(@RequestParam Integer c) {
        return responseHandler.getEntity(authorService.listAllWithMinBookNumber(c), OK);
    }
}