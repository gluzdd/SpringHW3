package ru.gb.springdemo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "readers")
@Schema(name = "Читатель")
public class Reader {

  @Id
  @Schema(name = "Идентификатор")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Schema(name = "Имя читателя")
  private String name;

    public Reader() {
    }

    public Reader(String name) {
        this.name = name;
    }

    public Reader(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
