package zhurenko.ua.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zhurenko.ua.model.Book;
import zhurenko.ua.service.BookService;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("")
    public String showBook(Model model) throws SQLException, ClassNotFoundException {
        model.addAttribute("books", bookService.getAllBooks());
        return "showBooks";
    }

    @GetMapping("/book/{id}")
    public String getBook(@PathVariable(value = "id", required = false) Long id,
                          Model model) throws SQLException, ClassNotFoundException {
        model.addAttribute("book", bookService.getByIdBook(id));
        return "book";
    }

    @GetMapping("/book/new")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("default", "Default value");
        return "addBook";
    }

    @GetMapping("/book/delete/{id}")
    public String deleteBook(@PathVariable(value = "id", required = false) Long id,
                             Model model){
        Book book = bookService.getByIdBook(id);

        bookService.deleteBook(book);
        model.addAttribute("books", bookService.getAllBooks());
        return "redirect:/";
    }

    @PostMapping("/book/add")
    public String saveBook(@ModelAttribute Book book,
                           Model model){
        if(bookService.getAllBooks().stream().filter(b -> b.getName().equalsIgnoreCase(book.getName()) &&
                b.getAuthor().equalsIgnoreCase(book.getAuthor()) &&
                b.getYear() == book.getYear() &&
                b.getStileOfBook().equalsIgnoreCase(book.getStileOfBook()) &&
                b.getNumPages() == book.getNumPages() &&
                b.getDescription().equalsIgnoreCase(book.getDescription()))
                .collect(Collectors.toList()).isEmpty()) {
            bookService.saveBook(book);
            return "redirect:/";
        }
        else{
            model.addAttribute("string", "Such a book already exists!" );
            List<Book> book1 = bookService.getAllBooks().stream()
                                                        .filter(b -> b.getName().equalsIgnoreCase(book.getName()))
                                                        .collect(Collectors.toList());
            System.out.println(book1.toString());
            model.addAttribute("bookDB", book1);
            return "addBook";
        }
    }

    @PostMapping("/book/update/{id}")
    public String updateBook(@PathVariable(value = "id", required = false) Long id,
                             @ModelAttribute Book book){
        bookService.updateBook(book);
        return "redirect:/";
    }

    @GetMapping("/book/update/{id}")
    public String showUpdateBook(@PathVariable(value = "id", required = false) Long id,
                                 Model model){
        model.addAttribute("book",bookService.getByIdBook(id));
        return "updateBook";
    }

    @GetMapping("/book/search")
    public String search(@ModelAttribute Book book){

        return "searchBook";
    }

    @GetMapping("/books")
    public String searchBook(@RequestParam(value = "search", required = false) String search,
                             Model model){
        model.addAttribute("booksSearch", bookService.searchBook(search));
        return "searchBook";
    }
}
