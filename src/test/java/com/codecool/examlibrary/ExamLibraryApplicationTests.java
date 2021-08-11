package com.codecool.examlibrary;

import com.codecool.examlibrary.testmodel.Author;
import com.codecool.examlibrary.testmodel.Book;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExamLibraryApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    JdbcTemplate jdbcTemplate;

    private String authorBaseUrl;
    private String booksBaseUrl;

    @BeforeEach
    void setup() {
        authorBaseUrl = "http://localhost:" + port + "/author";
        booksBaseUrl = "http://localhost:" + port + "/book";
    }

    @Test
    @Order(1)
    void testFindAllAuthor() {
        final ResponseEntity<Author[]> response = restTemplate.getForEntity(authorBaseUrl, Author[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        final Author[] authors = response.getBody();
        assertEquals(getAuthorCount(), authors.length);
    }

    @Test
    @Order(2)
    void testAddAuthor() {
        final int countBefore = getAuthorCount();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Author johnDoe = new Author();
        johnDoe.name = "John Doe";
        final HttpEntity<Author> authorHttpEntity = new HttpEntity<>(johnDoe, headers);
        final Author author = restTemplate.postForObject(authorBaseUrl, authorHttpEntity, Author.class);
        assertEquals(countBefore + 1, getAuthorCount());
        assertEquals(4, author.id);
        assertEquals(johnDoe.name, author.name);
        System.out.println(author);
    }

    @Test
    @Order(3)
    void testDeleteAuthor() {
        jdbcTemplate.update("INSERT INTO AUTHOR (id, name) VALUES (5, 'Jane Doe')");
        final int currentAuthors = getAuthorCount();
        restTemplate.delete(authorBaseUrl + "/5");
        assertEquals(currentAuthors - 1, getAuthorCount());
    }

    @Test
    @Order(4)
    void testUpdateAuthor() {
        jdbcTemplate.update("INSERT INTO AUTHOR (id, name) VALUES (6, 'John Doe XXX')");
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Author johnDoe = new Author();
        johnDoe.id = 6L;
        johnDoe.name = "John Doe Modified";
        final HttpEntity<Author> authorHttpEntity = new HttpEntity<>(johnDoe, headers);
        final Author author = restTemplate.postForObject(authorBaseUrl, authorHttpEntity, Author.class);
        assertEquals(johnDoe.id, author.id);
        assertEquals(johnDoe.name, author.name);
        System.out.println(author);
    }

    @Test
    @Order(5)
    void testAuthorFindById() {
        final Author author = restTemplate.getForObject(authorBaseUrl + "/2", Author.class);
        assertEquals(2L, author.id);
        assertEquals("Dante Alighieri", author.name);
    }

    @Test
    @Order(6)
    void testFindAuthorByIdWhenIdNotExists() {
        final ResponseEntity<Author> response = restTemplate.getForEntity(authorBaseUrl + "/987654321", Author.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    @Order(7)
    void testGetAllBooksForAuthor() {
        final Book[] books = restTemplate.getForObject(authorBaseUrl + "/1/books", Book[].class);
        assertEquals(2, books.length);
    }

    @Test
    @Order(8)
    void testGetAllAuthorsWithMoreThanCBooks() {
        final Author[] authors = restTemplate.getForObject(authorBaseUrl + "/search?c=2", Author[].class);
        assertEquals(2, authors.length);
    }

    private Integer getAuthorCount() {
        return jdbcTemplate.queryForObject("select count(*) from author", Integer.class);
    }

    @Test
    @Order(10)
    void testFindAllBooks() {
        final ResponseEntity<Book[]> response = restTemplate.getForEntity(booksBaseUrl, Book[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        final Book[] books = response.getBody();
        assertEquals(getBookCount(), books.length);
    }

    private Integer getBookCount() {
        return jdbcTemplate.queryForObject("select count(*) from book", Integer.class);
    }

    @Test
    @Order(12)
    void testAddBook() {
        final int countBefore = getBookCount();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final Book book = new Book();
        book.title = "The catcher in the rye";
        book.year = 1953;
        final HttpEntity<Book> httpEntity = new HttpEntity<>(book, headers);
        final Book savedBook = restTemplate.postForObject(booksBaseUrl, httpEntity, Book.class);
        assertEquals(countBefore + 1, getBookCount());
        assertEquals(6, savedBook.id);
        assertEquals(book.title, savedBook.title);
        assertEquals(book.year, savedBook.year);
    }

    @Test
    @Order(13)
    void testDeleteBook() {
        jdbcTemplate.update("INSERT INTO BOOK (id, title, year) VALUES (7, 'Jane Doe story', 2001)");
        final int currentAuthors = getBookCount();
        restTemplate.delete(booksBaseUrl + "/7");
        assertEquals(currentAuthors - 1, getBookCount());
    }

    @Test
    @Order(14)
    void testUpdateBook() {
        jdbcTemplate.update("INSERT INTO BOOK (id, title, year) VALUES (8, 'John Doe XXX', 2005)");
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Book johnDoe = new Book();
        johnDoe.id = 8L;
        johnDoe.title = "John Doe Modified";
        johnDoe.year = 2020;
        final HttpEntity<Book> httpEntity = new HttpEntity<>(johnDoe, headers);
        final Book savedBook = restTemplate.postForObject(booksBaseUrl, httpEntity, Book.class);
        assertEquals(johnDoe.id, savedBook.id);
        assertEquals(johnDoe.title, savedBook.title);
        assertEquals(johnDoe.year, savedBook.year);
    }

    @Test
    @Order(15)
    void testFindBookById() {
        final Book book = restTemplate.getForObject(booksBaseUrl + "/2", Book.class);
        assertEquals(2L, book.id);
        assertEquals("Corbaccio", book.title);
        assertEquals(1355, book.year);
    }

    @Test
    @Order(16)
    void testFindBookByIdWhenIdNotExists() {
        final ResponseEntity<Book> response = restTemplate.getForEntity(booksBaseUrl + "/987654321", Book.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    @Order(17)
    void testFindBooksByYear() {
        final Book[] books = restTemplate.getForObject(booksBaseUrl + "/search?year=1320", Book[].class);
        assertEquals(1, books.length);
    }

    @Test
    @Order(18)
    void testAddBookWithNewAuthor() {
        final int countBefore = getBookCount();
        final Integer authorCountBefore = getAuthorCount();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final Book book = new Book();
        book.title = "Clean code";
        book.year = 1953;
        book.author = new Author();
        book.author.name = "Robert C. Martin";
        final HttpEntity<Book> httpEntity = new HttpEntity<>(book, headers);
        final Book savedBook = restTemplate.postForObject(booksBaseUrl, httpEntity, Book.class);
        assertEquals(countBefore + 1, getBookCount());
        assertEquals(authorCountBefore + 1, getAuthorCount());
        assertEquals(book.title, savedBook.title);
        assertEquals(book.year, savedBook.year);
    }
}
