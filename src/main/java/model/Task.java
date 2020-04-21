package model;

import java.time.LocalDate;

public class Task extends Entity {
    private int id;
    private String name;
    private int course_id;
    private LocalDate startdate;
    private LocalDate deadline;
}
