package by.chyrkun.training.service.validator;

public class CourseValidator {
    public static boolean isCourseNameValid(String course) {
        return course.matches("^[\\w\\W]{1,45}$");
    }
}
