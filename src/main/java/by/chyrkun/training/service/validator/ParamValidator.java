package by.chyrkun.training.service.validator;

public class ParamValidator {
    public static boolean isPresent(String... params){
        for (String param : params) {
            if (param == null || param.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
