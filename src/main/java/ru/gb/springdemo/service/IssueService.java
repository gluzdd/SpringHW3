package ru.gb.springdemo.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.repository.BookRepository;
import ru.gb.springdemo.repository.IssueRepository;
import ru.gb.springdemo.repository.ReaderRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class IssueService {

    @Value("${application.max-allowed-books:1}")
    private int limitBooks;

    // спринг это все заинжектит
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;
    private final IssueRepository issueRepository;

    @Autowired
    public IssueService(BookRepository bookRepository, ReaderRepository readerRepository, IssueRepository issueRepository) {
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
        this.issueRepository = issueRepository;
    }

    public Issue issue(Issue issue) {
        if (bookRepository.findById(issue.getBookId()).isEmpty()) {
            throw new NoSuchElementException("Не найдена книга с идентификатором \"" + issue.getBookId() + "\"");
        }
        if (readerRepository.findById(issue.getReaderId()).isEmpty()) {
            throw new NoSuchElementException("Не найден читатель с идентификатором \"" + issue.getReaderId() + "\"");
        }
        // можно проверить, что у читателя нет книг на руках (или его лимит не превышает в Х книг)
        if (issueRepository.getIssueByReaderId(issue.getReaderId()).size() >= limitBooks) {
            throw new NullPointerException("Макс кол-во книг \"" + issue.getReaderId() + "\"");
        }
        //Issue issue2 = new Issue(issue.getBookId(), issue.getReaderId());
        issue.setIssuedAt(LocalDate.now());
        issueRepository.save(issue);
        return issue;
    }


    public List<Issue> getIssue() {
        return issueRepository.findAll();
    }

    public Issue getIssueById(Long id) {
        return issueRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    @Transactional
    public Issue returnBooks(Long id) {
        Issue updateIssue = issueRepository.findById(id).orElseThrow(()-> new RuntimeException("Issue not found"));
        updateIssue.setTimeReturn(LocalDate.now());
        return updateIssue;
    }

    public void deleteIssue(Long id) {
        issueRepository.deleteById(id);
    }

//    public void returnBooks(Long id) {
//        Issue issue;
//        if (issueRepository.getIssueById(id) != null) {
//            issueRepository.getIssueById(id).setTimeReturn(LocalDateTime.now());
//        }
//    }


}
