package ru.ftc.library.api.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@Table(name = "BOOKS_ISSUE_JN")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(BooksIssueJnEntity.BooksIssueJnKey.class)
public class BooksIssueJnEntity implements Persistable<BooksIssueJnEntity.BooksIssueJnKey> {

    @Id
    @Column(name = "READER_ID")
    private Long readerId;

    @Id
    @Column(name = "BOOK_ID")
    private Long bookId;

    @Id
    @Column(name = "DATE_OF_ISSUE")
    private LocalDateTime dateOfIssue;

    @Column(name = "DATE_OF_RETURN")
    private LocalDateTime dateOfReturn;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "READER_ID", referencedColumnName = "id", updatable = false, insertable = false)
    private ReaderEntity readerEntity;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "BOOK_ID", referencedColumnName = "id", updatable = false, insertable = false)
    private BookEntity bookEntity;

    @Override
    public BooksIssueJnKey getId() {
        return null;
    }

    @Override
    public boolean isNew() {
        return true;
    }

    public static class BooksIssueJnKey implements Serializable {

        private Long readerId;

        private Long bookId;

        private LocalDateTime dateOfIssue;
    }


}
