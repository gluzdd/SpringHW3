package ru.gb.springdemo.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.service.IssueService;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/issue")
public class IssuerController {

    @Autowired
    private IssueService service;

//  @PutMapping
//  public void returnBook(long issueId) {
//    // найти в репозитории выдачу и проставить ей returned_at
//  }

    @PostMapping
    public ResponseEntity<Issue> issueBook(@RequestBody Issue issue) {
        log.info("Получен запрос на выдачу: readerId = {}, bookId = {}", issue.getReaderId(), issue.getBookId());
        try {
            issue = service.issue(issue);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(issue);
    }

    @GetMapping
    public ResponseEntity<List<Issue>> getIssue() {
        return ResponseEntity.ok(service.getIssue());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getIssueById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Issue> returnBooks(@PathVariable Long id) {
        Issue updatedIssue = service.returnBooks(id);
        if (updatedIssue != null) {
            return new ResponseEntity<>(updatedIssue, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeIssue(@PathVariable Long id) {
        service.deleteIssue(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
