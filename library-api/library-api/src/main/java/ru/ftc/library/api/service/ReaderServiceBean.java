package ru.ftc.library.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.ftc.library.api.error.ReaderCreationException;
import ru.ftc.library.api.jpa.ReaderEntity;
import ru.ftc.library.api.jpa.ReaderRepository;
import ru.ftc.library.api.model.Sex;
import ru.ftc.library.api.model.Reader;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReaderServiceBean implements ReaderService {

    private final ReaderRepository readerRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void createNewReader(Reader newReader) {
        ReaderEntity readerEntity = ReaderEntity.builder()
                .name(newReader.getName())
                .surname(newReader.getSurname())
                .patronymic(newReader.getPatronymic())
                .sex(newReader.getSex())
                .birthday(newReader.getBirthday())
                .build();

        try {
            readerRepository.saveAndFlush(readerEntity);
        } catch (Exception e) {
            log.error("Cant create new reader = {}, cause: {}", newReader, e.getMessage(), e);
            throw new ReaderCreationException(e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<Reader> getAllBoys() {
        return readerRepository.findAllBySex(Sex.M).stream()
                .map(r -> Reader.builder()
                        .name(r.getName())
                        .surname(r.getSurname())
                        .patronymic(r.getPatronymic())
                        .sex(r.getSex())
                        .birthday(r.getBirthday())
                        .build())
                .collect(Collectors.toList());
    }
}
