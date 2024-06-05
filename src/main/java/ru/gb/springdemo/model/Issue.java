package ru.gb.springdemo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Запись о факте выдачи книги (в БД)
 */
@Data
@Entity
@Table(name = "issues")
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long bookId;
    private Long readerId;

    public Issue() {
    }
    /**
     * Дата выдачи
     */
    private LocalDateTime issuedAt;
    private LocalDateTime timeReturn = null;


    public Issue(Long bookId, Long readerId) {
        this.bookId = bookId;
        this.readerId = readerId;
        this.issuedAt = issuedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getBookId() {
        return bookId;
    }

    public Long getReaderId() {
        return readerId;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public LocalDateTime getTimeReturn() {
        return timeReturn;
    }


}
