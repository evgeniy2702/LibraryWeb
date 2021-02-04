package zhurenko.ua.hebirnate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zhurenko.ua.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HibernateBookDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public HibernateBookDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    private Session currentSession() {
        return sessionFactory.openSession();
    }


    public void addBook(Book book) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.saveOrUpdate(book);

        transaction.commit();
        session.close();
    }

    public void deleteBook(Book book){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String deleteHql = "delete from books where id = ?;";
        Query delete = session.createNativeQuery(deleteHql);
                delete.setParameter(1, book.getId());
                delete.executeUpdate();

        transaction.commit();
        session.close();
    }

    public void updateBook(Book book) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(book);
        transaction.commit();
        session.close();
    }

    public Book getBook(Long id){
        Transaction transaction = currentSession().beginTransaction();
        Book book = currentSession().get(Book.class, id);
        transaction.commit();
        currentSession().close();
        return book;
    }

    public List<Book> getAllBooks(){
        Transaction transaction = currentSession().beginTransaction();
        List<Book> books = currentSession().createQuery("from Book", Book.class).list();
        transaction.commit();
        currentSession().close();
        return books;
    }

    public List<Book> searchBook(String search) {
        Transaction transaction = currentSession().beginTransaction();
        List<Book> books = currentSession().createQuery("from Book", Book.class).list();
        List<Book> searchBook = new ArrayList<>();
        for (Book book: books) {
            if (book.toString().contains(search)){
                searchBook.add(book);
            }
        }
        transaction.commit();
        currentSession().close();
        return searchBook;
    }
}
