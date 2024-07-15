package ru.ftc.library.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.ftc.library.api.error.AuthorCreationException;
import ru.ftc.library.api.error.BookAuthorLinksCreationException;
import ru.ftc.library.api.error.BookCreationException;
import ru.ftc.library.api.error.NoSuchBookException;
import ru.ftc.library.api.jpa.BookEntity;
import ru.ftc.library.api.jpa.BookRepository;
import ru.ftc.library.api.model.AddBookRequest;
import ru.ftc.library.api.model.Book;
import ru.ftc.library.api.model.BookAuthorLinks;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookServiceBean implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final BookAuthorLinksService bookAuthorLinksService;


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addNewBookToLibrary(AddBookRequest newBook) {
        try {
            BookEntity bookEntity = bookRepository.findByTitleAndDateOfOPublication(
                            newBook.getTitle(),
                            newBook.getDateOfOPublication())
                    .map(book -> {
                        book.setNumberOfOCopies(book.getNumberOfOCopies() + newBook.getNumberOfOCopies());
                        return book;
                    })
                    .map(a -> {
                        log.info("addNewBookToLibrary book: {}", a);
                        return a;
                    })
                    .orElseGet(() -> BookEntity.builder()
                            .title(newBook.getTitle())
                            .dateOfOPublication(newBook.getDateOfOPublication())
                            .numberOfOCopies(newBook.getNumberOfOCopies())
                            .build());
            bookRepository.saveAndFlush(bookEntity);
        } catch (Exception e) {
            log.error("Cant create new book = {}, cause: {}", newBook, e.getMessage(), e);
            throw new BookCreationException(e);
        }

        try {
            Long bookId = bookRepository.findByTitleAndDateOfOPublication(
                    newBook.getTitle(),
                    newBook.getDateOfOPublication()).get().getId();
            log.info("book id = {}", bookId);
            List<Long> authorIds = newBook.getAuthors().stream()
                    .peek(a -> log.info("authors  = {}", a))
                    .map(author -> {
                        //Long id = -1L; //TODO как сделать нормально? как обрабатывать исключение в стриме?
                        try {
                            return authorService.findOrCreateNewAuthorAndGetId(author);
                        } catch (AuthorCreationException e) {
                            log.error(e.getMessage());
                            return -1L;
                        }
                        //return id;
                    })
                    .peek(a -> log.info("author Id = {}", a))
                    .peek(authorId -> {
                        try {
                            bookAuthorLinksService.createNewBookAuthorLink(new BookAuthorLinks(bookId, authorId));
                        } catch (BookAuthorLinksCreationException e) {
                            log.error("caught BookAuthorLinksCreationException");
                        }
                    }).toList();

            log.info(authorIds.toString());
        } catch (AuthorCreationException |
                 BookAuthorLinksCreationException e) {
            log.error("AuthorCreationException | BookAuthorLinksCreationException cause: {}", e.getMessage());
        } catch (
                Exception e) {
            log.error("Some authors exist = {}, cause: {}", newBook, e.getMessage(), e);
        }

    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Book getBookById(Long id) {
        Optional<BookEntity> bookEntity = bookRepository.findById(id);
        log.info("pass optional\n");
        log.info("id: {}", id);
        return bookEntity
                .map(b -> Book.builder()
                        .title(b.getTitle())
                        .dateOfOPublication(b.getDateOfOPublication())
                        .numberOfOCopies(b.getNumberOfOCopies())
                        .build())
                .orElseThrow(() -> new NoSuchBookException("Book with id " + id + " not found"));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void removeOneBookCopyById(Long bookId) {
        try {
            Optional<BookEntity> book = bookRepository.findById(bookId);
            book.map(bookEntity -> {
                bookEntity.setNumberOfOCopies(bookEntity.getNumberOfOCopies() - 1);
                bookRepository.saveAndFlush(bookEntity);
                return bookEntity;
            }).orElseThrow(() -> new BookCreationException("No such book"));
        } catch (DataAccessException e) {
            throw new BookCreationException(String.format("No copies of the book = %s left in library", bookId));//TODO кидать другое исключение
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void returnBookToLibrary(Long bookId) {
        try {
            Optional<BookEntity> book = bookRepository.findById(bookId);
            book.map(bookEntity -> {
                        bookEntity.setNumberOfOCopies(bookEntity.getNumberOfOCopies() + 1);
                        return bookEntity;
                    })
                    .ifPresent(bookRepository::saveAndFlush);
        } catch (DataAccessException e) {
            throw new BookCreationException(String.format("returnBookToLibrary<- Cant return book with id= %s to library", bookId));//TODO кидать другое исключение
        }
    }
}
