package com.codecool.examlibrary.testmodel;

public class Book {
    public Long id;
    public String title;
    public int year;
    public Author author;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", author=" + author +
                '}';
    }
}