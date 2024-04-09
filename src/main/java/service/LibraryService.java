package service;

import dao.LibraryDao;
import model.*;
import org.springframework.beans.factory.annotation.Autowired;

public class LibraryService {

    private final LibraryDao libraryDao;

    @Autowired
    public LibraryService(LibraryDao libraryDao) {
        this.libraryDao = libraryDao;
    }

    public AuthorResponse saveAuthor(AuthorRequest request) {
// Валидация входных параметров request

// Проверка наличия автора в базе данных
        if (libraryDao.authorExists(request.getFirstName(), request.getFamilyName())) {
            AuthorResponse response = new AuthorResponse();
            response.setAuthorId(-1L); // Идентификатор -1 указывает на ошибку (например, дублирование автора)
            return response;
        }

// Сохранение нового автора
        Author newAuthor = new Author();
        newAuthor.setFirstName(request.getFirstName());
        newAuthor.setFamilyName(request.getFamilyName());
        newAuthor.setSecondName(request.getSecondName());

        Long savedAuthorId = libraryDao.saveAuthor(newAuthor);
        AuthorResponse response = new AuthorResponse();
        response.setAuthorId(savedAuthorId);

        return response;
    }

    public BookResponse saveBook(BookRequest request) {
// Валидация входных параметров request

// Save the book using the libraryDao
        return libraryDao.saveBook(request);
    }
}
