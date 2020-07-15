package by.chyrkun.training.service.validator;

import java.time.LocalDate;

public class TaskValidator {
    public static boolean isNameValid(String name) {
        return  name.matches("^[\\w\\W]{1,100}$");
    }

    public static boolean isDateValid(LocalDate startdate, LocalDate deadline) {
        if (startdate.isBefore(LocalDate.now())) {
            return false;
        }else {
            return !deadline.isBefore(startdate);
        }
    }
}
