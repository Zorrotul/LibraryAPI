package ru.ftc.library.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.ftc.library.api.error.BooksIssueJnCreationException;
import ru.ftc.library.api.jpa.*;
import ru.ftc.library.api.model.entities.BooksIssueJn;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@Service
public class BooksIssueJnServiceBean implements BooksIssueJnService {

    private final BooksIssueJnRepository booksIssueJnRepository;
    private final ReaderRepository readerRepository;
    private final BookRepository bookRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void createNewBooksIssueJn(BooksIssueJn newJournal) {

        BooksIssueJnEntity booksIssueJnEntity;
        try {
            //if (readerRepository.findById(newJournal.getReaderId()).isPresent()) {
                if (bookRepository.findById(newJournal.getBookId())
                        .filter(bookEntity -> bookEntity.getNumberOfOCopies()>0).isPresent()) {
                    booksIssueJnEntity = BooksIssueJnEntity.builder()
                            .bookId(newJournal.getBookId())
                            .readerId(newJournal.getReaderId())
                            .dateOfIssue(LocalDate.now())
                            .build();

                    booksIssueJnRepository.saveAndFlush(booksIssueJnEntity);

                } else {
                    log.error("Cant give this reader the book cause: the library doesn't have book with this id: {} ", newJournal.getBookId());
                    throw new BooksIssueJnCreationException(
                            String.format("Cant give this reader the book cause: the library doesn't have book with this id: %s ", newJournal.getBookId()));
                }
//            } else {
//                log.error("Cant give this reader the book cause: reader with this id: {} does not exist", newJournal.getReaderId());
//                throw new BooksIssueJnCreationException(
//                        String.format("Cant give this reader the book cause: reader with this id: %s does not exist", newJournal.getReaderId()));
            //}
        } catch (Exception e) {
            log.error("Cant give this reader the book cause: the library doesn't have book with this id: {}... ", newJournal, e);
            throw new BooksIssueJnCreationException(e);
        }

    }

}
