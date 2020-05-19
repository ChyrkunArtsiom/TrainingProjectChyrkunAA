package by.chyrkun.training;

import by.chyrkun.training.dao.AbstractDAO;
import by.chyrkun.training.dao.db.impl.ConnectionPoolImpl;
import by.chyrkun.training.dao.impl.CourseDAO;
import by.chyrkun.training.dao.impl.RoleDAO;
import by.chyrkun.training.dao.impl.TaskDAO;
import by.chyrkun.training.dao.impl.UserDAO;
import by.chyrkun.training.model.*;
import by.chyrkun.training.service.invoker.Invoker;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        String content = "test";
        Invoker invoker = new Invoker();
        invoker.invokeCommand(content);
    }
}
