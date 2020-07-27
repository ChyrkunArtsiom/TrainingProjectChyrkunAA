package by.chyrkun.training.service.receiver;

import by.chyrkun.training.dao.exception.CourseNotFoundDAOException;
import by.chyrkun.training.dao.exception.UserNotFoundDAOException;
import by.chyrkun.training.dao.impl.CourseRegistrationDAO;
import by.chyrkun.training.model.CourseRegistration;
import by.chyrkun.training.service.exception.CourseNotFoundServiceException;
import by.chyrkun.training.service.exception.UserNotFoundServiceException;

/**
 * The class-receiver which calls methods of {@link CourseRegistration} objects.
 */
public class CourseRegistrationReceiver {
    /**
     * Creates a course_registration. Returns whether it was successful.
     *
     * @param courseRegistration the {@link CourseRegistration} object to create
     * @return whether it was successful
     * @throws CourseNotFoundServiceException if course was not found
     * @throws UserNotFoundServiceException   if user was not found
     */
    public boolean create(CourseRegistration courseRegistration) throws CourseNotFoundServiceException, UserNotFoundServiceException {
        CourseRegistrationDAO courseRegistrationDAO = new CourseRegistrationDAO();
        try {
            return courseRegistrationDAO.create(courseRegistration);
        }catch (CourseNotFoundDAOException ex){
            throw new CourseNotFoundServiceException(ex.getMessage(), ex);
        }catch (UserNotFoundDAOException ex) {
            throw new UserNotFoundServiceException(ex.getMessage(), ex);
        }finally {
            courseRegistrationDAO.close();
        }
    }

    /**
     * Checks if given course is registered by given student
     *
     * @param course_id  the course id
     * @param student_id the student id
     * @return {@code true} if course is registered by the student
     */
    public boolean isCourseRegistered(int course_id, int student_id) {
        CourseRegistrationDAO courseRegistrationDAO = new CourseRegistrationDAO();
        try {
            return courseRegistrationDAO.isCourseRegistered(course_id, student_id);
        }finally {
            courseRegistrationDAO.close();
        }
    }
}
