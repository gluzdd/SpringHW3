package ru.gb.springdemo.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import ru.gb.springdemo.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class BookRepository {

  private final Map<Long, Book> books = new ConcurrentHashMap<>();
  private static AtomicLong counter = new AtomicLong(0L);

  // Добавление книг в мапу
  @PostConstruct
  public void generateBooks() {
    books.put(counter.incrementAndGet(), new Book(counter.get(), "Война и мир"));
    books.put(counter.incrementAndGet(), new Book(counter.get(), "Метрвые души"));
    books.put(counter.incrementAndGet(), new Book(counter.get(), "Чистый код"));
    books.put(counter.incrementAndGet(), new Book(counter.get(), "Лёд и пламя"));
    books.put(counter.incrementAndGet(), new Book(counter.get(), "Игра престолов"));
  }

  // Показать все книги по id
  public Book getBookById(Long id) {
    return books.get(id);
  }

  // Показать все книги
  public List<Book> getBooks() {
    return new ArrayList<>(books.values());
  }

  // Создать книгу
  public Book addBook(Book book) {
    if (book.getId() == null) {
      book.setId(counter.incrementAndGet());
    }
    books.put(book.getId(), book);
    return book;
  }

  // Обновить книгу
  public Book updateBook(Long id, Book book) {
    Book updateBook = books.get(id);
    if (updateBook != null) {
      updateBook.setName(book.getName());
    }
    return updateBook;
  }

  // Удалить книгу
  public void removeBook(Long id) {
    books.remove(id);
  }

}
