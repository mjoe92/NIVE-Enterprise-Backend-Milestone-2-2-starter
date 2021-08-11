# Description

## Create a simple API for a library application.

### Entities:

 - `Author` with Long `id`, String `name`, List of Books `books` fields.
 - `Book` with Long `id`, String `title`, Author `author` fields.

Data should be automatically populated from `import.sql`, if you are using Spring Data JPA / Hibernate.

### Controllers:
 - CRUD methods for Book, Author objects
 - Find all books for an Author identified by `id`
 - Find all books by `year`
 - Find authors with more books than given parameter `c`.
#### Details:
 
| Url path | Request | Should return | Description |
|---|---|---|---|
| /author | GET | JSON list of Authors, without books |
| /author/{id} | GET | Author with `id`, without books. If no such author exists, return with `500 error`, (throw `RuntimeException`). | If you use `JPARepository`'s `getById`, it can return a proxy object, which can cause problems with JSON serialization. Use `findById` instead.
| /author | POST | Returns saved entity | Should save or update entity based on id. i.e.: If `id` is `null`, save a new entity.
| /author/{id} | DELETE | - | Deletes entity by `id`
| /author/{id}/books | GET | List all books with all properties for Author identified by `id` | 
| /author/search?c=2 | GET | JSON list of Authors, which have written more or equal than 2 books, without displaying the books in the JSON.
| /book | GET | JSON list of Books, with all properties |
| /book/{id} | GET | Book with `id`, or `500 error` if it doesn't exist | 
| /book | POST | Returns saved entity | Saves or updates entity, based on `id`.
| /book/{id} | DELETE | - | Deletes entity by `id`
| /book/search?year=1353 | GET | Books written in 1353, with all properties |


Run all tests locally, frequently to make sure you correctly solved the tasks! 
