package ru.ftc.library.api.jpa;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

@Data
@Builder
@Table(name = "AUTHORS")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AuthorEntity implements Persistable<Long> {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUTHORS_SQ")
    @SequenceGenerator(name = "AUTHORS_SQ", sequenceName = "AUTHORS_SQ", allocationSize = 1)
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "PATRONYMIC")
    private String patronymic;

    @Override
    public boolean isNew() {
        return true;
    }
}
