package ru.gb.springdemo.api;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.springdemo.aspects.Timer;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.service.BookService;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
@Tag(name = "Books")
public class BookController {

    private final BookService service;

    @Autowired
    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    @Operation(summary = "getBookById", description = "Найти книгу по id")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> someBook = service.getBookById(id);
        if(someBook.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(service.getBookById(id).orElseThrow(null));
        }
    }

    @Timer
    @GetMapping
    @Operation(summary = "getBooks", description = "Вывести список всех книг")
    public ResponseEntity<List<Book>> getBooks() {
        return ResponseEntity.ok(service.getBooks());
    }

    @PostMapping
    @Operation(summary = "createBook", description = "Добавить книгу в общий список(библиотека)")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return new ResponseEntity<>(service.addBook(book), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "updateBook", description = "Изменить информацию о книге")
    public ResponseEntity<Book> updateBook (@PathVariable Long id, @RequestBody Book book) {
        return ResponseEntity.ok(service.updateBook(id, book));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "removeBook", description = "Удалить книгу из общего списка")
    public ResponseEntity<Void> removeBook(@PathVariable Long id) {
        service.removeBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
