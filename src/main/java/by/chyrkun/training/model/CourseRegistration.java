package by.chyrkun.training.model;

import java.util.Objects;

/**
 * Class for course registration entity.
 */
public class CourseRegistration extends Entity {
    /** An id. */
    private int id;

    /** A {@link Course} object for course. */
    private Course course;

    /** An {@link User} object for student. */
    private User student;

    /**
     * Constructor for creating a clone of other course registration.
     *
     * @param courseRegistration the {@link CourseRegistration} object for course registration
     */
    public CourseRegistration(CourseRegistration courseRegistration) {
        setId(courseRegistration.getId());
        setCourse(courseRegistration.getCourse());
        setStudent(courseRegistration.getStudent());
    }

    /**
     * Constructor with all fields.
     *
     * @param id      the id
     * @param course  the {@link Course} object for course
     * @param student the {@link User} object for student
     */
    public CourseRegistration(int id, Course course, User student) {
        setId(id);
        setCourse(course);
        setStudent(student);
    }

    /**
     * Constructor with course and student fields.
     *
     * @param course  the {@link Course} object for course
     * @param student the {@link User} object for student
     */
    public CourseRegistration(Course course, User student) {
        setCourse(course);
        setStudent(student);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = new Course(course);
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = new User(student);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        CourseRegistration that = (CourseRegistration) o;
        return id == that.id &&
                course.equals(that.course) &&
                student.equals(that.student);
    }

    @Override
    public String toString() {
        return "Course registration №" + id +
                " by student " + student.getFirstname() + " " + student.getSecondname() +
                " for  course " + course.getName();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, course, student);
    }
}
