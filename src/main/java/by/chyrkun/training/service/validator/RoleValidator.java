package by.chyrkun.training.service.validator;

import by.chyrkun.training.model.Role;
import by.chyrkun.training.service.receiver.RoleReceiver;

public class RoleValidator {

    public static boolean isPresent(int role_id) {
        RoleReceiver roleReceiver = new RoleReceiver();
        Role role = roleReceiver.getById(role_id);
        return role != null;
    }

}
