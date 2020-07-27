package by.chyrkun.training.service.receiver;

import by.chyrkun.training.dao.exception.TaskNotFoundDAOException;
import by.chyrkun.training.dao.exception.UserNotFoundDAOException;
import by.chyrkun.training.dao.impl.ExerciseDAO;
import by.chyrkun.training.model.Exercise;
import by.chyrkun.training.service.exception.TaskNotFoundServiceException;
import by.chyrkun.training.service.exception.UserNotFoundServiceException;

import java.util.List;
import java.util.Optional;

/**
 * The class-receiver which calls methods of {@link Exercise} objects.
 */
public class ExerciseReceiver {
    /**
     * Checks if given exercise of given student is performed.
     *
     * @param task_id    the task id
     * @param student_id the student id
     * @return {@code true} if exercise is performed
     */
    public boolean isPerformed(int task_id, int student_id) {
        ExerciseDAO exerciseDAO = new ExerciseDAO();
        try {
            return exerciseDAO.isPerformed(task_id, student_id);
        }finally {
            exerciseDAO.close();
        }
    }

    /**
     * Creates an exercise. Returns whether it was successful.
     *
     * @param exercise the {@link Exercise} object to create
     * @return whether it was successful
     * @throws UserNotFoundServiceException if user was not found
     * @throws TaskNotFoundServiceException if task was not found
     */
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

    /**
     * Gets an exercise by id.
     *
     * @param exercise_id the exercise id
     * @return the Optional of {@link Exercise}
     */
    public Exercise getById(int exercise_id) {
        Optional<Exercise> exercise;
        ExerciseDAO exerciseDAO = new ExerciseDAO();
        try {
            exercise = exerciseDAO.getEntityById(exercise_id);
            return exercise.orElse(null);
        }finally {
            exerciseDAO.close();
        }
    }

    /**
     * Updates course. Takes exercise to update. Returns old exercise if successful and null if failed.
     *
     * @param exercise the exercise
     * @return the old exercise
     */
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

    /**
     * Delete an exercise. Takes course id. Returns whether it was successful.
     *
     * @param exercise_id the exercise id
     * @return whether it was successful
     */
    public boolean delete(int exercise_id) {
        ExerciseDAO exerciseDAO = new ExerciseDAO();
        try {
            Optional<Exercise> task = exerciseDAO.getEntityById(exercise_id);
            return task.map(exerciseDAO::delete).orElse(false);
        }finally {
            exerciseDAO.close();
        }
    }

    /**
     * Gets an exercise by task and student.
     *
     * @param task_id    the task id
     * @param student_id the student id
     * @return the exercise
     */
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

    /**
     * Gets list of exercises by task.
     *
     * @param task_id the task id
     * @return the list of exercises
     */
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
