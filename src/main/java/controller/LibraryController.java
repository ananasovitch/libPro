package controller;

import model.AuthorRequest;
import model.AuthorResponse;
import service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LibraryController {
    private final LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping("/authors/save")
    public AuthorResponse saveAuthor(@RequestBody AuthorRequest request, @RequestHeader("content-type") String contentType) {
        return libraryService.saveAuthor(request);
    }
}