package ru.gb.springdemo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Запись о факте выдачи книги (в БД)
 */
@Data
@Entity
@Table(name = "issues")
@Schema(name = "Выдачи")
public class Issue {

    @Id
    @Schema(name = "Идентификатор")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(name = "Идентификатор книги")
    private Long bookId;
    @Schema(name = "Идентификатор читателя")
    private Long readerId;

    public Issue() {
    }
    /**
     * Дата выдачи
     */
    @Schema(name = "Время выдачи книги")
    private LocalDate issuedAt;
    @Schema(name = "Время возврата книги")
    private LocalDate timeReturn = null;


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

    public LocalDate getIssuedAt() {
        return issuedAt;
    }

    public LocalDate getTimeReturn() {
        return timeReturn;
    }


}
