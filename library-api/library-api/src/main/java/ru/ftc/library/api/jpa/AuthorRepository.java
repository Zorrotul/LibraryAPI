package ru.ftc.library.api.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
    @Query("SELECT a FROM AuthorEntity a")
    List<AuthorEntity> getAuthors();
}
