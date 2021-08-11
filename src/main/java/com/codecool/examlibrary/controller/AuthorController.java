package com.codecool.examlibrary.controller;

import com.codecool.examlibrary.model.Author;
import com.codecool.examlibrary.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("author")
public class AuthorController extends BaseController<Author, AuthorService> {

    public AuthorController(AuthorService authorService) {
        super(authorService);
    }

    @GetMapping("{id}/books")
    public ResponseEntity<?> listAllBooksByAuthorId(@PathVariable("id") Long id) {
        return responseHandler.getEntity(service.listAllBooksByAuthorId(id), OK);
    }

    @GetMapping("search")
    public ResponseEntity<?> listAllWithMinBookNumber(@RequestParam Integer c) {
        return responseHandler.getEntity(service.listAllWithMinBookNumber(c), OK);
    }
}