package ru.gb.springdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.model.Reader;
import ru.gb.springdemo.repository.BookRepository;
import ru.gb.springdemo.repository.IssueRepository;
import ru.gb.springdemo.repository.ReaderRepository;

import java.util.List;

@Service
public class ReaderService {

    private final ReaderRepository repository;
    private final IssueRepository issueRepository;

    public ReaderService(ReaderRepository repository, IssueRepository issueRepository) {
        this.repository = repository;
        this.issueRepository = issueRepository;
    }

    public Reader getReaderById(Long id) {
        return repository.getReaderById(id);
    }

    public List<Reader> getReaders() {
        return repository.getReaders();
    }

    public Reader addReader(Reader reader) {
        return repository.addReader(reader);
    }

    public Reader updateReader(Long id, Reader reader) {
        return repository.updateReader(id, reader);
    }

    public void removeReader(Long id) {
        repository.removeReader(id);
    }

    public List<Issue> getListIssueById(Long id) {
        return issueRepository.getIssueByReaderId(id);
    }
}
