package ru.gb.springdemo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.model.Reader;
import ru.gb.springdemo.service.BookService;
import ru.gb.springdemo.service.ReaderService;

import java.util.List;

@RestController
@RequestMapping("/readers")
public class ReaderController {
    private final ReaderService service;

    @Autowired
    public ReaderController(ReaderService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reader> getReaderById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getReaderById(id));
    }

    @GetMapping
    public ResponseEntity<List<Reader>> getReaders() {
        return ResponseEntity.ok(service.getReaders());
    }

    @PostMapping
    public ResponseEntity<Reader> createReader(@RequestBody Reader reader) {
        return new ResponseEntity<>(service.addReader(reader), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reader> updateReader(@PathVariable Long id, @RequestBody Reader reader) {
        return ResponseEntity.ok(service.updateReader(id, reader));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeReader(@PathVariable Long id) {
        service.removeReader(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/issue")
    public ResponseEntity<List<Issue>> getAllIssueByReaderId(@PathVariable Long id) {
        return ResponseEntity.ok(service.getListIssueById(id));
    }
}
