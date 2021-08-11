package com.codecool.examlibrary.controller;

import com.codecool.examlibrary.handler.HttpResponseHandler;
import com.codecool.examlibrary.model.Author;
import com.codecool.examlibrary.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

public abstract class BaseController<E, S extends BaseService<E>> {

    @Autowired
    protected HttpResponseHandler responseHandler;
    protected final S service;

    protected BaseController(S service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> listAll() {
        return responseHandler.getEntity(service.listAll(), OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        E entity = service.findById(id).orElse(null);
        if (entity == null) {
            throw new RuntimeException();
        }
        return responseHandler.getEntity(entity, OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody E entity) {
        return responseHandler.getEntity(service.save(entity), OK);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable("id") Long id) {
        service.deleteById(id);
    }
}