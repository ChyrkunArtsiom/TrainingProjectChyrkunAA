package by.chyrkun.training.service.command.role;

import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Role;
import by.chyrkun.training.service.receiver.RoleReceiver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class GetRolesCommandTest {
    private RequestContent requestContent;

    @Mock private RoleReceiver receiver;

    @InjectMocks private GetRolesCommand command;

    @BeforeEach
    void setUp() {
        requestContent = new RequestContent();
        requestContent.setSessionAttribute("lang", "en_US");
    }

    @Test
    void execute() {
        Role role1 = new Role(1, "teacher");
        Role role2 = new Role(2, "student");
        List<Role> roles = List.of(role1, role2);
        Mockito.lenient().when(receiver.getAll()).thenReturn(roles);
        command.execute(requestContent);
        assertEquals(roles, requestContent.getRequestAttributes().get("roles"));
    }
}