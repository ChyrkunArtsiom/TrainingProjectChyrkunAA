package by.chyrkun.training.service.command.user;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Role;
import by.chyrkun.training.model.User;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.RoleReceiver;
import by.chyrkun.training.service.receiver.UserReceiver;
import by.chyrkun.training.service.resource.MessageManager;
import by.chyrkun.training.service.resource.PageManager;
import by.chyrkun.training.service.util.InputSanitizer;
import by.chyrkun.training.service.util.PasswordHasher;
import by.chyrkun.training.service.validator.ParamValidator;
import by.chyrkun.training.service.validator.UserValidator;

import java.util.Arrays;

/**
 * The class-command for updating user. Implements {@link Command}.
 */
public class UpdateUserCommand implements Command {
    private static final String ERROR_MESSAGE = "errorMessage";
    private UserReceiver userReceiver;
    private RoleReceiver roleReceiver;
    private CommandResult result;

    /**
     * Instantiates a new Update user command.
     */
    public UpdateUserCommand() {
        userReceiver = new UserReceiver();
        roleReceiver = new RoleReceiver();
        result = new CommandResult();
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = setLang(requestContent);
        String login = requestContent.getRequestParameters().get("login")[0];
        String old_username = requestContent.getRequestParameters().get("old_username")[0];
        String firstname = requestContent.getRequestParameters().get("firstname")[0];
        String secondname = requestContent.getRequestParameters().get("secondname")[0];
        String user_id = requestContent.getRequestParameters().get("user_id")[0];
        String session_user_id = String.valueOf(requestContent.getSessionAttributes().get("user_id"));
        String role_name;
        String old_password;
        String new_password = null;
        boolean admin = isAdmin(requestContent);
        first: try {
            if (admin) {
                role_name = requestContent.getRequestParameters().get("role_name")[0];
                if (!ParamValidator.isPresent(login, firstname, secondname, role_name)) {
                    requestContent.setSessionAttribute(ERROR_MESSAGE, messages.getMessage("lineIsEmpty"));
                    break first;
                }
            }
            else {
                role_name = requestContent.getSessionAttributes().get("role").toString();
                old_password = requestContent.getRequestParameters().get("old_password")[0];
                if (!requestContent.getRequestParameters().get("new_password")[0].isEmpty()) {
                    new_password = requestContent.getRequestParameters().get("new_password")[0];
                }

                if (!ParamValidator.isPresent(login, old_password, firstname, secondname)) {
                    requestContent.setSessionAttribute(ERROR_MESSAGE, messages.getMessage("lineIsEmpty"));
                    break first;
                }

                User user = userReceiver.getByLogin(old_username);

                if ((user == null) || (!Arrays.equals(user.getPassword(), PasswordHasher.getHash(old_password)))) {
                    requestContent.setSessionAttribute(ERROR_MESSAGE, messages.getMessage("loginDataIsNotValid"));
                    break first;
                }

                if (new_password != null && !UserValidator.isPasswordValid(new_password)) {
                    requestContent.setSessionAttribute(ERROR_MESSAGE, messages.getMessage("passwordIsNotValid"));
                    break first;
                }
            }

            Role role = roleReceiver.getByName(role_name);
            if (role == null) {
                requestContent.setSessionAttribute(ERROR_MESSAGE, messages.getMessage("roleNotFound"));
                break first;
            }
            if (!UserValidator.isLoginValid(login)) {
                requestContent.setSessionAttribute(ERROR_MESSAGE, messages.getMessage("usernameIsNotValid"));
                break first;
            }
            if (!UserValidator.isNameValid(firstname) || !UserValidator.isNameValid(secondname)) {
                requestContent.setSessionAttribute(ERROR_MESSAGE, messages.getMessage("nameIsNotValid"));
                break first;
            }

            User user;
            user = new User(Integer.parseInt(user_id), login, firstname, secondname, role);

            if (new_password != null) {
                user.setPassword(PasswordHasher.getHash(new_password));
            }

            if (!old_username.equals(user.getLogin()) && userReceiver.getByLogin(user.getLogin()) != null) {
                requestContent.setSessionAttribute(ERROR_MESSAGE, messages.getMessage("suchUserAlreadyExists"));
                break first;
            }

            if (userReceiver.update(user) != null && user_id.equals(session_user_id)) {
                requestContent.setSessionAttribute("userName", user.getLogin());
                requestContent.setSessionAttribute("role", user.getRole().getName());
                requestContent.setSessionAttribute("role_id", user.getRole().getId());
                requestContent.deleteSessionAttribute(ERROR_MESSAGE);
            }
        }finally {
            result.setPage(PageManager.getPage("shortpath.page.profile") + "/" + user_id);
            result.setResponseType(CommandResult.ResponseType.REDIRECT);
        }
        return result;
    }

    private boolean isAdmin(RequestContent requestContent) {
        if ((requestContent.getSessionAttributes().get("role")!= null) &&
                (requestContent.getSessionAttributes().get("role").equals("admin"))) {
            return true;
        }
        return false;
    }
}
