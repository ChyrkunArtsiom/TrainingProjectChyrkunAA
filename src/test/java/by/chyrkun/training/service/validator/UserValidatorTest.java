package by.chyrkun.training.service.validator;

import by.chyrkun.training.model.User;
import by.chyrkun.training.service.resource.MessageManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {
    @Test
    void testUserValidation() {
        MessageManager manager = MessageManager.en_US;
        StringBuffer message = new StringBuffer();
        User valid_user = new User("Login123", "Firstname", "Surname", "Password");
        User invalid_user = new User("Login123",  "123", "Firstname", "Secondname");
        assertTrue(UserValidator.isUserValid(valid_user, message, manager));
        assertFalse(UserValidator.isUserValid(invalid_user, message, manager));
        assertEquals(manager.getMessage("passwordIsNotValid"), message.toString());
    }
}
