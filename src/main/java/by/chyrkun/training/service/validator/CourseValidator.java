package by.chyrkun.training.service.validator;

public class CourseValidator {
    public static boolean isCourseNameValid(String course) {
        return course.matches("^[a-zA-Z]{1,45}$");
    }
}
