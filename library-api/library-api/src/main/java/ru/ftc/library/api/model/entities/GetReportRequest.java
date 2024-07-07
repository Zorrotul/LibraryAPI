package ru.ftc.library.api.model.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetReportRequest {

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFrom;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateTo;

    @AssertTrue(message = "Дебич")
    public boolean isDateToValid(){
        return dateFrom.isBefore(dateTo);
    }

}