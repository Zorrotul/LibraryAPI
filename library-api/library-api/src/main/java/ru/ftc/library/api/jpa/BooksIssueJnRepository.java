package ru.ftc.library.api.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BooksIssueJnRepository extends JpaRepository<BooksIssueJnEntity, BooksIssueJnEntity.BooksIssueJnKey> {
    List<BooksIssueJnEntity> findAllByReaderIdAndBookId(Long readerId, Long bookId);
}
