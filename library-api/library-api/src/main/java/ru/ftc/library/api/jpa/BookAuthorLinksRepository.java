package ru.ftc.library.api.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface BookAuthorLinksRepository extends JpaRepository<BookAuthorLinksEntity, BookAuthorLinksEntity.BookAuthorLinksKey> {
}
