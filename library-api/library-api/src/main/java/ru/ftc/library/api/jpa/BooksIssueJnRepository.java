package ru.ftc.library.api.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface BooksIssueJnRepository extends JpaRepository<BooksIssueJnEntity, BooksIssueJnEntity.BooksIssueJnKey> {
    List<BooksIssueJnEntity> findAllByReaderIdAndBookId(Long readerId, Long bookId);

    @Query("SELECT jn, r, b " +
            "FROM BooksIssueJnEntity jn " +
            "JOIN ReaderEntity r ON r.id = jn.readerId " +
            "JOIN BookEntity b ON b.id = jn.bookId " +
            "WHERE jn.dateOfIssue >= :dateFrom AND jn.dateOfIssue <= :dateTo")
        // hql, Page
    List<BooksIssueJnEntity> findAllByInterval(LocalDateTime dateFrom, LocalDateTime dateTo);

}
