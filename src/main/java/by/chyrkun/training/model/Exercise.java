package by.chyrkun.training.model;

import java.util.Objects;

/**
 * Class for exercise entity.
 */
public class Exercise extends Entity {
    /** An id. */
    private int id;

    /** A {@link Task} object of task. */
    private Task task;

    /** An {@link User} object of student. */
    private User student;

    /** A grade */
    private int grade;

    /** A string for review */
    private String review;

    /**
     * Constructor with all fields.
     *
     * @param id      the id
     * @param task    the {@link Task} object for task
     * @param student the {@link User} object for student
     * @param grade   the grade
     * @param review  the string of review
     */
    public Exercise(int id, Task task, User student, int grade, String review) {
        setId(id);
        setTask(task);
        setStudent(student);
        setGrade(grade);
        setReview(review);
    }

    /**
     * Constructor without id.
     *
     * @param task    the {@link Task} object of task
     * @param student the {@link User} object of student
     * @param grade   the grade
     * @param review  the string of review
     */
    public Exercise(Task task, User student, int grade, String review) {
        setTask(task);
        setStudent(student);
        setGrade(grade);
        setReview(review);
    }

    /**
     * Constructor for creating not reviewed exercise.
     *
     * @param task    the {@link Task} object of task
     * @param student the {@link User} object of student
     */
    public Exercise(Task task, User student) {
        setTask(task);
        setStudent(student);
    }

    /**
     * Constructor for creating a clone of other exercise.
     *
     * @param exercise the {@link Exercise} object of exercise
     */
    public Exercise(Exercise exercise) {
        setId(exercise.getId());
        setTask(exercise.getTask());
        setStudent(exercise.getStudent());
        setGrade(exercise.getGrade());
        setReview(exercise.getReview());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = new Task(task);
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = new User(student);
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, task, student, grade , review);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()){
            return false;
        }
        Exercise that = (Exercise) obj;
        return id == that.id &&
                task.equals(that.task) &&
                student.equals(that.student) &&
                grade == that.grade &&
                review.equals(that.review);
    }

    @Override
    public String toString() {
        return "Exercise №" + id + " of task №" + task.getId() +
                " done by " + student.getFirstname() + " " + student.getSecondname();
    }
}
