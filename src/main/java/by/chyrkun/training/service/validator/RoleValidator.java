package by.chyrkun.training.service.validator;

import by.chyrkun.training.dao.impl.RoleDAO;
import by.chyrkun.training.model.Role;

import java.util.Optional;

public class RoleValidator {
    public static Role getById(int id){
        Optional<Role> role;
        RoleDAO roleDAO = new RoleDAO();
        role = roleDAO.getEntityById(id);
        if (role.isPresent()){
            return role.get();
        }
        else {
            return null;
        }
    }
}
