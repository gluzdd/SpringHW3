package ru.gb.springdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        return repository.findById(id).get();
    }

    public List<Book> getBooks() {
        return repository.findAll();
    }

    public Book addBook(Book book) {
        return repository.save(book);
    }

    @Transactional
    public Book updateBook(Long id, Book book) {
        Book updateBook = repository.findById(id).orElseThrow(()-> new RuntimeException("id not found"));
        updateBook.setName(book.getName());
        return updateBook;
    }

    public void removeBook(Long id) {
        repository.deleteById(id);
    }
}
