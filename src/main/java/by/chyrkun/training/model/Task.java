package by.chyrkun.training.model;

import java.time.LocalDate;
import java.util.Objects;

public class Task extends Entity {
    private int id;
    private String name;
    private LocalDate startdate;
    private LocalDate deadline;
    private Course course;

    public Task(String name, LocalDate startdate, LocalDate deadline, Course course){
        this.name = name;
        this.startdate = startdate;
        this.deadline = deadline;
        this.course = new Course(course);
    }

    public Task(Integer id, String name, LocalDate startdate, LocalDate deadline, Course course){
        this.id = id;
        this.name = name;
        this.startdate = startdate;
        this.deadline = deadline;
        this.course = new Course(course);
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

    public LocalDate getStartdate() {
        return startdate;
    }

    public void setStartdate(LocalDate startdate) {
        this.startdate = startdate;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
                name.equals(task.name) &&
                Objects.equals(startdate, task.startdate) &&
                Objects.equals(deadline, task.deadline) &&
                course.equals(task.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, startdate, deadline, course);
    }

    @Override
    public String toString() {
        return "Task id: " + this.id + ", name: " + this.name + ", course: " + this.course.getName() +
                ", startdate: " + this.startdate + ", deadline: " + this.deadline;
    }
}
