package com.codecool.examlibrary.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class BaseService<E> {

    protected final JpaRepository<E, Long> repository;

    protected BaseService(JpaRepository<E, Long> repository) {
        this.repository = repository;
    }

    public List<E> listAll() {
        return repository.findAll();
    }

    public Optional<E> findById(Long id) {
        return repository.findById(id);
    }

    public E save(E entity) {
        repository.save(entity);
        return entity;
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}