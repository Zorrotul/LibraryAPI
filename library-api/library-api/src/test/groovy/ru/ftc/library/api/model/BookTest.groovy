package ru.ftc.library.api.model

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.validation.ConstraintViolation
import jakarta.validation.Validator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate


@SpringBootTest
class BookTest extends Specification {

    @Autowired
    ObjectMapper objectMapper

    @Autowired
    Validator validator

    def "Success. serialize book"() {
        setup:
        Book book = Book.builder()
                .title("1")
                .dateOfOPublication(LocalDate.of(2024, 7, 15))
                .numberOfOCopies(3)
                .build()

        when:
        String bookStr = objectMapper.writeValueAsString(book)

        then:
        bookStr == "{\"title\":\"1\",\"dateOfOPublication\":\"2024-07-15\",\"numberOfOCopies\":3}"
    }

    @Unroll
    def "Failed. invalid book"() {
        setup:
        Book book = Book.builder()
                .title(title)
                .dateOfOPublication(dateOfOPublication)
                .numberOfOCopies(numberOfOCopies)
                .build()

        when:
        Set<ConstraintViolation<Book>> violations = validator.validate(book)

        then:
        violations.size() == 1
        violations.first().getMessage() == expectedField

        where:
        title | dateOfOPublication        | numberOfOCopies | expectedField
        null  | LocalDate.of(2024, 7, 15) | 10              | "title не должно быть пустым"
        ""    | LocalDate.of(2024, 7, 15) | 10              | "title не должно быть пустым"
        " "   | LocalDate.of(2024, 7, 15) | 10              | "title не должно быть пустым"
        "1"   | null                      | 10              | "не должно равняться null"
        "1"   | LocalDate.of(2024, 7, 15) | null            | "не должно равняться null"

    }

}
