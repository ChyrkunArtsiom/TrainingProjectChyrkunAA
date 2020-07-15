package by.chyrkun.training.service.validator;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskValidatorTest {
    @Test
    void testNameValidation() {
        assertTrue(TaskValidator.isNameValid("Valid name"));
        assertFalse(TaskValidator.isNameValid(""));
    }

    @Test
    void testDateValidation() {
        assertTrue(TaskValidator.isDateValid(LocalDate.now(), LocalDate.now().plusDays(1)));
        assertFalse(TaskValidator.isDateValid(LocalDate.now().minusDays(1), LocalDate.now()));
    }
}
