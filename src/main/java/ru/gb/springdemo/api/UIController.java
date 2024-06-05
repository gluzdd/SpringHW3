package ru.gb.springdemo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.model.Reader;
import ru.gb.springdemo.service.BookService;
import ru.gb.springdemo.service.IssueService;
import ru.gb.springdemo.service.ReaderService;

import java.util.List;

@Controller
@RequestMapping("/ui")
public class UIController {

    private final BookService bookService;
    private final ReaderService readerService;
    private final IssueService issueService;

    @Autowired
    public UIController(BookService bookService, ReaderService readerService, IssueService issueService) {
        this.bookService = bookService;
        this.readerService = readerService;
        this.issueService = issueService;
    }

    @GetMapping("/books")
    public String getAllBooks(Model model) {
        List<Book> listBooks = bookService.getBooks();
        model.addAttribute("books", listBooks);
        return "books";
    }

    @GetMapping("/readers")
    public String getAllReaders(Model model) {
        List<Reader> listReaders = readerService.getReaders();
        model.addAttribute("readers", listReaders);
        return "readers";
    }

    @GetMapping("/issues")
    public String getAllIssues(Model model) {
        List<Issue> listAllIssues = issueService.getIssue();
        model.addAttribute("issues", listAllIssues);
        return "/issues";
    }

//    @GetMapping("/readers/{id}")
//    public String getAllBooksReaderId(@PathVariable Long id, Model model) {
//        List<Issue> listIssue = readerService.getListIssueById(id);
//        String nameReader = readerService.getReaderById(id).getName();
//        model.addAttribute("readerIssues", listIssue);
//        model.addAttribute("nameReader", nameReader);
//        return "readerIssues";
//    }

}
