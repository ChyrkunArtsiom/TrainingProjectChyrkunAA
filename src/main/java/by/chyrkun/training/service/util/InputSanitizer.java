package by.chyrkun.training.service.util;

public class InputSanitizer {
    public static String sanitize(String input) {
        input = input.replace("<", "&lt;");
        input = input.replace(">", "&gt;");
        return input;
    }
}
