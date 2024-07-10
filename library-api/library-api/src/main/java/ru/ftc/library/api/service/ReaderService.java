package ru.ftc.library.api.service;

import ru.ftc.library.api.model.entities.Reader;

import java.util.List;

public interface ReaderService {
    void createNewReader(Reader newReader);

    List<Reader> getAllBoys();
}
