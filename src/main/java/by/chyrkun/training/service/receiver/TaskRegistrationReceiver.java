package by.chyrkun.training.service.receiver;

import by.chyrkun.training.dao.exception.TaskNotFoundDAOException;
import by.chyrkun.training.dao.exception.UserNotFoundDAOException;
import by.chyrkun.training.dao.impl.ExerciseDAO;
import by.chyrkun.training.model.Exercise;
import by.chyrkun.training.service.exception.TaskNotFoundServiceException;
import by.chyrkun.training.service.exception.UserNotFoundServiceException;

import java.util.List;
import java.util.Optional;

public class TaskRegistrationReceiver {
    public boolean isPerformed(int task_id, int student_id) {
        ExerciseDAO exerciseDAO = new ExerciseDAO();
        try {
            return exerciseDAO.isPerformed(task_id, student_id);
        }finally {
            exerciseDAO.close();
        }
    }

    public boolean create(Exercise exercise) throws UserNotFoundServiceException, TaskNotFoundServiceException {
        ExerciseDAO exerciseDAO = new ExerciseDAO();
        try {
            return exerciseDAO.create(exercise);
        }catch (TaskNotFoundDAOException ex){
            throw new TaskNotFoundServiceException(ex.getMessage(), ex);
        }catch (UserNotFoundDAOException ex) {
            throw new UserNotFoundServiceException(ex.getMessage(), ex);
        }finally {
            exerciseDAO.close();
        }
    }

    public Exercise getById(int taskRegistration_id) {
        Optional<Exercise> exercise;
        ExerciseDAO exerciseDAO = new ExerciseDAO();
        try {
            exercise = exerciseDAO.getEntityById(taskRegistration_id);
            return exercise.orElse(null);
        }finally {
            exerciseDAO.close();
        }
    }

    public Exercise update(Exercise exercise) {
        Optional<Exercise> optional;
        ExerciseDAO exerciseDAO = new ExerciseDAO();
        try {
            optional = exerciseDAO.update(exercise);
            return optional.orElse(null);
        }finally {
            exerciseDAO.close();
        }
    }

    public boolean delete(int exercise_id) {
        ExerciseDAO exerciseDAO = new ExerciseDAO();
        try {
            Optional<Exercise> task = exerciseDAO.getEntityById(exercise_id);
            return task.map(exerciseDAO::delete).orElse(false);
        }finally {
            exerciseDAO.close();
        }
    }

    public boolean deleteByTaskStudent(int task_id, int student_id) {
        ExerciseDAO exerciseDAO = new ExerciseDAO();
        try {
            Optional<Exercise> task = exerciseDAO.getExerciseByTaskStudent(task_id, student_id);
            return task.map(exerciseDAO::delete).orElse(false);
        }finally {
            exerciseDAO.close();
        }
    }

    public Exercise getByTaskStudent(int task_id, int student_id) {
        Optional<Exercise> exercise;
        ExerciseDAO exerciseDAO = new ExerciseDAO();
        try {
            exercise = exerciseDAO.getExerciseByTaskStudent(task_id, student_id);
            return exercise.orElse(null);
        }finally {
            exerciseDAO.close();
        }
    }

    public List<Exercise> getAllByTask(int task_id) {
        List<Exercise> registrations;
        ExerciseDAO exerciseDAO = new ExerciseDAO();
        try {
            registrations = exerciseDAO.getAllByTask(task_id);
            return registrations;
        }finally {
            exerciseDAO.close();
        }
    }
}
