package ru.ftc.library.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ftc.library.api.model.Reader;
import ru.ftc.library.api.service.ReaderService;

import java.util.List;

@RequiredArgsConstructor
@Slf4j //logger
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

    @GetMapping("/getBoys/")
    @ResponseStatus(HttpStatus.OK)
    List<Reader> getBoys() {
        log.info("getBoys <-");
        return  readerService.getAllBoys();
    }

}




