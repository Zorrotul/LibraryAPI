package ru.ftc.library.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.ftc.library.api.error.BooksIssueJnCreationException;
import ru.ftc.library.api.jpa.*;
import ru.ftc.library.api.model.entities.BooksIssueJnResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BooksIssueJnServiceBean implements BooksIssueJnService {

    private final BooksIssueJnRepository booksIssueJnRepository;
    private final ReaderRepository readerRepository;
    private final BookRepository bookRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void createNewBooksIssueJn(BooksIssueJnResponse newJournal) {

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
    public void returnBook(BooksIssueJnResponse journalIssue) {

        Optional<BooksIssueJnEntity> booksIssueJnEntity = booksIssueJnRepository
                .findAllByReaderIdAndBookId(journalIssue.getReaderId(), journalIssue.getBookId())
                .stream()
                .filter(journal -> (journal.getDateOfReturn() == null))
                .findFirst();

        Optional<BookEntity> book = bookRepository.findById(journalIssue.getBookId());

        try {
            booksIssueJnEntity.ifPresentOrElse(issie -> {
                        issie.setDateOfReturn(LocalDateTime.now());
                    },
                    () -> {
                        throw new BooksIssueJnCreationException(
                                String.format("Cant return book which you do not take id book: %s ", journalIssue.getBookId()));
                    }
            );

            booksIssueJnRepository.saveAndFlush(booksIssueJnEntity.get());
            book.map(bookEntity -> {
                        bookEntity.setNumberOfOCopies(bookEntity.getNumberOfOCopies() + 1);
                        return bookEntity;
                    })
                    .ifPresent(bookRepository::saveAndFlush);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BooksIssueJnCreationException(e);
        }

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<BooksIssueJnEntity> getJournal(LocalDate dateFrom, LocalDate dateTo) {
        return booksIssueJnRepository.findAllByInterval(dateFrom.atStartOfDay(), dateTo.atStartOfDay());
    }


}
