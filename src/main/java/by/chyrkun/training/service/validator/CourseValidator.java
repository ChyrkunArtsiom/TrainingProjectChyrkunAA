package by.chyrkun.training.service.validator;

public class CourseValidator {
    public static boolean isCourseNameValid(String course) {
        if (course.matches("^[a-zA-Z]+$")) {
            return true;
        }
        return false;
    }
}
