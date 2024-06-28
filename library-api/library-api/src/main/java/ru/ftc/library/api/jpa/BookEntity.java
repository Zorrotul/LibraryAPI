package ru.ftc.library.api.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import java.time.LocalDate;

@Data
@Builder
@Table(name = "BOOKS")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BookEntity implements Persistable<Long> {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOKS_SQ")
    @SequenceGenerator(name = "BOOKS_SQ", sequenceName = "BOOKS_SQ", allocationSize = 1)
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DATE_OF_PUBLICATION")
    private LocalDate dateOfOPublication;

    @Column(name = "NUMBER_OF_COPIES")
    private Integer numberOfOCopies;


    @Override
    public boolean isNew() {
        return true;
    }
}