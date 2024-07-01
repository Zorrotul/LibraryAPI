package ru.ftc.library.api.model.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ftc.library.api.model.Sex;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BooksIssueJn {

    @NotNull
    private Long bookId;

    @NotNull
    private Long readerId;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfIssue;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfReturn;

}
