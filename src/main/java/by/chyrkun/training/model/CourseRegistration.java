package by.chyrkun.training.model;

import java.util.Objects;

public class CourseRegistration extends Entity {
    private int id;
    private Course course;
    private User student;

    public CourseRegistration(CourseRegistration courseRegistration){
        setId(courseRegistration.getId());
        setCourse(courseRegistration.getCourse());
        setStudent(courseRegistration.getStudent());
    }

    public CourseRegistration(int id, Course course, User student){
        setId(id);
        setCourse(course);
        setStudent(student);
    }

    public CourseRegistration(Course course, User student){
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
        if (this == o){
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
    public int hashCode() {
        return Objects.hash(id, course, student);
    }
}
