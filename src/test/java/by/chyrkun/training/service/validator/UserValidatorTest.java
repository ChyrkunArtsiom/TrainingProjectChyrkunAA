package by.chyrkun.training.service.validator;

import by.chyrkun.training.model.Role;
import by.chyrkun.training.model.User;
import by.chyrkun.training.service.resource.MessageManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {
    @Test
    void testUserValidation() {
        MessageManager manager = MessageManager.en_US;
        StringBuffer message = new StringBuffer();
        Role role = new Role(1, "test");
        User valid_user = new User(1, "Login123", "Firstname", "Surname", role);
        User invalid_user = new User(1, "Login_123", "Firstname", "Surname", role);
        assertTrue(UserValidator.isUserValid(valid_user, message, manager));
        assertFalse(UserValidator.isUserValid(invalid_user, message, manager));
        assertEquals(manager.getMessage("usernameIsNotValid"), message.toString());
    }
}
