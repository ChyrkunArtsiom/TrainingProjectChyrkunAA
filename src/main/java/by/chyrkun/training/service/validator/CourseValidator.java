package by.chyrkun.training.service.validator;

/**
 * Class for validating {@link by.chyrkun.training.model.Course} objects.
 */
public class CourseValidator {
    /**
     * Validates {@link by.chyrkun.training.model.Course} object's name.
     *
     * @param course the string of course name
     * @return {@code true} if name matches requirements
     */
    public static boolean isCourseNameValid(String course) {
        return course.matches("^[\\w\\W]{1,45}$");
    }
}
