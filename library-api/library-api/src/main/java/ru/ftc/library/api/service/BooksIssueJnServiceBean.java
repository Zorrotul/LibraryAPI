package ru.ftc.library.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.ftc.library.api.error.BooksIssueJnCreationException;
import ru.ftc.library.api.jpa.*;
import ru.ftc.library.api.model.entities.BooksIssueJn;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Optional<BookEntity> book = bookRepository.findById(newJournal.getBookId());
        try {
            if (book
                    .filter(bookEntity -> bookEntity.getNumberOfOCopies() > 0).isPresent()) {
                booksIssueJnEntity = BooksIssueJnEntity.builder()
                        .bookId(newJournal.getBookId())
                        .readerId(newJournal.getReaderId())
                        .dateOfIssue(LocalDateTime.now())
                        .build();

                booksIssueJnRepository.saveAndFlush(booksIssueJnEntity);

                book.map(bookEntity -> {
                    bookEntity.setNumberOfOCopies(bookEntity.getNumberOfOCopies() - 1);
                    return bookEntity;
                });

            } else {
                log.error("Cant give this reader the book cause: the library doesn't have book with this id: {} ", newJournal.getBookId());
                throw new BooksIssueJnCreationException(
                        String.format("Cant give this reader the book cause: the library doesn't have book with this id: %s ", newJournal.getBookId()));
            }

        } catch (Exception e) {
            log.error("Cant give this reader the book cause: the library doesn't have book with this id: {}... ", newJournal, e);
            throw new BooksIssueJnCreationException(e);
        }

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void returnBook(BooksIssueJn journalIssue) {

        BooksIssueJnEntity booksIssueJnEntity;
        Optional<BooksIssueJnEntity> booksIssueJnEntityO = booksIssueJnRepository
                .findAllByReaderIdAndBookId(journalIssue.getReaderId(), journalIssue.getBookId())
                .stream()
                .filter(book -> (book.getDateOfReturn() != null))
                .findFirst();
        Optional<BookEntity> book = bookRepository.findById(journalIssue.getBookId());

        try {
            if (booksIssueJnEntityO.isPresent()) {
                booksIssueJnEntity = BooksIssueJnEntity.builder()
                        .bookId(journalIssue.getBookId())
                        .readerId(journalIssue.getReaderId())
                        .dateOfIssue(journalIssue.getDateOfIssue())
                        .dateOfReturn(LocalDateTime.now())
                        .build();
            } else {
                log.error("Cant return book which you do not take id book: {} ", journalIssue.getBookId());
                throw new BooksIssueJnCreationException(
                        String.format("Cant return book which you do not take id book: %s ", journalIssue.getBookId()));

            }

//            booksIssueJnEntity = booksIssueJnRepository
//                    .findByReaderIdAndBookId(journalIssue.getReaderId(), journalIssue.getBookId())
//                    .map(booksIssueJnEntity1 -> {
//                        booksIssueJnEntity1.setDateOfReturn(LocalDateTime.now());
//                        return booksIssueJnEntity1;
//                    });

            booksIssueJnRepository.saveAndFlush(booksIssueJnEntity);

            book.map(bookEntity -> {
                bookEntity.setNumberOfOCopies(bookEntity.getNumberOfOCopies() + 1);
                return bookEntity;
            });


        } catch (
                Exception e) {
            log.error(e.getMessage());
            throw new BooksIssueJnCreationException(e);
        }

    }


}
