package by.chyrkun.training.model;

import java.util.Objects;

/**
 * Class for course entity.
 */
public class Course extends Entity {
    /** An id. */
    private int id;

    /** A string of name. */
    private String name;

    /** An {@link User} object of teacher. */
    private User teacher;

    /**
     * Constructor with all fields.
     *
     * @param id      the id
     * @param name    the string of name
     * @param teacher the {@link User} object of teacher
     */
    public Course(int id, String name, User teacher) {
        setId(id);
        setName(name);
        if (teacher != null) {
            setTeacher(teacher);
        }
    }

    /**
     * Constructor with name and teacher fields.
     *
     * @param name    the string of name
     * @param teacher the {@link User} object of teacher
     */
    public Course(String name, User teacher) {
        setName(name);
        setTeacher(teacher);
    }

    /**
     * Constructor for creating a clone of other course.
     *
     * @param course the {@link Course} object of course
     */
    public Course(Course course) {
        setId(course.getId());
        setName(course.getName());
        setTeacher(course.getTeacher());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = new User(teacher);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Course course = (Course) o;
        return id == course.id &&
                Objects.equals(name, course.name) &&
                Objects.equals(teacher, course.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, teacher);
    }

    @Override
    public String toString() {
        return "Course id: " + this.id + ", name: " + this.name +
                ", teacher: " + this.teacher.getFirstname() + " " + this.teacher.getSecondname();
    }
}
