package by.chyrkun.training.service.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CourseValidatorTest {
    @Test
    void testValidation() {
        String valid_course_name = "Course";
        String invalid_course_name = "More than forty five characters is presented here";
        assertTrue(CourseValidator.isCourseNameValid(valid_course_name));
        assertFalse(CourseValidator.isCourseNameValid(invalid_course_name));
    }
}
