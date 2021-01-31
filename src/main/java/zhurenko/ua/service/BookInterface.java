package zhurenko.ua.service;

import zhurenko.ua.model.Book;

import java.util.List;

public interface BookInterface {

    void saveBook(Book book);

    void deleteBook(Book book);

    void updateBook(Book book);

    Book getByIdBook(Long id);

    List<Book> getAllBooks();

    List<Book> searchBook(String search);

}
