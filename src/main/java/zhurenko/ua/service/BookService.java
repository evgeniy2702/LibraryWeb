package zhurenko.ua.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhurenko.ua.hebirnate.HibernateBookDAO;
import zhurenko.ua.model.Book;
import java.util.List;


@Service
public class BookService implements BookInterface {

    private final HibernateBookDAO hibernateBookDAO;

    @Autowired
    public BookService(HibernateBookDAO hibernateBookDAO) {
        this.hibernateBookDAO = hibernateBookDAO;
    }

    @Override
    public void saveBook(Book book){
        hibernateBookDAO.addBook(book);
    }

    @Override
    public void deleteBook(Book book){
        hibernateBookDAO.deleteBook(book);
    }

    @Override
    public void updateBook(Book book){
        hibernateBookDAO.updateBook(book);
    }

    @Override
    public Book getByIdBook(Long id){
        return hibernateBookDAO.getBook(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return hibernateBookDAO.getAllBooks();
    }

    @Override
    public List<Book> searchBook(String search) {
        return hibernateBookDAO.searchBook(search);
    }
}
