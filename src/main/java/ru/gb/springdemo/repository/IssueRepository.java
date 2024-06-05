package ru.gb.springdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.model.Issue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

//    private final AtomicLong counter =new AtomicLong();
//    private final Map<Long, Issue> issues = new ConcurrentHashMap<>();
//
//    //Сохранить в список выдач
//    public void save(Issue issue) {
//        issue.setId(counter.incrementAndGet());
//        issues.put(issue.getId(), issue);
//    }
//
//    //Показать список выдач
//    public List<Issue> getIssue() {
//        return new ArrayList<>(issues.values());
//    }
//
//    //Показать список выдач по id
//    public Issue getIssueById(Long id) {
//        return issues.get(id);
//    }
//
//    public Issue update(Long id) {
//        Issue updatedIssue = issues.get(id);
//        if (updatedIssue != null) {
//            updatedIssue.setTimeReturn(LocalDateTime.now());
//        }
//        return updatedIssue;
//    }
//
//
//    //Получить запрос по id читателя
////    public Issue getIssueByReaderId(Long id) {
////        return issues.values().stream().filter(issue -> issue.getReaderId().equals(id)).findFirst().orElse(null);
////    }
//
    @Query("SELECT i FROM Issue i WHERE i.readerId = :id")
    List<Issue> getIssueByReaderId(Long id);
//        return issues.values().stream()
//                .filter(issue -> issue.getReaderId().equals(id))
//                .filter(issue -> issue.getTimeReturn() == null)
//                .toList();
//    }
}
