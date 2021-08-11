package com.codecool.examlibrary.testmodel;

import java.util.ArrayList;
import java.util.List;

public class Author {
    public Long id;
    public String name;
    public List<Book> books = new ArrayList<>();

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}