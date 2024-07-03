package ru.ftc.library.api.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;
import ru.ftc.library.api.model.Sex;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@Table(name = "BOOKS_ISSUE_JN")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(BooksIssueJnEntity.BooksIssueJnKey.class)
public class BooksIssueJnEntity{

    @Id
    @Column(name = "READER_ID")
    private Long readerId;

    @Id
    @Column(name = "BOOK_ID")
    private Long bookId;

    @Id
    @Column(name = "DATE_OF_ISSUE")
    private LocalDate dateOfIssue;

    @Column(name = "DATE_OF_RETURN")
    private LocalDate dateOfReturn;

    public class BooksIssueJnKey implements Serializable {

        private Long readerId;

        private Long bookId;

        private LocalDate dateOfIssue;
    }

}
