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
        try{
            return courseDAO.create(course);
        }catch (EntityNotFoundDAOException ex){
            throw new EntityNotFoundServiceException(ex.getMessage(), ex);
        }finally {
            courseDAO.close();
        }
    }

    public boolean delete(int id) {
        CourseDAO courseDAO = new CourseDAO();
        try {
            Optional<Course> user = courseDAO.getEntityById(id);
            if (user.isPresent()) {
                return courseDAO.delete(user.get());
            }
            return false;
        }finally {
            courseDAO.close();
        }
    }

    public Course getById(int id){
        Optional<Course> course;
        CourseDAO courseDAO = new CourseDAO();
        try {
            course = courseDAO.getEntityById(id);
            if (course.isPresent()) {
                return course.get();
            }
            else {
                return null;
            }
        }finally {
            courseDAO.close();
        }
    }

    public List<Course> getByTeacher(int teacher_id) {
        List<Course> teachers;
        CourseDAO courseDAO = new CourseDAO();
        try {
            teachers = courseDAO.getByTeacher(teacher_id);
            return teachers;
        }finally {
            courseDAO.close();
        }
    }

    public Course update(Course course) {
        Optional<Course> optional;
        CourseDAO courseDAO = new CourseDAO();
        try {
            optional = courseDAO.update(course);
            if (optional.isPresent()) {
                return optional.get();
            }
            else {
                return null;
            }
        }finally {
            courseDAO.close();
        }
    }
}
