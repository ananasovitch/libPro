package dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import model.Author;
import model.Book;
import model.BookRequest;
import model.BookResponse;


public class LibraryDao {

    @PersistenceContext
    private EntityManager entityManager;

    public boolean authorExists(String firstName, String familyName) {
// Логика проверки наличия автора в базе данных
// Например:
        TypedQuery<Long> query = (TypedQuery<Long>) entityManager.createQuery("SELECT COUNT(a) FROM Author a WHERE a.firstName = :firstName AND a.familyName = :familyName");
        query.setParameter("firstName", firstName);
        query.setParameter("familyName", familyName);
        return query.getSingleResult() > 0;
    }

    @Transactional
    public Long saveAuthor(Author author) {
        entityManager.persist(author);
        return author.getId();
    }

    @Transactional
    public BookResponse saveBook(BookRequest request) {
        Book book = new Book();
        book.setBookTitle(request.getBookTitle());
        String firstName = request.getAuthor().getFirstName();// существует ли автор

        String familyName = request.getAuthor().getFamilyName();
        Author existingAuthor;
        if (authorExists(firstName, familyName)) {
            existingAuthor = entityManager.createQuery("SELECT a FROM Author a WHERE a.firstName = :firstName AND a.familyName = :familyName", Author.class)
                    .setParameter("firstName", firstName)
                    .setParameter("familyName", familyName)
                    .getSingleResult();
        } else {
            existingAuthor = new Author();// Создаем нового автора
            existingAuthor.setFirstName(firstName);
            existingAuthor.setFamilyName(familyName);
            existingAuthor.setSecondName(request.getAuthor().getSecondName());
            entityManager.persist(existingAuthor);
        }

        book.setAuthor(existingAuthor);// Устанавливаем автора книги
        entityManager.persist(book);// Сохраняем информацию о книге в базе данных

        BookResponse response = new BookResponse();// Возвращение объекта BookResponse с результатами сохранения
        response.setBookId(book.getId()); // Устанавливаем ID сохраненной книги
        return response;
    }
}