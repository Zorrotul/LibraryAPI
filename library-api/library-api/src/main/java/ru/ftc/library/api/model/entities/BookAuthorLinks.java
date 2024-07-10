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
public class BookAuthorLinks {//сделать автомат зоздание
    //удалить
    @NotNull
    private Long bookId;

    @NotNull
    private Long authorId;
}
