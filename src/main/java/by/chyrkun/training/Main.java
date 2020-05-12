package by.chyrkun.training;

import by.chyrkun.training.dao.AbstractDAO;
import by.chyrkun.training.dao.db.impl.ConnectionPoolImpl;
import by.chyrkun.training.dao.impl.CourseDAO;
import by.chyrkun.training.dao.impl.RoleDAO;
import by.chyrkun.training.dao.impl.TaskDAO;
import by.chyrkun.training.dao.impl.UserDAO;
import by.chyrkun.training.model.*;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        CourseDAO course = new CourseDAO();
        TaskDAO taskDAO = new TaskDAO();
        Course course1 = course.getEntityById(1).get();
        Task task = new Task("testtask", LocalDate.now(), null, course1);
        //(String name, LocalDate startdate, LocalDate deadline, Course course)
        System.out.println(taskDAO.getEntityById(1).get());
        ConnectionPoolImpl.getInstance().shutdown();
    }
}
