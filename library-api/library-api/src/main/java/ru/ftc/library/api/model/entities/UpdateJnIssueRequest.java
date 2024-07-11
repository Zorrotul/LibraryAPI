package ru.ftc.library.api.model.entities;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateJnIssueRequest {

    @NotNull
    private Long bookId;

    @NotNull
    private Long readerId;

}
