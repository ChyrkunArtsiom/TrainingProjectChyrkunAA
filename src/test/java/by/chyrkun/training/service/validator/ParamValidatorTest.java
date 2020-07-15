package by.chyrkun.training.service.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParamValidatorTest {
    @Test
    void testParamValidation() {
        assertTrue(ParamValidator.isPresent("String 1", "String 2"));
        assertFalse(ParamValidator.isPresent("String 1", ""));
    }
}
