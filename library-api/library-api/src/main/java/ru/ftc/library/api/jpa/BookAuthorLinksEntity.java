package ru.ftc.library.api.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;

@Data
@Builder
@Table(name = "BOOK_AUTHOR_LINKS")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(BookAuthorLinksEntity.BookAuthorLinksKey.class)
public class BookAuthorLinksEntity implements Persistable<BookAuthorLinksEntity.BookAuthorLinksKey> {

    @Id
    @Column(name = "BOOK_ID")
    private Long bookId;

    @Id
    @Column(name = "AUTHOR_ID")
    private Long authorId;


    public static class BookAuthorLinksKey implements Serializable {
        private Long bookId;
        private Long authorId;
    }

    @Override
    public BookAuthorLinksKey getId() {
        return null;
    }

    @Override
    public boolean isNew() {
        return true;
    }

}
