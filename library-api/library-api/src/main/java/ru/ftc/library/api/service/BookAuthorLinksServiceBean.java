package ru.ftc.library.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.ftc.library.api.error.BookAuthorLinksCreationException;
import ru.ftc.library.api.jpa.AuthorRepository;
import ru.ftc.library.api.jpa.BookAuthorLinksEntity;
import ru.ftc.library.api.jpa.BookAuthorLinksRepository;
import ru.ftc.library.api.jpa.BookRepository;
import ru.ftc.library.api.model.entities.BookAuthorLinks;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookAuthorLinksServiceBean implements BookAuthorLinksService {

    private final BookAuthorLinksRepository bookAuthorLinksRepository;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void createNewBookAuthorLink(BookAuthorLinks newBookAuthorLinks) {
        BookAuthorLinksEntity bookAuthorLinksEntity;
        try {
            if (bookRepository.findById(newBookAuthorLinks.getBookId()).isPresent()
                    && authorRepository.findById(newBookAuthorLinks.getAuthorId()).isPresent()) {
                bookAuthorLinksEntity = BookAuthorLinksEntity.builder()
                        .bookId(newBookAuthorLinks.getBookId())
                        .authorId(newBookAuthorLinks.getAuthorId())
                        .build();
            } else {
                log.error("Cant create new bookAuthorLinks = {}, cause: one of them not exist", newBookAuthorLinks);
                throw new BookAuthorLinksCreationException(
                        String.format("Cant create new bookAuthorLinks = %s, cause: one of them not exist", newBookAuthorLinks));
            }
            bookAuthorLinksRepository.saveAndFlush(bookAuthorLinksEntity);
        } catch (Exception e) {
            log.error("Cant create new bookAuthorLinks = {}, cause: {}", newBookAuthorLinks, e.getMessage(), e);
            throw new BookAuthorLinksCreationException(e);
        }
    }

//    @Transactional(propagation = Propagation.REQUIRED)
//    @Override
//    public List<BookAuthorLinks> getAllBoys() {
//        return bookAuthorLinksRepository.findAllBySex(Sex.M).stream()
//                .map(r -> BookAuthorLinks.builder()
//                        .name(r.getName())
//                        .surname(r.getSurname())
//                        .patronymic(r.getPatronymic())
//                        .sex(r.getSex())
//                        .birthday(r.getBirthday())
//                        .build())
//                .collect(Collectors.toList());
//    }
}
