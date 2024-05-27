package ru.gb.springdemo.api;

import lombok.Data;

/**
 * Запрос на выдачу
 */

public class IssueRequest {

  /**
   * Идентификатор читателя
   */
  private Long readerId;

  /**
   * Идентификатор книги
   */
  private Long bookId;

  public Long getReaderId() {
    return readerId;
  }

  public void setReaderId(Long readerId) {
    this.readerId = readerId;
  }

  public Long getBookId() {
    return bookId;
  }

  public void setBookId(Long bookId) {
    this.bookId = bookId;
  }
}
