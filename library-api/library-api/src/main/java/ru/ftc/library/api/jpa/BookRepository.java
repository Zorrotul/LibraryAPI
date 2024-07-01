package ru.ftc.library.api.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    //Optional<BookEntity> findBookById(Long id);
    Optional<BookEntity> findByTitleAndDateOfOPublication(String title, LocalDate dateOfOPublication);
}
