package by.chyrkun.training.service.validator;

import java.time.LocalDate;

/**
 * Class for validating {@link by.chyrkun.training.model.Task} objects.
 */
public class TaskValidator {
    /**
     * Validates {@link by.chyrkun.training.model.Task} object's name.
     *
     * @param name the string of task name
     * @return {@code true} if name matches requirements
     */
    public static boolean isNameValid(String name) {
        return  name.matches("^[\\w\\W]{1,100}$");
    }

    /**
     * Is date valid boolean.
     *
     * @param startdate the LocalDate of strating date
     * @param deadline  the LocalDate of deadline
     * @return {@code true} if starting date is not before today's date and before deadline
     */
    public static boolean isDateValid(LocalDate startdate, LocalDate deadline) {
        if (startdate.isBefore(LocalDate.now())) {
            return false;
        }else {
            return !deadline.isBefore(startdate);
        }
    }
}
