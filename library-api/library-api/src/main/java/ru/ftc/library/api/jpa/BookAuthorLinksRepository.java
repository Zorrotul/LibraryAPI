package ru.ftc.library.api.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookAuthorLinksRepository extends JpaRepository<BookAuthorLinksEntity, BookAuthorLinksEntity.BookAuthorLinksKey> {
    Optional<BookAuthorLinksEntity> findByBookIdAndAuthorId(Long bookId, Long authorId);
}
