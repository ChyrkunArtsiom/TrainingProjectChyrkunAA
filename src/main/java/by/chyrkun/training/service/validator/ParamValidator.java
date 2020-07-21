package by.chyrkun.training.service.validator;

/**
 * Class for validating different parameters
 */
public class ParamValidator {
    /**
     * Checks if all strings are present and not empty.
     *
     * @param params the strings
     * @return {@code true} if all strings are present
     */
    public static boolean isPresent(String... params){
        for (String param : params) {
            if (param == null || param.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
