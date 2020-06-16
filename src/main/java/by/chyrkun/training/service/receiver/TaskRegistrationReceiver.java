package by.chyrkun.training.service.receiver;

import by.chyrkun.training.dao.impl.TaskRegistrationDAO;

public class TaskRegistrationReceiver {
    public boolean isPerformed(int task_id, int student_id) {
        TaskRegistrationDAO taskRegistrationDAO = new TaskRegistrationDAO();
        try {
            return taskRegistrationDAO.isPerformed(task_id, student_id);
        }finally {
            taskRegistrationDAO.close();
        }
    }
}
