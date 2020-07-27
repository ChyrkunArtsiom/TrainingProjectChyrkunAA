package by.chyrkun.training.service.receiver;

import by.chyrkun.training.dao.exception.EntityNotFoundDAOException;
import by.chyrkun.training.dao.impl.CourseDAO;
import by.chyrkun.training.model.Course;
import by.chyrkun.training.service.exception.UserNotFoundServiceException;

import java.util.List;
import java.util.Optional;

/**
 * The class-receiver which calls methods of {@link CourseDAO} objects.
 */
public class CourseReceiver {
    /** The number of rows on per page in the table to show. */
    private final static int ROWS_PER_PAGE = 3;

    /**
     * Creates a course. Returns whether it was successful.
     *
     * @param course the {@link Course} object to create
     * @return whether it was successful
     * @throws UserNotFoundServiceException if user was not found
     */
    public boolean create(Course course) throws UserNotFoundServiceException {
        CourseDAO courseDAO = new CourseDAO();
        try {
            return courseDAO.create(course);
        }catch (EntityNotFoundDAOException ex) {
            throw new UserNotFoundServiceException(ex.getMessage(), ex);
        }finally {
            courseDAO.close();
        }
    }

    /**
     * Deletes a course. Takes course id. Returns whether it was successful.
     *
     * @param id the course id
     * @return whether it was successful
     */
    public boolean delete(int id) {
        CourseDAO courseDAO = new CourseDAO();
        try {
            Optional<Course> user = courseDAO.getEntityById(id);
            return user.map(courseDAO::delete).orElse(false);
        }finally {
            courseDAO.close();
        }
    }

    /**
     * Gets a course by id.
     *
     * @param id the course id
     * @return the Optional of {@link Course}
     */
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

    /**
     * Gets list of courses by teacher's id depending on page number.
     *
     * @param teacher_id the teacher id
     * @param page       the number of page
     * @return the list of courses
     */
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

    /**
     * Gets list of courses by student's id depending on registration status and page number.
     *
     * @param student_id the student id
     * @param chosen     {@code true} if return registered courses, {@code false} if not registered
     * @param page       the number of page
     * @return the list of courses
     */
    public List<Course> getByStudent(int student_id, boolean chosen, int page) {
        List<Course> courses;
        CourseDAO courseDAO = new CourseDAO();
        try {
            courses = courseDAO.getCoursesByStudent(student_id, chosen, page);
            return courses;
        }finally {
            courseDAO.close();
        }
    }

    /**
     * Updates course. Takes course to update. Returns old course if successful and null if failed.
     *
     * @param course the course to update
     * @return the old course if successful and null if failed
     */
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

    /**
     * Gets number of pages to show all courses for teacher.
     *
     * @param teacher_id the teacher id
     * @return the number of pages to show
     */
    public int getPagesForTeacher(int teacher_id) {
        CourseDAO courseDAO = new CourseDAO();
        try {
            return (int)Math.ceil((double)courseDAO.getCountByTeacher(teacher_id)/ROWS_PER_PAGE);
        }finally {
            courseDAO.close();
        }
    }

    /**
     * Gets number of pages to show all courses for teacher.
     *
     * @param student_id the student id
     * @param chosen     {@code true} if return registered courses, {@code false} if not registered
     * @return the number of pages to show
     */
    public int getPagesForStudent(int student_id, boolean chosen) {
        CourseDAO courseDAO = new CourseDAO();
        try {
            return (int)Math.ceil((double)courseDAO.getCountByStudent(student_id, chosen)/ROWS_PER_PAGE);
        }finally {
            courseDAO.close();
        }
    }
}
