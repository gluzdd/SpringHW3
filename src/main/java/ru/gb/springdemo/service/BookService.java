package ru.gb.springdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private final BookRepository repository;

    @Autowired
    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public Book getBookById(Long id) {
        return repository.getBookById(id);
    }

    public List<Book> getBooks() {
        return repository.getBooks();
    }

    public Book addBook(Book book) {
        return repository.addBook(book);
    }

    public Book updateBook(Long id, Book book) {
        return repository.updateBook(id, book);
    }

    public void removeBook(Long id) {
        repository.removeBook(id);
    }
}
