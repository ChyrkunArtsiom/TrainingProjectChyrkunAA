import dao.AbstractDAO;
import dao.impl.RoleDAO;
import model.Entity;
import model.Role;

public class Main {

    public static void main(String[] args) {
        Entity role = new Role("Testrole");
        AbstractDAO roleDAO = new RoleDAO();
        roleDAO.create(role);
        System.out.println("test");
    }
}
