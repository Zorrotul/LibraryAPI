package ru.ftc.library.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ftc.library.api.model.Reader;
import ru.ftc.library.api.service.ReaderService;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/readers/")
public class ReadersController {

    private final ReaderService readerService;

    @PostMapping("/addReader/")
    @ResponseStatus(HttpStatus.CREATED)
    void addReader(@RequestBody @Valid Reader newReader) {
        log.info("addReader <- newReader = {}", newReader);
        readerService.createNewReader(newReader);
    }
}




