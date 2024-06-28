package ru.gb.springdemo.api;

import lombok.Data;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.gb.springdemo.JUnitSpringBootBase;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.model.Reader;
import ru.gb.springdemo.repository.BookRepository;
import ru.gb.springdemo.repository.IssueRepository;
import ru.gb.springdemo.repository.ReaderRepository;
import ru.gb.springdemo.service.IssueService;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class IssuerControllerTest extends JUnitSpringBootBase {

    @Autowired
    WebTestClient webTestClient;
    @Autowired
    IssueRepository issueRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    ReaderRepository readerRepository;

    @Data
    static class JUnitIssue {
        private Long id;
        private Long bookId;
        private Long readerId;
        private LocalDate issuedAt;
        private LocalDate timeReturn;

        public JUnitIssue(Long bookId, Long readerId) {
            this.bookId = bookId;
            this.readerId = readerId;
        }
    }

    @Test
    void testGetIssueById() {
        Book book = bookRepository.save(new Book("Java"));
        Reader reader = readerRepository.save(new Reader("Artem"));
        Issue testIssue = issueRepository.save(new Issue(book.getId(), reader.getId()));

        JUnitIssue issue = webTestClient.get()
                .uri("/issue/" + testIssue.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(JUnitIssue.class)
                .returnResult().getResponseBody();
        Assertions.assertNotNull(issue);
        Assertions.assertEquals(testIssue.getId(), issue.getId());
        Assertions.assertEquals(testIssue.getReaderId(), issue.getReaderId());
        Assertions.assertEquals(testIssue.getBookId(), issue.getBookId());
        Assertions.assertEquals(testIssue.getIssuedAt(), issue.getIssuedAt());
        Assertions.assertEquals(testIssue.getTimeReturn(), issue.getTimeReturn());
        Assertions.assertNull(testIssue.getTimeReturn());
    }

    @Test
    void testFindByNotFound() {
        Long maxId = Long.MAX_VALUE;

        webTestClient.get()
                .uri("/issue/" + maxId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testGetAllIssue() {
        Book book = bookRepository.save(new Book("Java"));
        Reader reader = readerRepository.save(new Reader("Artem"));
        Book book2 = bookRepository.save(new Book("Java2"));
        Reader reader2 = readerRepository.save(new Reader("Artem2"));
        issueRepository.saveAll(List.of(new Issue(book.getId(), reader.getId()),
                new Issue(book2.getId(), reader2.getId())));
        List<Issue> testIssue = issueRepository.findAll();

        List<JUnitIssue> allIssue = webTestClient.get()
                .uri("/issue")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<JUnitIssue>>() {})
                .returnResult().getResponseBody();

        Assertions.assertNotNull(allIssue);
        Assertions.assertEquals(testIssue.size(), allIssue.size());
        for (JUnitIssue issue : allIssue) {
            boolean found = testIssue.stream()
                    .filter(it -> Objects.equals(it.getId(), issue.getId()))
                    .filter(it -> Objects.equals(it.getBookId(), issue.getBookId()))
                    .filter(it -> Objects.equals(it.getReaderId(), issue.getReaderId()))
                    .anyMatch(it -> Objects.equals(it.getIssuedAt(), issue.getIssuedAt()));
            Assertions.assertTrue(found);
        }
    }

    @Test
    void testSaveIssue() {
        Book book = bookRepository.save(new Book("Java"));
        Reader reader = readerRepository.save(new Reader("Artem"));
        JUnitIssue testIssueSave = new JUnitIssue(book.getId(), reader.getId());

        JUnitIssue issue = webTestClient.post()
                .uri("/issue")
                .bodyValue(testIssueSave)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(JUnitIssue.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(issue);
        Assertions.assertNotNull(issue.id);
        Assertions.assertTrue(issueRepository.findById(issue.getId()).isPresent());
        Assertions.assertEquals(testIssueSave.getBookId(), issue.getBookId());
        Assertions.assertEquals(testIssueSave.getReaderId(), issue.getReaderId());
    }

    @Test
    void testDeletedById() {
        Book book = bookRepository.save(new Book("Java"));
        Reader reader = readerRepository.save(new Reader("Artem"));
        Issue testIssueDelete = issueRepository.save(new Issue(book.getId(), reader.getId()));

        webTestClient.delete()
                .uri("/issue/" + testIssueDelete.getId())
                .exchange()
                .expectStatus().isNoContent();

        Assertions.assertFalse(issueRepository.findById(testIssueDelete.getId()).isPresent());
    }

    @Test
    void testUpdatedIssue() {
        Book book = bookRepository.save(new Book("Java"));
        Reader reader = readerRepository.save(new Reader("Artem"));
        Issue issueUpdate = issueRepository.save(new Issue(book.getId(), reader.getId()));
        JUnitIssue testIssueUpdate = new JUnitIssue(book.getId(), reader.getId());

        JUnitIssue issue = webTestClient.put()
                .uri("/issue/" + issueUpdate.getId())
                .bodyValue(testIssueUpdate)
                .exchange()
                .expectStatus().isOk()
                .expectBody(JUnitIssue.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(issue);
        Assertions.assertEquals(issueUpdate.getId(), issue.getId());
        Assertions.assertEquals(issueUpdate.getReaderId(), issue.getReaderId());
        Assertions.assertEquals(issueUpdate.getBookId(), issue.getBookId());
        Assertions.assertEquals(issueUpdate.getIssuedAt(), issue.getIssuedAt());
        Assertions.assertEquals(LocalDate.now(), issue.getTimeReturn());
        Assertions.assertTrue(issueRepository.findById(issue.getId()).isPresent());

    }
}