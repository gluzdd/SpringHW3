package ru.gb.springdemo.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.model.Reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReaderRepository {

    private final Map<Long, Reader> readers = new ConcurrentHashMap<>();
    private static AtomicLong counter = new AtomicLong(0L);
    public List<Issue> listIssue = new ArrayList<>();


    @PostConstruct
    public void generateReaders() {
        readers.put(counter.incrementAndGet(), new Reader(counter.get(), "Javokhir"));
        readers.put(counter.incrementAndGet(), new Reader(counter.get(), "Kris"));
        readers.put(counter.incrementAndGet(), new Reader(counter.get(), "Vika"));
    }

    // Заполнение списка выдачи
    public void showReaderOfIssue(Issue issue) {
        listIssue.add(issue);
    }

    // Получение списка запросов по id читателей
    public List<Issue> getListIssueById(Long id) {
        return listIssue.stream().filter(issue -> issue.getReaderId().equals(id)).toList();
    }

    // Показать всех читателей id
    public Reader getReaderById(Long id) {
        return readers.get(id);
    }

    // Показать всех читателей
    public List<Reader> getReaders() {
        return new ArrayList<>(readers.values());
    }

    // Создать читателя
    public Reader addReader(Reader reader) {
        if (reader.getId() == null) {
            reader.setId(counter.incrementAndGet());
        }
        readers.put(reader.getId(), reader);
        return reader;
    }

    // Обновить читателя
    public Reader updateReader(Long id, Reader reader) {
        Reader updateReader = readers.get(id);
        if (updateReader != null) {
            updateReader.setName(reader.getName());
        }
        return updateReader;
    }

    // Удалить читателя
    public void removeReader(Long id) {
        readers.remove(id);
    }


}
