package ru.gb.springdemo.api;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.parameters.P;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.gb.springdemo.JUnitSpringBootBase;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.repository.BookRepository;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class BookControllerTest extends JUnitSpringBootBase {

    @Autowired
    WebTestClient webTestClient;
    @Autowired
    BookRepository bookRepository;

    @Data
    static class JUnitBook {
        private Long id;
        private String name;
    }

    @Test
    void testGetBookById() {
        Book testBook = bookRepository.save(new Book("Гарри Поттер"));

        JUnitBook book = webTestClient.get()
                .uri("/books/" + testBook.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(JUnitBook.class)
                .returnResult().getResponseBody();
        Assertions.assertNotNull(book);
        Assertions.assertEquals(testBook.getId(), testBook.getId());
        Assertions.assertEquals(testBook.getName(), testBook.getName());
    }

    @Test
    void testFindByIdNotFound() {
        Long maxId = Long.MAX_VALUE;

        webTestClient.get()
                .uri("/books/" + maxId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testGetAll() {
        bookRepository.saveAll(List.of(new Book("Hello, hello"),
                new Book("Bye, bye")));
        List<Book> testBooks = bookRepository.findAll();

        List<JUnitBook> allbooks = webTestClient.get()
                .uri("/books")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<JUnitBook>>() {
                })
                .returnResult().getResponseBody();

        Assertions.assertNotNull(allbooks);
        Assertions.assertEquals(testBooks.size(), allbooks.size());
        for (JUnitBook book : allbooks) {
            boolean found = testBooks.stream()
                    .filter(it -> Objects.equals(it.getId(), book.getId()))
                    .anyMatch(it -> Objects.equals(it.getName(), book.getName()));
            Assertions.assertTrue(found);
        }
    }

    @Test
    void testSave() {
        JUnitBook testBook = new JUnitBook();
        testBook.setName("May");

        JUnitBook book = webTestClient.post()
                .uri("/books")
                .bodyValue(testBook)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(JUnitBook.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(book);
        Assertions.assertNotNull(book.id);
        Assertions.assertTrue(bookRepository.findById(book.getId()).isPresent());
        Assertions.assertEquals(testBook.getName(), book.getName());
    }

    @Test
    void testDeleteById() {
        Book deletedBook = bookRepository.save(new Book("Mao mao"));

        webTestClient.delete()
                .uri("/books/" + deletedBook.getId())
                .exchange()
                .expectStatus().isNoContent();

        Assertions.assertFalse(bookRepository.findById(deletedBook.getId()).isPresent());
    }

    @Test
    void testUpdate() {
        Book updatedBook = bookRepository.save(new Book("tukituki"));
        JUnitBook testUpdateBook = new JUnitBook();
        testUpdateBook.setName("May");

        JUnitBook bookRes = webTestClient.put()
                .uri("/books/" + updatedBook.getId())
                .bodyValue(testUpdateBook)
                .exchange()
                .expectStatus().isOk()
                .expectBody(JUnitBook.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(bookRes);
        Assertions.assertEquals(updatedBook.getId(), bookRes.getId());
        Assertions.assertEquals(testUpdateBook.getName(), bookRes.getName());
    }
}