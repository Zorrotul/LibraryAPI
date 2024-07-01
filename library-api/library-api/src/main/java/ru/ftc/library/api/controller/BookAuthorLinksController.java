package ru.ftc.library.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ftc.library.api.model.entities.BookAuthorLinks;
import ru.ftc.library.api.service.BookAuthorLinksService;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/bookAuthorLink/")
public class BookAuthorLinksController {

    private final BookAuthorLinksService bookAuthorLinksService;

    @PostMapping("/addBookAuthorLinks/")
    @ResponseStatus(HttpStatus.CREATED)
    void addBookAuthorLinks(@RequestBody @Valid BookAuthorLinks newBookAuthorLinks) {
        log.info("addBookAuthorLinks <- newBookAuthorLinks = {}", newBookAuthorLinks);
        bookAuthorLinksService.createNewBookAuthorLink(newBookAuthorLinks);
    }

}




