package ru.ftc.library.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.ftc.library.api.error.BookCreationException;
import ru.ftc.library.api.error.BooksIssueJnCreationException;
import ru.ftc.library.api.jpa.BooksIssueJnEntity;
import ru.ftc.library.api.jpa.BooksIssueJnRepository;
import ru.ftc.library.api.model.UpdateJnIssueRequest;

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
    private final BookService bookService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void createNewBooksIssueJn(UpdateJnIssueRequest newJournal) {

        BooksIssueJnEntity booksIssueJnEntity;
        try {
            bookService.removeOneBookCopyById(newJournal.getBookId());

            booksIssueJnEntity = BooksIssueJnEntity.builder()
                    .bookId(newJournal.getBookId())
                    .readerId(newJournal.getReaderId())
                    .dateOfIssue(LocalDateTime.now())
                    .build();

            booksIssueJnRepository.saveAndFlush(booksIssueJnEntity);
        } catch (BookCreationException e) {
            log.error(e.getMessage(), newJournal);
            throw new BooksIssueJnCreationException(e);
        } catch (Exception e) {
            log.error("Cant give this reader the book cause: the library doesn't have book with this id: {}... ", newJournal, e);
            throw new BooksIssueJnCreationException(e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void returnBook(UpdateJnIssueRequest journalIssue) {

        Optional<BooksIssueJnEntity> booksIssueJnEntity = booksIssueJnRepository
                .findAllByReaderIdAndBookId(journalIssue.getReaderId(), journalIssue.getBookId())
                .stream()
                .filter(journal -> (journal.getDateOfReturn() == null))
                .findFirst();

        bookService.returnBookToLibrary(journalIssue.getBookId());

        try {
            booksIssueJnEntity.ifPresentOrElse(issie -> {
                        issie.setDateOfReturn(LocalDateTime.now());
                    },
                    () -> {
                        throw new BooksIssueJnCreationException(
                                String.format("Cant return book which you do not take id book: %s ", journalIssue.getBookId()));
                    }
            );
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
