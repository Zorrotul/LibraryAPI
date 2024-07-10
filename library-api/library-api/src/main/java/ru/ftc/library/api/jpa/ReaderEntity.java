package ru.ftc.library.api.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;
import ru.ftc.library.api.model.Sex;

import java.time.LocalDate;

@Data
@Builder
@Table(name = "READERS")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ReaderEntity implements Persistable<Long> {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "READERS_SQ")
    @SequenceGenerator(name = "READERS_SQ", sequenceName = "READERS_SQ", allocationSize = 1)
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "PATRONYMIC")
    private String patronymic;

    @Enumerated(EnumType.STRING)
    @Column(name = "SEX")
    private Sex sex;

    @Column(name = "BIRTHDAY")
    private LocalDate birthday;

    @Override
    public boolean isNew() {
        return true;
    }
}
