package by.chyrkun.training.model;

import java.util.Objects;

public class TaskRegistration extends Entity {
    private int id;
    private Task task;
    private User student;
    private int grade;
    private String review;

    public TaskRegistration(int id, Task task, User student, int grade, String review) {
        setId(id);
        setTask(task);
        setStudent(student);
        setGrade(grade);
        setReview(review);
    }

    public TaskRegistration(Task task, User student, int grade, String review) {
        setTask(task);
        setStudent(student);
        setGrade(grade);
        setReview(review);
    }

    public TaskRegistration(Task task, User student) {
        setTask(task);
        setStudent(student);
    }

    public TaskRegistration(TaskRegistration taskRegistration) {
        setId(taskRegistration.getId());
        setTask(taskRegistration.getTask());
        setStudent(taskRegistration.getStudent());
        setGrade(taskRegistration.getGrade());
        setReview(taskRegistration.getReview());
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
        TaskRegistration that = (TaskRegistration) obj;
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
