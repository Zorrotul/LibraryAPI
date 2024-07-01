package ru.ftc.library.api.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ftc.library.api.model.Sex;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BooksIssueJnRepository extends JpaRepository<ReaderEntity, BooksIssueJnKey> {
}
