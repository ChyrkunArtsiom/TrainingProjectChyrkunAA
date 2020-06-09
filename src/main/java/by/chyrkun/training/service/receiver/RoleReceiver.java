package by.chyrkun.training.service.receiver;

import by.chyrkun.training.dao.impl.RoleDAO;
import by.chyrkun.training.model.Role;

import java.util.List;
import java.util.Optional;

public class RoleReceiver {

    public Role getById(int id){
        Optional<Role> role;
        RoleDAO roleDAO = new RoleDAO();
        try {
            role = roleDAO.getEntityById(id);
            if (role.isPresent()){
                return role.get();
            }
            else {
                return null;
            }
        }finally {
            roleDAO.close();
        }
    }

    public List<Role> getAll() {
        RoleDAO roleDAO = new RoleDAO();
        try {
            List<Role> roles = roleDAO.getAll();
            return roles;
        }finally {
            roleDAO.close();
        }
    }
}
