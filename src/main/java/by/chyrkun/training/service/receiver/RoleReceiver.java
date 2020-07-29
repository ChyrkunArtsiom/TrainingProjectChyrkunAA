package by.chyrkun.training.service.receiver;

import by.chyrkun.training.dao.impl.RoleDAO;
import by.chyrkun.training.model.Role;

import java.util.List;
import java.util.Optional;

/**
 * The class-receiver which calls methods of {@link Role} objects.
 */
public class RoleReceiver {
    /**
     * Gets a role by id.
     *
     * @param id the role id
     * @return the Optional of {@link Role}
     */
    public Role getById(int id) {
        Optional<Role> role;
        RoleDAO roleDAO = new RoleDAO();
        try {
            role = roleDAO.getEntityById(id);
            return role.orElse(null);
        }finally {
            roleDAO.close();
        }
    }

    /**
     * Gets a role by name.
     *
     * @param name the string for name of role
     * @return the Optional of {@link Role}
     */
    public Role getByName(String name) {
        Optional<Role> role;
        RoleDAO roleDAO = new RoleDAO();
        try {
            role = roleDAO.getRoleByName(name);
            return role.orElse(null);
        }finally {
            roleDAO.close();
        }
    }

    /**
     * Gets all roles.
     *
     * @return all roles
     */
    public List<Role> getAll() {
        RoleDAO roleDAO = new RoleDAO();
        try {
            return roleDAO.getAll();
        }finally {
            roleDAO.close();
        }
    }
}
