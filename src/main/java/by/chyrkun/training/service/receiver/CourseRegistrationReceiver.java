package by.chyrkun.training.service.receiver;

import by.chyrkun.training.dao.exception.CourseNotFoundDAOException;
import by.chyrkun.training.dao.exception.UserNotFoundDAOException;
import by.chyrkun.training.dao.impl.CourseRegistrationDAO;
import by.chyrkun.training.model.CourseRegistration;
import by.chyrkun.training.service.exception.CourseNotFoundServiceException;
import by.chyrkun.training.service.exception.UserNotFoundServiceException;

public class CourseRegistrationReceiver {
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

    public boolean isCourseRegistered(int course_id, int student_id) {
        CourseRegistrationDAO courseRegistrationDAO = new CourseRegistrationDAO();
        try {
            return courseRegistrationDAO.isCourseRegistered(course_id, student_id);
        }finally {
            courseRegistrationDAO.close();
        }
    }
}
