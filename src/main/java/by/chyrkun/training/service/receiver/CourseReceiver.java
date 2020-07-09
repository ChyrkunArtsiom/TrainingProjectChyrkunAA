package by.chyrkun.training.service.receiver;

import by.chyrkun.training.dao.exception.EntityNotFoundDAOException;
import by.chyrkun.training.dao.impl.CourseDAO;
import by.chyrkun.training.model.Course;
import by.chyrkun.training.service.exception.EntityNotFoundServiceException;

import java.util.List;
import java.util.Optional;

public class CourseReceiver {
    public boolean create(Course course) throws EntityNotFoundServiceException {
        CourseDAO courseDAO = new CourseDAO();
        try {
            return courseDAO.create(course);
        }catch (EntityNotFoundDAOException ex) {
            throw new EntityNotFoundServiceException(ex.getMessage(), ex);
        }finally {
            courseDAO.close();
        }
    }

    public boolean delete(int id) {
        CourseDAO courseDAO = new CourseDAO();
        try {
            Optional<Course> user = courseDAO.getEntityById(id);
            return user.map(courseDAO::delete).orElse(false);
        }finally {
            courseDAO.close();
        }
    }

    public Course getById(int id) {
        Optional<Course> course;
        CourseDAO courseDAO = new CourseDAO();
        try {
            course = courseDAO.getEntityById(id);
            return course.orElse(null);
        }finally {
            courseDAO.close();
        }
    }

    public List<Course> getByTeacher(int teacher_id, int page) {
        List<Course> courses;
        CourseDAO courseDAO = new CourseDAO();
        try {
            courses = courseDAO.getByTeacher(teacher_id, page);
            return courses;
        }finally {
            courseDAO.close();
        }
    }

    public List<Course> getByStudent(int student_id, boolean chosen) {
        List<Course> courses;
        CourseDAO courseDAO = new CourseDAO();
        try {
            courses = courseDAO.getCoursesByStudent(student_id, chosen);
            return courses;
        }finally {
            courseDAO.close();
        }
    }

    public Course update(Course course) {
        Optional<Course> optional;
        CourseDAO courseDAO = new CourseDAO();
        try {
            optional = courseDAO.update(course);
            return optional.orElse(null);
        }finally {
            courseDAO.close();
        }
    }
}
