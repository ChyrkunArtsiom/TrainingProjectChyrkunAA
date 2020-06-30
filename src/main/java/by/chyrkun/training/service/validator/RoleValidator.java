package by.chyrkun.training.service.validator;

import by.chyrkun.training.dao.impl.RoleDAO;
import by.chyrkun.training.model.Role;
import by.chyrkun.training.service.receiver.RoleReceiver;

import java.util.Optional;

public class RoleValidator {

    public static boolean isPresent(int role_id) {
        RoleReceiver roleReceiver = new RoleReceiver();
        Role role = roleReceiver.getById(role_id);
        return role != null;
    }

}
