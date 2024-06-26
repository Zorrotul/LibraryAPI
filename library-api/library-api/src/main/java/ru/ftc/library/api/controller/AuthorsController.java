package ru.ftc.library.api.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ftc.library.api.jpa.AuthorEntity;
import ru.ftc.library.api.model.Author;
import ru.ftc.library.api.model.Reader;
import ru.ftc.library.api.service.AuthorService;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/authors/")
public class AuthorsController {

    private final AuthorService authorService;

    @PostMapping("/addAuthor/")
    @ResponseStatus(HttpStatus.CREATED)
    void addAuthor(@RequestBody @Valid Author newAuthor) {
        log.info("addAuthor <- newAuthor = {}", newAuthor);
        authorService.createNewAuthor(newAuthor);
    }

    @GetMapping("/getAllAuthors/")
    @ResponseStatus(HttpStatus.OK)
    List<Author> getAllAuthors() {
        log.info("getAllAuthors <-");
        return authorService.getAllAuthors();
    }

}