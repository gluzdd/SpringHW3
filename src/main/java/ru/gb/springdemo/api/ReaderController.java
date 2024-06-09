package ru.gb.springdemo.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
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
@Tag(name = "Readers")
public class ReaderController {
    private final ReaderService service;

    @Autowired
    public ReaderController(ReaderService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    @Operation(summary = "getReaderById", description = "Найти читателя по id")
    public ResponseEntity<Reader> getReaderById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.getReaderById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping
    @Operation(summary = "getReaders", description = "Вывести список всех читателей")
    public ResponseEntity<List<Reader>> getReaders() {
        return ResponseEntity.ok(service.getReaders());
    }

    @PostMapping
    @Operation(summary = "createReader", description = "Добавить читателя в общий список(библиотека)")
    public ResponseEntity<Reader> createReader(@RequestBody Reader reader) {
        return new ResponseEntity<>(service.addReader(reader), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "updateReader", description = "Изменить информацию о читателе")
    public ResponseEntity<Reader> updateReader(@PathVariable Long id, @RequestBody Reader reader) {
        return ResponseEntity.ok(service.updateReader(id, reader));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "removeReader", description = "Удалить читателя из общего списка")
    public ResponseEntity<Void> removeReader(@PathVariable Long id) {
        service.removeReader(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/issue")
    @Operation(summary = "getAllIssueByReaderId", description = "Найти список выдач читателя по его id")
    public ResponseEntity<List<Issue>> getAllIssueByReaderId(@PathVariable Long id) {
        return ResponseEntity.ok(service.getListIssueById(id));
    }
}
