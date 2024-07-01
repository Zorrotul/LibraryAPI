package ru.ftc.library.api.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Table(name = "BOOK_AUTHOR_LINKS")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(BookAuthorLinksKey.class)
public class BookAuthorLinksEntity{

    @Id
    @Column(name = "BOOK_ID")
    private Long bookId;

    @Id
    @Column(name = "AUTHOR_ID")
    private Long authorId;

}
