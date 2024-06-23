package ru.ftc.library.api.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ftc.library.api.model.Sex;

import java.util.List;

public interface ReaderRepository extends JpaRepository<ReaderEntity, Long> {
    List<ReaderEntity> findAllBySex(Sex sex);
}
